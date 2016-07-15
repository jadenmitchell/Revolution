package org.mdev.revolution.communication.packets.incoming.room.session;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.network.sessions.Session;

public class OpenFlatConnectionEvent {
    @PacketEvent(number = ClientPacketHeader.RoomNetworkOpenConnectionMessageEvent)
    public static void goToRoom(Session session, ClientPacket packet) {
        int roomId = packet.readInt();
        String password = packet.readString();
        
        session.getPlayerBean().getAvatar().prepareRoom(roomId, password);
    }
}
