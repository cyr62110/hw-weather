package fr.cvlaminck.hwweather;

import javax.inject.Singleton;

import dagger.Component;
import fr.cvlaminck.hwweather.front.activities.ForecastActivity;
import fr.cvlaminck.hwweather.front.fragments.CityForecastFragment;
import fr.cvlaminck.hwweather.front.views.DailyForecastView;
import fr.cvlaminck.hwweather.front.views.HourlyForecastView;

@Singleton
@Component(modules = {AndroidModule.class, DataModule.class})
public interface ApplicationComponent {
    void inject(ForecastActivity activity);

    void inject(CityForecastFragment fragment);

    void inject(HourlyForecastView view);
    void inject(DailyForecastView view);
}
