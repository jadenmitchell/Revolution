package org.mdev.revolution.communication.packets.outgoing.notifications;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class MOTDNotificationComposer extends ServerPacket {
    public MOTDNotificationComposer(String message) {
        super(ServerPacketHeader.MOTDNotificationComposer);
        super.writeInt(1);
        super.writeString(message);
    }
}
