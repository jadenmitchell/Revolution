package org.mdev.revolution.communication.packets.incoming.tracking;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.network.sessions.Session;

public class EventLogEvent {
    @PacketEvent(number = ClientPacketHeader.EventLogMessageEvent)
    public static void eventLog(Session session, ClientPacket packet) {
        String event = packet.readString();
    }
}