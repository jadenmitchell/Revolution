package org.mdev.revolution.communication.packets.outgoing.landingview;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class PromoArticlesMessageComposer extends ServerPacket {
    public PromoArticlesMessageComposer() {
        super(ServerPacketHeader.PromoArticlesMessageComposer);
        super.writeInt(0);
    }
}
