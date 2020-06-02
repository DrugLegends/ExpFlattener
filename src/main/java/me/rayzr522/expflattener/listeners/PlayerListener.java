package me.rayzr522.expflattener.listeners;

import me.rayzr522.expflattener.ExpFlattener;
import me.rayzr522.expflattener.event.PlayerFlattenedExpChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        Player player = e.getPlayer();
        float amount = e.getAmount();

        float newValue = amount / plugin.getLevelRequirement();
        float currentExp = player.getExp();
        float newExp = currentExp + newValue;

        if (newExp + player.getLevel() >= plugin.getLevelCap()) {
            newExp = plugin.getLevelCap() - player.getLevel();
        }

        PlayerFlattenedExpChangeEvent flattenedExpChangeEvent = new PlayerFlattenedExpChangeEvent(player, plugin.getLevelRequirement(), newExp - currentExp, newValue);

        Bukkit.getPluginManager().callEvent(flattenedExpChangeEvent);

        newExp = currentExp + flattenedExpChangeEvent.getPercentageChange();

        e.setAmount(0);

        if (newExp > 1) {
            int levelDifference = (int) Math.floor(newExp);
            player.setLevel(player.getLevel() + levelDifference);
            newExp -= levelDifference;
        }

        if (player.getLevel() >= plugin.getLevelCap()) {
            player.setLevel(plugin.getLevelCap());
            player.setExp(0f);
        } else {
            player.setExp(newExp);
        }
    }
}
