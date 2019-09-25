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
        float amount = e.getAmount();

        float newValue = amount / plugin.getLevelRequirement();
        float currentExp = e.getPlayer().getExp();
        float newExp = currentExp + newValue;

        e.setAmount(0);

        if (newExp > 1) {
            int levelDifference = (int) Math.floor(newExp);
            e.getPlayer().setLevel(e.getPlayer().getLevel() + levelDifference);
            newExp -= levelDifference;
        }

        if (e.getPlayer().getLevel() >= plugin.getLevelCap()) {
            e.getPlayer().setLevel(plugin.getLevelCap());
            e.getPlayer().setExp(0f);
        } else {
            e.getPlayer().setExp(newExp);
        }
    }
}
