package freecell.controller;

import java.util.List;

import freecell.model.FreecellOperations;

/**
 * The type Freecell controller.
 */
public class FreecellController implements IFreecellController {
  /**
   * Instantiates a new Freecell controller.
   *
   * @param rd the readable interface object, used to take the input.
   * @param ap the appendable interface object, used to display the output.
   * @throws IllegalArgumentException the illegal argument exception if readable and/ or appendable
   * object are null.
   */
  public FreecellController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("rd or ap cannot be null");
    }
  }

  @Override
  public void playGame(List deck, FreecellOperations model, boolean shuffle)
          throws IllegalArgumentException, IllegalStateException {

  }
}
