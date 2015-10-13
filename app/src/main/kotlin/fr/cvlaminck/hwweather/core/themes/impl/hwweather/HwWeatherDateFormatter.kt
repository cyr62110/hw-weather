package fr.cvlaminck.hwweather.core.themes.impl.hwweather

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.themes.DateFormatter
import org.joda.time.DateTime

class HwWeatherDateFormatter(
        private val context: Context
) : DateFormatter {

    override fun formatDayForDaily(date: DateTime): Spannable {
        val index = date.dayOfWeek - 1;
        val day = context.resources.getStringArray(R.array.threeLettersDays)[index];
        return SpannableString(day);
    }

}