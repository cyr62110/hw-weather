package fr.cvlaminck.hwweather.core.model.weather

import fr.cvlaminck.hwweather.client.resources.weather.enums.WeatherDataType
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity

public class WeatherData {
    var city: CityEntity? = null;
    var daily: MutableList<DailyForecastEntity> = arrayListOf();

    val types: List<WeatherDataType>
        get(): List<WeatherDataType> {
            val types: MutableList<WeatherDataType> = arrayListOf();
            if (daily.isNotEmpty()) {
                types.add(WeatherDataType.DAILY);
            }
            //FIXME add other types;
            return types;
        };
}