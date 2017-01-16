package io

import model.{Board, Coordinate, FourDimlBoard, NDimlCoordinate}
import scalaz.std.list._
import scalaz.syntax.traverse._

/**
  * Created by ghseeli on 1/15/17.
  */
object ASCIIOutputEngine {
  def renderBoard(board: Board[_]): String = {
    board match {
      case FourDimlBoard(data, _) =>
        val verticalSpace = "   "
        val possibleCoords = allPossibleCoords(NDimlCoordinate(Seq(1,1,1,1)))
        val rePartitionedCoords = possibleCoords.groupBy{case NDimlCoordinate(Seq(xExt,yExt,_,yInt)) => (yExt,yInt,xExt)}
        val reOrderedCoords = rePartitionedCoords.mapValues(coordSeq => coordSeq.sortBy{case NDimlCoordinate(Seq(xExt,_,xInt,_)) => (xExt,xInt)})
        val boardTop = horizBoardBorder(4)
        val horizSepLine = (1 to 4).map(i => boardTop).fold("")((acc,_) => acc + boardTop + verticalSpace) + "\n"
        val boardRows = reOrderedCoords.mapValues(coordSeq => "|" + coordSeq.map {
          case coord: NDimlCoordinate => (data.get(coord) match {
            case Some(Some(player)) => " " + player.symbol + " "
            case _ => "   "
          }) + "|"
          case _ => "   |"
        }.foldLeft("")(_ + _))
        val completeRows = boardRows.toSeq.groupBy{case ((yExt,yInt,_),_) => (yExt,yInt)}.mapValues(seq => seq.sortBy{case ((_,_,xExt),_) => xExt}.map{case (_,s) => s}.foldLeft("")((acc,s) => acc + s + verticalSpace))
        val orderedYCoords = completeRows.keySet.toSeq.sorted
        orderedYCoords.foldLeft((horizSepLine,orderedYCoords.headOption)){
          case ((acc,Some((prevYExt,_))),(yExt,yInt)) =>
            val newAcc = (if (yExt == prevYExt) {
              acc
            } else {
              acc + "\n" + horizSepLine
            }) + completeRows((yExt, yInt)) + "\n" + horizSepLine
            (newAcc, Option((yExt, yInt)))
        }._1
    }
  }

  def horizBoardBorder(squares: Int): String = {
    require(squares > 0)
    "+" + (1 to squares).map(i => "---+").foldLeft("")(_+_)
  }

  def allPossibleCoords(sampleCoord: Coordinate): Seq[Coordinate] = {
    sampleCoord match {
      case NDimlCoordinate(values) =>
        val dim = values.length
        val base = (1 to dim).toList
        val allCoords = (1 to dim).map(i => base).toList.sequence
        allCoords.map(i => NDimlCoordinate(i))
    }
  }
}
