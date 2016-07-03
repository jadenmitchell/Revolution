package org.mdev.revolution.communication.packets.outgoing.handshake;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class UserRightsMessageComposer extends ServerPacket {
    public UserRightsMessageComposer(int rank, boolean hasClub, boolean hasVip, boolean isAmbassador) {
        super(ServerPacketHeader.UserRightsMessageComposer);

        if (!hasClub && hasVip) {
            super.writeInt(2);
        }
        else if (hasClub && !hasVip) {
            super.writeInt(1);
        }
        else {
            super.writeInt(0);
        }

        super.writeInt(rank);
        super.writeBoolean(isAmbassador);
    }
}
