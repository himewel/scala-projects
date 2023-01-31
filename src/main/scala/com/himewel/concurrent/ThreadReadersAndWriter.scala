package com.himewel.concurrent

import com.typesafe.scalalogging.Logger
import java.util.concurrent.{Executors, TimeUnit}
import scala.util.Random

abstract class ThreadClient extends Thread {
  val logger = Logger(getClass.getName)
  val name: String
  var _index: Int = 0
  var _db: Database = new Database()
  var _value: Option[Int] = Some(0)

  override def toString(): String = s"$name $_index"
  def setIndex(index: Int) = { _index = index }
  def setDatabase(db: Database) = { _db = db }
}

class ThreadReader extends ThreadClient {
  val name = "Consumer"
  override def run(): Unit = {
    _value = Some(_db.read())
    logger.debug(s"${this}: ${_value.get}")
  }
}

class ThreadWriter extends ThreadClient {
  val name = "Producer"
  override def run(): Unit = {
    _value = Some(_index)
    logger.debug(s"${this}: ${_db.write(_index)}")
  }
}

object ThreadReadersAndWriters {
  def apply(): Unit = {
    val logger = Logger(getClass.getName)
    logger.debug("Starting ReadersAndWriters...")
    val pool = Executors.newFixedThreadPool(200)

    val responseList = Random.shuffle(0 to 200).map { (index: Int) =>
      val client = (index % 2) match {
        case 0 => new ThreadReader()
        case 1 => new ThreadWriter()
      }
      val db = new Database()
      client.setIndex(index)
      client.setDatabase(db)
      pool.execute(client)
    }

    pool.shutdown()
    if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
      pool.shutdownNow(); // Cancel currently executing tasks
      // Wait a while for tasks to respond to being cancelled
      if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
        System.err.println("Pool did not terminate")
      }
    }
  }
}
