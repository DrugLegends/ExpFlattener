package me.rayzr522.expflattener.listeners;

import me.rayzr522.expflattener.ExpFlattener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerListener implements Listener {
    private final ExpFlattener plugin;

    public PlayerListener(ExpFlattener plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent e) {
        int level = e.getPlayer().getLevel();

        float expRequired = getExpRequired(level);
        int amount = e.getAmount();

        float newValue = (amount / expRequired) / plugin.getLevelRequirement();
        float currentExp = e.getPlayer().getExp();
        float newExp = currentExp + newValue;

        e.setAmount(0);

        while (newExp > 1) {
            e.getPlayer().setLevel(e.getPlayer().getLevel() + 1);
            newExp -= 1f;
        }

        if (e.getPlayer().getLevel() >= plugin.getLevelCap()) {
            e.getPlayer().setLevel(plugin.getLevelCap());
            e.getPlayer().setExp(0f);
        }

        e.getPlayer().setExp(newExp);
    }

    private int getExpRequired(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        } else if (level <= 30) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }
}
