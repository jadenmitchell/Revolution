package org.mdev.revolution.communication.packets.incoming.handshake;

import org.mdev.revolution.communication.encryption.HabboEncryption;
import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.handshake.InitDiffieHandshakeComposer;
import org.mdev.revolution.network.sessions.Session;

public class InitDiffieHandshakeEvent {
    @PacketEvent(number = ClientPacketHeader.InitDiffieHandshakeMessageEvent)
    public static void initCrypto(Session session, ClientPacket packet) {
        String prime = HabboEncryption.getRsaDiffieHellmanPrimeKey();
        String generator = HabboEncryption.getRsaDiffieHellmanGeneratorKey();
        session.sendPacket(new InitDiffieHandshakeComposer(prime, generator));
    }
}
