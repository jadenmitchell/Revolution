package org.mdev.revolution.communication.packets.incoming.handshake;

import org.mdev.revolution.communication.encryption.HabboEncryption;
import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.handshake.InitDiffieHandshakeComposer;
import org.mdev.revolution.network.codec.EncryptionDecoder;
import org.mdev.revolution.network.sessions.Session;

public class InitDiffieHandshakeMessageEvent {
    @PacketEvent(number = ClientPacketHeader.InitDiffieHandshakeMessageEvent)
    public static void initCrypto(Session session, ClientPacket packet) {
        session.sendPacket(new InitDiffieHandshakeComposer(HabboEncryption.getRsaDiffieHellmanPrimeKey(), HabboEncryption.getRsaDiffieHellmanGeneratorKey()));
    }
}
