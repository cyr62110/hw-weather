package fr.cvlaminck.hwweather.core.managers

import android.content.Context
import fr.cvlaminck.hwweather.core.themes.Theme
import fr.cvlaminck.hwweather.core.themes.impl.hwweather.HwWeatherTheme

public class ThemeManager(
        private val context: Context
) {

    val theme: Theme
        get() = HwWeatherTheme(context);

}