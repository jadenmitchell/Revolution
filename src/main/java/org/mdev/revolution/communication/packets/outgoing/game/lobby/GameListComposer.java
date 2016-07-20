package org.mdev.revolution.communication.packets.outgoing.game.lobby;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class GameListComposer extends ServerPacket {
    public GameListComposer() {
        super(ServerPacketHeader.GameListMessageComposer);
        super.writeInt(0);
    }
}