package fr.cvlaminck.hwweather.data.model

import org.joda.time.DateTime

interface Cacheable {
    companion object {
        val ONE_HOUR: Int = 60 * 60;
    }

    var cacheDate: DateTime; //Should be stored in device timezone.
    var expiryInSecond: Int;

    val expiryInMillisecond: Int
        get() = expiryInSecond * 1000;

    val expired: Boolean
        get() = cacheDate.isAfterNow
                || cacheDate.plusMillis(expiryInMillisecond).isBeforeNow;
}