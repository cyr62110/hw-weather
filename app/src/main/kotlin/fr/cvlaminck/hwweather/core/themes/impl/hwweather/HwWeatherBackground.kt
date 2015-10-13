package fr.cvlaminck.hwweather.core.themes.impl.hwweather

import android.content.Context
import android.view.View
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.themes.Background

class HwWeatherBackground (
        private val context: Context
): Background {

    override fun apply(view: View) {
        view.setBackgroundColor(context.resources.getColor(R.color.mariner_blue));
    }

}