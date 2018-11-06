package freecell.controller;

import java.util.List;
import java.util.Scanner;

import freecell.model.FreecellOperations;
import freecell.model.PileType;

/**
 * The type Freecell controller.
 */
public class FreecellController implements IFreecellController {

  private Readable rd;
  private Appendable ap;

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
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public void playGame(List deck, FreecellOperations model, boolean shuffle)
          throws IllegalArgumentException, IllegalStateException {
    if (deck == null) {
      throw new IllegalArgumentException("pata nai");
    }

    if (model == null) {
      throw new IllegalArgumentException("pata nai");
    }

    PileType pileType;

    Scanner sc = new Scanner(this.rd);
    while (sc.hasNext()) {
      String newString = sc.next();
      char[] arr = newString.toCharArray();
      for (int i = 0; i < arr.length; i++) {
        if (Character.isLetter(arr[i])) {
//          PileType pileType = getPileType(arr[i]);
        }
      }
      if (newString.matches("[-+]?\\d*\\.?\\d+")) {

      }
    }
  }
}
