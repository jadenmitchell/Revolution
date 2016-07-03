package org.mdev.revolution.communication.packets.incoming.landingview;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.landingview.PromoArticlesMessageComposer;
import org.mdev.revolution.network.sessions.Session;

public class GetPromoArticlesEvent {
    @PacketEvent(number = ClientPacketHeader.GetPromoArticlesEvent)
    public static void getArticles(Session session, ClientPacket packet) {
        session.sendPacket(new PromoArticlesMessageComposer());
    }
}
