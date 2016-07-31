package org.mdev.revolution.game.navigator;

public enum NavigatorCategoryType {
    CATEGORY,
    FEATURED,
    POPULAR,
    RECOMMENDED,
    QUERY,
    MY_ROOMS,
    MY_FAVORITES,
    MY_GROUPS,
    MY_HISTORY,
    MY_FRIENDS_ROOMS,
    MY_HISTORY_FREQ,
    TOP_PROMOTIONS,
    PROMOTION_CATEGORY,
    MY_RIGHTS;


    public static NavigatorCategoryType getValue(String key) {
        for (NavigatorCategoryType categoryType : NavigatorCategoryType.values()) {
            if (categoryType.name().equalsIgnoreCase(key))
                return categoryType;
        }

        return null;
    }
}
