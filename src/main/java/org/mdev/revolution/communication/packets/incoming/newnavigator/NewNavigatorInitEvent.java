package org.mdev.revolution.communication.packets.incoming.newnavigator;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.newnavigator.NavigatorCollapsedCategoriesComposer;
import org.mdev.revolution.communication.packets.outgoing.newnavigator.NavigatorLiftedRoomsComposer;
import org.mdev.revolution.communication.packets.outgoing.newnavigator.NavigatorMetaDataComposer;
import org.mdev.revolution.communication.packets.outgoing.newnavigator.NewNavigatorPreferencesComposer;
import org.mdev.revolution.network.sessions.Session;

public class NewNavigatorInitEvent {
    @PacketEvent(number = ClientPacketHeader.NewNavigatorInitEvent)
    public static void navigatorInit(Session session, ClientPacket packet) {
        session.sendQueued(new NavigatorMetaDataComposer())
                .sendQueued(new NavigatorLiftedRoomsComposer())
                .sendQueued(new NavigatorCollapsedCategoriesComposer())
                .sendQueued(new NewNavigatorPreferencesComposer())
                .getChannel().flush();
    }
}
