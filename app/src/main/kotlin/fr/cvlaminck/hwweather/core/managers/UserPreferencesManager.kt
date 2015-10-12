package fr.cvlaminck.hwweather.core.managers

import android.content.Context
import fr.cvlaminck.hwweather.core.model.weather.TemperatureUnit

class UserPreferencesManager(
        private val context: Context
) {

    var temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS;

}