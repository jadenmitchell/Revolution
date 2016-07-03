package org.mdev.revolution.communication.packets.outgoing.newnavigator;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class NavigatorCollapsedCategoriesMessageComposer extends ServerPacket {
    public NavigatorCollapsedCategoriesMessageComposer() {
        super(ServerPacketHeader.NavigatorCollapsedCategoriesMessageComposer);
        super.writeInt(0);
    }
}