import com.himewel.concurrent.{FutureReader, FutureWriter, Database}
import org.scalatest.funsuite.AnyFunSuite
import scala.util.{Success, Failure}
import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.DurationInt


class FutureClientSuite extends AnyFunSuite {
  implicit val ec: ExecutionContext = ExecutionContext.global

  test("should have the indicated index") {
    val reader = new FutureReader()
    reader.setIndex(2)
    assert(reader._index == 2)
  }

  test("should read same value wrote in the database") {
    val reader = new FutureReader()
    val db = new Database()
    reader.setDatabase(db)

    db.write(1)
    reader.task.onComplete{
      case Success(value)     => assert(value == 1)
      case Failure(exception) => fail(exception)
    }

    Await.result(reader.task, 100.seconds)
  }
}

class FutureWriterSuite extends AnyFunSuite {
  implicit val ec: ExecutionContext = ExecutionContext.global

  test("should have the indicated index") {
    val writer = new FutureWriter()
    writer.setIndex(2)
    assert(writer._index == 2)
  }

  test("should write your index in the database") {
    val writerIndex = 5
    val writer = new FutureWriter()
    val db = new Database()
    writer.setDatabase(db)
    writer.setIndex(writerIndex)

    writer.task.onComplete{
      case Success(value)     => assert(value == writerIndex)
      case Failure(exception) => fail(exception)
    }

    Await.result(writer.task, 100.seconds)
  }

  test("should lock the database after a write") {
    import java.time.LocalDateTime
    import java.time.temporal.ChronoUnit

    val writer = new FutureWriter()
    val db = new Database()
    writer.setDatabase(db)

    val start = LocalDateTime.now()
    writer.task.onComplete{
      case Success(value) =>
        val end = LocalDateTime.now()
        assert(end.plus(-1, ChronoUnit.SECONDS).isAfter(start))
      case Failure(exception) => fail(exception)
    }

    Await.result(writer.task, 100.seconds)
  }
}
