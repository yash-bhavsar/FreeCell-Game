package freecell.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Scanner;

import freecell.model.FreecellModel;
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
      this.ap = this.ap.append("\n").append(model.getGameState());
    } catch (Exception e) {
      throw new IllegalArgumentException("Start game failed");
    }

    int number;

    Scanner sc = new Scanner(this.rd);
    while (sc.hasNext()) {
      String newString = sc.next();
      char pileType = newString.charAt(0);
      if (Character.isLetter(pileType)) {
        if ('Q' == Character.toUpperCase(pileType)) {
          try {
            this.ap = this.ap.append("\nGame quit prematurely.");
            return;
          } catch (IOException e) {
            throw new IllegalStateException("Not valid state");
          }
        } else {
          PileType p = getPileType(Character.toUpperCase(pileType), sc);
          if (p == null) {
            return;
          }
          number = getPileNumber(newString.substring(1), sc);
          if (number == Integer.MAX_VALUE) {
            return;
          }
          if (this.sourcePileType == null) {
            this.sourcePileType = p;
          } else if (this.destPileType == null) {
            this.destPileType = p;
          }
        }
      } else {
        number = getPileNumber(newString, sc);
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
          if (model.isGameOver()) {
            this.ap = this.ap.append("\n").append(model.getGameState()).append("\nGame Over.");
            return;
          } else {
            this.ap = this.ap.append("\n").append(model.getGameState());
          }
        } catch (Exception e) {
          try {
            this.ap = this.ap.append("\nInvalid move. Try again. ").append(e.getMessage());
          } catch (IOException e1) {
            throw new IllegalStateException("State not valid");
          }
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
   * @param s is the string.
   * @return the number.
   */
  private int getPileNumber(String s, Scanner sc) {
    int number = Integer.MAX_VALUE;
    while (true) {
      try {
        number = Integer.parseInt(s);
        return number;
      } catch (Exception ne) {
        if (s.toUpperCase().equals("Q")) {
          try {
            this.ap = this.ap.append("\nGame quit prematurely.");
            break;
          } catch (IOException e) {
            throw new IllegalStateException("Not valid state");
          }
        } else {
          return getPileNumber(sc.next(), sc);
        }
      }
    }
    return number;
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
            break;
          } catch (IOException e) {
            throw new IllegalStateException("Not valid state");
          }
        } else {
          return getPileType(pileType, sc);
        }
      }
    }
    return null;
  }

  public static void main(String[] args) {
    Readable readable = new StringReader("C1 1 F1 C2 1 F1 C3 1 F1 C4 1 F1 C5 1 F1 " +
            "C6 1 F1 C7 1 F1 C8 1 F1 C9 1 F1 C10 1 F1 C11 1 F1 C12 1 F1 C13 1 F1 " +
            "C14 1 F2 C15 1 F2 C16 1 F2 C17 1 F2 C18 1 F2 C19 1 F2 C20 1 F2 C21 1 F2 " +
            "C22 1 F2 C23 1 F2 C24 1 F2 C25 1 F2 C26 1 F2 C27 1 F3 C28 1 F3 C29 1 F3 " +
            "C30 1 F3 C31 1 F3 C32 1 F3 C33 1 F3 C34 1 F3 C35 1 F3 C36 1 F3 C37 1 F3 " +
            "C38 1 F3 C39 1 F3 C40 1 F4 C40 1 F4 C41 1 F4 C42 1 F4 C43 1 F4 C44 1 F4 " +
            "C45 1 F4 C46 1 F4 C47 1 F4 C48 1 F4 C49 1 F4 C50 1 F4 C51 1 F4 C52 1 F4 C1 1 F1");
    Appendable appendable = new StringBuffer();
    IFreecellController freecellController = new FreecellController(readable, appendable);
    FreecellOperations model = FreecellModel.getBuilder().cascades(52).opens(4).build();
    freecellController.playGame(model.getDeck(), model, false);
    System.out.println(appendable);
  }
}
