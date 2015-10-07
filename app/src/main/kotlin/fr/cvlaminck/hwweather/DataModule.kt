package fr.cvlaminck.hwweather

import android.content.Context
import com.j256.ormlite.support.ConnectionSource
import dagger.Module
import dagger.Provides
import fr.cvlaminck.hwweather.data.HwWeatherDBOpenHelper
import fr.cvlaminck.hwweather.data.dao.city.CityRepository
import fr.cvlaminck.hwweather.data.dao.city.ExternalCityIdRepository
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.ExternalCityIdEntity
import javax.inject.Singleton

@Module
public class DataModule(private val helper: HwWeatherDBOpenHelper) {

    @Provides @Singleton
    fun provideConnectionSource(): ConnectionSource = helper.connectionSource;

    @Provides
    fun provideCityRepository() = helper.getDao(CityEntity::class.java) as CityRepository;

    @Provides
    fun provideExternalCityIdRepository() = helper.getDao(ExternalCityIdEntity::class.java) as ExternalCityIdRepository;

}