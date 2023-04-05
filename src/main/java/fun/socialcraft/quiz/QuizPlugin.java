package fun.socialcraft.quiz;

import com.comphenix.protocol.ProtocolLibrary;
import fun.socialcraft.quiz.commands.Commandanswer;
import fun.socialcraft.quiz.commands.Commandstartquiz;
import fun.socialcraft.quiz.config.Configurations;
import fun.socialcraft.quiz.listeners.ChatListener;
import fun.socialcraft.quiz.listeners.PacketListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class QuizPlugin extends JavaPlugin {

    private QuizManager quizManager;

    @Override
    public void onEnable() {
        // Регистрация всех викторин
        loadQuizzes();

        // Команды
        registerCommands();

        // Слушаетль
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(this));
        getServer().getPluginManager().registerEvents(new ChatListener(quizManager), this);
    }

    // Команды
    public void registerCommands() throws NullPointerException {
        this.getCommand("startquiz").setExecutor(new Commandstartquiz(quizManager));
        Commandanswer commandanswer = new Commandanswer(quizManager);
        getCommand("answer").setExecutor(commandanswer);
        getCommand("answer").setTabCompleter(commandanswer);
    }

    @Override
    public void onDisable() {
    }

    private void loadQuizzes() {
        //List<String> quizzes = List.of();

        Configurations quizConfigs = new Configurations(this, "rules.yml");

        quizConfigs.get("rules.yml");
        quizManager = new QuizManager(quizConfigs);
    }

    public QuizManager getQuizManager() {
        return quizManager;
    }
}
