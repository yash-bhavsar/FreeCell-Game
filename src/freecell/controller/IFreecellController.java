package freecell.controller;

import java.util.List;

import freecell.model.FreecellOperations;

/**
 * The interface Free-cell controller.
 *
 * @param <Card> the type parameter
 */
public interface IFreecellController<Card> {

  /**
   * Play game, play a new game of Free-cell using the provided model and the provided deck. If
   * “shuffle” is set to false, the deck is used as-is, else the deck is shuffled.
   *
   * @param deck    the deck
   * @param model   the model
   * @param shuffle the shuffle which decides if the deck should be shuffled or not.
   * @throws IllegalArgumentException the illegal argument exception if the model or deck are
   *                                  invalid.
   * @throws IllegalStateException    the illegal state exception only if the controller is unable
   *                                  to successfully receive input or transmit output.
   */
  void playGame(List<Card> deck, FreecellOperations<Card> model, boolean shuffle)
          throws IllegalArgumentException, IllegalStateException;
}