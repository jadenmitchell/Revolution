package org.mdev.revolution.communication.packets.outgoing.inventory.clothes;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class FigureSetIdsComposer extends ServerPacket {
    public FigureSetIdsComposer() {
        super(ServerPacketHeader.FigureSetIdsComposer);
        super.writeInt(0); // TODO
    }
}
