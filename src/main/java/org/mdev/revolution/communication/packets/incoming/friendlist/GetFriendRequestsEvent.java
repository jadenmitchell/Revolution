package org.mdev.revolution.communication.packets.incoming.friendlist;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.friendlist.FriendRequestsComposer;
import org.mdev.revolution.network.sessions.Session;

public class GetFriendRequestsEvent {
    @PacketEvent(number = ClientPacketHeader.GetFriendRequestsMessageEvent)
    public static void getFriendRequests(Session session, ClientPacket packet) {
        session.sendPacket(new FriendRequestsComposer());
    }
}