package fun.socialcraft.quiz;

import com.comphenix.protocol.ProtocolLibrary;
import fun.socialcraft.quiz.commands.Commandanswer;
import fun.socialcraft.quiz.commands.Commandquiz;
import fun.socialcraft.quiz.commands.Commandstartquiz;
import fun.socialcraft.quiz.config.Configurations;
import fun.socialcraft.quiz.listeners.ChatListener;
import fun.socialcraft.quiz.listeners.PacketListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class QuizPlugin extends JavaPlugin {

    private QuizManager quizManager;
    private Configurations quizConfigs;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Регистрация всех викторин
        loadQuizzes();

        // Команды
        registerCommands();

        // Слушаетль
        registerListeners();
    }

    // Команды
    public void registerCommands() throws NullPointerException {
        Commandstartquiz commandstartquiz = new Commandstartquiz(quizManager);
        getCommand("startquiz").setExecutor(commandstartquiz);
        getCommand("startquiz").setTabCompleter(commandstartquiz);
        Commandanswer commandanswer = new Commandanswer(quizManager);
        getCommand("answer").setExecutor(commandanswer);
        getCommand("answer").setTabCompleter(commandanswer);
        Commandquiz commandQuiz = new Commandquiz(this);
        getCommand("quiz").setExecutor(commandQuiz);
        getCommand("quiz").setTabCompleter(commandQuiz);
    }

    // Слушаетль
    public void registerListeners() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(this));
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }

    // загрузит настройки плагина и викторины
    // добавить проверку, если config.yml не изменен -> QuizManager#reloadConfiguration
    private void loadQuizzes() {
        List<String> quizList = getQuizList();

        quizConfigs = new Configurations(this, quizList.toArray(new String[0]));
        quizManager = new QuizManager(this); // quizConfigs
    }

    public List<String> getQuizList() {
        ConfigurationSection section = getConfig().getConfigurationSection("quizzes");

        List<String> quizzes = new ArrayList<>();
        debug("Quiz files list:");
        for (String key : section.getKeys(false)) {
            debug(key);
            quizzes.add(section.getString(key + ".file"));
        }

        return quizzes;
    }

    public QuizManager getQuizManager() {
        return quizManager;
    }

    public void reloadPlugin() {
        debug("Reloading config.yml");
        reloadConfig();

        debug("Reloading quiz files");
        loadQuizzes();

        registerCommands();
    }

    public Configurations getQuizConfigs() {
        return quizConfigs;
    }

    public void debug(String s) {
        logger.info(s);
    }
}
