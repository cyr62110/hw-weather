package fr.cvlaminck.hwweather.core.managers

import dagger.Component
import fr.cvlaminck.hwweather.data.model.City
import java.util.*

@Component
public class CityManager {

    fun getCities() = {
        Arrays.asList(City("Paris", null, null, "France"));
    }

}