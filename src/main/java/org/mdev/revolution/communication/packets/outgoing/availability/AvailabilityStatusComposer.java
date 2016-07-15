package org.mdev.revolution.communication.packets.outgoing.availability;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class AvailabilityStatusComposer extends ServerPacket {
    public AvailabilityStatusComposer() {
        super(ServerPacketHeader.AvailabilityStatusMessageComposer);
        super.writeBoolean(true);
        super.writeBoolean(false);
    }
}