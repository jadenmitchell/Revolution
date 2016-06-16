package org.mdev.revolution.communication.packets.incoming.handshake;

import org.mdev.revolution.communication.encryption.HabboEncryption;
import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.handshake.CompleteDiffieHandshakeComposer;
import org.mdev.revolution.communication.packets.outgoing.notifications.HabboBroadcastMessageComposer;
import org.mdev.revolution.network.sessions.Session;

import java.math.BigInteger;

public class CompleteDiffieHandshakeMessageEvent {
    @PacketEvent(number = ClientPacketHeader.CompleteDiffieHandshakeMessageEvent)
    public static void completeHandshake(Session session, ClientPacket packet) {
        String cipherPublicKey = packet.readString();
        //String publicKey = HabboEncryption.getRsaStringDecrypted(cipherPublicKey);

        BigInteger sharedKey = HabboEncryption.calculateDiffieHellmanSharedKey(cipherPublicKey);

        if (sharedKey.equals(BigInteger.ZERO)) {
            session.sendPacket(new HabboBroadcastMessageComposer("There was an error logging you in, please try again!"));
            return;
        }

        session.enableRC4(sharedKey.toByteArray());
        session.sendPacket(new CompleteDiffieHandshakeComposer(HabboEncryption.getRsaDiffieHellmanPublicKey()));
    }
}
