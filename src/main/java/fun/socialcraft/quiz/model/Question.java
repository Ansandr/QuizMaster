package fun.socialcraft.quiz.model;

import java.util.List;

public class Question {

    public String text;
    private final List<Option> options;

    public Question(String questionText, List<Option> optionList) {
        this.text = questionText;
        this.options = optionList;
    }

    public List<Option> getOptions() {
        return options;
    }

    public Option getCorrectOption() {
        return options.stream().filter(answer -> answer.isCorrect).findFirst().get();
    }
}
