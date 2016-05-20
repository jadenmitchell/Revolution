package org.mdev.revolution.communication.packets.outgoing.handshake;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class InitDiffieHandshakeComposer extends ServerPacket {
    public InitDiffieHandshakeComposer(String prime, String generator) {
        super(ServerPacketHeader.InitDiffieHandshakeComposer);
        super.writeString(prime);
        super.writeString(generator);
    }
}