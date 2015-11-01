package fr.cvlaminck.hwweather.front.formatters

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.utils.nowUTC
import fr.cvlaminck.hwweather.utils.todayUTC
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import javax.inject.Inject

class DateFormatter @Inject public constructor(
        private val context: Context
) {

    fun formatDayForDaily(date: LocalDateTime): Spannable {
        val index = date.dayOfWeek - 1;
        val day = context.resources.getStringArray(R.array.threeLettersDays)[index];
        return SpannableString(day);
    }

    fun formatHourForHourlyOrCurrent(date: LocalDateTime): String {
        val format = if (date.withMillisOfDay(0).equals(todayUTC())) {
            DateTimeFormat.forPattern("HH:mm");
        } else {
            DateTimeFormat.forPattern("dd-MM HH:mm");
        }
        return format.print(date);
    }

}