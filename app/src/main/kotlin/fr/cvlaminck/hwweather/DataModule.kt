package fr.cvlaminck.hwweather

import android.content.Context
import com.j256.ormlite.support.ConnectionSource
import dagger.Module
import dagger.Provides
import fr.cvlaminck.hwweather.data.HwWeatherDBOpenHelper
import fr.cvlaminck.hwweather.data.dao.CityRepository
import fr.cvlaminck.hwweather.data.model.CityEntity
import javax.inject.Singleton

@Module
public class DataModule(private val helper: HwWeatherDBOpenHelper) {

    @Provides @Singleton
    fun provideConnectionSource() : ConnectionSource = helper.connectionSource;

    @Provides
    fun provideCityRepository() : CityRepository = helper.getDao(CityEntity::class.java);

}