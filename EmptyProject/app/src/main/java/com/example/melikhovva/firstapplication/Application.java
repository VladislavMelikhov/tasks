package com.example.melikhovva.firstapplication;

public final class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        InstancesHolder.init(this);
    }
}
