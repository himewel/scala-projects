package com.himewel.design.factory

// Material abstract and concrete classes
abstract class Material {
  val materialName: String

  override def toString(): String = s"$materialName material"
}

class WoodyMaterial extends Material {
  override val materialName = "Woody"
}

class BrickMaterial extends Material {
  override val materialName = "Brick"
}

// Style abstract and concrete classes
abstract class Style {
  val styleName: String

  override def toString(): String = s"$styleName style"
}

class ImpressionismStyle extends Style {
  override val styleName = "Impressionism"
}

class RenaissanceStyle extends Style {
  override val styleName = "Renaissance"
}

// Factory abstract and concrete classes
abstract class AbstractHouseFactory {
  val style: Style
  val material: Material

  override def toString(): String = s"House made of $material in a $style using AbstractFactory"
}

class WoodyImpressionismHouse extends AbstractHouseFactory {
  val style = new ImpressionismStyle()
  val material = new WoodyMaterial()
}

class BrickRenaissanceHouse extends AbstractHouseFactory {
  val style = new RenaissanceStyle()
  val material = new BrickMaterial()
}

// Main object able to call the factory
object AbstractFactory {
  def apply(): Unit = {
    println(new WoodyImpressionismHouse())
    println(new BrickRenaissanceHouse())
  }
}
