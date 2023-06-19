package fun.socialcraft.quiz.listeners;

import fun.socialcraft.quiz.QuizManager;
import fun.socialcraft.quiz.QuizPlugin;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Arrays;
import java.util.List;

public class ChatListener implements Listener {

    private final QuizPlugin plugin;
    private final List<String> commands = Arrays.asList("quiz", "answer", "leavequiz");

    public ChatListener(QuizPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        if (plugin.getQuizManager().isInQuiz(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
//            e.originalMessage();
        }
    }



    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (plugin.getQuizManager().isInQuiz(e.getPlayer().getUniqueId())) {
            if (commands.contains(getCommandLabel(e.getMessage()))) return; // если разрешенная команда, выполнить
            e.getPlayer().sendMessage("Нельзя пользоватся командами во время теста");
            e.setCancelled(true);
        }
    }

    public static String getCommandLabel(String cmd) {
        String[] parts = cmd.split(" ");
        if (parts[0].startsWith("/"))
            parts[0] = parts[0].substring(1);
        return parts[0];
    }
}
