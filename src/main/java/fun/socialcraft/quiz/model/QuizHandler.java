package fun.socialcraft.quiz.model;

import fun.socialcraft.quiz.QuizManager;
import fun.socialcraft.quiz.config.Localization;
import fun.socialcraft.quiz.utils.CharUtil;
import org.apache.commons.lang3.CharUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static fun.socialcraft.quiz.utils.AdventureUtil.formatAdventure;
import static fun.socialcraft.quiz.utils.CharUtil.getCharFromNumber;

public class QuizHandler {


    private final Player p;
    private final Quiz quiz;
    private final QuizManager manager;

    private final List<Answer> results;

    // Номер текущего вопроса
    private int current;
    int correctAnswersNumber = 0;

    public QuizHandler(Quiz quiz, Player player, QuizManager manager) {
        this.quiz = quiz;
        this.p = player;
        this.manager = manager;

        results = new ArrayList<>();
        current = 0;
    }



    // отправить первый вопрос
    public void start() {
        p.sendMessage(formatAdventure(quiz.getTitle()));
        p.sendMessage(formatAdventure(quiz.getDescription()));

        sendQuestion(current);
    }

    public void sendQuestion(int number) {
        Question q = quiz.getQuestions().get(number);

        p.sendMessage(formatAdventure(String.format("<b><color:#fff3c9>%1$1d)</color></b> %2$1s", number + 1, q.text)));

        for (int i = 0; i < q.getOptions().size(); i++) {
           p.sendMessage(formatAdventure(String.format("<red>%1$c</red> %2$s", getCharFromNumber(i+1), q.getOptions().get(i).text)));
        }
    }

    // Переход к следующему вопросу
    public void next(int input) {
        // Записываеи ответ
        Answer answer = new Answer(getCurrentQuestion(), input);
        results.add(answer);

        if (answer.isCorrect()) correctAnswersNumber++;

        // идем дальше
        current++;
        // если больше нет вопрос - заканчиваем
        if (current >= quiz.getQuestions().size()) {
            end();
            return;
        }

        sendQuestion(current);
    }

    // подсчитать результаты
    public void end() {
        p.sendMessage(formatAdventure(String.format("<gray>[<green>i</green>]</gray> Тест завершен. Твой результат %1$d/%2$d", correctAnswersNumber,  quiz.getQuestions().size())));

        if (correctAnswersNumber >= quiz.getPassingScore()) {
            p.sendMessage("Успех");
            quiz.getSuccessCommands().forEach(command ->
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", p.getName()))
            );
        } else {
            p.sendMessage(formatAdventure(String.format("<gray>[<gold>i</gold>]</gray> Вы не достигли проходного бала <dark_gray>(<aqua>%1$d</aqua>)</dark_gray>. <color:#ed1e11>Тест провален</color>", quiz.getPassingScore())));
            quiz.getFailCommands().forEach(command ->
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", p.getName()))
            );
        }

        manager.endQuiz(this);
    }

    public Question getCurrentQuestion() {
        return quiz.getQuestions().get(current);
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Player getPlayer() {
        return p;
    }
}
