package eapli.base.persistence.impl.inmemory;

import eapli.base.infrastructure.bootstrapers.BaseBootstrapper;

final class InMemoryInitializer {

    private static class LazyHolder {
        private static final InMemoryInitializer INSTANCE = new InMemoryInitializer();

        private LazyHolder() {
        }
    }

    private InMemoryInitializer() {
        // to ensure some default test data is available, specially when using
        // in memory persistence
        new BaseBootstrapper().execute();
    }

    private void initialize() {
        // nothing to do; data has already been initialized in the singleton
        // constructor.
    }

    public static void init() {
        LazyHolder.INSTANCE.initialize();
    }
}
