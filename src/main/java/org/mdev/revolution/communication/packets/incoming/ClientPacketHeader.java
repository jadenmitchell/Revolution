package org.mdev.revolution.communication.packets.incoming;

public final class ClientPacketHeader {
    public static final short VersionCheckMessageEvent = 4000;//4000
    public static final short InitDiffieHandshakeMessageEvent = 316;
    public static final short CompleteDiffieHandshakeMessageEvent = 3847;
    public static final short SSOTicketMessageEvent = 1778;
    public static final short UniqueIDMessageEvent = 1471;
    public static final short InfoRetrieveMessageEvent = 186;
    public static final short GetPromoArticlesEvent = 3895;
    public static final short RefreshCampaignEvent = 3544;
    public static final short EventLogMessageEvent = 2386;
    public static final short GetUserFlatCatsMessageEvent = 3672;
    public static final short NewNavigatorInitEvent = 882;
    public static final short NewNavigatorSearchEvent = 2722;
}
