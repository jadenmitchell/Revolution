package org.mdev.revolution.communication.packets.outgoing.landingview;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class CampaignComposer extends ServerPacket {
    public CampaignComposer(String campaignString, String campaignName) {
        super(ServerPacketHeader.CampaignMessageComposer);
        super.writeString(campaignString);
        super.writeString(campaignName);
    }
}