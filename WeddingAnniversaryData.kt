data class AnniversaryConfig(
    val defaultReminderWeeks: Int = 2,
    val specialAnniversaryConfig: List<SpecialAnniversaryConfig> = listOf(
        SpecialAnniversaryConfig(5, defaultReminderWeeks + 1),
        SpecialAnniversaryConfig(10, defaultReminderWeeks + 2)
    )
)

data class SpecialAnniversaryConfig(
    val anniversary: Int,
    val reminderWeeks: Int
)

data class CoupleRecord(
    val id: String,
    val weddingDate: String?
)

data class WeddingAnniversaryReminderRecord(
    val id: String,
    val nextAnniversary: String
)



