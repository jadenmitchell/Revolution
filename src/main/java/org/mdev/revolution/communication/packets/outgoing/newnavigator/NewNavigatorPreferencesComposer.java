package org.mdev.revolution.communication.packets.outgoing.newnavigator;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class NewNavigatorPreferencesComposer extends ServerPacket {
    public NewNavigatorPreferencesComposer() {
        super(ServerPacketHeader.NewNavigatorPreferencesComposer);
        super.writeInt(68);//X
        super.writeInt(42);//Y
        super.writeInt(425);//Width
        super.writeInt(592);//Height
        super.writeBoolean(false);//Show or hide saved searches.
        super.writeInt(0);//No idea?
    }
}
