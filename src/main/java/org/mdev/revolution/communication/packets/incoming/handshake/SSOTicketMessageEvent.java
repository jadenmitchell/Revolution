package org.mdev.revolution.communication.packets.incoming.handshake;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.notifications.HabboBroadcastMessageComposer;
import org.mdev.revolution.network.sessions.Session;

public class SSOTicketMessageEvent {
    public static final String TICKET_DELIMITER = "-";

    @PacketEvent(number = ClientPacketHeader.SSOTicketMessageEvent)
    public static void tryLogin(Session session, ClientPacket packet) {
        String ssoTicket = packet.readString();

        if (!session.tryLogin(ssoTicket)) {
            session.sendPacket(new HabboBroadcastMessageComposer("There was an error while logging you in."));
        }
    }
}
