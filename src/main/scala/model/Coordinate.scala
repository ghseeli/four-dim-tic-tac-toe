package model

import scalaz.std.list._
import scalaz.syntax.traverse._

/**
  * Created by ghseeli on 1/14/17.
  */

trait Coordinate {
  def values: Seq[Int]
}

trait Addable[+A] {
  def add[B >: A](coordinate: B): B with Addable[B]
  def negate: A with Addable[A]
  def neighbors: Seq[A with Addable[A]]
}




case class NDimlCoordinate(values: Seq[Int]) extends Coordinate with Addable[NDimlCoordinate] {
  val dim: Int = values.length
  override def neighbors: Seq[NDimlCoordinate] = {
    val base = List(-1,0,1)
    val diffs = (0 until dim).toList.map(i => base).sequence
    val removedDuplicate = diffs.map(i => this.add(NDimlCoordinate(i))).filter(coord => coord != this)
    val removedOutOfBounds = removedDuplicate.filter(coord => coord.values.forall(_ > 0))
    removedOutOfBounds
  }
  def add[B >: NDimlCoordinate](coordinate: B): NDimlCoordinate = {
    coordinate match {
      case NDimlCoordinate(otherValues) => NDimlCoordinate(otherValues.zip(values).map{case (i,j) => i+j})
    }
  }

  override def negate: NDimlCoordinate = NDimlCoordinate(values.map(-1*_))
}
