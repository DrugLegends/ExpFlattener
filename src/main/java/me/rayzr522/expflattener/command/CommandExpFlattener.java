package me.rayzr522.expflattener.command;

import me.rayzr522.expflattener.ExpFlattener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandExpFlattener implements CommandExecutor, TabCompleter {
    private final ExpFlattener plugin;

    public CommandExpFlattener(ExpFlattener plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Stream.of("version", "reload", "help", "level-requirement", "level-cap")
                    .filter(sub -> sub.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.checkPermission(sender, "use", true)) {
            return true;
        }

        if (args.length < 1) {
            showUsage(sender);
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "version":
                sender.sendMessage(plugin.tr("command.expflattener.version", plugin.getName(), plugin.getDescription().getVersion()));
                break;
            case "reload":
                plugin.reload();
                sender.sendMessage(plugin.tr("command.expflattener.reloaded"));
                break;
            case "level-requirement":
                if (args.length < 2) {
                    sender.sendMessage(plugin.tr("command.expflattener.level-requirement.get", plugin.getLevelRequirement()));
                    break;
                }

                int newLevelRequirement;
                try {
                    newLevelRequirement = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(plugin.tr("error.command.not-number", args[1]));
                    break;
                }

                if (newLevelRequirement < 1) {
                    newLevelRequirement = 1;
                }

                plugin.getConfig().set("level-requirement", newLevelRequirement);
                plugin.saveConfig();

                sender.sendMessage(plugin.tr("command.expflattener.level-requirement.set", newLevelRequirement));
                break;
            case "level-cap":
                if (args.length < 2) {
                    sender.sendMessage(plugin.tr("command.expflattener.level-cap.get", plugin.getLevelCap()));
                    break;
                }

                int newLevelCap;
                try {
                    newLevelCap = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(plugin.tr("error.command.not-number", args[1]));
                    break;
                }

                if (newLevelCap < 1) {
                    newLevelCap = 1;
                }

                plugin.getConfig().set("level-cap", newLevelCap);
                plugin.saveConfig();

                sender.sendMessage(plugin.tr("command.expflattener.level-cap.set", newLevelCap));
                break;
            case "help":
            case "?":
            default:
                showUsage(sender);
        }

        return true;
    }

    private void showUsage(CommandSender sender) {
        sender.sendMessage(plugin.tr("command.expflattener.help.message"));
    }
}