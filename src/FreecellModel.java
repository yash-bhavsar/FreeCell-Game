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

  private HashMap<Integer, LinkedList<Card>> cascade = new HashMap<>(8);
  private HashMap<Integer, LinkedList<Card>> foundation = new HashMap<>(4);
  private HashMap<Integer, Card> open = new HashMap<>(4);

  private int noOfCascadePiles = 8;
  private int noOfOpenPiles = 4;
  private String gameState;
  private boolean gameOver;

  /**
   * Instantiates a new Freecell model.
   */
  FreecellModel() {
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
        card = new Card(Suit.SPADE, ((i % 13) + 1));
      } else {
        card = new Card(Suit.HEART, ((i % 13) + 1));
      }
      deck.add(card);
    }
    return (deck);
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle) throws IllegalArgumentException {
    List distinctDeck = deck.stream().map(Card::toString).distinct().collect(Collectors.toList());
    if (deck.size() != distinctDeck.size() || distinctDeck.size() != 52) {
      throw new IllegalArgumentException("Deck is invalid");
    }
    deck = shuffle ? shuffle(deck) : deck;

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

//    for (int i = 0; i < noOfCascadePiles; i++) {
//      System.out.println(this.cascade.get(i));
//    }
//    exit();
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
   *
   *
   * @param destCard
   * @param sourceCard
   * @return
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
    if (sourceType == PileType.CASCADE && destinationType == PileType.CASCADE) {
      if (sourcePileNumber >= 0 && sourcePileNumber < noOfCascadePiles && destPileNumber >= 0 &&
              destPileNumber < noOfCascadePiles) {
        if (cardIndex == this.cascade.get(sourcePileNumber).size() - 1) {
          Card c = this.cascade.get(sourcePileNumber).getLast();
          Card dPLCard = this.cascade.get(destPileNumber).getLast();
          if ((dPLCard.getNumber() - 1 == c.getNumber()) && checkAlternateSuit(dPLCard, c)) {
            this.cascade.get(destPileNumber).add(c);
            this.cascade.get(sourcePileNumber).removeLast();
          }
        }
      }
    } else if (sourceType == PileType.CASCADE && destinationType == PileType.OPEN) {
      if (sourcePileNumber >= 0 && sourcePileNumber < noOfCascadePiles && destPileNumber >= 0 &&
              destPileNumber < noOfOpenPiles) {
        if (cardIndex == this.cascade.get(sourcePileNumber).size() - 1) {
          Card c = this.cascade.get(sourcePileNumber).getLast();
          this.open.putIfAbsent(destPileNumber, c);
          this.cascade.get(sourcePileNumber).removeLast();
        }
      }
    } else if (sourceType == PileType.CASCADE && destinationType == PileType.FOUNDATION) {
      if (sourcePileNumber >= 0 && sourcePileNumber < noOfCascadePiles && destPileNumber >= 0 &&
              destPileNumber < 4) {
        if (cardIndex == this.cascade.get(sourcePileNumber).size() - 1) {
          Card c = this.cascade.get(sourcePileNumber - 1).getLast();
          Card dPLCard = this.foundation.get(destPileNumber - 1).getLast();
          if ((dPLCard.getNumber() + 1 == c.getNumber()) && dPLCard.getSuit() == c.getSuit()) {
            this.foundation.get(destPileNumber).add(c);
            this.cascade.get(sourcePileNumber).removeLast();
            for (int i = 0; i < this.foundation.size(); i++) {
              Card lastCard = this.foundation.get(i).getLast();
              if (this.foundation.get(i).size() == 13 && lastCard.getNumber() == 13) {
                this.gameOver = true;
              }
            }
          } else {
            throw new IllegalArgumentException("Invalid Card");
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
        Card c = this.open.get(sourcePileNumber);
        Card dPLCard = this.cascade.get(destPileNumber).getLast();
        if ((dPLCard.getNumber() - 1 == c.getNumber()) && checkAlternateSuit(dPLCard, c)) {
          this.cascade.get(destPileNumber).add(c);
          this.open.put(sourcePileNumber, null);
        }
      }
    } else if (sourceType == PileType.OPEN && destinationType == PileType.FOUNDATION) {
      if (sourcePileNumber >= 0 && sourcePileNumber < 4 && destPileNumber >= 0 &&
              destPileNumber < noOfCascadePiles) {
        Card c = this.open.get(sourcePileNumber);
        Card dPLCard = this.foundation.get(destPileNumber).getLast();
        if ((dPLCard.getNumber() + 1 == c.getNumber()) && dPLCard.getSuit() == c.getSuit()) {
          this.foundation.get(destPileNumber).add(c);
          this.open.put(sourcePileNumber, null);
          for (int i = 0; i < this.foundation.size(); i++) {
            Card lastCard = this.foundation.get(i).getLast();
            if (this.foundation.get(i).size() == 13 && lastCard.getNumber() == 13) {
              this.gameOver = true;
            }
          }
        }
      }
    }
  }

  @Override
  public String getGameState() {
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
      if (open.get(i) != null) {
        String tempOpenString = "";
        Card c = open.get(i);
        tempOpenString += c.toString() + ", ";
        tempOpenString = tempOpenString.replaceAll(", $", "").trim();
        openPileString += "O" + i + ":" + " " + tempOpenString + "\n";
      } else {
        openPileString += "O" + i + ":" + "\n";
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
    return this.gameState + foundationPileString + openPileString + cascadePileString;
  }

  @Override
  public boolean isGameOver() {
    boolean gO = this.gameOver;
    return gO;
  }
}
