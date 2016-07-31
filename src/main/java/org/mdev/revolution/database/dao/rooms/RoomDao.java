package org.mdev.revolution.database.dao.rooms;

import org.mdev.revolution.database.dao.Dao;
import org.mdev.revolution.database.domain.rooms.Room;
import org.mdev.revolution.database.domain.rooms.RoomModel;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class RoomDao {
    @Inject
    Dao<Room, Integer> rooms;
    @Inject
    Dao<RoomModel, Integer> models;

    @Inject
    @PostConstruct
    public void initialize() {
        rooms.setClazz(Room.class);
        models.setClazz(RoomModel.class);
    }

    public Room findRoom(int roomId) {
        return rooms.findByPropertyUnique("id", roomId);
    }

    public RoomModel findRoomModel(String model) {
        return models.findByPropertyUnique("name", model);
    }
}
