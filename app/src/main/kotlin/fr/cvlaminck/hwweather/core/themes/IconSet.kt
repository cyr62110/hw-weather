package fr.cvlaminck.hwweather.core.themes

import android.graphics.drawable.Drawable
import fr.cvlaminck.hwweather.data.model.weather.WeatherCondition

interface IconSet {
    fun getIconForWeatherCondition(weatherCondition: WeatherCondition): Drawable;
    fun getThumbnailForWeatherCondition(weatherCondition: WeatherCondition): Drawable;
}