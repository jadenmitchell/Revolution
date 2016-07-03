package org.mdev.revolution.communication.packets.outgoing.newnavigator;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class NavigatorLiftedRoomsComposer extends ServerPacket {
    public NavigatorLiftedRoomsComposer() {
        super(ServerPacketHeader.NavigatorLiftedRoomsComposer);
        super.writeInt(0);
    }
}