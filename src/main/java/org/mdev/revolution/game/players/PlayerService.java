package org.mdev.revolution.game.players;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.incoming.handshake.SSOTicketMessageEvent;
import org.mdev.revolution.database.DatabaseModule;
import org.mdev.revolution.database.dao.PlayerDao;
import org.mdev.revolution.database.domain.Player;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerService {
    private static final Logger logger = LogManager.getLogger(PlayerService.class);

    private static final PlayerService INSTANCE = new PlayerService();

    public static PlayerService getInstance() {
        return PlayerService.INSTANCE;
    }

    @Inject
    private PlayerDao playerDao;

    @SuppressWarnings("unchecked")
    public PlayerDao getPlayerDao() {
        return playerDao;
    }

    public PlayerService() {
        Injector injector = Guice.createInjector(new DatabaseModule());
        injector.injectMembers(this);
    }

    @SuppressWarnings("unchecked")
    public void save(Player player) {
        playerDao.save(player);
    }

    public Player findPlayer(String ssoTicket) {
        if (ssoTicket.contains(SSOTicketMessageEvent.TICKET_DELIMITER)) {
            ssoTicket = ssoTicket.split(SSOTicketMessageEvent.TICKET_DELIMITER)[1];

            if (ssoTicket.isEmpty()) {
                // INVALID SSO TICKET !!!
                return null;
            }
        }

        Connection connection = Revolution.getInstance().getDatabaseManager().getConnection();
        int playerId = -1;

        try (PreparedStatement stmt = connection.prepareStatement("SELECT id FROM users WHERE auth_ticket = ?")) {
            stmt.setString(1, ssoTicket);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                playerId = rs.getInt(1);
            }
            if (rs.getFetchSize() > 1) {
                logger.warn("Possible SQL injection detected, for authentication ticket: " + ssoTicket);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("SQL exception while executing a prepared statement.", e);
            return null;
        }

        System.out.println("PLAYER ID: " + playerId);
        return playerDao.findByPropertyUnique("id", playerId);
    }

    public static void removeSSOTicket(int playerId) {
        Connection connection = Revolution.getInstance().getDatabaseManager().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement("UPDATE `users` SET `auth_ticket` = '' WHERE `id` = ? LIMIT 1")) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("SQL exception while executing a prepared statement.", e);
        }
    }
}
