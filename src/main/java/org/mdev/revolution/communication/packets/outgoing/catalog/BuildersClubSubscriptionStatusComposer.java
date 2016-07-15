package org.mdev.revolution.communication.packets.outgoing.catalog;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class BuildersClubSubscriptionStatusComposer extends ServerPacket {
    public BuildersClubSubscriptionStatusComposer() {
        super(ServerPacketHeader.BuildersClubSubscriptionStatusMessageComposer);
        super.writeInt(Integer.MAX_VALUE);
        super.writeInt(100);
        super.writeInt(0);
        super.writeInt(Integer.MAX_VALUE);
    }
}
