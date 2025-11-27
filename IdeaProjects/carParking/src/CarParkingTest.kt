import jdk.incubator.vector.VectorOperators.Test
import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class CarParkingTest {
    private val outContent = ByteArrayOutputStream()
    private val originalOut: PrintStream? = System.out

    @BeforeEach
    fun setUpStreams() {
        System.setOut(PrintStream(outContent))
        carParking.garage.clear()
    }

    @AfterEach
    fun restoreStreams() {
        System.setOut(originalOut)
    }

    @Test
    fun testArrivalHasRoom() {
        carParking.outputArrival("UB11-11")
        Assertions.assertTrue(outContent.toString().contains("There is room."))
    }

    @Test
    fun testArrivalGarageFull() {
        for (i in 0..9) {
            carParking.outputArrival("C" + i)
        }

        outContent.reset()
        carParking.outputArrival("XX00-00")
        Assertions.assertTrue(outContent.toString().contains("Garage full"))
    }

    @Test
    fun testDepartureNotFound() {
        carParking.outputDeparture("AB12-34")
        Assertions.assertTrue(outContent.toString().contains("not in the garage"))
    }

    @Test
    fun testDepartureMoveCars() {
        carParking.outputArrival("A1")
        carParking.outputArrival("A2")
        carParking.outputArrival("A3")

        outContent.reset()
        carParking.outputDeparture("A1")

        Assertions.assertTrue(outContent.toString().contains("2 cars moved out"))
    }
}