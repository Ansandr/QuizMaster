package fun.socialcraft.quiz.commands;

import fun.socialcraft.quiz.QuizManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Commandstartquiz implements CommandExecutor {

    private final QuizManager manager;

    public Commandstartquiz(QuizManager manager) {
        this.manager = manager;
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
}
