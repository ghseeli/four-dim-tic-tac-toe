package io

import model._

import scala.util.Try

/**
  * Created by ghseeli on 1/15/17.
  */
object GamePlay {
  def playGame(players: Seq[Player], inBoard: Board[NDimlCoordinate]): Option[(Option[Player],Seq[NDimlCoordinate])] = {
    var currentBoard = Option(inBoard)
    var currentPlayer = players.headOption
    var currentPlayerIndex = 0
    val totalPlayers = players.length
    var winner: Option[(Option[Player],Seq[NDimlCoordinate])] = None
    while(currentBoard.flatMap(b => GameEngine.recentMoveWinner(b)).isEmpty && currentPlayer.nonEmpty) {
      currentBoard.map(b => ASCIIOutputEngine.renderBoard(b)) match {
        case Some(boardString) => println(boardString)
        case None =>
      }
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
      winner = currentBoard.flatMap(board => GameEngine.recentMoveWinner(board))
      winner match {
        case Some(_) =>
          currentPlayer = None
        case None =>
      }
    }
    currentBoard.map(b => ASCIIOutputEngine.renderBoard(b)) match {
      case Some(boardString) => println(boardString)
      case None =>
    }
    winner
  }

  def getMove(player: Player, board: Board[NDimlCoordinate]): Option[BoardAction[NDimlCoordinate]] = {
    println("It is " + player.name + "'s turn.")
    println("Please enter coordinates you would like to play or type U for undo.")
    val input = scala.io.StdIn.readLine()
    if (input.equals("U") || input.equals("u")) {
      Option(UndoLastMove(board))
    } else {
      val coordinate = extractCoordinates(input)
      coordinate.map(coord => PlayMove(board, player, coord))
    }
  }

  def extractCoordinates(coordString: String): Option[NDimlCoordinate] = {
    val data = Try(coordString.split(",").map(s => s.toInt)).toOption
    data.map(data => NDimlCoordinate(data))
  }
}
