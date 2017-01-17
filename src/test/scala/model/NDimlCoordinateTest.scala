package model

import org.scalatest.FlatSpec
import org.scalatest.Matchers._
/**
  * Created by ghseeli on 1/17/17.
  */
class NDimlCoordinateTest extends FlatSpec {
  behavior of "NDimlCoordinate"

  it should "list all n-dimensional neighbors with positive coordinates" in {
    NDimlCoordinate(Seq(2)).neighbors.toSet shouldEqual Set(NDimlCoordinate(Seq(1)), NDimlCoordinate(Seq(3)))
    NDimlCoordinate(Seq(1)).neighbors.toSet shouldEqual Set(NDimlCoordinate(Seq(2)))
    NDimlCoordinate(Seq(1,1,1)).neighbors.toSet shouldEqual Set(Seq(2,2,2),Seq(2,2,1),Seq(2,1,2),Seq(2,1,1),Seq(1,2,2),Seq(1,2,1),Seq(1,1,2)).map(NDimlCoordinate)
  }

  it should "be able to add coordinates" in {
    NDimlCoordinate(Seq(1,1)).add(NDimlCoordinate(Seq(2,-1))) shouldEqual NDimlCoordinate(Seq(3,0))
  }

  it should "be able to negate coordinates" in {
    NDimlCoordinate(Seq(-2,1,0,3)).negate shouldEqual NDimlCoordinate(Seq(2,-1,0,-3))
  }

  it should "be able to print in standard coordinate notation" in {
    NDimlCoordinate(Seq(1,0,-1)).display shouldEqual "(1,0,-1)"
  }
}
