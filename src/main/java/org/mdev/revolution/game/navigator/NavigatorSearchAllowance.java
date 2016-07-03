package org.mdev.revolution.game.navigator;

public enum NavigatorSearchAllowance {
    NOTHING,
    SHOW_MORE,
    GO_BACK;

    public static int getIntValue(NavigatorSearchAllowance searchAllowance) {
        switch(searchAllowance) {
            default:
            case NOTHING:
                return 0;
            case SHOW_MORE:
                return 1;
            case GO_BACK:
                return 2;
        }
    }
}
