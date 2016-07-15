package org.mdev.revolution.communication.packets.outgoing.room.session;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class OpenConnectionComposer extends ServerPacket {
    public OpenConnectionComposer() {
        super(ServerPacketHeader.OpenConnectionMessageComposer);
    }
}