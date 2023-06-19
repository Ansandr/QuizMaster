package fun.socialcraft.quiz.config;

import com.google.common.collect.Lists;
import dev.dejvokep.boostedyaml.YamlDocument;
import fun.socialcraft.quiz.QuizPlugin;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Configurations {

    private final Map<String, YamlDocument> configurations = new HashMap<>();
    private List<String> configurationNames;

    private final File dataFolder;
    private final QuizPlugin plugin;

    public Configurations(QuizPlugin plugin, String... configurationNames) {
        this.plugin = plugin;
        this.configurationNames = Lists.newArrayList(configurationNames);
        this.dataFolder = new File(plugin.getDataFolder(), "quizzes");

        // Проверить существует ли папка
        if (!dataFolder.exists())
            dataFolder.mkdirs();

        loadConfigurations();
    }

    // Создать файл
    private File generateFile(File folder, String name) throws IOException {
        File file = new File(folder, name);

        if (!file.exists()) { // Файла не существует
            file.createNewFile(); // создать
            if (!saveResourceToFile("rules.yml", file)) {// Сохранить в файл содержание по умолчанию
                throw new IOException("Failed to create a default config for quiz: " + name + " Skipping loading quiz: " + name);
            }
        }
        return file;
    }

    public void loadConfigurations() {
        // create quiz example

        for (String configName : configurationNames) {
            if (this.configurations.containsKey(configName)) continue; // Пропускаем если объект конфига уже создан

            try {
                configurations.put(configName, loadConfiguration(configName));
            } catch (IOException e) {
                plugin.debug(Level.WARNING, e.getMessage());
            }
        }
    }

    public YamlDocument loadConfiguration(String quizFileName) throws IOException {
        //Проверка формата
        if (!quizFileName.endsWith(".yml")) {
            plugin.debug(Level.INFO, "Filename specified for quiz: " + quizFileName + " is not a .yml file!");
        }

        File configFile = generateFile(dataFolder, quizFileName); // Создать файл
        YamlDocument config = YamlDocument.create(configFile); /// Создать конфигурацию

        // Если файл пустой
        config.getKeys();
        if (config.getKeys().isEmpty()) {
            plugin.debug(Level.INFO, "Quiz config: " + quizFileName + " is empty! Creating default config example");
            saveResourceToFile("rules.yml", configFile); // заполнить
            config = YamlDocument.create(configFile);
        }

        return config;
    }


    public void reloadConfigurations(String... configurationNames) {
        configurations.clear();
        this.configurationNames = Lists.newArrayList(configurationNames);
        loadConfigurations();
    }

    private YamlDocument getValue(String configurationName) {
        return configurations.get(configurationName);
    }

    public YamlDocument get(String configurationName)  {
        return getValue(configurationName);
    }

    public File getFile(String configurationName)  {
        return getValue(configurationName).getFile();
    }

    public void save(String configurationName) {
        try {
            getValue(configurationName).save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getConfigurationsNames() {
        return configurationNames;
    }

    private boolean saveResourceToFile(String resource, File file) {
        try (OutputStream os = new FileOutputStream(file); // Открыть каналы чтения и записи
             InputStream is = this.plugin.getResource(resource)) {

            byte[] buffer = new byte[is.available()]; // буфер для ввода
            is.read(buffer); // Прочитать из плагина
            os.write(buffer); // Вписать в файл

            os.close();
            is.close(); // закрыть
            return true;
        } catch (NullPointerException|IOException ex) {
            ex.printStackTrace();
            plugin.debug(Level.SEVERE, "Failed to save default settings for:" + file.getName() + " from resource:" + resource);
            return false;
        }
    }
}
