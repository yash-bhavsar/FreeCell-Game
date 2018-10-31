package freecell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class FreecellModel which implements the FreeCellOperations interface and has methods like
 * resetValues, checkAlternateSuit and shuffle which are used to reset values, check the alternate
 * suit during moving the cards and shuffle the deck if the boolean shuffle is true. It also has
 * private variables for cascade, foundation and open piles.
 */
public class FreecellModel implements FreecellOperations<Card> {

  private HashMap<Integer, LinkedList<Card>> cascade;
  private HashMap<Integer, LinkedList<Card>> foundation;
  private HashMap<Integer, Card> open;

  private int noOfCascadePiles;
  private int noOfOpenPiles;
  private String gameState;
  private boolean gameOver;
  private boolean gameStarted;

  /**
   * Instantiates a new Freecell model.
   *
   * @param noOfCascadePiles the no of cascade piles
   * @param noOfOpenPiles    the no of open piles
   */
  public FreecellModel(int noOfCascadePiles, int noOfOpenPiles) {
    cascade = new HashMap<>();
    foundation = new HashMap<>();
    open = new HashMap<>();
    this.noOfCascadePiles = noOfCascadePiles;
    this.noOfOpenPiles = noOfOpenPiles;
    resetValues();
    this.gameStarted = false;
  }

  /**
   * Return a valid and complete deck of cards for a game of Freecell. There is
   * no restriction imposed on the ordering of these cards in the deck. An
   * invalid deck is defined as a deck that has one or more of these flaws:
   * <ul>
   * <li>It does not have 52 cards</li> <li>It has duplicate cards</li> <li>It
   * has at least one invalid card (invalid suit or invalid number) </li> </ul>
   *
   * @return the deck of cards as a list
   */
  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (int i = 0; i < 52; i++) {
      Card card;
      if (i <= 12) {
        card = new Card(Suit.CLUB, ((i % 13) + 1));
      } else if (i <= 25) {
        card = new Card(Suit.DIAMOND, ((i % 13) + 1));
      } else if (i <= 38) {
        card = new Card(Suit.HEART, ((i % 13) + 1));
      } else {
        card = new Card(Suit.SPADE, ((i % 13) + 1));
      }
      deck.add(card);
    }
    return (deck);
  }

  /**
   * Private method used to reset all the values.
   */
  private void resetValues() {
    for (int i = 0; i < noOfCascadePiles; i++) {
      this.cascade.put(i, new LinkedList<>());
    }
    for (int i = 0; i < 4; i++) {
      this.foundation.put(i, new LinkedList<>());
    }
    for (int i = 0; i < noOfOpenPiles; i++) {
      this.open.put(i, null);
    }
    this.gameState = "";
    this.gameOver = false;
  }

  /**
   * Deal a new game of freecell with the given deck, with or without shuffling
   * it first. This method first verifies that the deck is valid. It deals the
   * deck among the cascade piles in roundrobin fashion. Thus if there are 4
   * cascade piles, the 1st pile will get cards 0, 4, 8, ..., the 2nd pile will
   * get cards 1, 5, 9, ..., the 3rd pile will get cards 2, 6, 10, ... and the
   * 4th pile will get cards 3, 7, 11, .... Depending on the number of cascade
   * piles, they may have a different number of cards
   *
   * @param deck    the deck to be dealt
   * @param shuffle if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the deck is invalid
   */
  @Override
  public void startGame(List<Card> deck, boolean shuffle) throws IllegalArgumentException {
    List distinctDeck = deck.stream().map(Card::toString).distinct().collect(Collectors.toList());
    if (deck.size() != distinctDeck.size() || distinctDeck.size() != 52) {
      throw new IllegalArgumentException("Deck is invalid");
    }
    deck = shuffle ? shuffle(deck) : deck;

    resetValues();

    LinkedList<Card> c;
    for (int i = 0; i < deck.size(); i++) {
      for (int j = 0; j < this.noOfCascadePiles; j++) {
        if (i % noOfCascadePiles == j % noOfCascadePiles) {
          c = this.cascade.get(j % noOfCascadePiles);
          c.add(deck.get(i));
          this.cascade.put(j % noOfCascadePiles, c);
        }
      }
    }

    this.gameStarted = true;
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

  /**
   * Used to check the suit.
   * @param destCard is the card from destination pile.
   * @param sourceCard is the card from source pile.
   * @return Returns true if the suits are alternate.
   */
  private boolean checkAlternateSuit(Card destCard, Card sourceCard) {
    if (destCard.getSuit() == Suit.SPADE || destCard.getSuit() == Suit.CLUB) {
      return sourceCard.getSuit() == Suit.HEART || sourceCard.getSuit() == Suit.DIAMOND;
    } else if (destCard.getSuit() == Suit.HEART || destCard.getSuit() == Suit.DIAMOND) {
      return sourceCard.getSuit() == Suit.SPADE || sourceCard.getSuit() == Suit.CLUB;
    } else {
      return false;
    }
  }


  /**
   * Move a card from the given source pile to the given destination pile, if
   * the move is valid.
   *
   * @param sourceType         the type of the source pile see @link{freecell.model.PileType}
   * @param sourcePileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0
   * @param destinationType    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible {@link
   *                                  PileType})
   * @throws IllegalStateException    if a move is attempted before the game has
   *                                  starts
   */
  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex,
                   PileType destinationType, int destPileNumber) {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game not started yet so cannot make a move.");
    }
    if (sourceType == PileType.CASCADE && destinationType == PileType.CASCADE) {
      if (sourcePileNumber >= 0 && sourcePileNumber < noOfCascadePiles && destPileNumber >= 0 &&
              destPileNumber < noOfCascadePiles) {
        if (cardIndex == this.cascade.get(sourcePileNumber).size() - 1) {
          Card c = this.cascade.get(sourcePileNumber).getLast();
          Card dPLCard = this.cascade.get(destPileNumber).getLast();
          if (((dPLCard.getNumber() - 1) == c.getNumber()) && checkAlternateSuit(dPLCard, c)) {
            this.cascade.get(destPileNumber).add(c);
            this.cascade.get(sourcePileNumber).removeLast();
          } else {
            throw new IllegalArgumentException("Invalid Card");
          }
        } else {
          throw new IllegalArgumentException("Invalid card index");
        }
      } else {
        throw new IllegalArgumentException("Invalid pile number");
      }
    } else if (sourceType == PileType.CASCADE && destinationType == PileType.OPEN) {
      if (sourcePileNumber >= 0 && sourcePileNumber < noOfCascadePiles && destPileNumber >= 0 &&
              destPileNumber < noOfOpenPiles) {
        if (cardIndex == this.cascade.get(sourcePileNumber).size() - 1) {
          Card c = this.cascade.get(sourcePileNumber).getLast();
          if (this.open.get(destPileNumber) == null) {
            this.open.put(destPileNumber, c);
            this.cascade.get(sourcePileNumber).removeLast();
          } else {
            throw new IllegalArgumentException("Open pile already filled");
          }
        } else {
          throw new IllegalArgumentException("Invalid card index");
        }
      } else {
        throw new IllegalArgumentException("Invalid pile number");
      }
    } else if (sourceType == PileType.CASCADE && destinationType == PileType.FOUNDATION) {
      if (sourcePileNumber >= 0 && sourcePileNumber < noOfCascadePiles && destPileNumber >= 0 &&
              destPileNumber < 4) {
        if (cardIndex == this.cascade.get(sourcePileNumber).size() - 1) {
          Card c = this.cascade.get(sourcePileNumber).getLast();
          if (this.foundation.get(destPileNumber).isEmpty()) {
            if (c.getNumber() == 1) {
              this.foundation.get(destPileNumber).add(c);
              this.cascade.get(sourcePileNumber).removeLast();
            } else {
              throw new IllegalArgumentException("Card cannot be moved");
            }
          } else {
            Card dPLCard = this.foundation.get(destPileNumber).getLast();
            if (((dPLCard.getNumber() + 1) == c.getNumber()) && dPLCard.getSuit() == c.getSuit()) {
              this.foundation.get(destPileNumber).add(c);
              this.cascade.get(sourcePileNumber).removeLast();
            } else {
              throw new IllegalArgumentException("Invalid Card");
            }
          }
        } else {
          throw new IllegalArgumentException("Invalid Card Index");
        }
      } else {
        throw new IllegalArgumentException("Invalid Pile Number");
      }
    } else if (sourceType == PileType.OPEN && destinationType == PileType.CASCADE) {
      if (sourcePileNumber >= 0 && sourcePileNumber < noOfOpenPiles && destPileNumber >= 0 &&
              destPileNumber < noOfCascadePiles) {
        Card c;
        if (this.open.get(sourcePileNumber) != null) {
          c = this.open.get(sourcePileNumber);
        } else {
          throw new IllegalArgumentException("No Card in open pile");
        }
        Card dPLCard = this.cascade.get(destPileNumber).getLast();
        if (((dPLCard.getNumber() - 1) == c.getNumber()) && checkAlternateSuit(dPLCard, c)) {
          this.cascade.get(destPileNumber).add(c);
          this.open.put(sourcePileNumber, null);
        } else {
          throw new IllegalArgumentException("Invalid card");
        }
      } else {
        throw new IllegalArgumentException("Invalid pile number");
      }
    } else if (sourceType == PileType.OPEN && destinationType == PileType.FOUNDATION) {
      if (sourcePileNumber >= 0 && sourcePileNumber < 4 && destPileNumber >= 0 &&
              destPileNumber < noOfCascadePiles) {
        Card c;
        if (this.open.get(sourcePileNumber) != null) {
          c = this.open.get(sourcePileNumber);
        } else {
          throw new IllegalArgumentException("No card in open pile");
        }

        if (this.foundation.get(destPileNumber).isEmpty()) {
          if (c.getNumber() == 1) {
            this.foundation.get(destPileNumber).add(c);
            this.open.put(sourcePileNumber, null);
          } else {
            throw new IllegalArgumentException("Card cannot be moved");
          }
        } else {
          Card dPLCard = this.foundation.get(destPileNumber).getLast();
          if (((dPLCard.getNumber() + 1) == c.getNumber()) && dPLCard.getSuit() == c.getSuit()) {
            this.foundation.get(destPileNumber).add(c);
            this.open.put(sourcePileNumber, null);
          } else {
            throw new IllegalArgumentException("Invalid card");
          }
        }
      } else {
        throw new IllegalArgumentException("Invalid pile number");
      }
    } else if (sourceType == PileType.OPEN && destinationType == PileType.OPEN) {
      if (sourcePileNumber >= 0 && sourcePileNumber < noOfOpenPiles && destPileNumber >= 0 &&
              destPileNumber < noOfOpenPiles) {
        if (this.open.get(sourcePileNumber) != null && this.open.get(destPileNumber) == null) {
          this.open.put(destPileNumber, this.open.get(sourcePileNumber));
          this.open.put(sourcePileNumber, null);
        } else {
          throw new IllegalArgumentException("No card in open pile");
        }
      }
    }
  }

  /**
   * Return the present state of the game as a string. The string is formatted
   * as follows:
   * <pre>
   * F1:[b]f11,[b]f12,[b],...,[b]f1n1[n] (Cards in foundation pile 1 in order)
   * F2:[b]f21,[b]f22,[b],...,[b]f2n2[n] (Cards in foundation pile 2 in order)
   * ...
   * Fm:[b]fm1,[b]fm2,[b],...,[b]fmnm[n] (Cards in foundation pile m in
   * order)
   * O1:[b]o11[n] (Cards in open pile 1)
   * O2:[b]o21[n] (Cards in open pile 2)
   * ...
   * Ok:[b]ok1[n] (Cards in open pile k)
   * C1:[b]c11,[b]c12,[b]...,[b]c1p1[n] (Cards in cascade pile 1 in order)
   * C2:[b]c21,[b]c22,[b]...,[b]c2p2[n] (Cards in cascade pile 2 in order)
   * ...
   * Cs:[b]cs1,[b]cs2,[b]...,[b]csps (Cards in cascade pile s in order)
   *
   * where [b] is a single blankspace, [n] is newline. Note that there is no
   * newline on the last line
   * </pre>
   *
   * @return the formatted string as above
   */
  @Override
  public String getGameState() {
    if (!this.gameStarted) {
      return "";
    }

    String cascadePileString = "";
    String foundationPileString = "";
    String openPileString = "";

    for (int i : foundation.keySet()) {
      if (foundation.get(i).size() > 0) {
        String tempFoundationString = "";
        int k = i + 1;
        for (int j = 0; j < foundation.get(i).size(); j++) {
          Card c = foundation.get(i).get(j);
          tempFoundationString += c.toString() + ", ";
        }
        tempFoundationString = tempFoundationString.replaceAll(", $", "").trim();
        foundationPileString += "F" + k + ":" + " " + tempFoundationString + "\n";
      } else {
        int k = i + 1;
        foundationPileString += "F" + k + ":" + "\n";
      }
    }

    for (int i : open.keySet()) {
      int k = i + 1;
      if (open.get(i) != null) {
        String tempOpenString = "";
        Card c = open.get(i);
        tempOpenString += c.toString() + ", ";
        tempOpenString = tempOpenString.replaceAll(", $", "").trim();
        openPileString += "O" + k + ":" + " " + tempOpenString + "\n";
      } else {
        openPileString += "O" + k + ":" + "\n";
      }
    }

    for (int i : cascade.keySet()) {
      if (cascade.get(i).size() > 0) {
        String tempString = "";
        int k = i + 1;
        for (int j = 0; j < cascade.get(i).size(); j++) {
          Card c = cascade.get(i).get(j);
          tempString += c.toString() + ", ";
        }
        tempString = tempString.replaceAll(", $", "").trim();
        cascadePileString += "C" + k + ":" + " " + tempString + "\n";
      } else {
        int k = i + 1;
        cascadePileString += "C" + k + ":" + "\n";
      }
    }
    return this.gameState + foundationPileString + openPileString + cascadePileString.trim();
  }

  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    for (int i = 0; i < 4; i++) {
      if (this.foundation.get(i).size() != 13) {
        return false;
      }
    }
    return true;
  }

  /**
   * Gets builder.
   *
   * @return the builder
   */
  public static FreecellOperationsBuilder getBuilder() {
    return new FreecellOperationsBuilderImpl();
  }

  /**
   * The type Freecell operations builder.
   */
  public static class FreecellOperationsBuilderImpl implements FreecellOperationsBuilder {

    /**
     * The Cascade piles.
     */
    int cascadePiles;
    /**
     * The Open piles.
     */
    int openPiles;

    /**
     * Instantiates a new Freecell operations builder.
     */
    public FreecellOperationsBuilderImpl() {
      this.cascadePiles = 8;
      this.openPiles = 4;
    }

    /**
     * Cascades freecell operations builder.
     *
     * @param c the c
     * @return the freecell operations builder
     */
    @Override
    public FreecellOperationsBuilder cascades(int c) {
      if (c < 4) {
        throw new IllegalArgumentException("Cascades cannot be less than 4");
      }
      this.cascadePiles = c;
      return this;
    }

    /**
     * Opens freecell operations builder.
     *
     * @param o the o
     * @return the freecell operations builder
     */
    @Override
    public FreecellOperationsBuilder opens(int o) {
      if (o < 1) {
        throw new IllegalArgumentException("Open piles cannot be less than 1");
      }
      this.openPiles = o;
      return this;
    }

    /**
     * Build freecell operations.
     *
     * @return the freecell operations
     */
    @Override
    public FreecellOperations<Card> build() {
      return new FreecellModel(this.cascadePiles, this.openPiles);
    }
  }
}
