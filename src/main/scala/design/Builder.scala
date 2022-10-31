package com.himewel

abstract class AbstractBuilding(material: String, style: String, color: String)

abstract class AbstractBuilder {
  var material: String = ""
  var style: String = ""
  var color: String = ""

  def withMaterial(material: String): AbstractBuilder
  def withStyle(style: String): AbstractBuilder
  def withColor(color: String): AbstractBuilder
  def build(): AbstractBuilding
}

class Building(material: String, style: String, color: String) extends AbstractBuilding(material, style, color) {
  override def toString(): String = s"A $color building made with $material in $style style"
}

class BuildingBuilder extends AbstractBuilder {
  def withMaterial(material: String): BuildingBuilder = {
    this.material = material
    this
  }

  def withStyle(style: String): BuildingBuilder = {
    this.style = style
    this
  }

  def withColor(color: String): BuildingBuilder = {
    this.color = color
    this
  }

  def build(): Building = new Building(material, style, color)
}

object Builder {
  def apply(): Unit = {
    val builder = new BuildingBuilder()
    val myBuilding = builder
      .withMaterial("rock")
      .withStyle("renaissance")
      .withColor("green")
      .build()

    println(myBuilding)
  }
}
