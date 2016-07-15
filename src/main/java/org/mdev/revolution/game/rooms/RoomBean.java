package org.mdev.revolution.game.rooms;

import org.mdev.revolution.database.domain.rooms.Room;

import java.io.Serializable;

public class RoomBean implements Serializable {
    private Room room;
    private boolean unloaded;

    public RoomBean(Room room) {
        this.room = room;
        this.unloaded = false;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isUnloaded() {
        return unloaded;
    }
}
