package org.mdev.revolution.communication.packets.outgoing.notifications;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class HabboBroadcastMessageComposer extends ServerPacket {
    public HabboBroadcastMessageComposer(String message) {
        this(message, "");
    }

    public HabboBroadcastMessageComposer(String message, String url) {
        super(ServerPacketHeader.HabboBroadcastMessageComposer);
        super.writeString(message);
        super.writeString(url);
    }
}
