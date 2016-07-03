package org.mdev.revolution.communication.packets.incoming.landingview;


import org.mdev.revolution.communication.packets.PacketEvent;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.communication.packets.incoming.ClientPacketHeader;
import org.mdev.revolution.communication.packets.outgoing.landingview.CampaignMessageComposer;
import org.mdev.revolution.network.sessions.Session;

public class RefreshCampaignEvent {
    @PacketEvent(number = ClientPacketHeader.RefreshCampaignEvent)
    public static void refreshCampaigns(Session session, ClientPacket packet) {
        String campaigns = packet.readString();

        if (campaigns.contains("gamesmaker")) {
            return;
        }

        String name = "";
        String[] parser = campaigns.split(";");

        for (int i = 0; i < parser.length; i++) {
            if (parser[i].isEmpty() || parser[i].endsWith(",")) {
                continue;
            }

            String[] data = parser[i].split(",");
            name = data[1];
        }

        session.sendPacket(new CampaignMessageComposer(campaigns, name));
    }
}
