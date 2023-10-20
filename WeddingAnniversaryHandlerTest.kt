import WeddingAnniversaryHandler.generateReminderRecords
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class WeddingAnniversaryHandlerTest {
    private val currentDateIso = Date(123, 9, 21, 9,35).toInstant().toString()
    @Test
    fun testReminderRecordGenerated() {
        val coupleId = "1234"
        val weddingDate = Date(122, 10, 5, 6, 30)
        val weddingDateIso = weddingDate.toInstant().toString()
        val coupleRecord = CoupleRecord(id = coupleId, weddingDateIso)
        val reminderRecord = generateReminderRecords(currentDateIso, listOf(coupleRecord))

        val nextAnniversaryDate = weddingDate.clone() as Date
        nextAnniversaryDate.year = weddingDate.year + 1
        val expectedReminderRecord = WeddingAnniversaryReminderRecord(
            coupleId,
            nextAnniversaryDate.toInstant().toString()
        )
        assertEquals(listOf(expectedReminderRecord), reminderRecord)
    }
}