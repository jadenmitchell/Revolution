package org.mdev.revolution.communication.packets.incoming.newnavigator;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.newnavigator.SearchResultSet;
import org.mdev.revolution.database.domain.navigator.FlatCat;
import org.mdev.revolution.game.navigator.NavigatorSearchService;
import org.mdev.revolution.network.sessions.Session;

import java.util.List;

public class NewNavigatorSearchEvent {
    @PacketEvent(number = ClientPacketHeader.NewNavigatorSearchEvent)
    public static void navigatorSearch(Session session, ClientPacket packet) {
        String category = packet.readString();
        String junk = packet.readString();

        if (category.isEmpty()) {
            category = "hotel_view";
        }

        List<FlatCat> results = NavigatorSearchService.search(category, junk).get();
        session.sendPacket(new SearchResultSet(category, junk, results));
    }
}
