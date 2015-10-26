package fr.cvlaminck.hwweather

import com.j256.ormlite.support.ConnectionSource
import dagger.Module
import dagger.Provides
import fr.cvlaminck.hwweather.data.HwWeatherDBOpenHelper
import fr.cvlaminck.hwweather.data.dao.city.CityRepository
import fr.cvlaminck.hwweather.data.dao.city.ExternalCityIdRepository
import fr.cvlaminck.hwweather.data.dao.city.FavoriteCityRepository
import fr.cvlaminck.hwweather.data.dao.weather.DailyForecastRepository
import fr.cvlaminck.hwweather.data.dao.weather.HourlyForecastRepository
import fr.cvlaminck.hwweather.data.dao.weather.CurrentWeatherRepository
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.ExternalCityIdEntity
import fr.cvlaminck.hwweather.data.model.city.FavoriteCityEntity
import fr.cvlaminck.hwweather.data.model.weather.CurrentWeatherEntity
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity
import javax.inject.Singleton

@Module
public class DataModule(private val helper: HwWeatherDBOpenHelper) {

    @Provides @Singleton
    fun provideConnectionSource(): ConnectionSource = helper.connectionSource;

    @Provides
    fun provideCityRepository() = helper.getDao(CityEntity::class.java) as CityRepository;

    @Provides
    fun provideExternalCityIdRepository() = helper.getDao(ExternalCityIdEntity::class.java) as ExternalCityIdRepository;

    @Provides
    fun provideCurrentWeatherRepository() = helper.getDao(CurrentWeatherEntity::class.java) as CurrentWeatherRepository;

    @Provides
    fun provideHourlyForecastRepository() = helper.getDao(HourlyForecastEntity::class.java) as HourlyForecastRepository;

    @Provides
    fun provideDailyForecastRepository() = helper.getDao(DailyForecastEntity::class.java) as DailyForecastRepository;

    @Provides
    fun provideFavoriteCityRepository() = helper.getDao(FavoriteCityEntity::class.java) as FavoriteCityRepository;
}