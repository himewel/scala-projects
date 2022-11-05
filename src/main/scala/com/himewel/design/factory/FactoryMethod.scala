package com.himewel.design.factory

// Factory method and his companion object
class HouseFactory(style: Style, material: Material) {
  override def toString(): String =
    s"House made of $material in a $style using FactoryMethod"
}

object HouseFactory {
  def createWoodyImpressionismHouse(): HouseFactory = {
    HouseFactory(new ImpressionismStyle(), new WoodyMaterial())
  }

  def createBrickRenaissanceHouse(): HouseFactory = {
    HouseFactory(new RenaissanceStyle(), new BrickMaterial())
  }
}

// Main object able to call the factory
object FactoryMethod {
  def apply(): Unit = {
    println(HouseFactory.createWoodyImpressionismHouse())
    println(HouseFactory.createBrickRenaissanceHouse())
  }
}
