package model

/**
  * Created by ghseeli on 1/14/17.
  */
object GameEngine {

  def recentMoveWinner(board: Board[NDimlCoordinate]): Option[(Option[Player],Seq[NDimlCoordinate])] = {
    board.history match {
      case (coordinate: Addable[Coordinate], squareValue) :: _ =>
        val occupiedNeighbors = coordinate.neighbors.map(coord => (coord, board.data.get(coord))).collect {
          case (coord, Some(res)) if res == squareValue => coord
        }
        val lines = occupiedNeighbors.flatMap(neighbor => testForLine(board, coordinate, neighbor, squareValue, board.winningLength))
        lines.headOption match {
          case Some(line) => Option((squareValue,line))
          case None => None
        }
      case _ => None
    }
  }

  def testForLine(board: Board[NDimlCoordinate], coord1: NDimlCoordinate, coord2: NDimlCoordinate, player: Option[Player], length: Int): Option[Seq[NDimlCoordinate]] = {
    require(length > 1, "Cannot have line of length 1 or less.")
    require(coord1 != coord2, "Cannot have line of slope vector 0.")
    val slope = coord2.add(coord1.negate)
    require(slope.values.forall(i => i >= -1 && i <= 1), "Cannot test lines with slope coordinates with magnitude greater than 1.")
    val coordsToCheck = (1 to length).fold((Seq(coord1),Seq(coord1))){case ((accPos: Seq[NDimlCoordinate], accNeg: Seq[NDimlCoordinate]),i: Int) =>
      val recentPos = accPos.head
      val recentNeg = accNeg.head
      val nextPos = recentPos.add(slope)
      val nextNeg = recentNeg.add(slope.negate)
      (nextPos +: accPos, nextNeg +: accNeg)
    } match {
      case (posSeq: Seq[NDimlCoordinate], negSeq: Seq[NDimlCoordinate]) => negSeq ++ posSeq.reverse.drop(1)
    }
    val checkedCoords = coordsToCheck.filter(coord => board.validCoordinate(coord)).map(coord => (coord,board.data.get(coord))).collect{case (coord, Some(occupyingPlayer)) => (coord,occupyingPlayer)}
    val foundLine = checkedCoords.foldLeft(Seq[NDimlCoordinate]()){
      case (acc, _) if acc.length == length => acc
      case (Seq(), (coord: NDimlCoordinate,occupyingPlayer)) if occupyingPlayer == player => Seq(coord)
      case (acc, (coord: NDimlCoordinate, occupyingPlayer)) if occupyingPlayer == player && acc.nonEmpty => coord +: acc
      case _ => Seq[NDimlCoordinate]()
    }
    if (foundLine.length == length) {
      Option(foundLine)
    } else {
      None
    }
  }


}
