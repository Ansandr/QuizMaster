package fun.socialcraft.quiz.commands;

import fun.socialcraft.quiz.QuizManager;
import fun.socialcraft.quiz.model.QuizHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static fun.socialcraft.quiz.utils.CharUtil.getNumberFromChar;

public class Commandanswer implements TabExecutor {

    private QuizManager quizManager;

    public Commandanswer(QuizManager quizManager) {
        this.quizManager = quizManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) return true;

        Player p = (Player) sender;
        if (!(quizManager.isInQuiz(p.getUniqueId()))) return true;

        QuizHandler quizHandler = quizManager.getQuizHandler(p.getUniqueId());
        quizHandler.next(getNumberFromChar(args[0].charAt(0)));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 0) return List.of("A", "B", "C", "D");
        return null;
    }


}
