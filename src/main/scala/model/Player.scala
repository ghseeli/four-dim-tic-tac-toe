package model

/**
  * Created by ghseeli on 1/14/17.
  */
trait Player {
  def name: String
  def symbol: String
}

case class HumanPlayer(name: String, symbol: String) extends Player
