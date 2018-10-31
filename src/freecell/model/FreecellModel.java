package freecell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Freecell model.
 */
public class FreecellModel implements FreecellOperations<Card> {

  private HashMap<Integer, LinkedList<Card>> cascade = new HashMap<>();
  private HashMap<Integer, LinkedList<Card>> foundation = new HashMap<>();
  private HashMap<Integer, Card> open = new HashMap<>();

  private int noOfCascadePiles;
  private int noOfOpenPiles;
  private String gameState;
  private boolean gameStarted;

  /**
   * Instantiates a new Freecell model.
   *
   * @param noOfCascadePiles the no of cascade piles
   * @param noOfOpenPiles    the no of open piles
   */
  public FreecellModel(int noOfCascadePiles, int noOfOpenPiles) {
    this.noOfCascadePiles = noOfCascadePiles;
    this.noOfOpenPiles = noOfOpenPiles;
    resetValues();
    this.gameStarted = false;
  }

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
  }

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
   * This methods checks whether the two card passed as argument are of different color or not.
   *
   * @param destCard Card of destination pile to check the color.
   * @param sourceCard Card from source Pile.
   * @return true if the cards are of different color, false otherwise.
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

  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destinationType, int destPileNumber) {
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

  @Override
  public String getGameState() {
    if (!this.gameStarted) {
      return "";
    }

    String cascadePileString = "";
    String foundationPileString = "";
    String openPileString = "";

    // Foundation Pile code.
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

    // Open pile code.
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

    // Cascade pile code.
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

    int cascadePiles;
    int openPiles;

    /**
     *
     */
    public FreecellOperationsBuilderImpl() {
      this.cascadePiles = 8;
      this.openPiles = 4;
    }

    @Override
    public FreecellOperationsBuilder cascades(int c) {
      if (c < 4) {
        throw new IllegalArgumentException("Cascades cannot be less than 4");
      }
      this.cascadePiles = c;
      return this;
    }

    @Override
    public FreecellOperationsBuilder opens(int o) {
      if (o < 1) {
        throw new IllegalArgumentException("Open piles cannot be less than 1");
      }
      this.openPiles = o;
      return this;
    }

    @Override
    public FreecellOperations<Card> build() {
      return new FreecellModel(this.cascadePiles, this.openPiles);
    }
  }
}
