package fr.cvlaminck.hwweather.core.themes.impl.hwweather

import android.content.Context
import android.graphics.drawable.Drawable
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.themes.IconSet
import fr.cvlaminck.hwweather.data.model.weather.WeatherCondition

public class HwWeatherIconSet(
        private val context: Context
): IconSet {
    override fun getIconForWeatherCondition(weatherCondition: WeatherCondition): Drawable {
        return context.getResources().getDrawable(R.mipmap.sun);
    }

    override fun getThumbnailForWeatherCondition(weatherCondition: WeatherCondition): Drawable {
        return context.getResources().getDrawable(R.mipmap.sun);
    }
}