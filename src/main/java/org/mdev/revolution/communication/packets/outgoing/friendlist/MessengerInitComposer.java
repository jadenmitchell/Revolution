package org.mdev.revolution.communication.packets.outgoing.friendlist;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class MessengerInitComposer extends ServerPacket {
    public MessengerInitComposer() {
        super(ServerPacketHeader.MessengerInitMessageComposer);
        super.writeInt(5000);//Friends max.
        super.writeInt(300);
        super.writeInt(800);
        super.writeInt(1100);
        super.writeInt(0);
    }
}
