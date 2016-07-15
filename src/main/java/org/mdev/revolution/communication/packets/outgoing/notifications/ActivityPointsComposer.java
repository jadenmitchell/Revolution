package org.mdev.revolution.communication.packets.outgoing.notifications;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class ActivityPointsComposer extends ServerPacket {
    public ActivityPointsComposer(int duckets, int diamonds) {
        super(ServerPacketHeader.ActivityPointsMessageComposer);
        super.writeInt(11);
        super.writeInt(0); // Pixels
        super.writeInt(duckets);
        super.writeInt(1); // Snowflakes
        super.writeInt(16);
        super.writeInt(2); // Hearts
        super.writeInt(15);
        super.writeInt(3); // Gift points
        super.writeInt(14);
        super.writeInt(4); // Shells
        super.writeInt(13);
        super.writeInt(5); // Diamonds
        super.writeInt(diamonds);
        super.writeInt(101); // Snowflakes
        super.writeInt(10);
        super.writeInt(102);
        super.writeInt(0); // --
        super.writeInt(103); // Stars
        super.writeInt(0);
        super.writeInt(104); // Clouds
        super.writeInt(0);
        super.writeInt(105); // Diamonds
        super.writeInt(0);
    }
}
