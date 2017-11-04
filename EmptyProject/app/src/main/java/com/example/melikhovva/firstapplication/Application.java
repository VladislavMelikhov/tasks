package com.example.melikhovva.firstapplication;

public final class Application extends android.app.Application {

    private static Application application;

    public static Application getInstance() {
        return application;
    }

    private ApplicationToolsProvider applicationToolsProvider;

    public ApplicationToolsProvider getApplicationToolsProvider() {
        return applicationToolsProvider;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (applicationToolsProvider == null) {
            applicationToolsProvider = new ApplicationToolsProvider(this);
        }
        if (application == null) {
            application = this;
        }
    }
}