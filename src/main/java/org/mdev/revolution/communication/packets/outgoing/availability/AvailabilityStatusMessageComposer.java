package org.mdev.revolution.communication.packets.outgoing.availability;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class AvailabilityStatusMessageComposer extends ServerPacket {
    public AvailabilityStatusMessageComposer() {
        super(ServerPacketHeader.AvailabilityStatusMessageComposer);
        super.writeBoolean(true);
        super.writeBoolean(false);
    }
}