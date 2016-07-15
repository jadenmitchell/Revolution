package org.mdev.revolution.communication.packets.outgoing.room.session;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class RoomForwardComposer extends ServerPacket {
    public RoomForwardComposer(int roomId) {
        super(ServerPacketHeader.RoomForwardMessageComposer);
        super.writeInt(roomId);
    }
}