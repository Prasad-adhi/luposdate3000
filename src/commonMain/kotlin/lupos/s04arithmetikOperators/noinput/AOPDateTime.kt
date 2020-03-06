package lupos.s04arithmetikOperators.noinput

import kotlin.jvm.JvmField
import lupos.s00misc.EOperatorID
import lupos.s04logicalOperators.OPBase


class AOPDateTime : AOPConstant, AOPXPathCompareable {
    override val operatorID = EOperatorID.AOPDateTimeID
    override val classname = "AOPDateTime"
    override val children: Array<OPBase> = arrayOf()
    @JvmField
    val year: Int
    @JvmField
    val month: Int
    @JvmField
    val day: Int
    @JvmField
    val hours: Int
    @JvmField
    val minutes: Int
    @JvmField
    val seconds: Int
    @JvmField
    val timezoneHours: Int
    @JvmField
    val timezoneMinutes: Int

override operator fun compareTo(other: AOPConstant): Int{
require(other is AOPDateTime)
if(year!=other.year)
	return year.compareTo(other.year)
if(month!=other.month)
	return month.compareTo(other.month)
if(day!=other.day)
	return day.compareTo(other.day)
if(hours!=other.hours)
	return hours.compareTo(other.hours)
if(minutes!=other.minutes)
	return minutes.compareTo(other.minutes)
if(seconds!=other.seconds)
	return seconds.compareTo(other.seconds)
if(timezoneHours!=other.timezoneHours)
	return timezoneHours.compareTo(other.timezoneHours)
if(timezoneMinutes!=other.timezoneMinutes)
	return timezoneMinutes.compareTo(other.timezoneMinutes)
return 0
}

    constructor() : super() {
        val time = com.soywiz.klock.DateTime.now()
        year = time.yearInt
        month = time.month1
        day = time.dayOfMonth
        hours = time.hours
        minutes = time.minutes
        seconds = time.seconds
        timezoneHours = 0
        timezoneMinutes = 0
    }

    constructor(str: String) : super() {
        if (str.length >= 10) {
            year = str.substring(1, 5).toInt()
            month = str.substring(6, 8).toInt()
            day = str.substring(9, 11).toInt()
        } else {
            year = 0
            month = 0
            day = 0
        }
        if (str.length >= 19) {
            hours = str.substring(12, 14).toInt()
            minutes = str.substring(15, 17).toInt()
            seconds = str.substring(18, 20).toInt()
        } else {
            hours = 0
            minutes = 0
            seconds = 0
        }
        if (str.length >= 25 && str[20] == '-') {
            timezoneHours = str.substring(21, 23).toInt()
            timezoneMinutes = str.substring(24, 26).toInt()
        } else if (str.length >= 20 && str[20] == 'Z') {
            timezoneHours = 0
            timezoneMinutes = 0
        } else {
            timezoneHours = -1
            timezoneMinutes = -1
        }
    }

    fun getTZ(): String {
        if (timezoneHours == 0 && timezoneMinutes == 0)
            return "Z"
        if (timezoneHours == -1 && timezoneMinutes == -1)
            return ""
        return "-${timezoneHours.toString().padStart(2, '0')}:${timezoneMinutes.toString().padStart(2, '0')}"
    }

    fun getTimeZone(): String {
        if (timezoneHours == 0 && timezoneMinutes == 0)
            return "\"PT0S\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>"
        if (timezoneHours >= 0 && timezoneMinutes == 0)
            return "\"-PT${timezoneHours}H\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>"
        return ""
    }

    override fun toXMLElement() = super.toXMLElement().addAttribute("value", valueToString())

    override fun valueToString(): String {
        if (timezoneHours == -1 && timezoneMinutes == -1)
            return "\"${year.toString().padStart(4, '0')}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
        else if (timezoneHours == 0 && timezoneMinutes == 0)
            return "\"${year.toString().padStart(4, '0')}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
        else
            return "\"${year.toString().padStart(4, '0')}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}-${timezoneHours.toString().padStart(2, '0')}:${timezoneMinutes.toString().padStart(2, '0')}\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AOPDateTime)
            return false
        return valueToString() == other.valueToString()
    }

    override fun toDouble(): Double {
        throw Exception("cannot cast AOPDateTime to Double")
    }

    override fun toInt(): Int {
        throw Exception("cannot cast AOPDateTime to Integer")
    }

    override fun toBoolean(): Boolean {
        throw Exception("cannot cast AOPDateTime to Boolean")
    }
}
