package org.mdev.revolution.game.rooms.avatar.effects;

import org.mdev.revolution.Revolution;

public class AvatarEffect {
    private int id;
    private int userId;
    private int spriteId;
    private double duration;
    private boolean activated;
    private double timestampActivated;
    private int quantity;

    public AvatarEffect(int id, int userId, int spriteId, double duration, boolean activated, double timestampActivated, int quantity) {
        this.id = id;
        this.userId = userId;
        this.spriteId = spriteId;
        this.duration = duration;
        this.activated = activated;
        this.timestampActivated = timestampActivated;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getSpriteId() {
        return spriteId;
    }

    public double getDuration() {
        return duration;
    }

    public boolean isActivated() {
        return activated;
    }

    public double getTimestampActivated() {
        return timestampActivated;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTimeUsed() {
        return (Revolution.getUnixTimestamp() - timestampActivated);
    }

    public double getTimeLeft() {
        double tl = (activated ? duration - getTimeUsed() : duration);
        return (tl < 0 ? 0 : tl);
    }

    public boolean hasExpired() {
        return (activated && getTimeLeft() <= 0);
    }
}
