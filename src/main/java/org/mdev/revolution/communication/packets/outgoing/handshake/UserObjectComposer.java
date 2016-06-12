package org.mdev.revolution.communication.packets.outgoing.handshake;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class UserObjectComposer extends ServerPacket {
    public UserObjectComposer() {
        super(ServerPacketHeader.UserObjectComposer);

    }
}
