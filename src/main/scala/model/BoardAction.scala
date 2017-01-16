package model

/**
  * Created by ghseeli on 1/16/17.
  */
sealed trait BoardAction[A <: Coordinate] {
  def board: Board[A]
}
case class PlayMove[A <: Coordinate](board: Board[A], player: Player, coordinate: A) extends BoardAction[A]
case class UndoLastMove[A <: Coordinate](board: Board[A]) extends BoardAction[A]
