import io.{GameEnd, GamePlay, GameSetup}
import model._

/**
  * Created by ghseeli on 1/15/17.
  */
object FourDTicTacToe extends App {
  val board: Board[NDimlCoordinate] = FourDimlBoard(Map(), Seq())
  val players = GameSetup.initializePlayers()
  val gameEngine = new GameEngine(board)
  val gamePlayer = new GamePlay(gameEngine)
  val results = gamePlayer.playGame(players,board)
  results match {
    case Some((player,winningSequence)) => GameEnd.declareWinner(player,winningSequence)
    case None => println("Uh oh! Something went wrong!")
  }
}
