package org.mdev.revolution.communication.packets.outgoing.inventory.achievements;


import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class AchievementsScoreComposer extends ServerPacket {
    public AchievementsScoreComposer(int achievementScore) {
        super(ServerPacketHeader.AchievementsScoreComposer);
        super.writeInt(achievementScore);
    }
}