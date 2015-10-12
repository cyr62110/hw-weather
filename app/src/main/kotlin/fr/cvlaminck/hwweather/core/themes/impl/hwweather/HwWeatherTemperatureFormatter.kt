package fr.cvlaminck.hwweather.core.themes.impl.hwweather

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import fr.cvlaminck.hwweather.core.model.weather.TemperatureUnit
import fr.cvlaminck.hwweather.core.themes.TemperatureFormatter

class HwWeatherTemperatureFormatter(
        private val context: Context
) : TemperatureFormatter {
    override fun formatHourly(temperatureInCelsius: Double, temperatureUnit: TemperatureUnit): Spannable {
        throw UnsupportedOperationException()
    }

    override fun formatDaily(temperatureInCelsius: Double, temperatureUnit: TemperatureUnit): Spannable {
        val temperature = temperatureUnit.convertFromCelsius(temperatureInCelsius).toString();

        val spannable = SpannableStringBuilder();
        spannable.append(temperature);
        spannable.append(temperatureUnit.symbol);
        return spannable;
    }
}