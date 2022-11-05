package com.himewel.design.builder

class Building(material: String, style: String, color: String) {
  override def toString(): String = s"A $color building made with $material in $style style"
}

case class BuildingBuilder(material: String = "", style: String = "", color: String = "") {
  def withMaterial(material: String): BuildingBuilder = copy(material=material)

  def withStyle(style: String): BuildingBuilder = copy(style=style)

  def withColor(color: String): BuildingBuilder = copy(color=color)

  def build(): Building = new Building(this.material, this.style, this.color)
}

object Builder {
  def apply(): Unit = {
    val myBuilding = new BuildingBuilder()
      .withMaterial("rock")
      .withStyle("renaissance")
      .withColor("green")
      .build()

    println(myBuilding)
  }
}
