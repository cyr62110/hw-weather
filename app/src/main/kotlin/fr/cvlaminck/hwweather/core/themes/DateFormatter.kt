package fr.cvlaminck.hwweather.core.themes

import android.text.Spannable
import org.joda.time.DateTime

interface DateFormatter {
    fun formatDayForDaily(date: DateTime): Spannable;
}