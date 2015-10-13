package fr.cvlaminck.hwweather.core.themes

import fr.cvlaminck.hwweather.data.model.city.CityEntity

public interface  Theme {

    val temperatureFormatter: TemperatureFormatter;

    val dateFormatter: DateFormatter;

    val iconSet: IconSet;

    fun getBackground(city: CityEntity): Background;

    fun getApplicationTint(city: CityEntity): Int;
}