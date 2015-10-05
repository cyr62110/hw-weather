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
                .baseUrl("http://192.168.1.10:8080")
                .build();
    }

}