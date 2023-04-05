package fun.socialcraft.quiz.model;

import java.util.List;

public class Quiz {

    private final List<Question> questions;

    private final String name;
    private final String title;
    private final String description;

    private final List<String> successCommands;
    private final List<String> failCommands;
    private final int passScore;


    public Quiz(String name, String title, String description,
                List<String> successCommands, List<String> failCommands,
                List<Question> questions, int passScore) {
        this.questions = questions;
        this.name = name;
        this.title = title;
        this.description = description;
        this.successCommands = successCommands;
        this.failCommands = failCommands;
        this.passScore = passScore;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<String> getSuccessCommands() {
        return successCommands;
    }

    public List<String> getFailCommands() {
        return failCommands;
    }

    public int getPassingScore() {
        return passScore;
    }
}
