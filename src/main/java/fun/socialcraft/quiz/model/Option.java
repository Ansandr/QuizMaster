package fun.socialcraft.quiz.model;

public class Option {

    public String text;
    public boolean isCorrect;

    public Option(String optionText, boolean isCorrect) {
        this.text = optionText;
        this.isCorrect = isCorrect;
    }
}
