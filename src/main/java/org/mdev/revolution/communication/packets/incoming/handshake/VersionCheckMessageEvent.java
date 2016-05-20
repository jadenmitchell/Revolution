package org.mdev.revolution.communication.packets.incoming.handshake;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.network.sessions.Session;

@SuppressWarnings("unused")
public class VersionCheckMessageEvent {
    @PacketEvent(number = ClientPacketHeader.VersionCheckMessageEvent)
    public static void versionCheck(Session session, ClientPacket packet) {
        System.out.println(packet.readString());
    }
}
