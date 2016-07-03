package org.mdev.revolution.communication.packets.incoming.handshake;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.communication.encryption.HabboEncryption;
import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.handshake.InitDiffieHandshakeComposer;
import org.mdev.revolution.network.sessions.Session;

public class InitDiffieHandshakeMessageEvent {
    private static final Logger logger = LogManager.getLogger(InitDiffieHandshakeMessageEvent.class);

    @PacketEvent(number = ClientPacketHeader.InitDiffieHandshakeMessageEvent)
    public static void initCrypto(Session session, ClientPacket packet) {
        String prime = HabboEncryption.getRsaDiffieHellmanPrimeKey();
        String generator = HabboEncryption.getRsaDiffieHellmanGeneratorKey();
        logger.info("DH Keys, Prime: " + prime);
        logger.info("DH Keys, Generator: " + generator);
        session.sendPacket(new InitDiffieHandshakeComposer(HabboEncryption.getRsaDiffieHellmanPrimeKey(), HabboEncryption.getRsaDiffieHellmanGeneratorKey()));
        //session.sendPacket(new InitDiffieHandshakeComposer("4F32CF24678A02D28DC4B8D104AE3A283C90E1B0A45C5379009ACDC38A5BDB5DC279DD8F5857ABF3A1B116A2D431DC10168FDAEBC73B2329D27F88A165000B4E42A007B6A2B6B64A9ABD6EF0FA652C21EF81E4CFC56C25AF681437C0DD49195ACCD1CACD5105B116DAEF5B1585095C2A1DF744C3D39CCB0C339E69DCD94C7EB7", "0CD3A72EEC8E0851213A3271D598B092554F41EFDABD989EA4A3CDC2D1263E68B95280B82D0EDEEE7A0656A2AEADA3B739B29FA3F6C85925EF3B671E134E879DEA87C06C6A919BA20965119A167FC8CBA452A6F6BFCCF87096C58E364FD000B3DDFCDE1631C26918539AE5973BB90EE0D15A022A523EE8FFEAA6DF7A680B3600"));
    }
}
