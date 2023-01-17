package com.himewel.concurrent

import java.util.concurrent.{Executors, TimeUnit}
import scala.util.Random

abstract class ThreadClient extends Thread {
  val name: String
  var _index: Int = 0

  override def toString(): String = s"$name $_index"
  def setIndex(index: Int) = { _index = index }
}

class ThreadReader extends ThreadClient {
  val name = "Consumer"
  override def run(): Unit = println(s"${this}: ${Database.read()}")
}

class ThreadWriter extends ThreadClient {
  val name = "Producer"
  override def run(): Unit = println(s"${this}: ${Database.write(_index)}")
}

object ThreadReadersAndWriters {
  def apply(): Unit = {
    println("Starting ReadersAndWriters...")
    val pool = Executors.newFixedThreadPool(200)

    val responseList = Random.shuffle(0 to 200).map { (index: Int) =>
      {
        val client = (index % 2) match {
          case 0 => new ThreadReader()
          case 1 => new ThreadWriter()
        }
        client.setIndex(index)
        pool.execute(client)
      }
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
