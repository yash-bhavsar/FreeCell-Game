package freecell.controller;

import java.io.IOException;
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
  private PileType sourcePileType = null;
  private PileType destPileType = null;
  private int sourcePileNumber = Integer.MAX_VALUE;
  private int destPileNumber = Integer.MAX_VALUE;
  private int cardIndex = Integer.MAX_VALUE;

  /**
   * Instantiates a new Freecell controller.
   *
   * @param rd the readable interface object, used to take the input.
   * @param ap the appendable interface object, used to display the output.
   * @throws IllegalArgumentException the illegal argument exception if readable and/ or appendable
   *                                  object are null.
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
      throw new IllegalArgumentException("deck");
    }

    if (model == null) {
      throw new IllegalArgumentException("model not initialized");
    }

    try {
      model.startGame(deck, shuffle);
    } catch (Exception e) {
      throw new IllegalArgumentException("Start game failed");
    }

    int number = Integer.MAX_VALUE;

    Scanner sc = new Scanner(this.rd);

    while (sc.hasNext()) {
      String newString = sc.next();
      char pileType = newString.charAt(0);
      if (Character.isLetter(pileType)) {
        if ('Q' == Character.toUpperCase(pileType)) {
          try {
            this.ap = this.ap.append("\nGame quit prematurely. ");
          } catch (IOException e) {
            throw new IllegalStateException("Not valid state");
          }
        } else {
          number = getPileNumber(1, newString, sc);
          if (this.sourcePileType == null) {
            this.sourcePileType = getPileType(Character.toUpperCase(pileType), sc);
          } else if (this.destPileType == null) {
            this.destPileType = getPileType(Character.toUpperCase(pileType), sc);
          }
        }
      } else {
        number = getPileNumber(0, newString, sc);
      }

      if (this.sourcePileNumber == Integer.MAX_VALUE) {
        this.sourcePileNumber = number;
      } else if (this.cardIndex == Integer.MAX_VALUE) {
        this.cardIndex = number;
      } else if (this.destPileNumber == Integer.MAX_VALUE) {
        this.destPileNumber = number;
      }

      if (this.sourcePileType != null && this.destPileType != null
              && this.sourcePileNumber != Integer.MAX_VALUE
              && this.destPileNumber != Integer.MAX_VALUE && this.cardIndex != Integer.MAX_VALUE) {
        try {
          model.move(this.sourcePileType, (this.sourcePileNumber - 1), (this.cardIndex - 1),
                  this.destPileType, (this.destPileNumber - 1));
        } catch (Exception e) {
          try {
            this.ap = this.ap.append("Invalid move. Try again. " + e.getMessage());
          } catch (IOException e1) {
            throw new IllegalStateException("State not valid");
          }
        }
        try {
          this.ap = this.ap.append(model.getGameState());
        } catch (IOException e) {
          throw new IllegalStateException("State not valid");
        }
        resetVal();
      }
    }
  }

  private void resetVal() {
    this.sourcePileNumber = Integer.MAX_VALUE;
    this.sourcePileType = null;
    this.destPileNumber = Integer.MAX_VALUE;
    this.destPileType = null;
    this.cardIndex = Integer.MAX_VALUE;
  }

  /**
   * Private method to get the source, destination pile number and card index.
   *
   * @param i is the first index of substring.
   * @param s is the string.
   * @return the number.
   */
  private int getPileNumber(int i, String s, Scanner sc) {
    while (true) {
      try {
        return Integer.parseInt(s.substring(i));
      } catch (Exception ne) {

      }
    }
  }

  /**
   * Private method which returns the pile type depending on the parameter passed.
   *
   * @param pileType is the pile type passed by user.
   * @return the pile type.
   */
  private PileType getPileType(char pileType, Scanner sc) {
    while (true) {
      if (pileType == 'O') {
        return PileType.OPEN;
      } else if (pileType == 'C') {
        return PileType.CASCADE;
      } else if (pileType == 'F') {
        return PileType.FOUNDATION;
      } else {
        try {
          this.ap = ap.append(pileType);
        } catch (IOException e) {
          throw new IllegalStateException("pata nai");
        }
        String s = sc.next();
        pileType = s.charAt(0);
        if (Character.toUpperCase(pileType) == 'Q') {
          try {
            this.ap = this.ap.append("\nGame quit prematurely.");
          } catch (IOException e) {
            throw new IllegalStateException("Not valid state");
          }
        }
      }
    }
  }
}
