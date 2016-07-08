package org.mdev.revolution.communication.packets.incoming.tracking;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.network.sessions.Session;

public class LatencyPingRequestMessageEvent {
    @PacketEvent(number = ClientPacketHeader.LatencyPingRequestMessageEvent)
    public static void pingRequest(Session session, ClientPacket packet) {

    }
}
