package com.github.kkrull;

import com.github.kkrull.Minimax.GameState;
import com.github.kkrull.Minimax.Move;
import com.github.kkrull.Minimax.Player;

public class ComputerPlayer {
  private final Player me;
  private final Player other;

  public ComputerPlayer(Player me, Player other) {
    this.me = me;
    this.other = other;
  }

  public Move pickMove(GameState game) {
    Minimax minimax = new Minimax(this.me, this.other);

    int bestScore = -100;
    Move bestMove = null;
    for(Move nextMove : game.availableMoves()) {
      int nextScore = minimax.score(game.move(nextMove), this.other);
      if(bestMove == null || nextScore > bestScore) {
        bestMove = nextMove;
        bestScore = nextScore;
      }
    }

    return bestMove;
  }
}
