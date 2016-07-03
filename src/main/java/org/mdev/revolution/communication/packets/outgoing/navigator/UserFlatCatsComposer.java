package org.mdev.revolution.communication.packets.outgoing.navigator;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;
import org.mdev.revolution.database.domain.navigator.FlatCat;

import java.util.List;

public class UserFlatCatsComposer extends ServerPacket {
    public UserFlatCatsComposer(List<FlatCat> flatCats, int rank) {
        super(ServerPacketHeader.UserFlatCatsComposer);
        super.writeInt(flatCats.size());
        flatCats.forEach((flatCat) -> {
            super.writeInt(flatCat.getId());
            super.writeString(flatCat.getPublicName());
            super.writeBoolean(flatCat.getRequiredRank() <= rank);
            super.writeBoolean(false);
            super.writeString("");
            super.writeString("");
            super.writeBoolean(false);
        });
    }
}