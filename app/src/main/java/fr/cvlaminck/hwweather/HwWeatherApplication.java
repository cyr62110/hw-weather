package fr.cvlaminck.hwweather;

import android.app.Application;

public class HwWeatherApplication extends Application {

    private ApplicationComponent component = null;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public ApplicationComponent component() {
        return component;
    }
}
