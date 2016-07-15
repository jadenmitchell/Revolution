package org.mdev.revolution.communication.packets.incoming.roomdirectory;

import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.network.sessions.Session;

public class RoomNetworkOpenConnectionEvent {
    @PacketEvent(number = ClientPacketHeader.RoomNetworkOpenConnectionMessageEvent)
    public static void openConnection(Session session, ClientPacket packet) {
    }
}
