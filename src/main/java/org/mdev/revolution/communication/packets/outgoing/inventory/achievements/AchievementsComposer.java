package org.mdev.revolution.communication.packets.outgoing.inventory.achievements;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class AchievementsComposer extends ServerPacket {
    public AchievementsComposer() {
        super(ServerPacketHeader.AchievementsComposer);
        super.writeInt(0);
    }
}