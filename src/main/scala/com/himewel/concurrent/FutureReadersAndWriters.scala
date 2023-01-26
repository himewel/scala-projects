package com.himewel.concurrent

import com.typesafe.scalalogging.Logger
import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.DurationInt
import scala.util.{Success, Failure, Random}

object Database {
  @volatile private var value: Int = 0
  @volatile private var lock: Boolean = false
  val logger = Logger(getClass.getName)

  def read(): Int = {
    logger.debug("Consumer: Waiting for lock be released")
    while (lock) Thread.sleep(1000)
    value
  }

  def write(newValue: Int): Int = {
    logger.debug("Producer: Waiting for lock be released")
    while (lock) Thread.sleep(1000)

    logger.debug("Producer: Acquiring lock")
    lock = true

    Thread.sleep(1000)
    value = newValue

    logger.debug("Producer: Releasing lock")
    lock = false
    newValue
  }
}

abstract class FutureClient {
  lazy val task: Future[Int]
  val name: String
  var _index: Int = 0

  override def toString(): String = s"$name $_index"
  def setIndex(index: Int) = { _index = index }
}

class FutureReader(implicit ec: ExecutionContext) extends FutureClient {
  lazy val task = Future[Int] { Database.read() }
  val name = "Consumer"
}

class FutureWriter(implicit ec: ExecutionContext) extends FutureClient {
  lazy val task = Future[Int] { Database.write(_index) }
  val name = "Producer"
}

object FutureReadersAndWriters {
  def apply(): Unit = {
    val logger = Logger(getClass.getName)
    logger.debug("Starting ReadersAndWriters...")

    val responseList = Random.shuffle(0 to 200).map { (index: Int) =>
      {
        val executor = Executors.newFixedThreadPool(200)
        implicit val ec = ExecutionContext.fromExecutor(executor)
        val client = (index % 2) match {
          case 0 => new FutureWriter()
          case 1 => new FutureReader()
        }
        client.setIndex(index)
        client.task.onComplete {
          case Success(response) => println(s"$client: $response")
          case Failure(response) => println(response.getMessage)
        }
        client.task
      }
    }

    responseList.map { (f: Future[Int]) => Await.result(f, 100.seconds) }
  }
}
