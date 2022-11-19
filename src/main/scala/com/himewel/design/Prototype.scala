package com.himewel.design.prototype

case class Person(name: String, occupation: String) {
  override def toString(): String = s"${name} is a ${occupation}"
}

object Prototype {
  def apply(): Unit = {
    val joe  = new Person("Joe", "Data engineer")
    val jack = joe.copy("Jack")

    assert(joe == joe.copy(), "copy has returned a different object")

    println(joe)
    println(jack)
  }
}
