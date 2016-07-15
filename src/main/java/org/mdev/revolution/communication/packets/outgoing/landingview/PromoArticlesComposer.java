package org.mdev.revolution.communication.packets.outgoing.landingview;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;
import org.mdev.revolution.database.domain.landingview.Promotion;

import java.util.List;

public class PromoArticlesComposer extends ServerPacket {
    public PromoArticlesComposer(List<Promotion> promos) {
        super(ServerPacketHeader.PromoArticlesMessageComposer);
        super.writeInt(promos.size());
        promos.forEach((promo) -> {
            super.writeInt(promo.getId());
            super.writeString(promo.getTitle());
            super.writeString(promo.getText());
            super.writeString(promo.getButtonText());
            super.writeInt(promo.getButtonType()); // Link type 0 and 3
            super.writeString(promo.getButtonLink());
            super.writeString(promo.getImageLink());
        });
    }
}
