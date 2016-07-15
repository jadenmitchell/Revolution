package org.mdev.revolution.communication.packets.outgoing.notifications;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class HabboBroadcastComposer extends ServerPacket {
    public HabboBroadcastComposer(String message) {
        this(message, "");
    }

    public HabboBroadcastComposer(String message, String url) {
        super(ServerPacketHeader.HabboBroadcastMessageComposer);
        super.writeString(message);
        super.writeString(url);
    }
}
