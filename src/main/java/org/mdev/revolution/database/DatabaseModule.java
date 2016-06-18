package org.mdev.revolution.database;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.mdev.revolution.database.dao.GenericJpaDao;
import org.mdev.revolution.database.dao.PlayerDao;

import javax.inject.Inject;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new JpaPersistModule("org.mdev.revolution.jpa"));
        bind(JPAInitializer.class).asEagerSingleton();
        bind(GenericJpaDao.class).to(PlayerDao.class);
    }

    @Singleton
    public static class JPAInitializer {

        @Inject
        public JPAInitializer(final PersistService service) {
            service.start();
        }
    }
}