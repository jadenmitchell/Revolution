package org.mdev.revolution.communication.packets.outgoing.newnavigator;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;
import org.mdev.revolution.database.domain.navigator.FlatCat;
import org.mdev.revolution.game.navigator.NavigatorSearchAllowance;
import org.mdev.revolution.game.navigator.NavigatorViewMode;

import java.util.List;

public class SearchResultSet extends ServerPacket {
    public SearchResultSet(String category, String data, List<FlatCat> categories) {
        super(ServerPacketHeader.SearchResultSet);
        super.writeString(category);
        super.writeString(data);
        super.writeInt(categories.size());
        categories.forEach((c) -> {
            super.writeString("lol"); // Category Identifier
            super.writeString(c.getPublicName());
            super.writeInt(NavigatorSearchAllowance.getIntValue(c.getSearchAllowance()));
            super.writeBoolean(false);
            super.writeInt(c.getViewMode() == NavigatorViewMode.REGULAR ? 0 : c.getViewMode() == NavigatorViewMode.THUMBNAIL ? 1 : 0);
            // TODO: Send the rest of the shit.
        });
    }
}
