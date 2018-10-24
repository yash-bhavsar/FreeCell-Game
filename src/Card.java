public class Card {

  private Suit suit;
  private int number;

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
}
