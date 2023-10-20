import WeddingAnniversaryHandler.hello
import org.junit.Test
import kotlin.test.assertEquals

class WeddingAnniversaryHandlerTest {
    @Test
    fun testHello() {
        val x = hello()
        assertEquals(3, x)
    }
}