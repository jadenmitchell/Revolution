package org.mdev.revolution.database.dao.landingview;

import org.mdev.revolution.database.dao.Dao;
import org.mdev.revolution.database.domain.landingview.Promotion;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class LandingViewDao {
    @Inject
    Dao<Promotion, Integer> promotions;

    @Inject
    @PostConstruct
    public void initialize() {
        promotions.setClazz(Promotion.class);
    }

    public List<Promotion> getPromos() {
        return Collections.unmodifiableList(promotions.findAll());
    }
}
