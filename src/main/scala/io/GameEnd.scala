package io

import model.{Coordinate, Player}

/**
  * Created by ghseeli on 1/16/17.
  */
object GameEnd {
  def declareWinner(winner: Option[Player], winningSequence: Seq[Coordinate]): Unit = {
    winner match {
      case Some(player) => println("Congratulations to " + player.name + " for winning with sequence " + winningSequence.toString())
      case None => println("No one has won the game. So sad :-(")
    }
  }
}
