package fr.cvlaminck.hwweather.front.formatters

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import fr.cvlaminck.hwweather.R
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import javax.inject.Inject

class DateFormatter @Inject public constructor(
        private val context: Context
) {

    fun formatDayForDaily(date: DateTime): Spannable {
        val index = date.dayOfWeek - 1;
        val day = context.resources.getStringArray(R.array.threeLettersDays)[index];
        return SpannableString(day);
    }

    fun formatHourForHourlyOrCurrent(date: DateTime): String {
        val format = if (date.withTimeAtStartOfDay().equals(DateTime.now().withTimeAtStartOfDay())) {
            DateTimeFormat.forPattern("HH:mm");
        } else {
            DateTimeFormat.forPattern("dd-MM HH:mm");
        }
        return format.print(date);
    }

}