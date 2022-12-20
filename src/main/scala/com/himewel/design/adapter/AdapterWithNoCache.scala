package com.himewel.design.adapter

case class Point(x: Int, y: Int) {
  def draw(): Unit = {
    println(".")
  }
}
case class Line(start: Point, end: Point)
case class Rectangle(x: Int, y: Int, width: Int, height: Int) {
  lazy val lineList = Seq(
    Line(Point(x, y), Point(x + width, y)),
    Line(Point(x + width, y), Point(x + width, y + height)),
    Line(Point(x, y), Point(x, y + height)),
    Line(Point(x, y + height), Point(x + width, y + height))
  )
}

object AdapterWithNoCache {
  var count = 0

  def lineToPoint(line: Line): Seq[Point] = {
    count += 1
    println(
      s"${count}: Generating points for line " +
        s"[${line.start.x}, ${line.start.y}] → " +
        s"[${line.end.x}, ${line.end.y}]"
    )

    val left   = Seq(line.start.x, line.end.x).min
    val right  = Seq(line.start.x, line.end.x).max
    val top    = Seq(line.start.y, line.end.y).min
    val bottom = Seq(line.start.y, line.end.y).min

    (
      if (right - left == 0)
        (top to bottom).toSeq.map(Point(left, _))
      else if (line.end.y - line.start.y == 0)
        (left to right).toSeq.map(Point(_, top))
      else Seq()
    )
  }

  def apply(): Unit = {
    val rs = Seq(Rectangle(1, 1, 10, 10), Rectangle(3, 3, 6, 6))

    print("\n\n--- Drawing some stuff ---\n")
    rs.map(_.lineList.map(lineToPoint(_).map((p: Point) => p.draw())))
  }
}
