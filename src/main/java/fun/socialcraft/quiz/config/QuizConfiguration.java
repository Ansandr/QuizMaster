package fun.socialcraft.quiz.config;

import com.google.common.io.Files;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.route.Route;
import fun.socialcraft.quiz.model.Option;
import fun.socialcraft.quiz.model.Question;
import fun.socialcraft.quiz.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizConfiguration {

    private YamlDocument config;


    public QuizConfiguration(YamlDocument quizConfiguration) {
        this.config = quizConfiguration;
    }

    public String getTitle() {
        return config.getString("title");
    }

    public String getDescription() {
        return config.getString("description");
    }

    public List<Question> readQuestions() {
        Section questionSection = config.getSection("questions");

        List<Question> questions = new ArrayList<>();

        for (Route questionRoute : questionSection.getRoutes(false)) {
            String questionText = questionSection.getString(questionRoute.add("question"));
            Section optionSection = questionSection.getSection(questionRoute.add("options"));

            List<Option> optionList = new ArrayList<>();

            for (Route optionRoute : optionSection.getRoutes(false)) {
                String optionText = optionSection.getString(optionRoute.add("text"));
                boolean isCorrect = optionSection.getBoolean(optionRoute.add("is_correct"));
                optionList.add(new Option(optionText, isCorrect));
            }

            questions.add(new Question(questionText, optionList));
        }

        return questions;
    }

    public List<String> getSuccessCommands() {
        return (List<String>) config.getList("command.success");
    }

    public List<String> getFailCommands() {
        return (List<String>) config.getList("command.fail");
    }

    public int getPassingScore() {
        return config.getInt("pass-score");
    }

    public Quiz loadQuiz() {
        return new Quiz(
                Files.getNameWithoutExtension(config.getFile().getName()),
                getTitle(),
                getDescription(),
                getSuccessCommands(),
                getFailCommands(),
                readQuestions(),
                getPassingScore()
        );
    }
}
