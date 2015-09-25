package fr.cvlaminck.hwweather.data.model

interface Cacheable {
    companion object {
        val ONE_HOUR: Int = 60 * 60;
        val HALF_A_HOUR: Int = 30 * 60;
    }

    var cacheTimestamp: Long;
    var expiryInSecond: Int;
    var gracePeriodInSecond: Int;

    val expiryInMillisecond: Int
        get() = expiryInSecond * 1000;

    val gracePeriodInMillisecond: Int
        get() = gracePeriodInSecond * 1000;

    val expired: Boolean
        get() = (cacheTimestamp > System.currentTimeMillis()) || (cacheTimestamp + expiryInMillisecond < System.currentTimeMillis());

    val inGracePeriod: Boolean
        get() = expired && (cacheTimestamp + expiryInMillisecond + gracePeriodInMillisecond) > System.currentTimeMillis();
}