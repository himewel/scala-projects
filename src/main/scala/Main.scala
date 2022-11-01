package com.himewel

import com.himewel.concurrent.{FutureReadersAndWriters, ThreadReadersAndWriters}
import com.himewel.design.builder.Builder
import com.himewel.design.factory.{AbstractFactory, FactoryMethod}

@main
def main(option: String): Unit = {
  if (option.isEmpty) {
    printMenu()
    System.exit(0)
  }

  option match {
    case "1" => FutureReadersAndWriters()
    case "2" => ThreadReadersAndWriters()
    case "3" => AbstractFactory()
    case "4" => FactoryMethod()
    case "5" => Builder()
    case _ => println("Wrong option!"); printMenu()
  }
}

def printMenu(): Unit = {
  println("Please, use one of the following options as argument:")
  println("1 - FutureReadersAndWriters")
  println("2 - ThreadReadersAndWriters")
  println("3 - AbstractFactory")
  println("4 - FactoryMethod")
  println("5 - Builder")
}
