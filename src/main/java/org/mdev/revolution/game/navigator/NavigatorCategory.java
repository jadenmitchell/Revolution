package org.mdev.revolution.game.navigator;

public enum NavigatorCategory {
    OFFICIAL_VIEW,
    HOTEL_VIEW,
    MYWORLD_VIEW,
    ROOMADS_VIEW,
    QUERY;

    public static NavigatorCategory getValue(String key) {
        for (NavigatorCategory category : NavigatorCategory.values()) {
            if (category.name().equalsIgnoreCase(key))
                return category;
        }

        return null;
    }
}
