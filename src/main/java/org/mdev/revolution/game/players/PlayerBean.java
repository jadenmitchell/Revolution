package org.mdev.revolution.game.players;

import org.mdev.revolution.database.domain.Player;

import java.io.Serializable;

public class PlayerBean implements Serializable {
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public PlayerBean(Player player) {
        super();
        this.player = player;
    }

    public void cleanup() {
        if (player != null) {
            PlayerService.getInstance().save(player);
            player = null;
        }
    }
}
