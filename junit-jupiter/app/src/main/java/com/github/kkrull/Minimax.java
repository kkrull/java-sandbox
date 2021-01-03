package com.github.kkrull;

import java.util.Collection;

public class Minimax {
  private final Player maximizer;
  private final Player minimizer;

  public Minimax(Player maximizer, Player minimizer) {
    this.maximizer = maximizer;
    this.minimizer = minimizer;
  }

  public int score(GameState game, Player player) {
    if(player == this.maximizer)
      return negamax(game, +1);
    else
      return -1 * negamax(game, -1);
  }

  private int negamax(GameState game, int polarity) {
    if(game.findWinner() == this.maximizer)
      return 1 * polarity;
    else if(game.findWinner() == this.minimizer)
      return -1 * polarity;
    else if(game.isOver())
      return 0;

    int maxScore = -100;
    for (Move nextMove : game.availableMoves()) {
      GameState nextGame = game.move(nextMove);
      int nextScore = -1 * negamax(nextGame, -1 * polarity);
      maxScore = Math.max(maxScore, nextScore);
    }

    return maxScore;
  }

  public static interface GameState {
    Collection<Move> availableMoves();    
    Player findWinner();
    boolean isOver();
    GameState move(Move nextMove);
  }

  public static class Move {
    private final String id;

    public Move(String id) {
      this.id = id;
    }

    @Override
    public String toString() {
      return this.id;
    }
  }

  public static class Player {}
}
