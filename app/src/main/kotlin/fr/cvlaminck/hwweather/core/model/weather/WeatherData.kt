package fr.cvlaminck.hwweather.core.model.weather

import android.os.Parcel
import android.os.Parcelable
import fr.cvlaminck.hwweather.client.resources.weather.enums.WeatherDataType
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.weather.CurrentWeatherEntity
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity

public class WeatherData public constructor(): Parcelable {
    var city: CityEntity? = null;
    var current: CurrentWeatherEntity? = null;
    var hourly: MutableList<HourlyForecastEntity> = arrayListOf();
    var daily: MutableList<DailyForecastEntity> = arrayListOf();

    private constructor(source: Parcel): this() {
        city = source.readParcelable(CityEntity::class.java.classLoader);
        val types = arrayListOf<String>()
        source.readStringList(types);

        if (types.contains(WeatherDataType.CURRENT.name)) {
            current = source.readParcelable(CurrentWeatherEntity::class.java.classLoader);
        }
        if (types.contains(WeatherDataType.HOURLY.name)) {
            source.readTypedList(hourly, HourlyForecastEntity.CREATOR);
        }
        if (types.contains(WeatherDataType.DAILY.name)) {
            source.readTypedList(daily, DailyForecastEntity.CREATOR);
        }
    }

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
        val types = this.types.map { it.name }.toList();

        dest.writeParcelable(city, 0);
        dest.writeStringList(types);
        if (this.types.contains(WeatherDataType.CURRENT)) {
            dest.writeParcelable(current, 0);
        }
        if (this.types.contains(WeatherDataType.HOURLY)) {
            dest.writeTypedList(hourly);
        }
        if (this.types.contains(WeatherDataType.DAILY)) {
            dest.writeTypedList(daily);
        }
    }

    override fun describeContents(): Int = 0;

    companion object {
        val CREATOR = object : Parcelable.Creator<WeatherData> {
            override fun createFromParcel(source: Parcel): WeatherData = WeatherData(source);
            override fun newArray(size: Int): Array<WeatherData?> = arrayOfNulls<WeatherData?>(size);
        };
    }
}