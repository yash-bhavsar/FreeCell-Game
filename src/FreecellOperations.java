import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * The interface Freecell operations.
 */
public interface FreecellOperations {

  /**
   * Gets deck.
   *
   * @return the deck
   */
  List<Card> getDeck();

  /**
   * Start game.
   *
   * @param deck    the deck
   * @param shuffle the shuffle
   */
  void startGame(List<Card> deck, boolean shuffle);

  /**
   * Move.
   *
   * @param sourceType       the source type
   * @param sourcePileNumber the source pile number
   * @param cardIndex        the card index
   * @param destinationType  the destination type
   * @param destPileNumber   the dest pile number
   */
  void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destinationType,
            int destPileNumber);

  /**
   * Gets game state.
   *
   * @return the game state
   */
  String getGameState();

  /**
   * Is game over boolean.
   *
   * @return the boolean
   */
  boolean isGameOver();
}