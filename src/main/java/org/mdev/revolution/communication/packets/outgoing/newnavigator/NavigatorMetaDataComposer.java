package org.mdev.revolution.communication.packets.outgoing.newnavigator;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class NavigatorMetaDataComposer extends ServerPacket {
    public NavigatorMetaDataComposer() {
        super(ServerPacketHeader.NavigatorMetaDataComposer);
        super.writeInt(0);
    }
}