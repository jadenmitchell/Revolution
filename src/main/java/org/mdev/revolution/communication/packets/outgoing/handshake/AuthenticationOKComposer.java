package org.mdev.revolution.communication.packets.outgoing.handshake;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class AuthenticationOKComposer extends ServerPacket {
    public AuthenticationOKComposer() {
        super(ServerPacketHeader.AuthenticationOKComposer);
    }
}
