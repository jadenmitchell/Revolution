package org.mdev.revolution.communication.packets.outgoing.handshake;

import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.communication.packets.outgoing.ServerPacketHeader;

public class NoobnessLevelMessageComposer extends ServerPacket {
    public NoobnessLevelMessageComposer() {
        super(ServerPacketHeader.NoobnessLevelMessageComposer);
        super.writeInt(15); // Count
        super.writeString("USE_GUIDE_TOOL");
        super.writeString("");
        super.writeBoolean(false);
        super.writeString("GIVE_GUIDE_TOURS");
        super.writeString("requirement.unfulfilled.helper_le");
        super.writeBoolean(false);
        super.writeString("JUDGE_CHAT_REVIEWS");
        super.writeString(""); // ??
        super.writeBoolean(true);
        super.writeString("VOTE_IN_COMPETITIONS");
        super.writeString(""); // ??
        super.writeBoolean(true);
        super.writeString("CALL_ON_HELPERS");
        super.writeString(""); // ??
        super.writeBoolean(false);
        super.writeString("CITIZEN");
        super.writeString(""); // ??
        super.writeBoolean(true);
        super.writeString("TRADE");
        super.writeString(""); // ??
        super.writeBoolean(true);
        super.writeString("HEIGHTMAP_EDITOR_BETA");
        super.writeString(""); // ??
        super.writeBoolean(false);
        super.writeString("EXPERIMENTAL_CHAT_BETA");
        super.writeString("requirement.unfulfilled.helper_level_2");
        super.writeBoolean(true);
        super.writeString("EXPERIMENTAL_TOOLBAR");
        super.writeString(""); // ??
        super.writeBoolean(true);
        super.writeString("BUILDER_AT_WORK");
        super.writeString(""); // ??
        super.writeBoolean(true);
        super.writeString("NAVIGATOR_PHASE_ONE_2014");
        super.writeString(""); // ??
        super.writeBoolean(false);
        super.writeString("CAMERA");
        super.writeString(""); // ??
        super.writeBoolean(false);
        super.writeString("NAVIGATOR_PHASE_TWO_2014");
        super.writeString(""); // ??
        super.writeBoolean(true);
        super.writeString("MOUSE_ZOOM");
        super.writeString(""); // ??
        super.writeBoolean(true);
        super.writeString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA");
        super.writeString(""); // ??
        super.writeBoolean(false);
    }
}