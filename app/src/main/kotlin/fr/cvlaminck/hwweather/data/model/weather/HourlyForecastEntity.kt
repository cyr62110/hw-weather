package fr.cvlaminck.hwweather.data.model.weather

import android.os.Parcel
import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.weather.HourlyForecastRepository
import fr.cvlaminck.hwweather.data.model.Cacheable
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.utils.nowUTC
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import java.util.Date

@DatabaseTable(tableName = "hourly", daoClass = HourlyForecastRepository::class)
public class HourlyForecastEntity public constructor(): Cacheable, Parcelable {

    private constructor(source: Parcel): this() {
        city = CityEntity();

        id = source.readInt();
        hour = LocalDateTime.parse(source.readString());
        city!!.id = source.readInt();
        // weather condition
        temperature = source.readDouble();
        cacheDate = LocalDateTime.parse(source.readString());
        expiryInSecond = source.readInt();
    }

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField(index = true, uniqueCombo = true)
    var hour: LocalDateTime? = null;

    @DatabaseField(foreign = true, index = true, uniqueCombo = true)
    var city: CityEntity? = null;

    @DatabaseField
    var condition: WeatherCondition = WeatherCondition.CLEAR;

    @DatabaseField
    var temperature: Double = 0.0;

    @DatabaseField
    override var cacheDate: LocalDateTime = nowUTC();

    @DatabaseField
    override var expiryInSecond: Int = Cacheable.ONE_HOUR;

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id as Int);
        dest.writeString(hour.toString());
        dest.writeInt(city?.id as Int);
        // weather condition
        dest.writeString(cacheDate.toString());
        dest.writeInt(expiryInSecond);
    }

    override fun describeContents(): Int = 0;

    companion object {
        val CREATOR = object : Parcelable.Creator<HourlyForecastEntity> {
            override fun createFromParcel(source: Parcel): HourlyForecastEntity = HourlyForecastEntity(source);
            override fun newArray(size: Int): Array<HourlyForecastEntity?> = arrayOfNulls<HourlyForecastEntity?>(size);
        };
    }
}