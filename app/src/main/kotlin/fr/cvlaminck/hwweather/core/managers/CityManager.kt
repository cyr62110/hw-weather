package fr.cvlaminck.hwweather.core.managers

import fr.cvlaminck.hwweather.data.dao.CityRepository
import fr.cvlaminck.hwweather.data.model.CityEntity
import javax.inject.Inject

public class CityManager @Inject constructor(
        private val cityRepository: CityRepository
) {

    fun getCities(): List<CityEntity> = cityRepository.queryForAll();

}