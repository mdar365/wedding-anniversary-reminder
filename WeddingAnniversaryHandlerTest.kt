import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class WeddingAnniversaryHandlerTest {
    private val currentDateIso = Date(123, 9, 21, 9,35).toInstant().toString()

    @Test
    fun testDefaultReminderWeeksReturnsReminder() {
        val config = AnniversaryConfig(defaultReminderWeeks = 1)
        val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, config)

        val coupleId = "1234"
        val weddingDate = Date(122, 9, 28, 6, 30)
        val weddingDateIso = weddingDate.toInstant().toString()
        val coupleRecord = CoupleRecord(id = coupleId, weddingDateIso)
        val reminderRecord = weddingAnniversaryHandler.generateReminderRecords(listOf(coupleRecord))

        val nextAnniversaryDate = weddingDate.clone() as Date
        nextAnniversaryDate.year = weddingDate.year + 1
        val expectedReminderRecord = WeddingAnniversaryReminderRecord(
            coupleId,
            nextAnniversaryDate.toInstant().toString()
        )
        assertEquals(listOf(expectedReminderRecord), reminderRecord)
    }

    @Test
    fun testDefaultReminderWeeksDoesNotReturnReminder() {
        val config = AnniversaryConfig(defaultReminderWeeks = 1)
        val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, config)

        val coupleId = "1234"
        val weddingDate = Date(122, 9, 30, 6, 30)
        val weddingDateIso = weddingDate.toInstant().toString()
        val coupleRecord = CoupleRecord(id = coupleId, weddingDateIso)
        val reminderRecord = weddingAnniversaryHandler.generateReminderRecords(listOf(coupleRecord))

        assertEquals(listOf(), reminderRecord)
    }

    @Test
    fun testSpecialConfigReturnsReminder() {
        val specialConfig = SpecialAnniversaryConfig(anniversary = 2, reminderWeeks = 1)
        val config = AnniversaryConfig(specialAnniversaryConfig = listOf(specialConfig))
        val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, config)

        val coupleId = "1234"
        val weddingDate = Date(121, 9, 28, 6, 30)
        val weddingDateIso = weddingDate.toInstant().toString()
        val coupleRecord = CoupleRecord(id = coupleId, weddingDateIso)
        val reminderRecord = weddingAnniversaryHandler.generateReminderRecords(listOf(coupleRecord))

        val nextAnniversaryDate = weddingDate.clone() as Date
        nextAnniversaryDate.year = weddingDate.year + 2
        val expectedReminderRecord = WeddingAnniversaryReminderRecord(
            coupleId,
            nextAnniversaryDate.toInstant().toString()
        )
        assertEquals(listOf(expectedReminderRecord), reminderRecord)
    }

    @Test
    fun testSpecialConfigDoesNotReturnReminder() {
        val specialConfig = SpecialAnniversaryConfig(anniversary = 2, reminderWeeks = 1)
        val config = AnniversaryConfig(specialAnniversaryConfig = listOf(specialConfig))
        val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, config)

        val coupleId = "1234"
        val weddingDate = Date(121, 9, 30, 6, 30)
        val weddingDateIso = weddingDate.toInstant().toString()
        val coupleRecord = CoupleRecord(id = coupleId, weddingDateIso)
        val reminderRecord = weddingAnniversaryHandler.generateReminderRecords(listOf(coupleRecord))

        assertEquals(listOf(), reminderRecord)
    }

    @Test
    fun testMultipleSpecialConfigOnlyBiggestAnniversaryApplies() {
        val specialConfig = listOf(
            SpecialAnniversaryConfig(anniversary = 2, reminderWeeks = 2),
            SpecialAnniversaryConfig(anniversary = 1, reminderWeeks = 1)
        )

        val config = AnniversaryConfig(specialAnniversaryConfig = specialConfig)
        val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, config)

        val coupleId = "1234"
        val weddingDate = Date(121, 10, 4, 6, 30)
        val weddingDateIso = weddingDate.toInstant().toString()
        val coupleRecord = CoupleRecord(id = coupleId, weddingDateIso)
        val reminderRecord = weddingAnniversaryHandler.generateReminderRecords(listOf(coupleRecord))

        val nextAnniversaryDate = weddingDate.clone() as Date
        nextAnniversaryDate.year = weddingDate.year + 2
        val expectedReminderRecord = WeddingAnniversaryReminderRecord(
            coupleId,
            nextAnniversaryDate.toInstant().toString()
        )
        assertEquals(listOf(expectedReminderRecord), reminderRecord)
    }

    @Test
    fun testMultipleSpecialConfigButDefaultIsUsed() {
        val specialConfig = listOf(
            SpecialAnniversaryConfig(anniversary = 10, reminderWeeks = 4),
            SpecialAnniversaryConfig(anniversary = 5, reminderWeeks = 3)
        )

        val config = AnniversaryConfig(defaultReminderWeeks = 1, specialAnniversaryConfig = specialConfig)
        val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, config)

        val coupleId = "1234"
        val weddingDate = Date(121, 9, 28, 6, 30)
        val weddingDateIso = weddingDate.toInstant().toString()
        val coupleRecord = CoupleRecord(id = coupleId, weddingDateIso)
        val reminderRecord = weddingAnniversaryHandler.generateReminderRecords(listOf(coupleRecord))

        val nextAnniversaryDate = weddingDate.clone() as Date
        nextAnniversaryDate.year = weddingDate.year + 2
        val expectedReminderRecord = WeddingAnniversaryReminderRecord(
            coupleId,
            nextAnniversaryDate.toInstant().toString()
        )
        assertEquals(listOf(expectedReminderRecord), reminderRecord)
    }
}