package org.mdev.revolution.communication.packets.outgoing.newnavigator;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class NavigatorCollapsedCategoriesComposer extends ServerPacket {
    public NavigatorCollapsedCategoriesComposer() {
        super(ServerPacketHeader.NavigatorCollapsedCategoriesMessageComposer);
        super.writeInt(0);
    }
}