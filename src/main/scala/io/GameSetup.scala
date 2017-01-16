package io

import model.{HumanPlayer, Player}

/**
  * Created by ghseeli on 1/15/17.
  */
object GameSetup {
  def initializePlayers(): Seq[Player] = {
    var players = Seq[Player]()
    var mostRecentPlayer = getPlayer(players)
    while (players.lastOption != mostRecentPlayer) {
      mostRecentPlayer match {
        case Some(player) =>
          players = players :+ player
          mostRecentPlayer = getPlayer(players)
        case None => mostRecentPlayer = players.lastOption
      }
    }
    players
  }

  def getPlayer(players: Seq[Player]): Option[Player] = {
    val playerName = getPlayerName(players)
    val symbol = playerName.map(getSymbol)
    (playerName, symbol) match {
      case (Some(name), Some(symb)) => Option(HumanPlayer(name, symb))
      case _ => None
    }
  }

  def getPlayerName(players: Seq[Player]): Option[String] = {
    println("Please enter a unique player name.")
    val playerName = scala.io.StdIn.readLine()
    val existingPlayerNames = players.map(_.name)
    if (playerName.nonEmpty && !existingPlayerNames.contains(playerName)) {
      Option(playerName)
    } else if (playerName.isEmpty) {
      None
    } else {
      getPlayerName(players)
    }
  }

  def getSymbol(playerName: String): String = {
    println("Please enter " + playerName + "'s symbol. It must be only 1 character long!")
    val symbol = scala.io.StdIn.readLine()
    if (symbol.length() == 1) {
      symbol
    } else {
      getSymbol(playerName)
    }
  }

}
