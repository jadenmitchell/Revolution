package org.mdev.revolution.communication.packets.incoming.handshake;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.network.sessions.Session;

public class SSOTicketMessageEvent {
    public static final String TICKET_DELIMITER = "-";

    @PacketEvent(number = ClientPacketHeader.SSOTicketMessageEvent)
    public static void tryLogin(Session session, ClientPacket packet) {
        String ssoTicket = packet.readString();
        session.tryAuthenticate(ssoTicket);
    }
}
