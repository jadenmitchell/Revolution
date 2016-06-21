package org.mdev.revolution.communication.packets.incoming.handshake;

import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.network.sessions.Session;

@SuppressWarnings("unused")
public class VersionCheckMessageEvent {
    @PacketEvent(number = ClientPacketHeader.VersionCheckMessageEvent)
    public static void versionCheck(Session session, ClientPacket packet) {
        String build = packet.readString();

        if (!build.equals(Revolution.TARGET_CLIENT)) {
            System.out.println("The server has detected different client versions, some packets may go unhandled!");
        }
    }
}
