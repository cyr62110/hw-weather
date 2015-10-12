package fr.cvlaminck.hwweather.core.themes

import android.text.Spannable
import fr.cvlaminck.hwweather.core.model.weather.TemperatureUnit

interface TemperatureFormatter {
    fun formatHourly(temperatureInCelsius: Double, temperatureUnit: TemperatureUnit): Spannable;
    fun formatDaily(temperatureInCelsius: Double, temperatureUnit: TemperatureUnit): Spannable;
}