package org.mdev.revolution.database.dao;

import org.mdev.revolution.database.domain.navigator.FlatCat;
import org.mdev.revolution.database.domain.navigator.PublicRoom;

import javax.inject.Inject;
import java.util.List;

public class NavigatorDao {
    GenericJpaDao<FlatCat, Integer> flatCategories;
    GenericJpaDao<PublicRoom, Integer> publicRooms;

    @Inject
    public NavigatorDao() {
        // TODO: Use Guice and deprecate the use of JpaDao.java
        flatCategories = new JpaDao<>();
        publicRooms = new JpaDao<>();
        flatCategories.setClazz(FlatCat.class);
        publicRooms.setClazz(PublicRoom.class);
    }

    public List<FlatCat> getFlatCategories() {
        return flatCategories.findAll();
    }
}
