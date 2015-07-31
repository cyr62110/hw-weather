package fr.cvlaminck.hwweather.core.managers

import fr.cvlaminck.hwweather.data.model.CityEntity
import java.util.Arrays
import javax.inject.Inject

public class CityManager @Inject constructor() {

    fun getCities() : List<CityEntity> {
        return listOf(CityEntity("Paris", null, null, "France"));
    }

}