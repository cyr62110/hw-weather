package fr.cvlaminck.hwweather.core.themes.impl.hwweather

import android.content.Context
import fr.cvlaminck.hwweather.core.themes.IconSet
import fr.cvlaminck.hwweather.core.themes.TemperatureFormatter
import fr.cvlaminck.hwweather.core.themes.Theme

public class HwWeatherTheme(
        private val context: Context
): Theme {

    override val temperatureFormatter: TemperatureFormatter
        get() = HwWeatherTemperatureFormatter();

    override val iconSet: IconSet
        get() = HwWeatherIconSet(context);

}