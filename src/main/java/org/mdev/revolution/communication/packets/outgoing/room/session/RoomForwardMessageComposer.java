package org.mdev.revolution.communication.packets.outgoing.room.session;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class RoomForwardMessageComposer extends ServerPacket {
    public RoomForwardMessageComposer(int roomId) {
        super(ServerPacketHeader.RoomForwardMessageComposer);
        super.writeInt(roomId);
    }
}