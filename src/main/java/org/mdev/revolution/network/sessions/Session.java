package org.mdev.revolution.network.sessions;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.encryption.ARC4;
import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.availability.AvailabilityStatusMessageComposer;
import org.mdev.revolution.communication.packets.outgoing.handshake.AuthenticationOKComposer;
import org.mdev.revolution.communication.packets.outgoing.handshake.UserRightsMessageComposer;
import org.mdev.revolution.communication.packets.outgoing.inventory.avatareffect.AvatarEffectsMessageComposer;
import org.mdev.revolution.communication.packets.outgoing.navigator.NavigatorSettingsComposer;
import org.mdev.revolution.communication.packets.outgoing.notifications.HabboBroadcastMessageComposer;
import org.mdev.revolution.communication.packets.outgoing.notifications.MOTDNotificationComposer;
import org.mdev.revolution.database.domain.Player;
import org.mdev.revolution.game.players.PlayerBean;
import org.mdev.revolution.game.players.PlayerService;
import org.mdev.revolution.network.codec.EncryptionDecoder;
import org.mdev.revolution.utilities.DefaultConfig;

public class Session {
    private static final Logger logger = LogManager.getLogger(Session.class);

    private Channel channel;
    private ARC4 rc4;
    private PlayerBean playerBean;

    public Channel getChannel() {
        return channel;
    }

    public ARC4 getRC4() {
        return rc4;
    }

    public Session(Channel channel) {
        this.channel = channel;
    }

    public boolean tryLogin(String ssoTicket) {
        try {
            logger.info("logging in...");
            //ssoTicket = ESAPI.encoder().encodeForSQL(new OracleCodec(), ssoTicket);
            Player player = PlayerService.findPlayer(ssoTicket);
            if (player == null) {
                sendPacket(new HabboBroadcastMessageComposer("No player found with your session ticket"));
                return false;
            }

            PlayerService.removeSSOTicket(player.getId());

            playerBean = new PlayerBean(player);

            sendQueued(new AuthenticationOKComposer())
                    .sendQueued(new AvailabilityStatusMessageComposer())
                    .sendQueued(new NavigatorSettingsComposer(0)) // HOMEROOM
                    .sendQueued(new UserRightsMessageComposer(true, true, false)) // CLUB, VIP, AMBASSADOR SETTINGS
                    .sendQueued(new AvatarEffectsMessageComposer(null))
                    .sendQueued(new MOTDNotificationComposer(Revolution.getConfig().getOrDefault("motd", DefaultConfig.motd)))
                    .getChannel().flush();
        }
        catch (Exception e) {
            logger.error("Bug during user login.", e);
            disconnect();
            return false;
        }

        return false;
    }

    public void sendPacket(ServerPacket packet) {
        channel.writeAndFlush(packet);
    }

    public Session sendQueued(ServerPacket packet) {
        channel.write(packet).addListener(ChannelFutureListener.CLOSE);
        return this;
    }

    public void enableRC4(byte[] sharedKey) {
        rc4 = new ARC4();
        rc4.init(sharedKey);
        channel.pipeline().addBefore("packetDecoder", "packetCrypto", new EncryptionDecoder());
    }

    public void disconnect() {
        if (channel != null) {
            channel.disconnect();
            channel = null;
        }
    }
}