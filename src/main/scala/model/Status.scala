package model

/**
  * Created by ghseeli on 1/14/17.
  */
sealed trait Status {
  def message: String
}
case class SquareAlreadyOccupied[B <: Coordinate](board: Board[B], player: Option[Player], coordinate: B) extends Status {
  val message = "Entered square is already occupied!"
}
case class InvalidCoordinate[B <: Coordinate](board: Board[B], coordinate: B) extends Status {
  val message = "Entered coordinate is invalid!"
}

case class NothingToUndo[B <: Coordinate](board: Board[B]) extends Status {
  val message = "There is nothing to undo!"
}