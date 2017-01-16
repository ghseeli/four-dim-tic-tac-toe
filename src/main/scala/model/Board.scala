package model

/**
  * Created by ghseeli on 1/14/17.
  */
sealed trait Board[CoordinateType <: Coordinate] {
  type SquareType = Option[Player]
  def data: Map[CoordinateType, SquareType]
  def playMove(coordinate: CoordinateType, newSquareState: SquareType): Board[CoordinateType]
  def undoLastMove(): Board[CoordinateType]
  def history: Iterable[(CoordinateType, SquareType)]
  def validCoordinate(coord: CoordinateType): Boolean
  def winningLength: Int
  def processAction(action: BoardAction[CoordinateType]): (Board[CoordinateType],Option[Status])
}

case class FourDimlBoard(data: Map[NDimlCoordinate,Option[Player]], history: Seq[(NDimlCoordinate, Option[Player])]) extends Board[NDimlCoordinate] {
  val winningLength = 4
  override def playMove(coordinate: NDimlCoordinate, newSquareState: Option[Player]): FourDimlBoard = {
    FourDimlBoard(data.updated(coordinate, newSquareState), (coordinate, newSquareState) +: history)
  }

  override def undoLastMove(): FourDimlBoard = {
    history.headOption match {
      case Some((lastCoord,player)) => FourDimlBoard(data.filterKeys(coord => coord != lastCoord), history.tail)
      case None => this
    }
  }
  override def validCoordinate(coord: NDimlCoordinate): Boolean = {
    coord.values.length == 4 && coord.values.forall(i => i > 0 && i <= 4)
  }

  override def processAction(action: BoardAction[NDimlCoordinate]): (Board[NDimlCoordinate],Option[Status]) = {
    action match {
      case PlayMove(board: Board[NDimlCoordinate],player,coordinate: NDimlCoordinate) => GameEngine.playMove(board,Option(player),coordinate)
      case UndoLastMove(board) => (board.undoLastMove(),None)
    }
  }
}



