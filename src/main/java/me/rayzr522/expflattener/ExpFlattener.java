package me.rayzr522.expflattener;

import me.rayzr522.expflattener.command.CommandExpFlattener;
import me.rayzr522.expflattener.listeners.PlayerListener;
import me.rayzr522.expflattener.utils.MessageHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author Rayzr
 */
public class ExpFlattener extends JavaPlugin {
    private MessageHandler messages = new MessageHandler();

    @Override
    public void onEnable() {
        // Load configs
        reload();

        // Set up commands
        getCommand("expflattener").setExecutor(new CommandExpFlattener(this));

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    /**
     * (Re)loads all configs from the disk
     */
    public void reload() {
        saveDefaultConfig();
        reloadConfig();

        messages.load(getConfig("messages.yml"));
    }

    /**
     * If the file is not found and there is a default file in the JAR, it saves the default file to the plugin data folder first
     *
     * @param path The path to the config file (relative to the plugin data folder)
     * @return The {@link YamlConfiguration}
     */
    public YamlConfiguration getConfig(String path) {
        if (!getFile(path).exists() && getResource(path) != null) {
            saveResource(path, true);
        }
        return YamlConfiguration.loadConfiguration(getFile(path));
    }

    /**
     * @param path The path of the file (relative to the plugin data folder)
     * @return The {@link File}
     */
    public File getFile(String path) {
        return new File(getDataFolder(), path.replace('/', File.separatorChar));
    }

    /**
     * Translates a message from the language file.
     *
     * @param key     The key of the message to translate
     * @param objects The formatting objects to use
     * @return The formatted message
     */
    public String tr(String key, Object... objects) {
        return messages.tr(key, objects);
    }

    /**
     * Checks a target {@link CommandSender} for a given permission, and optionally sends a message if they don't.
     * <br>
     * This will automatically prefix any permission with the name of the plugin.
     *
     * @param target      The target {@link CommandSender} to check
     * @param permission  The permission to check, excluding the permission base (which is the plugin name)
     * @param sendMessage Whether or not to send a no-permission message to the target
     * @return Whether or not the target has the given permission
     */
    public boolean checkPermission(CommandSender target, String permission, boolean sendMessage) {
        String fullPermission = String.format("%s.%s", getName(), permission);

        if (!target.hasPermission(fullPermission)) {
            if (sendMessage) {
                target.sendMessage(tr("no-permission", fullPermission));
            }

            return false;
        }

        return true;
    }

    public int getLevelCap() {
        return  getConfig().getInt("level-cap", 1000);
    }

    public int getLevelRequirement() {
        return getConfig().getInt("level-requirement", 100);
    }
}
