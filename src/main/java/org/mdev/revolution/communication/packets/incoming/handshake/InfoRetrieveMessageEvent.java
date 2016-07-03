package org.mdev.revolution.communication.packets.incoming.handshake;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.handshake.NoobnessLevelMessageComposer;
import org.mdev.revolution.communication.packets.outgoing.handshake.UserObjectComposer;
import org.mdev.revolution.network.sessions.Session;

public class InfoRetrieveMessageEvent {
    @PacketEvent(number = ClientPacketHeader.InfoRetrieveMessageEvent)
    public static void infoRetrieve(Session session, ClientPacket packet) {
        session.sendQueued(new UserObjectComposer(session.getPlayerBean()))
                .sendQueued(new NoobnessLevelMessageComposer())
                .getChannel().flush();
    }
}