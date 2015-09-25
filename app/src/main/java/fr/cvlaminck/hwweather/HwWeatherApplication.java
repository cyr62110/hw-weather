package fr.cvlaminck.hwweather;

import android.app.Application;

import fr.cvlaminck.hwweather.data.HwWeatherDBOpenHelper;

public class HwWeatherApplication extends Application {

    private ApplicationComponent component = null;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .dataModule(new DataModule(new HwWeatherDBOpenHelper(this)))
                .build();
    }

    public ApplicationComponent component() {
        return component;
    }
}
