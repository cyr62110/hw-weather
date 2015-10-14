package fr.cvlaminck.hwweather

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import fr.cvlaminck.hwweather.front.formatters.DateFormatter
import fr.cvlaminck.hwweather.front.formatters.TemperatureFormatter
import fr.cvlaminck.hwweather.core.managers.IconSetManager
import fr.cvlaminck.hwweather.core.managers.UserPreferencesManager
import javax.inject.Singleton

@Module
public class AndroidModule(private val application: Application) {

    @Provides @Singleton fun provideApplicationContext(): Context {
        return application.applicationContext;
    }

    @Provides @Singleton fun provideIconSetManager(): IconSetManager {
        return IconSetManager(application.applicationContext);
    }

    @Provides @Singleton fun provideUserPreferencesManager(): UserPreferencesManager {
        return UserPreferencesManager(application.applicationContext);
    }
}