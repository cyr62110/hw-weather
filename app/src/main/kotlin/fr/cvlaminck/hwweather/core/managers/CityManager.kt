package fr.cvlaminck.hwweather.core.managers

import fr.cvlaminck.hwweather.data.model.City
import java.util.*

public class CityManager {

    fun getCities() = {
        Arrays.asList(City("Paris", null, null, "France"));
    }

}