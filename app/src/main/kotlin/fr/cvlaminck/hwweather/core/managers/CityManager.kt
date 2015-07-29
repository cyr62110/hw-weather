package fr.cvlaminck.hwweather.core.managers

import fr.cvlaminck.hwweather.data.model.City
import java.util.Arrays
import javax.inject.Inject

public class CityManager @Inject constructor() {

    fun getCities() : List<City> {
        return listOf(City("Paris", null, null, "France"));
    }

}