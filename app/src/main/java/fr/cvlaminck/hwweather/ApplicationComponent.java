package fr.cvlaminck.hwweather;

import javax.inject.Singleton;

import dagger.Component;
import fr.cvlaminck.hwweather.front.activities.FavoriteCitiesWeatherActivity;
import fr.cvlaminck.hwweather.front.activities.RoutingStartActivity;
import fr.cvlaminck.hwweather.front.activities.WeatherActivity;
import fr.cvlaminck.hwweather.front.activities.SearchCityActivity;
import fr.cvlaminck.hwweather.front.fragments.WeatherFragment;
import fr.cvlaminck.hwweather.front.views.DailyForecastView;
import fr.cvlaminck.hwweather.front.views.HourlyForecastView;

@Singleton
@Component(modules = {AndroidModule.class, CoreModule.class, DataModule.class})
public interface ApplicationComponent {
    void inject(RoutingStartActivity activity);
    void inject(SearchCityActivity activity);
    void inject(WeatherActivity activity);
    void inject(FavoriteCitiesWeatherActivity activity);

    void inject(WeatherFragment fragment);

    void inject(HourlyForecastView view);
    void inject(DailyForecastView view);
}
