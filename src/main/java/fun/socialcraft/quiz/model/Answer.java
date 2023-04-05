package fun.socialcraft.quiz.model;

public class Answer {

    private Question question;
    private int answerIndex;

    public Answer(Question question, int answerIndex) {
        this.question = question;
        this.answerIndex = answerIndex;
    }

    public boolean isCorrect() {
        return question.getOptions().get(answerIndex).isCorrect;
    }
}
