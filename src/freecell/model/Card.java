package freecell.model;

public final class Card {

  private final Suit suit;
  private final int number;

  public Card(Suit suit, int number) {
    if (number > 13) {
      throw new IllegalArgumentException("freecell.model.Card number invalid");
    }
    this.number = number;
    this.suit = suit;
  }

  public Suit getSuit() {
    return suit;
  }

  public int getNumber() {
    return number;
  }

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
