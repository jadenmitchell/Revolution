package org.mdev.revolution.communication.packets.outgoing.room.session;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class RoomReadyComposer extends ServerPacket {
    public RoomReadyComposer(int roomId, String model) {
        super(ServerPacketHeader.RoomReadyMessageComposer);
        super.writeString(model);
        super.writeInt(roomId);
    }
}