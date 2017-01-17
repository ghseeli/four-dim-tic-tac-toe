package io

import model._

import scala.util.Try

/**
  * Created by ghseeli on 1/15/17.
  */
class GamePlay[A >: NDimlCoordinate <: Coordinate](gameEngine: GameEngine[A], output: GameOutput = ASCIIOutputEngine) {
  def playGame(players: Seq[Player], inBoard: Board[A]): Option[(Option[Player],Seq[A])] = {
    var currentBoard = Option(inBoard)
    var currentPlayer = players.headOption
    var currentPlayerIndex = 0
    val totalPlayers = players.length
    var winner: Option[(Option[Player],Seq[A])] = None
    while(currentBoard.flatMap(b => gameEngine.recentMoveWinner(b)).isEmpty && currentPlayer.nonEmpty) {
      currentBoard.foreach(b => output.displayBoard(b))
      val nextAction = (currentPlayer,currentBoard) match {
        case (Some(player),Some(board)) => getMove(player, board)
        case _ => None
      }
      nextAction match {
        case Some(action) =>
          action.board.processAction(action) match {
            case (board, None) =>
              currentBoard = Option(board)
              action match {
                case _: PlayMove[_] => currentPlayerIndex += 1
                case _: UndoLastMove[_] => currentPlayerIndex -= 1
              }
              currentPlayerIndex = currentPlayerIndex % totalPlayers
              currentPlayer = players.lift(currentPlayerIndex)
            case (_, Some(status)) =>
              println(status.message)
          }
      }
      winner = currentBoard.flatMap(board => gameEngine.recentMoveWinner(board))
      winner match {
        case Some(_) =>
          currentPlayer = None
        case None =>
      }
    }
    currentBoard.foreach(b => output.displayBoard(b))
    winner
  }

  def getMove(player: Player, board: Board[A]): Option[BoardAction[A]] = {
    output.displayMessage("It is " + player.name + "'s turn.")
    output.displayMessage("Please enter coordinates you would like to play or type U for undo.")
    val input = scala.io.StdIn.readLine()
    if (input.equals("U") || input.equals("u")) {
      Option(UndoLastMove(board))
    } else {
      val coordinate = extractCoordinates(input)
      coordinate.map(coord => PlayMove(board, player, coord))
    }
  }

  def extractCoordinates(coordString: String): Option[A] = {
    val data = Try(coordString.split(",").map(s => s.toInt)).toOption
    data.map(data => NDimlCoordinate(data))
  }
}
