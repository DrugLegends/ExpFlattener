package me.rayzr522.expflattener.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerFlattenedExpChangeEvent extends PlayerEvent {
    private final static HandlerList handlerList = new HandlerList();

    private final int pointsPerLevel;
    private float percentageChange;
    private final float uncappedPercentageChange;

    public PlayerFlattenedExpChangeEvent(Player who, int pointsPerLevel, float percentageChange, float uncappedPercentageChange) {
        super(who);
        this.pointsPerLevel = pointsPerLevel;
        this.percentageChange = percentageChange;
        this.uncappedPercentageChange = uncappedPercentageChange;
    }

    public int getPointsPerLevel() {
        return pointsPerLevel;
    }

    public float getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(float percentageChange) {
        this.percentageChange = percentageChange;
    }

    public float getUncappedPercentageChange() {
        return uncappedPercentageChange;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
