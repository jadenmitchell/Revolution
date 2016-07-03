package org.mdev.revolution.game.navigator;

import com.google.common.collect.Lists;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.database.domain.navigator.FlatCat;
import org.mdev.revolution.game.redis.RedisStorage;

import java.lang.ref.WeakReference;
import java.util.List;

public class NavigatorSearchService {
    // TODO: Cache navigator search data using Redis
    private static RedisStorage storage;

    public static WeakReference<List<FlatCat>> search(String category, String data) {
        List<FlatCat> categories = Lists.newArrayList();
        if (data.isEmpty()) {
            Revolution.getInstance().getGame().getNavigatorDao().getFlatCategories().forEach((c) -> {
                if (c.getCategory() == NavigatorCategory.valueOf(category)) {
                    categories.add(c);
                } else if (c.getCategoryType() == NavigatorCategoryType.valueOf(category)) {
                    categories.add(c);
                }
            });
        }
        return new WeakReference<>(categories);
    }
}
