# wedding_anniversary_reminder

This project requires JDK 16

This is an implementation of a class that generates wedding anniversary reminders. Implementation can be found in `src/main/kotlin/WeddingAnniversaryHandler.kt`

Different configurations used in the implementation can be found in `src/main/kotlin/WeddingAnniversaryData.kt` The default anniversary configs is set as 2 weeks for most anniversaries, 3 weeks for multiple-of-5 anniversaries, and 4 weeks for multiple-of-10 anniversaries

Example usage (can be found in `src/main/kotlin/Main.kt`):
```kotlin
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
```


To build the project, run the following in your terminal:
`./gradlew build`

Tests can be found in `src/test/kotlin/WeddingAnniversaryHandlerTest.kt`

To run tests: `./gradlew test`