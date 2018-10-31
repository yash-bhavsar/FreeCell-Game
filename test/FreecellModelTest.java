import org.junit.Before;
import org.junit.Test;

import java.util.List;

import freecell.model.Card;
import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * The Freecell game model test.
 */
public class FreecellModelTest {
  private FreecellOperations fcoDefault;
  private FreecellOperations fcoOpen4Cascade6;

  /**
   * Sets up freecell game models.
   */
  @Before
  public void setUp() {
    fcoDefault = FreecellModel.getBuilder().build();
    fcoOpen4Cascade6 = FreecellModel.getBuilder().cascades(6).opens(4).build();
  }

  /**
   * Test move cascade to cascade no shuffle.
   */
  @Test
  public void testMoveCascadetoCascadeNoShuffle() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    assertEquals(fcoDefault.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠, 10♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 6♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥, A♠, 9♠");

    fcoDefault.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 4);
    assertEquals(fcoDefault.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 6♠\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 10♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥, A♠, 9♠");
  }

  /**
   * Test move cascade to empty open.
   */
  @Test
  public void testMoveCascadetoEmptyOpen() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    assertEquals(fcoDefault.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 6♠\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠, 10♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥, A♠, 9♠");
  }

  /**
   * Test move cascade to empty foundation.
   */
  @Test
  public void testMoveCascadetoEmptyFoundation() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    fcoDefault.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    assertEquals(fcoDefault.getGameState(), "F1: A♠\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 9♠\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠, 10♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 6♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥");
  }

  /**
   * Test move open to cascade.
   */
  @Test
  public void testMoveOpenToCascade() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    fcoDefault.move(PileType.CASCADE, 4, 5, PileType.OPEN, 1);
    fcoDefault.move(PileType.OPEN, 0, 0, PileType.CASCADE, 4);
    assertEquals(fcoDefault.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2: 6♠\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 10♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥, A♠, 9♠");
  }

  /**
   * Test move open to foundation.
   */
  @Test
  public void testMoveOpentoFoundation() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    fcoDefault.move(PileType.CASCADE, 7, 4, PileType.OPEN, 1);
    fcoDefault.move(PileType.OPEN, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(fcoDefault.getGameState(), "F1: A♠\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 9♠\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠, 10♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 6♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥");
  }

  /**
   * Test move open to open.
   */
  @Test
  public void testMoveOpentoOpen() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    fcoDefault.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
    assertEquals(fcoDefault.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2: 9♠\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠, 10♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 6♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥, A♠");
  }

  /**
   * Test move invalid cascade to cascade.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidCascadetoCascade() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 1);
    assertEquals(fcoDefault.getGameState(), "");
  }

  /**
   * Test move invalid card index cascade.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidCardIndexCascade() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 0, 3, PileType.CASCADE, 1);
    assertEquals(fcoDefault.getGameState(), "");
  }

  /**
   * Test move invalid cascade pile number.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidCascadePileNumber() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 9);
    assertEquals(fcoDefault.getGameState(), "");
  }

  /**
   * Test move cascade same pile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeSamePile() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 0);
    assertEquals(fcoDefault.getGameState(), "");
  }

  /**
   * Test moveto invalid open.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMovetoInvalidOpen() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertEquals(fcoDefault.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 10♠\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 6♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥, A♠, 9♠");
    fcoDefault.move(PileType.CASCADE, 0, 5, PileType.OPEN, 0);
    assertEquals(fcoDefault.getGameState(), "");
  }

  /**
   * Test move invalid open to cascade.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidOpenToCascade() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    fcoDefault.move(PileType.OPEN, 0, 0, PileType.CASCADE, 0);
    assertEquals(fcoDefault.getGameState(), "");
  }

  /**
   * Test move foundation pile multiple cards.
   */
  @Test
  public void testMoveFoundationPileMultipleCards() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    fcoDefault.move(PileType.CASCADE, 7, 5, PileType.OPEN, 1);
    assertEquals(fcoDefault.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 10♠\n" +
            "O2: 9♠\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 6♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥, A♠");
    fcoDefault.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    fcoDefault.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
    assertEquals(fcoDefault.getGameState(), "F1: A♠, 2♠\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 10♠\n" +
            "O2: 9♠\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 6♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥");
  }

  /**
   * Test game over fail.
   */
  @Test
  public void testGameOverFail() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertFalse(fcoDefault.isGameOver());
  }

  /**
   * Test move invalid card to foundation.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidCardToFoundation() {
    List<Card> defaultDeck = fcoDefault.getDeck();
    fcoDefault.startGame(defaultDeck, false);
    fcoDefault.move(PileType.CASCADE, 7, 5, PileType.OPEN, 1);
    fcoDefault.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    assertEquals(fcoDefault.getGameState(), "F1: A♠\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2: 9♠\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠, 10♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 6♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥");
    fcoDefault.move(PileType.CASCADE, 7, 3, PileType.FOUNDATION, 0);
    assertEquals(fcoDefault.getGameState(), "F1: A♠\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2: 9♠\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 9♣, 4♦, Q♦, 7♥, 2♠, 10♠\n" +
            "C2: 2♣, 10♣, 5♦, K♦, 8♥, 3♠, J♠\n" +
            "C3: 3♣, J♣, 6♦, A♥, 9♥, 4♠, Q♠\n" +
            "C4: 4♣, Q♣, 7♦, 2♥, 10♥, 5♠, K♠\n" +
            "C5: 5♣, K♣, 8♦, 3♥, J♥, 6♠\n" +
            "C6: 6♣, A♦, 9♦, 4♥, Q♥, 7♠\n" +
            "C7: 7♣, 2♦, 10♦, 5♥, K♥, 8♠\n" +
            "C8: 8♣, 3♦, J♦, 6♥");
  }

  /**
   * Test negative cascade piles and open piles.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeCascadePilesAndOpenPiles() {
    FreecellOperations freecellOperations = FreecellModel.getBuilder().cascades(-1)
            .opens(-1).build();
  }

  /**
   * Test cascade piles and open piles less than minimum limit.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCascadePilesandOpenPilesLessThanMinimumLimit() {
    FreecellOperations freecellOperations = FreecellModel.getBuilder().cascades(3).opens(0).build();
  }

  /**
   * Test move before start game.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveBeforeStartGame() {
    fcoDefault.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
  }

  /**
   * Test moveto open pile not existing.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMovetoOpenPileNotExisting() {
    List<Card> defaultDeck = fcoOpen4Cascade6.getDeck();
    fcoOpen4Cascade6.startGame(defaultDeck, false);
    fcoOpen4Cascade6.move(PileType.CASCADE, 0, 8, PileType.OPEN, 5);
  }

  /**
   * Test moveto foundation pile not existing.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMovetoFoundationPileNotExisting() {
    List<Card> defaultDeck = fcoOpen4Cascade6.getDeck();
    fcoOpen4Cascade6.startGame(defaultDeck, false);
    fcoOpen4Cascade6.move(PileType.CASCADE, 3, 8, PileType.OPEN, 0);
    fcoOpen4Cascade6.move(PileType.CASCADE, 3, 7, PileType.OPEN, 1);
    assertEquals(fcoOpen4Cascade6.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: K♠\n" +
            "O2: 7♠\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 7♣, K♣, 6♦, Q♦, 5♥, J♥, 4♠, 10♠\n" +
            "C2: 2♣, 8♣, A♦, 7♦, K♦, 6♥, Q♥, 5♠, J♠\n" +
            "C3: 3♣, 9♣, 2♦, 8♦, A♥, 7♥, K♥, 6♠, Q♠\n" +
            "C4: 4♣, 10♣, 3♦, 9♦, 2♥, 8♥, A♠\n" +
            "C5: 5♣, J♣, 4♦, 10♦, 3♥, 9♥, 2♠, 8♠\n" +
            "C6: 6♣, Q♣, 5♦, J♦, 4♥, 10♥, 3♠, 9♠");
    fcoOpen4Cascade6.move(PileType.CASCADE, 3, 6, PileType.OPEN, 5);
  }

  /**
   * Test start game resets values.
   */
  @Test
  public void testStartGameResetsValues() {
    List<Card> defaultDeck = fcoOpen4Cascade6.getDeck();
    fcoOpen4Cascade6.startGame(defaultDeck, false);
    assertEquals(fcoOpen4Cascade6.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 7♣, K♣, 6♦, Q♦, 5♥, J♥, 4♠, 10♠\n" +
            "C2: 2♣, 8♣, A♦, 7♦, K♦, 6♥, Q♥, 5♠, J♠\n" +
            "C3: 3♣, 9♣, 2♦, 8♦, A♥, 7♥, K♥, 6♠, Q♠\n" +
            "C4: 4♣, 10♣, 3♦, 9♦, 2♥, 8♥, A♠, 7♠, K♠\n" +
            "C5: 5♣, J♣, 4♦, 10♦, 3♥, 9♥, 2♠, 8♠\n" +
            "C6: 6♣, Q♣, 5♦, J♦, 4♥, 10♥, 3♠, 9♠");
    fcoOpen4Cascade6.move(PileType.CASCADE, 0, 8, PileType.OPEN, 0);
    fcoOpen4Cascade6.startGame(defaultDeck, false);
    assertEquals(fcoOpen4Cascade6.getGameState(), "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 7♣, K♣, 6♦, Q♦, 5♥, J♥, 4♠, 10♠\n" +
            "C2: 2♣, 8♣, A♦, 7♦, K♦, 6♥, Q♥, 5♠, J♠\n" +
            "C3: 3♣, 9♣, 2♦, 8♦, A♥, 7♥, K♥, 6♠, Q♠\n" +
            "C4: 4♣, 10♣, 3♦, 9♦, 2♥, 8♥, A♠, 7♠, K♠\n" +
            "C5: 5♣, J♣, 4♦, 10♦, 3♥, 9♥, 2♠, 8♠\n" +
            "C6: 6♣, Q♣, 5♦, J♦, 4♥, 10♥, 3♠, 9♠");
  }

  /**
   * Test move open to foundation multiple cards.
   */
  @Test
  public void testMoveOpentoFoundationMultipleCards() {
    List<Card> defaultDeck = fcoOpen4Cascade6.getDeck();
    fcoOpen4Cascade6.startGame(defaultDeck, false);
    fcoOpen4Cascade6.move(PileType.CASCADE, 3, 8, PileType.OPEN, 0);
    fcoOpen4Cascade6.move(PileType.CASCADE, 3, 7, PileType.OPEN, 1);
    fcoOpen4Cascade6.move(PileType.CASCADE, 3, 6, PileType.OPEN, 2);
    fcoOpen4Cascade6.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 0);
    fcoOpen4Cascade6.move(PileType.CASCADE, 4, 7, PileType.OPEN, 2);
    fcoOpen4Cascade6.move(PileType.CASCADE, 4, 6, PileType.OPEN, 3);
    fcoOpen4Cascade6.move(PileType.OPEN, 3, 0, PileType.FOUNDATION, 0);
    assertEquals(fcoOpen4Cascade6.getGameState(), "F1: A♠, 2♠\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: K♠\n" +
            "O2: 7♠\n" +
            "O3: 8♠\n" +
            "O4:\n" +
            "C1: A♣, 7♣, K♣, 6♦, Q♦, 5♥, J♥, 4♠, 10♠\n" +
            "C2: 2♣, 8♣, A♦, 7♦, K♦, 6♥, Q♥, 5♠, J♠\n" +
            "C3: 3♣, 9♣, 2♦, 8♦, A♥, 7♥, K♥, 6♠, Q♠\n" +
            "C4: 4♣, 10♣, 3♦, 9♦, 2♥, 8♥\n" +
            "C5: 5♣, J♣, 4♦, 10♦, 3♥, 9♥\n" +
            "C6: 6♣, Q♣, 5♦, J♦, 4♥, 10♥, 3♠, 9♠");
  }

  /**
   * Test invalid move open to foundation.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveOpentoFoundation() {
    List<Card> defaultDeck = fcoOpen4Cascade6.getDeck();
    fcoOpen4Cascade6.startGame(defaultDeck, false);
    fcoOpen4Cascade6.move(PileType.CASCADE, 3, 8, PileType.OPEN, 0);
    fcoOpen4Cascade6.move(PileType.CASCADE, 2, 7, PileType.OPEN, 1);
  }

  /**
   * Test invalid move 2 nd card open pile to foundation pile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove2ndCardOpentoFoundation() {
    List<Card> defaultDeck = fcoOpen4Cascade6.getDeck();
    fcoOpen4Cascade6.startGame(defaultDeck, false);
    fcoOpen4Cascade6.move(PileType.CASCADE, 3, 8, PileType.OPEN, 0);
    fcoOpen4Cascade6.move(PileType.CASCADE, 3, 7, PileType.OPEN, 1);
    fcoOpen4Cascade6.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 0);
    fcoOpen4Cascade6.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  /**
   * Test shuffle.
   */
  @Test
  public void testShuffle() {
    FreecellOperations fcoOpen4Cascade62 = FreecellModel.getBuilder().cascades(6).opens(4).build();
    List<Card> defaultDeck = fcoOpen4Cascade6.getDeck();
    fcoOpen4Cascade6.startGame(defaultDeck, false);
    fcoOpen4Cascade62.startGame(defaultDeck, true);
    assertNotEquals(fcoOpen4Cascade6.getGameState(), fcoOpen4Cascade62.getGameState());
  }
}