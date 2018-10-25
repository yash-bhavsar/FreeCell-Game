public class Card {

  private Suit suit;
  private int number;

  Card() {}

  public Card(Suit suit, int number) {
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
      return number + "\u2666";
    } else if (suit == Suit.CLUB) {
      return number + "\u2663";
    } else if (suit == Suit.HEART) {
      return number + "\u2665";
    } else {
      return number + "\u2660";
    }
  }
}
