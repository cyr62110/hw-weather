package fr.cvlaminck.hwweather.core.model.weather

import android.os.Parcel
import android.os.Parcelable
import fr.cvlaminck.hwweather.client.resources.weather.enums.WeatherDataType
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.weather.CurrentWeatherEntity
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity

public class WeatherData: Parcelable {
    var city: CityEntity? = null;
    var current: CurrentWeatherEntity? = null;
    var hourly: MutableList<HourlyForecastEntity> = arrayListOf();
    var daily: MutableList<DailyForecastEntity> = arrayListOf();

    val types: List<WeatherDataType>
        get(): List<WeatherDataType> {
            val types: MutableList<WeatherDataType> = arrayListOf();
            if (current != null) {
                types.add(WeatherDataType.CURRENT);
            }
            if (hourly.isNotEmpty()) {
                types.add(WeatherDataType.HOURLY);
            }
            if (daily.isNotEmpty()) {
                types.add(WeatherDataType.DAILY);
            }
            return types;
        };

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }

    override fun describeContents(): Int = 0;
}