package org.mdev.revolution.communication.packets.outgoing.friendlist;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class FriendDataComposer extends ServerPacket {
    public FriendDataComposer() {
        super(ServerPacketHeader.FriendDataMessageComposer);
        super.writeInt(1);
        super.writeInt(0);
        super.writeInt(0); // Friend count
    }
}
