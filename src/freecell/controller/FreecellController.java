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
  private boolean quit;

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
    quit = false;
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
      this.ap.append("\n").append(model.getGameState());
    } catch (Exception e) {
      throw new IllegalArgumentException("Start game failed");
    }

    PileType sourcePileType = null;
    PileType destPileType;
    int sourcePileNumber = Integer.MAX_VALUE;
    int destPileNumber;
    int cardIndex = Integer.MAX_VALUE;

    Scanner sc = new Scanner(this.rd);
    int count = 0;
    while (sc.hasNext()) {
      String newString = sc.next();

      if (newString.toUpperCase().contains("Q")) {
        try {
          this.ap = this.ap.append("\nGame quit prematurely.");
          return;
        } catch (IOException e) {
          throw new IllegalStateException("Not valid state");
        }
      }

      if (count == 0) {
        char pileType = newString.charAt(0);
        sourcePileType = getPileType(Character.toUpperCase(pileType), sc);
        if (sourcePileType == null) {
          return;
        }
        sourcePileNumber = getPileNumber(newString.substring(1), sc);
        if (quit) {
          try {
            this.ap.append("\nGame quit prematurely.");
            break;
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        }
        count += 1;
      } else if (count == 1) {
        cardIndex = getPileNumber(newString, sc);
        if (quit) {
          try {
            this.ap.append("\nGame quit prematurely.");
            break;
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        }
        count += 1;
      } else {
        destPileType = getPileType(newString.charAt(0), sc);
        if (destPileType == null) {
          try {
            this.ap.append("\nGame quit prematurely.");
            break;
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        }
        destPileNumber = getPileNumber(newString.substring(1), sc);
        if (quit) {
          try {
            this.ap.append("\nGame quit prematurely.");
            break;
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        }
        count = 0;
        try {
          model.move(sourcePileType, sourcePileNumber - 1, cardIndex - 1,
                  destPileType, destPileNumber - 1);
          try {
            this.ap.append("\n").append(model.getGameState());
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
          if (model.isGameOver()) {
            try {
              this.ap.append(model.getGameState()).append("\nGame over.");
            } catch (IOException ioe) {
              ioe.printStackTrace();
            }
          }
        } catch (IllegalArgumentException | IllegalStateException iae) {
          try {
            this.ap.append("\n").append("Invalid move. Try again. ").append(iae.getMessage());
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * Private method to get the source, destination pile number and card index.
   *
   * @param s is the string.
   * @return the number.
   */
  private int getPileNumber(String s, Scanner sc) {
    if (s.matches("\\s*[Q|q]+\\s*")) {
      quit = true;
      return -5000;
    }
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return getPileNumber(sc.next(), sc);
    }
  }

  /**
   * Private method which returns the pile type depending on the parameter passed.
   *
   * @param pileType is the pile type passed by user.
   * @return the pile type.
   */
  private PileType getPileType(char pileType, Scanner sc) {
    if (pileType == 'O') {
      return PileType.OPEN;
    } else if (pileType == 'C') {
      return PileType.CASCADE;
    } else if (pileType == 'F') {
      return PileType.FOUNDATION;
    } else {
      if (Character.toUpperCase(pileType) == 'Q') {
        try {
          this.ap.append("\nGame quit prematurely.");
        } catch (IOException e) {
          throw new IllegalStateException("Not valid state");
        }
      } else {
        return getPileType(sc.next().charAt(0), sc);
      }
    }
    return null;
  }
}
