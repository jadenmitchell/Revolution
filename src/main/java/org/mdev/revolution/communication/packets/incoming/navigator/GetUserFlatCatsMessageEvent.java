package org.mdev.revolution.communication.packets.incoming.navigator;

import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.navigator.UserFlatCatsComposer;
import org.mdev.revolution.network.sessions.Session;

public class GetUserFlatCatsMessageEvent {
    @PacketEvent(number = ClientPacketHeader.GetUserFlatCatsMessageEvent)
    public static void getUserFlats(Session session, ClientPacket packet) {
        session.sendPacket(new UserFlatCatsComposer(Revolution.getInstance().getGame().getNavigatorDao().getFlatCategories(), 1));
    }
}
