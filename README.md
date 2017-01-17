4D-Tic-Tac-Toe
==============
This project is inspired by a Hackathon problem I did in
Spring 2015 at Loyola University Chicago. The idea is to
create a tic-tac-toe game that is played on a 4-dimensional
hypercube. 

Note that this project is intentionally over-engineered with
Scala generics to experiment with the Scala type system. It
also does not adhere to most of my personal coding standards,
but it will with time.

How to Play
===========

Setup
-----
Currently, the project only has a primitive terminal interface.
To start the game, run FourDTicTacToe with sbt. Enter player names
when prompted and enter a blank name to finish with players.

Gameplay
--------
For gameplay, coordinates must be entered to play moves.
The coordinate system is such that the `X` is in position 1,2,3,4.
The first two coordinates represent which board you play on and the
second two represent which square on the board. Coordinates must be
entered in the form `1,2,3,4` or else the system will reject them as
invalid.
    
        --> x-axis    
      | +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
      V |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
      y +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   | X |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   
        |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   
        +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   +---+---+---+---+   