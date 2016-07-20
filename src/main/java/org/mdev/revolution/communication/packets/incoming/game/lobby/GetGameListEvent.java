package org.mdev.revolution.communication.packets.incoming.game.lobby;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.game.lobby.GameListComposer;
import org.mdev.revolution.network.sessions.Session;

public class GetGameListEvent {
    @PacketEvent(number = ClientPacketHeader.GetGameListMessageEvent)
    public static void getGameList(Session session, ClientPacket packet) {
        // ??? This is redundant.
        session.sendPacket(new GameListComposer());
    }
}