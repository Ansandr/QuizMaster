package fun.socialcraft.quiz;

import fun.socialcraft.quiz.config.Configurations;
import fun.socialcraft.quiz.config.QuizConfiguration;
import fun.socialcraft.quiz.model.Quiz;
import fun.socialcraft.quiz.model.QuizHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static fun.socialcraft.quiz.utils.AdventureUtil.formatAdventure;

public class QuizManager {

    private final Configurations configurations;
    private final ArrayList<Quiz> quizzes = new ArrayList<>();
    private final Map<UUID, QuizHandler> runningQuizzes = new HashMap<>();

    public QuizManager(Configurations storage) {
        this.configurations = storage;
        loadQuizzes();
    }

    private void loadQuizzes() {
        for (String configName : configurations.getConfigurationsNames()) {
            QuizConfiguration quizConfig = new QuizConfiguration(configurations.get(configName));

            quizzes.add(quizConfig.loadQuiz());
        }
    }

    // quiz name = file name
    public Quiz getQuiz(String quizName) {
        return quizzes.stream()
                .filter(quiz -> quiz.getName().equals(quizName))
                .findFirst()
                .orElse(null);
    }

    public Quiz getQuiz(int id) {
        return quizzes.get(id);
    }

    public void startQuiz(String quizName, Player p) {
        Quiz quiz = getQuiz(quizName);

        if (quiz == null) {
            p.sendMessage(formatAdventure("<gray>[<red>i</red>]</gray> Теста не существует"));
            return;
        }

        QuizHandler quizHandler = new QuizHandler(quiz, p, this);
        p.sendMessage(formatAdventure("<gray>[<green>i</green>]</gray> Используй команду <aqua>/answer</aqua> для ответов"));
        runningQuizzes.put(p.getUniqueId(), quizHandler);
        quizHandler.start();
    }

    public void endQuiz(QuizHandler quiz) {
        runningQuizzes.remove(quiz.getPlayer().getUniqueId());
    }

    public boolean isInQuiz(UUID uuid) {
        return runningQuizzes.containsKey(uuid);
    }

    public QuizHandler getQuizHandler(UUID uniqueId) {
        return runningQuizzes.get(uniqueId);
    }
}
