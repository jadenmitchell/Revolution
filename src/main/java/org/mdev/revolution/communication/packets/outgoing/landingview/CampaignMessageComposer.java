package org.mdev.revolution.communication.packets.outgoing.landingview;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class CampaignMessageComposer extends ServerPacket {
    public CampaignMessageComposer(String campaignString, String campaignName) {
        super(ServerPacketHeader.CampaignMessageComposer);
        super.writeString(campaignString);
        super.writeString(campaignName);
    }
}