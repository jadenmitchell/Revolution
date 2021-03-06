package org.mdev.revolution.database.dao.navigator;

import org.mdev.revolution.database.dao.Dao;
import org.mdev.revolution.database.domain.navigator.FlatCat;
import org.mdev.revolution.database.domain.navigator.PublicRoom;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class NavigatorDao {
    @Inject
    Dao<FlatCat, Integer> flatCategories;
    @Inject
    Dao<PublicRoom, Integer> publicRooms;

    @Inject
    @PostConstruct
    public void initialize() {
        flatCategories.setClazz(FlatCat.class);
        publicRooms.setClazz(PublicRoom.class);
    }

    public List<FlatCat> getFlatCategories() {
        return Collections.unmodifiableList(flatCategories.findAll());
    }
}
