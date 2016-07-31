package org.mdev.revolution.communication.packets.outgoing.friendlist;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class FriendRequestsComposer extends ServerPacket {
    public FriendRequestsComposer() {
        super(ServerPacketHeader.FriendRequestsMessageComposer);
        super.writeInt(1);
        super.writeInt(1);
        super.writeInt(2);
        super.writeString("Moonshine");
        super.writeString("hr-828-45.ch-215-66.hd-180-2.sh-300-1408.lg-285-82");
    }
}
