package fr.cvlaminck.hwweather.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import fr.cvlaminck.hwweather.data.model.CityEntity
import fr.cvlaminck.hwweather.data.model.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.HourlyForecastEntity
import fr.cvlaminck.hwweather.data.model.WeatherEntity

public class HwWeatherDBOpenHelper(context: Context) : OrmLiteSqliteOpenHelper(context, HwWeatherDBOpenHelper.DATABASE_NAME, null, HwWeatherDBOpenHelper.VERSION) {
    companion object {
        val VERSION = 1;
        val DATABASE_NAME = "hwweather";
    }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        val entityClasses = listOf(CityEntity::class, WeatherEntity::class, DailyForecastEntity::class, HourlyForecastEntity::class);
        for(entityClass in entityClasses) {
            TableUtils.createTableIfNotExists(connectionSource, entityClass.java);
        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
    }
}