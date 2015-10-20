package fr.cvlaminck.hwweather.core.managers

import android.content.Context
import android.graphics.drawable.Drawable
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.weather.WeatherCondition


public class IconSetManager(
        private val context: Context) {

    fun getIconForWeatherCondition(weatherCondition: WeatherCondition): Drawable {
        val densityDpi = context.resources.displayMetrics.densityDpi;
        return context.resources.getDrawableForDensity(R.drawable.sun, densityDpi);
    }

    fun getThumbnailForWeatherCondition(weatherCondition: WeatherCondition): Drawable {
        return getIconForWeatherCondition(weatherCondition);
    }
}