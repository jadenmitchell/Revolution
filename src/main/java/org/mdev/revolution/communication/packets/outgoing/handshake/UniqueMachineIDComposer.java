package org.mdev.revolution.communication.packets.outgoing.handshake;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class UniqueMachineIDComposer extends ServerPacket {
    public UniqueMachineIDComposer(String machineId) {
        super(ServerPacketHeader.UniqueMachineIDComposer);
        super.writeString(machineId);
    }
}
