package com.himewel.design.adapter

object AdapterWithCache {
  var count: Int = 0
  var cache: Map[Int, Seq[Point]] = Map[Int, Seq[Point]]()

  def lineToPoint(line: Line): Option[Seq[Point]] = {
    if (cache.contains(line.hashCode())) {
      return cache.get(line.hashCode())
    }

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

    val pointList = (
      if (right - left == 0)
        Some((top to bottom).toSeq.map(Point(left, _)))
      else if (line.end.y - line.start.y == 0)
        Some((left to right).toSeq.map(Point(_, top)))
      else None
    )

    cache = cache ++ Map(line.hashCode() -> pointList.get)
    pointList
  }

  def apply(): Unit = {
    val x = Line(Point(0, 0), Point(1, 0)).hashCode()
    val y = Line(Point(1, 0), Point(1, 0)).hashCode()
    println((x == y))


    val rs = Seq(
      Rectangle(1, 1, 10, 10), 
      Rectangle(1, 1, 10, 10), 
      Rectangle(3, 3, 6, 6)
    )

    print("\n\n--- Drawing some stuff ---\n")
    rs.map(_.lineList.map(lineToPoint(_).map((p: Seq[Point]) => p.map(_.draw()))))
  }
}
