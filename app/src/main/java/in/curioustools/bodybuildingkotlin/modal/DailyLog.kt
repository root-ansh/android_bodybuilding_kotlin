package `in`.curioustools.bodybuildingkotlin.modal

import java.text.SimpleDateFormat
import java.util.*

data class DailyLog(val id: String, val date: String, val exerciseCount: Int) {
    constructor(date: String,exerciseCount: Int):this(getID(),date,exerciseCount)

    fun ddd(): Unit {
        this@DailyLog.date
    }

    companion object{
        private fun getID() = UUID.randomUUID().toString()
        fun getRandomDate(): String {
            val date = 1 + Random().nextInt() % 30
            val month = 1 + date % 12
            return SimpleDateFormat(
                "EEE, $date $month yyyy",
                Locale.ROOT
            ).format(Calendar.getInstance().time)
        }
    }
}
