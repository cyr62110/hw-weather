package fr.cvlaminck.hwweather.data.model

import android.os.Parcel
import android.os.Parcelable
import fr.cvlaminck.hwweather.data.model.WeatherCondition
import java.util.Date

public class HourlyForecastEntity(
        val hour: Date,
        val city: CityEntity,
        val condition: WeatherCondition,
        val temperature: Double
) {

}