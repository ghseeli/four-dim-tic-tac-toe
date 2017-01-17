package model

/**
  * Created by ghseeli on 1/14/17.
  */
sealed trait Board[CoordinateType <: Coordinate] {
  type SquareType = Option[Player]
  def data: Map[CoordinateType, SquareType]
  protected def playMove(coordinate: CoordinateType, newSquareState: SquareType): Board[CoordinateType]
  protected def undoLastMove(): Board[CoordinateType]
  def history: Iterable[(CoordinateType, SquareType)]
  def validCoordinate(coord: CoordinateType): Boolean
  def winningLength: Int
  def processAction(action: BoardAction[CoordinateType]): (Board[CoordinateType],Option[Status]) = {
    action match {
      case PlayMove(board,player,coordinate) =>
        if (board.validCoordinate(coordinate)) {
          board.data.get(coordinate) match {
            case Some(Some(occupyingPlayer)) => (board, Option(SquareAlreadyOccupied(board, Option(occupyingPlayer), coordinate)))
            case _ => (board.playMove(coordinate, Option(player)), None)
          }
        } else {
          (board, Option(InvalidCoordinate(board, coordinate)))
        }
      case UndoLastMove(board) =>
        if (history.nonEmpty) {
          (board.undoLastMove(), None)
        } else {
          (this, Option(NothingToUndo(this)))
        }
    }
  }
}

case class FourDimlBoard(data: Map[NDimlCoordinate,Option[Player]], history: Seq[(NDimlCoordinate, Option[Player])]) extends Board[NDimlCoordinate] {
  val winningLength = 4
  protected override def playMove(coordinate: NDimlCoordinate, newSquareState: Option[Player]): FourDimlBoard = {
    FourDimlBoard(data.updated(coordinate, newSquareState), (coordinate, newSquareState) +: history)
  }

  protected override def undoLastMove(): FourDimlBoard = {
    history.headOption match {
      case Some((lastCoord,player)) => FourDimlBoard(data.filterKeys(coord => coord != lastCoord), history.tail)
      case None => this
    }
  }
  override def validCoordinate(coord: NDimlCoordinate): Boolean = {
    coord.values.length == 4 && coord.values.forall(i => i > 0 && i <= 4)
  }
}



