package org.mdev.revolution.communication.packets.outgoing.handshake;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class CompleteDiffieHandshakeComposer extends ServerPacket {
    public CompleteDiffieHandshakeComposer(String publicKey) {
        super(ServerPacketHeader.CompleteDiffieHandshakeComposer);
        super.writeString(publicKey);
    }
}