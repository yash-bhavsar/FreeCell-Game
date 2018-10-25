import java.util.HashSet;
import java.util.List;

/**
 * The type Freecell model.
 */
public class FreecellModel implements FreecellOperations {
  @Override
  public HashSet<Card> getDeck() {
    HashSet<Card> deck = new HashSet<>();
    for (int i = 0; i < 52; i++) {
      if (i >= 0 && i <= 12)
      Card card = new Card(i%13, )
    }
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle) {

  }

  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destinationType, int destPileNumber) {

  }

  @Override
  public String getGameState() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }
}
