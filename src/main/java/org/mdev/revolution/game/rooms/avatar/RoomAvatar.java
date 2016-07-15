package org.mdev.revolution.game.rooms.avatar;

import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.outgoing.navigator.RoomRatingComposer;
import org.mdev.revolution.communication.packets.outgoing.room.session.OpenConnectionComposer;
import org.mdev.revolution.communication.packets.outgoing.room.session.RoomReadyComposer;
import org.mdev.revolution.game.players.PlayerBean;
import org.mdev.revolution.game.rooms.RoomBean;
import org.mdev.revolution.game.rooms.RoomType;

public class RoomAvatar {
    private PlayerBean playerBean;
    private RoomAvatarType type;
    private RoomBean currentRoom;

    public RoomAvatar(PlayerBean playerBean) {
        this.playerBean = playerBean;
        this.type = RoomAvatarType.PLAYER;
    }

    public boolean inRoom() {
        return (currentRoom != null);
    }

    public void prepareRoom(int roomId) {
        prepareRoom(roomId, "");
    }

    public void prepareRoom(int roomId, String password) {
        if (inRoom()) {
            return;
        }

        RoomBean roomBean = Revolution.getInstance().getGame().getRoomManager().loadRoom(roomId);
        if (roomBean == null) {
            return;
        }

        currentRoom = roomBean;
        // CHECK IF USER IS BANNED FROM THE ROOM
        // CHECK IF ROOM IS FULL
        // ADD ROOM QUEUE
        if (currentRoom.getRoom().getType() == RoomType.FLAT) {
            playerBean.getSession().sendPacket(new OpenConnectionComposer());
        }
        // CHECK ROOM AUTHENTICATION
        if (!enterRoom()) {
            // stuff
        }
    }

    private boolean enterRoom() {
        playerBean.getSession().sendQueued(new RoomReadyComposer(currentRoom.getRoom().getId(), currentRoom.getRoom().getModel()))
                .sendQueued(new RoomRatingComposer(currentRoom.getRoom().getScore(), false))
                .getChannel().flush();
        return true;
    }
}
