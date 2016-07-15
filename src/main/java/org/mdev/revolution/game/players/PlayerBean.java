package org.mdev.revolution.game.players;

import org.mdev.revolution.database.domain.Player;
import org.mdev.revolution.game.rooms.avatar.RoomAvatar;
import org.mdev.revolution.network.sessions.Session;

import java.io.Serializable;

public class PlayerBean implements Serializable {
    private Session session;
    private Player player;
    private RoomAvatar avatar;

    public PlayerBean(Session session, Player player) {
        super();
        this.session = session;
        this.player = player;
        this.avatar = new RoomAvatar(this);
    }

    public void cleanup() {
        if (player != null) {
            player = null;
        }
    }

    public Session getSession() {
        return session;
    }

    public Player getPlayer() {
        return player;
    }

    public RoomAvatar getAvatar() {
        return avatar;
    }
}
