package org.mdev.revolution.communication.packets.incoming.inventory.purse;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.inventory.purse.CreditBalanceComposer;
import org.mdev.revolution.communication.packets.outgoing.notifications.ActivityPointsMessageComposer;
import org.mdev.revolution.network.sessions.Session;

public class GetCreditsInfoEvent {
    @PacketEvent(number = ClientPacketHeader.GetCreditsInfoEvent)
    public static void getCredits(Session session, ClientPacket packet) {
        session.sendQueued(new CreditBalanceComposer(100))
                .sendQueued(new ActivityPointsMessageComposer(10, 10))
                .getChannel().flush();
    }
}
