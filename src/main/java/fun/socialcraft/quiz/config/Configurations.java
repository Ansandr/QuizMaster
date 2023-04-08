package fun.socialcraft.quiz.config;

import com.google.common.collect.Lists;
import dev.dejvokep.boostedyaml.YamlDocument;
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
    private final Plugin plugin;

    public Configurations(Plugin plugin, String... configurationNames) {
        this.plugin = plugin;
        this.configurationNames = Lists.newArrayList(configurationNames);
        this.dataFolder = new File(plugin.getDataFolder(), "quizzes");

        loadConfigurations();
    }

    // Для начала нужно создать файл
    private File generateFile(File folder, String name) {
        File file = new File(folder, name);

        if (!file.exists()) {
            if (file.getParentFile().mkdirs())
                saveResourceToFile("rules.yml", file);
        }
        return file;
    }

    public void loadConfigurations() {
        // create quiz example

        for (String configName : configurationNames) {
            if (this.configurations.containsKey(configName)) continue; // Пропускаем если есть

            File configFile = generateFile(dataFolder, configName); // создаем файл

            try {
                YamlDocument config = YamlDocument.create(configFile);

                configurations.put(configName, config);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
        try (OutputStream os = new FileOutputStream(file);
             InputStream is = this.plugin.getResource(resource)){

            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            os.write(buffer);

            os.close();
            is.close();
            return true;
        } catch (NullPointerException|IOException ex) {
            ex.printStackTrace();
            plugin.getLogger().severe("Failed to save default settings for:" + file.getName() + " from resource:" + resource);
            return false;
        }
    }
}
