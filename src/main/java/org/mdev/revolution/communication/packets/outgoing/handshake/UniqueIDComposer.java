package org.mdev.revolution.communication.packets.outgoing.handshake;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class UniqueIDComposer extends ServerPacket {
    public UniqueIDComposer(String machineId) {
        super(ServerPacketHeader.UniqueIDComposer);
        super.writeString(machineId);
    }
}
