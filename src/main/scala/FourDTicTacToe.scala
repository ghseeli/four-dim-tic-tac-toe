import io.{GameEnd, GamePlay, GameSetup}
import model._

/**
  * Created by ghseeli on 1/15/17.
  */
object FourDTicTacToe extends App {
  var board: Board[NDimlCoordinate] = FourDimlBoard(Map(), Seq())
  val players = GameSetup.initializePlayers()
  val results = GamePlay.playGame(players,board)
  results match {
    case Some((player,winningSequence)) => GameEnd.declareWinner(player,winningSequence)
    case None => println("Uh oh! Something went wrong!")
  }
}
