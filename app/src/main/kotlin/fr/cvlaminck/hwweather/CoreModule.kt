package fr.cvlaminck.hwweather

import dagger.Module
import dagger.Provides
import fr.cvlaminck.hwweather.client.HwWeatherClientFactory

@Module
public class CoreModule {

    @Provides
    fun provideHwWeatherClient() = {
        HwWeatherClientFactory()
                .baseUrl("http://192.168.1.10")
                .build();
    }

}