package com.himewel.design.facade

object Generator {
  def apply(count: Int): Seq[Int] = {
    val rand = new scala.util.Random()
    Seq.fill(count)(rand.nextInt(count))
  }
}

object Splitter {
  def apply(array: Seq[Seq[Int]]): Seq[Seq[Int]] = {
    val row_count = array.size
    val col_count = array(0).size

    val rows = array
    var columns: Seq[Seq[Int]] = Seq()
    for (i <- 0 to col_count - 1) {
      var tmpArray: Seq[Int] = Seq()
      for (j <- 0 to row_count - 1) {
        tmpArray = tmpArray ++ Seq(array(j)(i))
      }
      columns = columns ++ Seq(tmpArray)
    }

    var diag1: Seq[Int] = Seq()
    var diag2: Seq[Int] = Seq()
    for (i <- 0 to col_count - 1) {
      for (j <- 0 to row_count - 1) {
        if (i == j)
          diag1 = diag1 ++ Seq(array(i)(j))
        if (i == row_count - j - 1)
          diag2 = diag2 ++ Seq(array(i)(j))
      }
    }

    rows ++ columns ++ Seq(diag1, diag2)
  }
}

object Verifier {
  def apply(arrays: Seq[Seq[Int]]): Boolean = {
    val first = arrays(0).sum

    arrays
      .map((i) => i.sum)
      .filter((s) => s != first)
      .size > 0
  }
}

object MagicSquareGenerator {
  def apply(size: Int): Unit = {
    val square = Seq.fill(size)(Generator(size))

    if (Verifier(Splitter(square))) println(square)
    else MagicSquareGenerator(size)
  }
}
