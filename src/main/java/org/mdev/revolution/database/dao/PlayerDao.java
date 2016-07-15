package org.mdev.revolution.database.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.incoming.handshake.SSOTicketEvent;
import org.mdev.revolution.database.DatabaseManager;
import org.mdev.revolution.database.domain.Player;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDao  {
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);

    @Inject GenericJpaDao<Player, Integer> players;

    @Inject
    @PostConstruct
    public void initialize() {
        players.setClazz(Player.class);
    }

    public Player findPlayer(String ssoTicket) {
        if (ssoTicket.contains(SSOTicketEvent.TICKET_DELIMITER)) {
            ssoTicket = ssoTicket.split(SSOTicketEvent.TICKET_DELIMITER)[1];

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

        return players.findByPropertyUnique("id", playerId);
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