package com.himewel

import com.himewel.concurrent.{FutureReadersAndWriters, ThreadReadersAndWriters}
import com.himewel.design.builder.{Builder, BuilderFacets}
import com.himewel.design.factory.{AbstractFactory, FactoryMethod}
import com.himewel.design.prototype.Prototype
import com.himewel.design.adapter.{AdapterWithNoCache, AdapterWithCache}
import com.himewel.design.facade.MagicSquareGenerator
import com.himewel.circe.JsonCodec

object MainApp {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      printMenu()
      System.exit(0)
    }

    val option = args(0)
    option match {
      case "1"  => FutureReadersAndWriters()
      case "2"  => ThreadReadersAndWriters()
      case "3"  => AbstractFactory()
      case "4"  => FactoryMethod()
      case "5"  => Builder()
      case "6"  => BuilderFacets()
      case "7"  => Prototype()
      case "8"  => AdapterWithNoCache()
      case "9"  => AdapterWithCache()
      case "10" => MagicSquareGenerator(5)
      case "11" => JsonCodec()
      case _    => println("Wrong option!"); printMenu()
    }
  }

  def printMenu(): Unit = {
    println("Please, use one of the following options as argument:")
    println("1 - FutureReadersAndWriters")
    println("2 - ThreadReadersAndWriters")
    println("3 - AbstractFactory")
    println("4 - FactoryMethod")
    println("5 - Builder")
    println("6 - BuilderFacets")
    println("7 - Prototype")
    println("8 - AdapterWithNoCache")
    println("9 - AdapterWithCache")
    println("10 - MagicSquareGenerator")
    println("11 - Circe")
  }
}
