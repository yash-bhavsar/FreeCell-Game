import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Freecell model.
 */
public class FreecellModel implements FreecellOperations {

  private HashMap<Integer, LinkedList<Card>> cascade = new HashMap<>(8);
  private HashMap<Integer, LinkedList<Card>> foundation = new HashMap<>(4);
  private HashMap<Integer, Card> open = new HashMap<>(4);

  private String gameState;
  private boolean gameOver;

  FreecellModel() {
    for (int i = 0; i < 8; i++) {
      this.cascade.put(i, new LinkedList<>());
    }
    for (int i = 0; i < 4; i++) {
      this.foundation.put(i, new LinkedList<>());
    }

    this.gameState = "";
    this.gameOver = false;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (int i = 0; i < 52; i++) {
      Card card;
      if (i <= 12) {
        card = new Card(Suit.CLUB, i % 13);
      } else if (i <= 25) {
        card = new Card(Suit.DIAMOND, i % 13);
      } else if (i <= 38) {
        card = new Card(Suit.SPADE, i % 13);
      } else {
        card = new Card(Suit.HEART, i % 13);
      }
      deck.add(card);
    }
    return deck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle) {
    if (deck.size() != 52) {
      throw new IllegalArgumentException("Deck is invalid");
    }
    deck = shuffle ? shuffle(deck) : deck;

    LinkedList<Card> c;
    for (int i = 0; i < deck.size(); i++) {
      if (i % 8 == 0) {
        c = this.cascade.get(0);
        c.add(deck.get(i));
        this.cascade.put(0, c);
      } else if (i % 8 == 1) {
        c = this.cascade.get(1);
        c.add(deck.get(i));
        this.cascade.put(1, c);
      } else if (i % 8 == 2) {
        c = this.cascade.get(2);
        c.add(deck.get(i));
        this.cascade.put(2, c);
      } else if (i % 8 == 3) {
        c = this.cascade.get(3);
        c.add(deck.get(i));
        this.cascade.put(3, c);
      } else if (i % 8 == 4) {
        c = this.cascade.get(4);
        c.add(deck.get(i));
        this.cascade.put(4, c);
      } else if (i % 8 == 5) {
        c = this.cascade.get(5);
        c.add(deck.get(i));
        this.cascade.put(5, c);
      } else if (i % 8 == 6) {
        c = this.cascade.get(6);
        c.add(deck.get(i));
        this.cascade.put(6, c);
      } else if (i % 8 == 7) {
        c = this.cascade.get(7);
        c.add(deck.get(i));
        this.cascade.put(7, c);
      }
    }
  }

  /**
   * Return deck after shuffling it.
   *
   * @param deck the deck that needs to be shuffled.
   * @return deck of cards as a List of cards after shuffling.
   */
  private List<Card> shuffle(List<Card> deck) {
    Collections.shuffle(deck);
    return deck;
  }

  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destinationType, int destPileNumber) {
    if (sourceType == PileType.CASCADE && destinationType == PileType.CASCADE) {
      LinkedList<Card> c1 = this.cascade.get(sourcePileNumber);
      LinkedList<Card> c2 = this.cascade.get(destPileNumber);
      Card c = c1.remove(cardIndex);
      c2.add(c);
    }
  }

  @Override
  public String getGameState() {
    return this.gameState;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }
}
