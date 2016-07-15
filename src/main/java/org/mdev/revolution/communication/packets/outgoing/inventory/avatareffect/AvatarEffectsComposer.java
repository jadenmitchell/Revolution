package org.mdev.revolution.communication.packets.outgoing.inventory.avatareffect;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;
import org.mdev.revolution.game.rooms.avatar.effects.AvatarEffect;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AvatarEffectsComposer extends ServerPacket {
    @NotNull
    public AvatarEffectsComposer(List<AvatarEffect> effects) {
        super(ServerPacketHeader.AvatarEffectsMessageComposer);

        // Until Avatar Effects are cached
        if (effects == null) {
            super.writeInt(0);
            return;
        }

        super.writeInt(effects.size());
        effects.forEach((effect) -> {
            super.writeInt(effect.getSpriteId());
            super.writeInt(0); // Type: Hand = 0, Full = 1
            super.writeInt((int)effect.getDuration());
            super.writeInt(effect.isActivated() ? effect.getQuantity() - 1 : effect.getQuantity());
            super.writeInt(effect.isActivated() ? (int)effect.getTimeLeft() : -1);
        });
    }
}