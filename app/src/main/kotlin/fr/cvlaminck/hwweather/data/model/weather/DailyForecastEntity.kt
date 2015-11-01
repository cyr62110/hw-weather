package fr.cvlaminck.hwweather.data.model.weather

import android.os.Parcel
import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.weather.DailyForecastRepository
import fr.cvlaminck.hwweather.data.model.Cacheable
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.utils.nowUTC
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime

@DatabaseTable(tableName = "daily", daoClass = DailyForecastRepository::class)
public class DailyForecastEntity public constructor() : Cacheable, Parcelable {

    private constructor(source: Parcel): this() {
        city = CityEntity();

        id = source.readInt();
        day = LocalDateTime.parse(source.readString());
        city!!.id = source.readInt();
        // weather condition
        minTemperatureInCelsius = source.readDouble();
        maxTemperatureInCelsius = source.readDouble();
        cacheDate = LocalDateTime.parse(source.readString());
        expiryInSecond = source.readInt();
    }

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField(index = true, uniqueCombo = true)
    var day: LocalDateTime? = null;

    @DatabaseField(foreign = true, index = true, uniqueCombo = true)
    var city: CityEntity? = null;

    @DatabaseField
    var condition: WeatherCondition = WeatherCondition.CLEAR;

    @DatabaseField
    var minTemperatureInCelsius: Double = 0.0;

    @DatabaseField
    var maxTemperatureInCelsius: Double = 0.0;

    @DatabaseField
    override var cacheDate: LocalDateTime = nowUTC();

    @DatabaseField
    override var expiryInSecond: Int = Cacheable.SIX_HOUR;

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id as Int);
        dest.writeString(day.toString());
        // weather condition
        dest.writeDouble(minTemperatureInCelsius);
        dest.writeDouble(maxTemperatureInCelsius);
        dest.writeString(cacheDate.toString());
        dest.writeInt(expiryInSecond);
    }

    override fun describeContents(): Int = 0;

    companion object {
        val CREATOR = object : Parcelable.Creator<DailyForecastEntity> {
            override fun createFromParcel(source: Parcel): DailyForecastEntity = DailyForecastEntity(source);
            override fun newArray(size: Int): Array<DailyForecastEntity?> = arrayOfNulls<DailyForecastEntity?>(size);
        };
    }
}