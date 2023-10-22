import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class WeddingAnniversaryHandlerTest {
    private val currentDateIso = Date(123, 9, 21, 9,35).toInstant().toString()

    @Test
    fun testDefaultReminderWeeksReturnsReminder() {
        val config = AnniversaryConfig(defaultReminderWeeks = 1, specialAnniversaryConfig = listOf())
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
        val config = AnniversaryConfig(defaultReminderWeeks = 1, specialAnniversaryConfig = listOf())
        val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, config)

        val coupleId = "1234"
        val weddingDate = Date(122, 9, 30, 6, 30)
        val weddingDateIso = weddingDate.toInstant().toString()
        val coupleRecord = CoupleRecord(id = coupleId, weddingDateIso)
        val reminderRecord = weddingAnniversaryHandler.generateReminderRecords(listOf(coupleRecord))

        assertEquals(listOf(), reminderRecord)
    }

    @Test
    fun testDefaultReminderWeeksWithNullWeddingDateDoesNotReturnReminder() {
        val config = AnniversaryConfig(defaultReminderWeeks = 1, specialAnniversaryConfig = listOf())
        val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, config)

        val coupleId = "1234"
        val coupleRecord = CoupleRecord(id = coupleId, null)
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

    @Test
    fun testDefaultReminderWeeksMultipleCouples() {
        val config = AnniversaryConfig(defaultReminderWeeks = 1, specialAnniversaryConfig = listOf())
        val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, config)

        val coupleId1 = "1234"
        val weddingDate1 = Date(122, 9, 28, 6, 30)
        val weddingDateIso1 = weddingDate1.toInstant().toString()
        val coupleRecord1 = CoupleRecord(id = coupleId1, weddingDateIso1)

        val coupleId2 = "4321"
        val weddingDate2 = Date(121, 9, 27, 6, 30)
        val weddingDateIso2 = weddingDate2.toInstant().toString()
        val coupleRecord2 = CoupleRecord(id = coupleId2, weddingDateIso2)

        val reminderRecords = weddingAnniversaryHandler.generateReminderRecords(listOf(coupleRecord1, coupleRecord2))

        val nextAnniversaryDate1 = weddingDate1.clone() as Date
        nextAnniversaryDate1.year = weddingDate1.year + 1
        val expectedReminderRecord1 = WeddingAnniversaryReminderRecord(
            coupleId1,
            nextAnniversaryDate1.toInstant().toString()
        )

        val nextAnniversaryDate2 = weddingDate2.clone() as Date
        nextAnniversaryDate2.year = weddingDate2.year + 2
        val expectedReminderRecord2 = WeddingAnniversaryReminderRecord(
            coupleId2,
            nextAnniversaryDate2.toInstant().toString()
        )
        assertEquals(listOf(expectedReminderRecord1, expectedReminderRecord2), reminderRecords)
    }
}