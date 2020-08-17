package com.notify.api.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/*

class TimeConvertorUtil {


}

fun main() {

    val DATE_FORMAT: String = "dd-M-yyyy hh:mm:ss a";
    val dateInString = "22-1-2015 10:15:55 AM"
    val ldt = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern(DATE_FORMAT))

    val singaporeZoneId = ZoneId.of("Asia/Singapore")
    println("TimeZone : $singaporeZoneId")

    //LocalDateTime + ZoneId = ZonedDateTime

    //LocalDateTime + ZoneId = ZonedDateTime
    val asiaZonedDateTime = ldt.atZone(singaporeZoneId)
    println("Date (Singapore) : $asiaZonedDateTime")

    val newYokZoneId = ZoneId.of("America/New_York")
    println("TimeZone : $newYokZoneId")

    val nyDateTime = asiaZonedDateTime.withZoneSameInstant(newYokZoneId)
    println("Date (New York) : $nyDateTime")

    val format = DateTimeFormatter.ofPattern(DATE_FORMAT)
    println("\n---DateTimeFormatter---")
    println("Date (Singapore) : " + format.format(asiaZonedDateTime))
    println("Date (New York) : " + format.format(nyDateTime))


}*/
