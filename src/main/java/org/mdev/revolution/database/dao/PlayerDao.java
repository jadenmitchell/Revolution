package org.mdev.revolution.database.dao;

import org.mdev.revolution.database.domain.Player;

public class PlayerDao extends GenericJpaDao<Player, Long> {
    public PlayerDao() {
        super();
        super.setClazz(Player.class);
    }
}