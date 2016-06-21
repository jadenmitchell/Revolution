package org.mdev.revolution.communication.packets.outgoing.handshake;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;
import org.mdev.revolution.game.players.PlayerBean;

public class UserObjectComposer extends ServerPacket {
    public UserObjectComposer(PlayerBean playerBean) {
        super(ServerPacketHeader.UserObjectComposer);
        super.writeInt(playerBean.getPlayer().getId());
        super.writeString(playerBean.getPlayer().getUsername());
        super.writeString(""); // Look
        super.writeString("M"); // Gender
        super.writeString("Revolution Server Developer"); // Motto
        super.writeString("");
        super.writeBoolean(false);
        super.writeInt(0); // Respects
        super.writeInt(3); // Daily Respects
        super.writeInt(3); // Daily Scratches
        super.writeBoolean(false); // Friends stream active
        super.writeString(""); // last online?
        super.writeBoolean(false); // Can change name
        super.writeBoolean(false);
    }
}
