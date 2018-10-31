package freecell.model;

/**
 * The class Card which consists of private variable suit and number. It also includes methods
 * to get the suit, get the number and a toString method which returns the string and the number.
 */
public final class Card {

  private final Suit suit;
  private final int number;

  /**
   * Instantiates a new Card.
   *
   * @param suit   the suit
   * @param number the number
   */
  public Card(Suit suit, int number) {
    if (number > 13) {
      throw new IllegalArgumentException("freecell.model.Card number invalid");
    }
    this.number = number;
    this.suit = suit;
  }

  /**
   * Gets suit.
   *
   * @return the suit
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Gets number.
   *
   * @return the number
   */
  public int getNumber() {
    return number;
  }

  /**
   * Overridden toString method.
   * @return Returns the string which consists of the number and the suit.
   */
  @Override
  public String toString() {
    if (suit == Suit.DIAMOND) {
      if (number == 1) {
        return "A\u2666";
      } else if (number == 11) {
        return "J\u2666";
      } else if (number == 12) {
        return "Q\u2666";
      } else if (number == 13) {
        return "K\u2666";
      } else {
        return number + "\u2666";
      }
    } else if (suit == Suit.CLUB) {
      if (number == 1) {
        return "A\u2663";
      } else if (number == 11) {
        return "J\u2663";
      } else if (number == 12) {
        return "Q\u2663";
      } else if (number == 13) {
        return "K\u2663";
      } else {
        return number + "\u2663";
      }
    } else if (suit == Suit.HEART) {
      if (number == 1) {
        return "A\u2665";
      } else if (number == 11) {
        return "J\u2665";
      } else if (number == 12) {
        return "Q\u2665";
      } else if (number == 13) {
        return "K\u2665";
      } else {
        return number + "\u2665";
      }
    } else {
      if (number == 1) {
        return "A\u2660";
      } else if (number == 11) {
        return "J\u2660";
      } else if (number == 12) {
        return "Q\u2660";
      } else if (number == 13) {
        return "K\u2660";
      } else {
        return number + "\u2660";
      }
    }
  }
}
