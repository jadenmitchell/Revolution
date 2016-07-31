package org.mdev.revolution.communication.packets.incoming.friendlist;

import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.friendlist.FriendDataComposer;
import org.mdev.revolution.communication.packets.outgoing.friendlist.MessengerInitComposer;
import org.mdev.revolution.network.sessions.Session;

public class MessengerInitEvent {
    @PacketEvent(number = ClientPacketHeader.MessengerInitMessageEvent)
    public static void messengerInit(Session session, ClientPacket packet) {
        session.sendQueued(new MessengerInitComposer())
                .sendQueued(new FriendDataComposer())
                .getChannel().flush();
    }
}
