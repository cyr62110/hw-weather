package fr.cvlaminck.hwweather;

import javax.inject.Singleton;

import dagger.Component;
import fr.cvlaminck.hwweather.front.activities.ForecastActivity;

@Singleton
@Component(modules = {AndroidModule.class})
public interface ApplicationComponent {
    public void inject(ForecastActivity activity);
}
