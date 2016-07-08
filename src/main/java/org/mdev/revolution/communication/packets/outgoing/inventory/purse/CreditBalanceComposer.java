package org.mdev.revolution.communication.packets.outgoing.inventory.purse;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class CreditBalanceComposer extends ServerPacket {
    public CreditBalanceComposer(int credits) {
        super(ServerPacketHeader.CreditBalanceComposer);
        super.writeString(credits + ".0");
    }
}
