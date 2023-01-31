import com.himewel.concurrent.Database
import org.scalatest.funsuite.AnyFunSuite


class DatabaseSuite extends AnyFunSuite {
  val database = new Database()

  test("database should read same value wrote earlier") {
    val testValue = 1
    database.write(testValue)
    assert(testValue == database.read())
  }

  test("database lock should be enabled when writing") {
    import java.time.LocalDateTime
    import java.time.temporal.ChronoUnit

    val start = LocalDateTime.now()
    database.write(1000)
    val end = LocalDateTime.now()
    assert(end.plus(-1, ChronoUnit.SECONDS).isAfter(start))
  }
}
