package org.mdev.revolution.game.players;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.incoming.handshake.SSOTicketMessageEvent;
import org.mdev.revolution.database.dao.PlayerDao;
import org.mdev.revolution.database.models.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class PlayerService {
    private static final Logger logger = LogManager.getLogger(PlayerService.class);

    private static PlayerDao playerDao;

    @SuppressWarnings("unchecked")
    public static PlayerDao getPlayerDao() {
        if (playerDao == null) playerDao = new PlayerDao();
        return playerDao;
    }

    public static void save(Player player) {
        playerDao.save(player);
    }

    public static Player findPlayer(String ssoTicket) {
        if (ssoTicket.contains(SSOTicketMessageEvent.TICKET_DELIMITER)) {
            ssoTicket = ssoTicket.split(SSOTicketMessageEvent.TICKET_DELIMITER)[1];

            if (ssoTicket.isEmpty()) {
                // INVALID SSO TICKET !!!
                return null;
            }
        }

        Player player = playerDao.getByPropertyUnique("auth_ticket", ssoTicket);
        removeSSOTicket(player.getId());
        return player;
    }

    public static void removeSSOTicket(Long playerId) {
        try {
            PreparedStatement stmt = Revolution.getInstance().getDatabaseManager().query("UPDATE `users` SET `auth_ticket` = '' WHERE `id` = ? LIMIT 1;");
            stmt.setLong(1, playerId);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            logger.error("Error while nullifying a user's authentication ticket", e);
        }
    }
}
