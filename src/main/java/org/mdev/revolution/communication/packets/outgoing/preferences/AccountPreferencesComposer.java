package org.mdev.revolution.communication.packets.outgoing.preferences;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class AccountPreferencesComposer extends ServerPacket {
    public AccountPreferencesComposer() {
        super(ServerPacketHeader.AccountPreferencesComposer);

        // VOLUME
        super.writeInt(100);
        super.writeInt(100);
        super.writeInt(100);

        // CHAT PREFERENCE
        super.writeBoolean(false);

        // INVITE STATUS
        super.writeBoolean(true);

        // FOCUS PREFERENCE
        super.writeBoolean(true);

        // FRIEND BAR STATE
        super.writeInt(0);
        super.writeInt(0);
        super.writeInt(0);
    }
}
