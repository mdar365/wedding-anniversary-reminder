import java.text.SimpleDateFormat
import java.util.*

object WeddingAnniversaryHandler {
    fun generateReminderRecords(
        currentDate: String,
        records: List<CoupleRecord>
    ): List<WeddingAnniversaryReminderRecord> {
        val weddingAnniversaryReminderRecords = listOf<WeddingAnniversaryReminderRecord>()
        val parsedCurrentDate = parseIsoDateIfValid(currentDate) ?: throw Exception("currentDate not valid")

        for (record in records) {
            val parsedWeddingDate = parseIsoDateIfValid(record.weddingDate) ?: continue
            val nextAnniversaryDate = getNextAnniversaryDate(parsedCurrentDate, parsedWeddingDate)
            val anniversaryNumber = nextAnniversaryDate.year - parsedWeddingDate.year
        }

        return weddingAnniversaryReminderRecords
    }

    private fun parseIsoDateIfValid(isoDate: String?): Date? {
        isoDate?.let {
            return try {
                val format = SimpleDateFormat("yyyy-MM-dd")
                format.parse(isoDate)
            } catch (e: Exception) {
                null
            }
        }

        return null
    }

    private fun getNextAnniversaryDate(currentDate: Date, weddingDate: Date): Date {
        val nextAnniversaryDate = weddingDate
        nextAnniversaryDate.year = currentDate.year
        if (nextAnniversaryDate.before(currentDate)) {
            nextAnniversaryDate.year = currentDate.year + 1
        }

        return nextAnniversaryDate
    }

    private fun shouldSendReminder(currentDate: Date, nextAnniversaryDate:Date, anniversaryNumber: Int): Boolean {
        return false
    }

    private fun getNumberOfWeeksBeforeReminder(anniversaryNumber: Int): Int {
        for (key in reminderWeeksSpecialConfig.keys.sortedDescending()) {
            if (anniversaryNumber % key == 0) return reminderWeeksSpecialConfig[key]!!
        }
        return NORMAL_REMINDER_WEEKS
    }
}