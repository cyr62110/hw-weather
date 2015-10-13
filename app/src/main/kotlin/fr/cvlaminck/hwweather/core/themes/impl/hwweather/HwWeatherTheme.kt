package fr.cvlaminck.hwweather.core.themes.impl.hwweather

import android.content.Context
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.themes.*
import fr.cvlaminck.hwweather.data.model.city.CityEntity

public class HwWeatherTheme(
        private val context: Context
): Theme {

    override val temperatureFormatter: TemperatureFormatter
        get() = HwWeatherTemperatureFormatter(context);

    override val dateFormatter: DateFormatter
        get() = HwWeatherDateFormatter(context);

    override val iconSet: IconSet
        get() = HwWeatherIconSet(context);

    override fun getBackground(city: CityEntity): Background {
        return HwWeatherBackground(context);
    }

    override fun getApplicationTint(city: CityEntity): Int {
        return context.resources.getColor(R.color.mariner_blue);
    }

}