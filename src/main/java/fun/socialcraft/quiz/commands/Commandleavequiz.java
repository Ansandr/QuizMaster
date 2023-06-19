package fun.socialcraft.quiz.commands;

import fun.socialcraft.quiz.QuizManager;
import fun.socialcraft.quiz.model.Quiz;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Commandleavequiz implements CommandExecutor {

    private final QuizManager manager;
    private final List<String> quizList;

    public Commandleavequiz(QuizManager manager) {
        this.manager = manager;
        quizList = manager.getQuizList().stream().map(Quiz::getName).toList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if (manager.endQuiz(p.getUniqueId()) != null) {
            sender.sendMessage("Тест отменен");
            return true;
        }

        return true;
    }
}