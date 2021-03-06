package fr.cvlaminck.hwweather.data.model

import fr.cvlaminck.hwweather.utils.isAfterNowUTC
import fr.cvlaminck.hwweather.utils.isBeforeNowUTC
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime

interface Cacheable {
    companion object {
        val HALF_HOUR: Int = 30 * 60;
        val ONE_HOUR: Int = 60 * 60;
        val SIX_HOUR: Int = 6 * 60 * 60;
        val HALF_DAY: Int = 12 * 60 * 60;
        val DAY: Int = 24 * 60 * 60;
    }

    var cacheDate: LocalDateTime; //Should be stored in device timezone.
    var expiryInSecond: Int;

    val expiryInMillisecond: Int
        get() = expiryInSecond * 1000;

    val expired: Boolean
        get() = cacheDate.isAfterNowUTC()
                || cacheDate.plusMillis(expiryInMillisecond).isBeforeNowUTC();
}