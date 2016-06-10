package org.mdev.revolution.communication.packets.incoming.handshake;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.handshake.UniqueIDComposer;
import org.mdev.revolution.network.sessions.Session;

public class UniqueMachineIDMessageEvent {
    @PacketEvent(number = ClientPacketHeader.UniqueIDMessageEvent)
    public static void uniqueID(Session session, ClientPacket packet) {
        String junk = packet.readString();
        String machineId = packet.readString();

        session.sendPacket(new UniqueIDComposer(machineId));
    }
}