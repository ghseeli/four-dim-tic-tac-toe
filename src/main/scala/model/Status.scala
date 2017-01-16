package model

/**
  * Created by ghseeli on 1/14/17.
  */
sealed trait Status
case class SquareAlreadyOccupied[B <: Coordinate](board: Board[B], player: Option[Player], coordinate: B) extends Status
case class InvalidCoordinate[B <: Coordinate](board: Board[B], coordinate: B) extends Status
