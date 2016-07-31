package org.mdev.revolution.game.navigator;

import com.google.common.collect.Lists;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.database.domain.navigator.FlatCat;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public final class NavigatorSearchService {
    private static final HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
    private static final Map<String, List<FlatCat>> hCache = hzInstance.getMap("default");

    public static WeakReference<List<FlatCat>> search(final String category, String data) {
        List<FlatCat> categories = Lists.newArrayList();
        if (hCache.containsKey(data)) {
            return new WeakReference<>(hCache.get(data));
        }
        if (data.isEmpty()) {
            Revolution.getInstance().getGame().getNavigatorDao().getFlatCategories().forEach((c) -> {
                if (c.getCategory() == NavigatorCategory.getValue(category)) {
                    categories.add(c);
                } else if (c.getCategoryType() == NavigatorCategoryType.getValue(category)) {
                    categories.add(c);
                }
            });
        }
        hCache.put(data, categories);
        return new WeakReference<>(categories);
    }
}
