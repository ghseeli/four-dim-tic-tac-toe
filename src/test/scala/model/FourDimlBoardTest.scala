package model

import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import TestFixtures.{blankBoard, dummyPlayer, dummyPlayer2}

/**
  * Created by ghseeli on 1/16/17.
  */
class FourDimlBoardTest extends FlatSpec {
  behavior of "FourDimlBoard"

  it should "be able to play valid moves and record them correctly" in {
    val coordinate = NDimlCoordinate(Seq(1,1,1,1))
    val postAction = blankBoard.processAction(PlayMove(blankBoard,dummyPlayer,coordinate))
    val (newBoard, status) = postAction
    status shouldEqual None
    newBoard.data.get(coordinate) shouldEqual Option(Option(dummyPlayer))
    newBoard.history.headOption shouldEqual Option((coordinate,Option(dummyPlayer)))
  }

  it should "be able to identify invalid coordinates" in {
    blankBoard.validCoordinate(NDimlCoordinate(Seq(1,1,1,1))) shouldEqual true
    blankBoard.validCoordinate(NDimlCoordinate(Seq(1,1,0,1))) shouldEqual false
    blankBoard.validCoordinate(NDimlCoordinate(Seq(1,-1,1,1))) shouldEqual false
    blankBoard.validCoordinate(NDimlCoordinate(Seq(1,1,5,1))) shouldEqual false
  }

  it should "return a status message indicating invalid coordinates are invalid when attempted" in {
    val badCoord1 = NDimlCoordinate(Seq(1,1,0,1))
    blankBoard.processAction(PlayMove(blankBoard,dummyPlayer,badCoord1)) shouldEqual (blankBoard,Option(InvalidCoordinate(blankBoard,badCoord1)))
  }

  it should "return a status message indicating when a coordinate is attempted that has already been occupied" in {
    val coordinate = NDimlCoordinate(Seq(1,1,1,1))
    val postAction = blankBoard.processAction(PlayMove(blankBoard,dummyPlayer,coordinate))
    val (newBoard, _) = postAction
    val postAction2 = newBoard.processAction(PlayMove(newBoard,dummyPlayer,coordinate))
    postAction2 shouldEqual (newBoard,Option(SquareAlreadyOccupied(newBoard,Option(dummyPlayer),coordinate)))
    val postAction3 = newBoard.processAction(PlayMove(newBoard,dummyPlayer2,coordinate))
    postAction3 shouldEqual postAction2
  }

  it should "be able to undo a previous move" in {
    val coordinate = NDimlCoordinate(Seq(1,1,1,1))
    val (newBoard,_) = blankBoard.processAction(PlayMove(blankBoard,dummyPlayer,coordinate))
    val (undoneBoard,_) = newBoard.processAction(UndoLastMove(newBoard))
    undoneBoard shouldEqual blankBoard
  }

  it should "indicate when there is nothing to be undone" in {
    val (newBoard,status) = blankBoard.processAction(UndoLastMove(blankBoard))
    newBoard shouldEqual blankBoard
    status shouldEqual Option(NothingToUndo(blankBoard))
  }
}
