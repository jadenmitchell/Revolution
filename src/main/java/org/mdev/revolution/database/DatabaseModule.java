package org.mdev.revolution.database;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

import javax.inject.Inject;

public class DatabaseModule extends AbstractModule {
    public static final DatabaseModule INSTANCE = new DatabaseModule();

    @Override
    protected void configure() {
        install(new JpaPersistModule("org.mdev.revolution.jpa"));
        bind(JPAInitializer.class).asEagerSingleton();
    }

    @Singleton
    public static class JPAInitializer {

        @Inject
        public JPAInitializer(final PersistService service) {
            service.start();
        }
    }
}