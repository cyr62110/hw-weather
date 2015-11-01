package fr.cvlaminck.hwweather.utils

import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime

fun nowUTC(): LocalDateTime = LocalDateTime.now(DateTimeZone.UTC);

fun todayUTC(): LocalDateTime = nowUTC().withMillisOfDay(0);

fun LocalDateTime.isAfterNowUTC(): Boolean = this.isAfter(nowUTC());

fun LocalDateTime.isBeforeNowUTC(): Boolean = this.isBefore(nowUTC());
