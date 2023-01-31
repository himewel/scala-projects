import org.scalatest.funsuite.AnyFunSuite
import com.himewel.concurrent.{Database, ThreadReader}
import com.himewel.concurrent.ThreadWriter


class ThreadReaderSuite extends AnyFunSuite {
  test("should have same index as indicated") {
    val reader = new ThreadReader()
    reader.setIndex(1)
    assert(reader._index == 1)
  }

  test("should read same value stored at the database") {
    val reader = new ThreadReader()
    val db = new Database()
    reader.setDatabase(db)
    db.write(1234)
    reader.run()
    assert(reader._value.get == 1234)
  }
}

class ThreadWriterSuite extends AnyFunSuite {
  test("should have same index as indicated") {
    val writer = new ThreadWriter()
    writer.setIndex(1)
    assert(writer._index == 1)
  }

  test("should write your index in the database") {
    val writerIndex = 5
    val writer = new ThreadWriter()
    val db = new Database()
    writer.setDatabase(db)
    writer.setIndex(writerIndex)

    writer.run()
    assert(writer._value.get == writerIndex)
  }

  test("should lock the database after a write") {
    import java.time.LocalDateTime
    import java.time.temporal.ChronoUnit

    val writer = new ThreadWriter()
    val db = new Database()
    writer.setDatabase(db)

    val start = LocalDateTime.now()
    writer.run()
    val end = LocalDateTime.now()
    assert(
      end.plus(-1, ChronoUnit.SECONDS).isAfter(start) ||
      end.plus(-1, ChronoUnit.SECONDS).isEqual(start), 
      s"${start} ${end}"
    )
  }
}
