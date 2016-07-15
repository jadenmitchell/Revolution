package org.mdev.revolution.network.sessions;

import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.encryption.ARC4;
import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.inventory.achievements.AchievementsComposer;
import org.mdev.revolution.communication.packets.outgoing.inventory.achievements.AchievementsScoreComposer;
import org.mdev.revolution.communication.packets.outgoing.availability.AvailabilityStatusComposer;
import org.mdev.revolution.communication.packets.outgoing.catalog.BuildersClubSubscriptionStatusComposer;
import org.mdev.revolution.communication.packets.outgoing.handshake.AuthenticationOKComposer;
import org.mdev.revolution.communication.packets.outgoing.handshake.UserRightsComposer;
import org.mdev.revolution.communication.packets.outgoing.inventory.avatareffect.AvatarEffectsComposer;
import org.mdev.revolution.communication.packets.outgoing.moderation.CfhChatlogComposer;
import org.mdev.revolution.communication.packets.outgoing.navigator.NavigatorSettingsComposer;
import org.mdev.revolution.communication.packets.outgoing.notifications.HabboBroadcastComposer;
import org.mdev.revolution.communication.packets.outgoing.notifications.MOTDNotificationComposer;
import org.mdev.revolution.communication.packets.outgoing.preferences.AccountPreferencesComposer;
import org.mdev.revolution.database.domain.Player;
import org.mdev.revolution.game.players.PlayerBean;
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

    public PlayerBean getPlayerBean() {
        return playerBean;
    }

    public Session(Channel channel) {
        this.channel = channel;
    }

    public boolean tryLogin(String ssoTicket) {
        try {
            Player player = Revolution.getInstance().getGame().getPlayerDao().findPlayer(ssoTicket);
            if (player == null) {
                sendPacket(new HabboBroadcastComposer("No player found with your session ticket"));
                return false;
            }

            //PlayerDao.removeSSOTicket(player.getId());

            sendQueued(new AuthenticationOKComposer())
                    .sendQueued(new AvatarEffectsComposer(null))
                    .sendQueued(new NavigatorSettingsComposer(0)) // HOMEROOM
                    .sendQueued(new UserRightsComposer(0, true, true, false)) // CLUB, VIP, AMBASSADOR SETTINGS
                    .sendQueued(new AvailabilityStatusComposer())
                    .sendQueued(new AchievementsScoreComposer(0))
                    .sendQueued(new BuildersClubSubscriptionStatusComposer())
                    .sendQueued(new CfhChatlogComposer())
                    .sendQueued(new AchievementsComposer())
                    .sendQueued(new AccountPreferencesComposer())
                    .getChannel().flush();

            sendPacket(new MOTDNotificationComposer(Revolution.getConfig().getOrDefault("motd", DefaultConfig.motd)));
            playerBean.getAvatar().prepareRoom(1);
            return true;
        }
        catch (Exception e) {
            logger.error("Bug during user login.", e);
            disconnect();
            return false;
        }
    }

    public void sendPacket(ServerPacket packet) {
        channel.writeAndFlush(packet);
    }

    public Session sendQueued(ServerPacket packet) {
        channel.write(packet);
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