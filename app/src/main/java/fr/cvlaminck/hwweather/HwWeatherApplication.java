package fr.cvlaminck.hwweather;

import android.app.Application;

import org.mybop.ormlitejodatime.OrmliteJodatimeUtils;

import fr.cvlaminck.hwweather.data.HwWeatherDBOpenHelper;

public class HwWeatherApplication extends Application {

    private ApplicationComponent component = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // We register JodaTime types for ORMLite persistence at the start of our application.
        OrmliteJodatimeUtils.registerTypes();

        component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .coreModule(new CoreModule())
                .dataModule(new DataModule(new HwWeatherDBOpenHelper(this)))
                .build();
    }

    public ApplicationComponent component() {
        return component;
    }
}
