package fr.cvlaminck.hwweather

import dagger.Module
import dagger.Provides
import fr.cvlaminck.hwweather.client.HwWeatherClient
import fr.cvlaminck.hwweather.client.HwWeatherClientFactory

@Module
public class CoreModule {

    @Provides
    fun provideHwWeatherClient(): HwWeatherClient {
        return HwWeatherClientFactory()
                .baseUrl("http://hwweather-cvlaminck.rhcloud.com")
                .build();
    }

}