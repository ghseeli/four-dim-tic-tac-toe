package model

/**
  * Created by ghseeli on 1/14/17.
  */
class GameEngine[A <: Coordinate](initialBoard: Board[A]) {

  def recentMoveWinner(board: Board[A]): Option[(Option[Player],Seq[A])] = {
    board.history match {
      case (coordinate: A with Addable[A], squareValue) :: _ =>
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

  def testForLine(board: Board[A], coord1: A with Addable[A], coord2: A with Addable[A], player: Option[Player], length: Int): Option[Seq[A]] = {
    require(length > 1, "Cannot have line of length 1 or less.")
    require(coord1 != coord2, "Cannot have line of slope vector 0.")
    val slope = coord2.add(coord1.negate)
    require(slope.values.forall(i => i >= -1 && i <= 1), "Cannot test lines with slope coordinates with magnitude greater than 1.")
    val coordsToCheck = (1 to length).fold((Seq(coord1),Seq(coord1))){case ((accPos: Seq[A with Addable[A]], accNeg: Seq[A with Addable[A]]),i: Int) =>
      val recentPos = accPos.head
      val recentNeg = accNeg.head
      val nextPos = recentPos.add(slope)
      val nextNeg = recentNeg.add(slope.negate)
      (nextPos +: accPos, nextNeg +: accNeg)
    } match {
      case (posSeq: Seq[A], negSeq: Seq[A]) => negSeq ++ posSeq.reverse.drop(1)
    }
    val checkedCoords = coordsToCheck.filter(coord => board.validCoordinate(coord)).map(coord => (coord,board.data.get(coord))).collect{case (coord, Some(occupyingPlayer)) => (coord,occupyingPlayer)}
    val foundLine = checkedCoords.foldLeft(Seq[A]()){
      case (acc, _) if acc.length == length => acc
      case (Seq(), (coord: A,occupyingPlayer)) if occupyingPlayer == player => Seq(coord)
      case (acc, (coord: A, occupyingPlayer)) if occupyingPlayer == player && acc.nonEmpty => coord +: acc
      case _ => Seq[A]()
    }
    if (foundLine.length == length) {
      Option(foundLine)
    } else {
      None
    }
  }


}
