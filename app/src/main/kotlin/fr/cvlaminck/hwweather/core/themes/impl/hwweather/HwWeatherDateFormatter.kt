package fr.cvlaminck.hwweather.core.themes.impl.hwweather

import android.content.Context
import android.text.Spannable
import fr.cvlaminck.hwweather.core.themes.DateFormatter
import org.joda.time.DateTime

class HwWeatherDateFormatter(
        private val context: Context
): DateFormatter {

    override fun formatDay(date: DateTime): Spannable {
        throw UnsupportedOperationException()
    }

}