const val NORMAL_REMINDER_WEEKS = 2

val reminderWeeksSpecialConfig: Map<Int, Int> = mapOf(
    5 to NORMAL_REMINDER_WEEKS + 1,
    10 to NORMAL_REMINDER_WEEKS + 2
)

data class CoupleRecord(
    val id: String,
    val weddingDate: String?
)

data class WeddingAnniversaryReminderRecord(
    val id: String,
    val nextAnniversary: String
)



