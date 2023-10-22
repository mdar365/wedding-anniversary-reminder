import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

class WeddingAnniversaryHandler(private val currentDate: String, private val config: AnniversaryConfig) {

  fun generateReminderRecords(records: List<CoupleRecord>): List<WeddingAnniversaryReminderRecord> {
    val weddingAnniversaryReminderRecords = mutableListOf<WeddingAnniversaryReminderRecord>()
    val parsedCurrentDate = parseIsoDateIfValid(currentDate) ?: throw Exception("currentDate not valid")

    for (record in records) {
      val parsedWeddingDate = parseIsoDateIfValid(record.weddingDate) ?: continue
      val nextAnniversaryDate = getNextAnniversaryDate(parsedCurrentDate, parsedWeddingDate)
      val anniversaryNumber = nextAnniversaryDate.year - parsedWeddingDate.year
      if (shouldSendReminder(parsedCurrentDate, nextAnniversaryDate, anniversaryNumber)) {
        weddingAnniversaryReminderRecords.add(WeddingAnniversaryReminderRecord(
          record.id,
          nextAnniversaryDate.toInstant().toString()
        ))
      }
    }

    return weddingAnniversaryReminderRecords
  }

  private fun parseIsoDateIfValid(isoDate: String?): OffsetDateTime? {
    isoDate?.let {
      return try {
        OffsetDateTime.parse(it)
      } catch (e: Exception) {
        null
      }
    }

    return null
  }

  private fun getNextAnniversaryDate(currentDate: OffsetDateTime, weddingDate: OffsetDateTime): OffsetDateTime {
    val nextAnniversaryDate = weddingDate.plusYears((currentDate.year - weddingDate.year).toLong())
    if (nextAnniversaryDate < currentDate) {
      nextAnniversaryDate.plusYears(1)
    }

    return nextAnniversaryDate
  }

  private fun shouldSendReminder(currentDate: OffsetDateTime,
                                 nextAnniversaryDate: OffsetDateTime,
                                 anniversaryNumber: Int): Boolean {
    val weeksLeft = ChronoUnit.HOURS.between(currentDate.toInstant(), nextAnniversaryDate.toInstant()) /
        HOURS_IN_A_WEEK
    val weeksBeforeReminder = getNumberOfWeeksBeforeReminder(anniversaryNumber)
    return weeksLeft <= weeksBeforeReminder
  }

  private fun getNumberOfWeeksBeforeReminder(anniversaryNumber: Int): Int {
    val descendingAnniversaryConfig = config.specialAnniversaryConfig.sortedByDescending { it.anniversary }
    for (config in descendingAnniversaryConfig) {
      if (anniversaryNumber % config.anniversary == 0) return config.reminderWeeks
    }
    return config.defaultReminderWeeks
  }
}