package org.mdev.revolution.communication.packets.outgoing.navigator;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class RoomRatingComposer extends ServerPacket {
    public RoomRatingComposer(int score, boolean canRate) {
        super(ServerPacketHeader.RoomRatingComposer);
        super.writeInt(score);
        super.writeBoolean(canRate);
    }
}