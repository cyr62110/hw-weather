package fr.cvlaminck.hwweather.front.formatters

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import fr.cvlaminck.hwweather.core.model.weather.TemperatureUnit
import java.text.DecimalFormat
import javax.inject.Inject

class TemperatureFormatter @Inject public constructor(
        private val context: Context
) {
    private val decimalFormat: DecimalFormat = DecimalFormat("###.#");

    fun formatHourly(temperatureInCelsius: Double, temperatureUnit: TemperatureUnit): Spannable {
        throw UnsupportedOperationException()
    }

    fun formatDaily(temperatureInCelsius: Double, temperatureUnit: TemperatureUnit): Spannable {
        val temperature = temperatureUnit.convertFromCelsius(temperatureInCelsius);
        val sTemperature = decimalFormat.format(temperature);

        val spannable = SpannableStringBuilder();
        spannable.append(sTemperature);
        return spannable;
    }
}