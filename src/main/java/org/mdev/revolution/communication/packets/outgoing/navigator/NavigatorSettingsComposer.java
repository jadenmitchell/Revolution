package org.mdev.revolution.communication.packets.outgoing.navigator;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class NavigatorSettingsComposer extends ServerPacket {
    public NavigatorSettingsComposer(int roomId) {
        super(ServerPacketHeader.NavigatorSettingsComposer);
        super.writeInt(roomId);
        super.writeInt(roomId);
    }
}