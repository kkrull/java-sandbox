package com.github.kkrull;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.kkrull.Minimax.GameState;
import com.github.kkrull.Minimax.Move;
import com.github.kkrull.Minimax.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
@DisplayName("Minimax")
class MinimaxTest {
  Minimax subject;
  GameWithKnownStates game;
  Player max, min;

  public MinimaxTest() {
    max = new Player();
    min = new Player();
    subject = new Minimax(max, min);
  }

  @Nested
  @DisplayName("#score")
  class score {
    @DisplayName("scores a game ending in a draw as 0")
    @Test
    void gameOverDraw() {
      game = new GameWithKnownStates(true);
      assertEquals(0, subject.score(game, max));
    }

    @DisplayName("scores a game won by the maximizer as +1")
    @Test
    void gameOverMaxWins() {
      game = new GameWithKnownStates(true, max);
      assertEquals(+1, subject.score(game, max));
    }

    @DisplayName("scores a game won by the minimizer as -1")
    @Test
    void gameOverMinWins() {
      game = new GameWithKnownStates(true, min);
      assertEquals(-1, subject.score(game, min));
    }

    @DisplayName("the maximizer picks the move with the highest score")
    @Test
    void maxGoesHigh() {
      game = new GameWithKnownStates(false);
      game.addState(new Move("Draw"), new GameWithKnownStates(true));
      game.addState(new Move("Max Wins"), new GameWithKnownStates(true, max));
      assertEquals(+1, subject.score(game, max));
    }

    @DisplayName("the minimizer picks the move with the lowest score")
    @Test
    void minGoesLow() {
      game = new GameWithKnownStates(false);
      game.addState(new Move("Draw"), new GameWithKnownStates(true));
      game.addState(new Move("Max Loses"), new GameWithKnownStates(true, min));
      assertEquals(-1, subject.score(game, min));
    }

    @Nested
    @DisplayName("given a game with 2 or more moves left")
    class givenTwoOrMoreMovesLeft {
      @BeforeEach
      void setup() {
        game = new GameWithKnownStates(false);

        GameWithKnownStates leftTree = new GameWithKnownStates(false);
        game.addState(new Move("Left"), leftTree);
        leftTree.addState(new Move("Draw"), new GameWithKnownStates(true));
        leftTree.addState(new Move("Max Wins"), new GameWithKnownStates(true, max));

        GameWithKnownStates rightTree = new GameWithKnownStates(false);
        game.addState(new Move("Right"), rightTree);
        rightTree.addState(new Move("Draw"), new GameWithKnownStates(true));
        rightTree.addState(new Move("Max Loses"), new GameWithKnownStates(true, min));
      }

      @DisplayName("the maximizer assumes the minimizer picked the move with the lowest score")
      @Test
      void maxAssumes() {
        assertEquals(0, subject.score(game, max));
      }

      @DisplayName("the minimizer assumes the maximizer picked the move with the highest score")
      @Test
      void minAssumes() {
        assertEquals(0, subject.score(game, min));
      }
    }
  }

  public static class GameWithKnownStates implements GameState {
    private final boolean isOver;
    private final Player winner;
    private final Map<Move, GameWithKnownStates> availableMoves;

    public GameWithKnownStates(boolean isOver) {
      this.isOver = isOver;
      this.winner = null;
      this.availableMoves = new LinkedHashMap<>();
    }

    public GameWithKnownStates(boolean isOver, Player winner) {
      this.isOver = isOver;
      this.winner = winner;
      this.availableMoves = new LinkedHashMap<>();
    }

    public void addState(Move nextMove, GameWithKnownStates nextGame) {
      this.availableMoves.put(nextMove, nextGame);
    }

    @Override
    public Collection<Move> availableMoves() {
      return this.availableMoves.keySet();
    }

    @Override
    public Player findWinner() {
      return this.winner;
    }

    @Override
    public boolean isOver() {
      return this.isOver;
    }

    @Override
    public GameState move(Move nextMove) {
      return this.availableMoves.get(nextMove);
    }
  }
}
