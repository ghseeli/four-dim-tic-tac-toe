package io

import model.Board

/**
  * Created by ghseeli on 1/16/17.
  */
trait GameOutput {
  def displayBoard(board: Board[_]): Unit
  def displayMessage(message: String): Unit
}
