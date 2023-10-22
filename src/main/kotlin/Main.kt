import java.util.*

fun main(args: Array<String>) {
  // Creating an instance of WeddingAnniversaryHandler
  val currentDateIso = Date().toInstant().toString()
  val weddingAnniversaryHandler = WeddingAnniversaryHandler(currentDateIso, AnniversaryConfig())

  // Declaring couple details for creating a couple record
  val coupleId = "1234"
  val weddingDate = Date(122, 9, 28, 6, 30)
  val weddingDateIso = weddingDate.toInstant().toString()
  val coupleRecord = CoupleRecord(id = coupleId, weddingDateIso)

  // Generating reminder records
  val reminderRecord = weddingAnniversaryHandler.generateReminderRecords(listOf(coupleRecord))

  println(reminderRecord)
}