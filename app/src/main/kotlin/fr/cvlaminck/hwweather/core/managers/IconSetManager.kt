package fr.cvlaminck.hwweather.core.managers

import android.content.Context
import android.graphics.drawable.Drawable
import fr.cvlaminck.hwweather.data.model.WeatherCondition
import fr.cvlaminck.hwweather.R


public class IconSetManager(
        private val context: Context) {

    fun getIconForWeatherCondition(weatherCondition: WeatherCondition): Drawable {
        return context.getResources().getDrawable(R.mipmap.sun);
    }

    fun getThumbnailForWeatherCondition(weatherCondition: WeatherCondition): Drawable {
        return context.getResources().getDrawable(R.mipmap.sun);
    }
}