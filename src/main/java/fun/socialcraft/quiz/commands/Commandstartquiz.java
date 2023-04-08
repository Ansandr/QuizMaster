package fun.socialcraft.quiz.commands;

import fun.socialcraft.quiz.QuizManager;
import fun.socialcraft.quiz.QuizPlugin;
import fun.socialcraft.quiz.model.Quiz;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Commandstartquiz implements TabExecutor {

    private final QuizManager manager;
    private final List<String> quizList;

    public Commandstartquiz(QuizManager manager) {
        this.manager = manager;
        quizList = manager.getQuizList().stream().map(Quiz::getName).toList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if (manager.isInQuiz(p.getUniqueId())) {
            p.sendMessage("Тест уже идет");
            return true;
        }

        manager.startQuiz(args[0], ((Player) sender));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return quizList;
        return null;
    }
}
