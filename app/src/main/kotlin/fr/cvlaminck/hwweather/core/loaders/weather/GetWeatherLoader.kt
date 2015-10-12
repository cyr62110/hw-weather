package fr.cvlaminck.hwweather.core.loaders.weather

import android.content.Context
import fr.cvlaminck.hwweather.client.resources.weather.enums.WeatherDataType
import fr.cvlaminck.hwweather.core.loaders.HwWeatherOperationLoader
import fr.cvlaminck.hwweather.core.managers.WeatherManager
import fr.cvlaminck.hwweather.core.model.weather.WeatherData
import fr.cvlaminck.hwweather.data.model.city.CityEntity

class GetWeatherLoader(
        context: Context,
        val city: CityEntity,
        val typesToRefresh: Collection<WeatherDataType>,
        val weatherManager: WeatherManager
) : HwWeatherOperationLoader<WeatherData>(context) {

    override fun doRequest(): WeatherData {
        return weatherManager.getWeatherForCity(city, typesToRefresh);
    }

}