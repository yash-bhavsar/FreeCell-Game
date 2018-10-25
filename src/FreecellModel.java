import java.util.ArrayList;
import java.util.List;

/**
 * The type Freecell model.
 */
public class FreecellModel implements FreecellOperations {
  @Override
  public ArrayList<Card> getDeck() {
    return null;
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
