package fun.socialcraft.quiz.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import fun.socialcraft.quiz.QuizPlugin;

public class PacketListener extends PacketAdapter {

    private final QuizPlugin plugin;

    public PacketListener(QuizPlugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT);
        this.plugin = plugin;
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        if (plugin.getQuizManager().isInQuiz(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
