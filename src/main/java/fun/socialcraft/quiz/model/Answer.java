package fun.socialcraft.quiz.model;

public class Answer {

    private final Question question;
    private final int answerIndex;

    public Answer(Question question, int answerIndex) {
        this.question = question;
        this.answerIndex = answerIndex;
    }

    public boolean isCorrect() {
        if (question.getOptions().size() <= answerIndex) return false;
        return question.getOptions().get(answerIndex).isCorrect;
    }
}
