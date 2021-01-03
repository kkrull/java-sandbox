package com.github.kkrull;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;

import com.github.kkrull.Minimax.GameState;
import com.github.kkrull.Minimax.Move;
import com.github.kkrull.Minimax.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ComputerPlayerTest {
  ComputerPlayer subject;
  Player me, other;

  @BeforeEach
  void setup() {
    me = new Player();
    other = new Player();
    subject = new ComputerPlayer(me, other);
  }

  @Nested
  @DisplayName("#pickMove")
  class pickMove {
    Move returned;

    @Mock(lenient = true)
    GameState game;

    @DisplayName("returns null when no moves are left")
    @Test void finishedGame() {
      when(game.availableMoves())
        .thenReturn(Collections.emptyList());
      returned = subject.pickMove(game);
    }

    @DisplayName("picks the only move left, in an unfinished game")
    @Test void unfinishedGameOneMove() {
      GameState maxWins = Mockito.mock(GameState.class, Mockito.withSettings().lenient());
      when(maxWins.findWinner()).thenReturn(me);
      when(maxWins.isOver()).thenReturn(true);

      Move only = new Move("Only");
      when(game.availableMoves()).thenReturn(Arrays.asList(only));
      when(game.move(only)).thenReturn(maxWins);
      
      returned = subject.pickMove(game);
      assertSame(only, returned);
    }

    @DisplayName("picks the move with the highest score, in an unfinished game")
    @Test void unfinishedGameBestMove() {
      Move first = new Move("First");
      Move second = new Move("Second");
      when(game.availableMoves())
        .thenReturn(Arrays.asList(first, second));

      GameState maxLoses = Mockito.mock(GameState.class, Mockito.withSettings().lenient());
      when(maxLoses.findWinner()).thenReturn(other);
      when(maxLoses.isOver()).thenReturn(true);
      when(game.move(first)).thenReturn(maxLoses);
  
      GameState maxWins = Mockito.mock(GameState.class, Mockito.withSettings().lenient());
      when(maxWins.findWinner()).thenReturn(me);
      when(maxWins.isOver()).thenReturn(true);
      when(game.move(second)).thenReturn(maxWins);
      
      returned = subject.pickMove(game);
      assertSame(second, returned);
    }
  }
}
