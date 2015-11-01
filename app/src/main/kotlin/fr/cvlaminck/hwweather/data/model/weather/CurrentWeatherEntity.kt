package fr.cvlaminck.hwweather.data.model.weather

import android.os.Parcel
import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.weather.CurrentWeatherRepository
import fr.cvlaminck.hwweather.data.model.Cacheable
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.utils.nowUTC
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Instant
import org.joda.time.LocalDateTime

@DatabaseTable(tableName = "current", daoClass = CurrentWeatherRepository::class)
public class CurrentWeatherEntity public constructor(): Cacheable, Parcelable {

    private constructor(source: Parcel): this() {
        city = CityEntity();

        id = source.readInt();
        hour = LocalDateTime.parse(source.readString());
        city!!.id = source.readInt();
        temperature = source.readDouble();
        // weather condition
        cacheDate = LocalDateTime.parse(source.readString());
        expiryInSecond = source.readInt();
    }

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField(foreign = true, unique = true)
    var city: CityEntity? = null;

    var hour: LocalDateTime? = nowUTC();

    @DatabaseField
    var temperature: Double = 0.0;

    @DatabaseField
    var condition: WeatherCondition = WeatherCondition.CLEAR;

    @DatabaseField
    override var cacheDate: LocalDateTime = nowUTC();

    @DatabaseField
    override var expiryInSecond: Int = Cacheable.HALF_HOUR;

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id as Int);
        dest.writeString(hour.toString());
        dest.writeInt(city?.id as Int);
        dest.writeDouble(temperature);
        // weather condition
        dest.writeString(cacheDate.toString());
        dest.writeInt(expiryInSecond);
    }

    override fun describeContents(): Int = 0;

    companion object {
        val CREATOR = object : Parcelable.Creator<CurrentWeatherEntity> {
            override fun createFromParcel(source: Parcel): CurrentWeatherEntity = CurrentWeatherEntity(source);
            override fun newArray(size: Int): Array<CurrentWeatherEntity?> = arrayOfNulls<CurrentWeatherEntity?>(size);
        };
    }
}