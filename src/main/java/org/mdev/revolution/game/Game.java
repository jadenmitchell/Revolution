package org.mdev.revolution.game;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.netflix.governator.guice.lazy.LazySingleton;
import org.mdev.revolution.database.DatabaseModule;
import org.mdev.revolution.database.dao.LandingViewDao;
import org.mdev.revolution.database.dao.NavigatorDao;
import org.mdev.revolution.database.dao.PlayerDao;

import javax.inject.Inject;

@LazySingleton
public class Game {
    @Inject
    private LandingViewDao landingViewDao;

    @Inject
    private PlayerDao playerDao;

    @Inject
    private NavigatorDao navigatorDao;

    public Game() {
        Injector injector = Guice.createInjector(new DatabaseModule());
        injector.injectMembers(this);
    }

    @SuppressWarnings("unchecked")
    public LandingViewDao getLandingViewDao() {
        return landingViewDao;
    }

    @SuppressWarnings("unchecked")
    public PlayerDao getPlayerDao() {
        return playerDao;
    }

    @SuppressWarnings("unchecked")
    public NavigatorDao getNavigatorDao() {
        return navigatorDao;
    }
}
