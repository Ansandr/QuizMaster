package fun.socialcraft.quiz.commands;

import fun.socialcraft.quiz.QuizPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class Commandquiz implements TabExecutor {

    private final QuizPlugin plugin;

    public Commandquiz(QuizPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("Команда недосутпна");
            return true;
        }

        if (args.length < 1) {
            return true;
        }

        if ("reload".equals(args[0])) {
            plugin.reloadPlugin();
            sender.sendMessage("Плагин перезагружен");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) return null;

        return List.of("reload");
    }
}
