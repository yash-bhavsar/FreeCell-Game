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

  /*@Override
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
      ap = ap.append("\n").append(model.getGameState());
    } catch (Exception e) {
      throw new IllegalArgumentException("Start game failed");
    }

    int number;

    Scanner sc = new Scanner(rd);
    while (sc.hasNext()) {
      String newString = sc.next();
      char pileType = newString.charAt(0);
      if (Character.isLetter(pileType)) {
        if ('Q' == Character.toUpperCase(pileType)) {
          try {
            ap = ap.append("\nGame quit prematurely.");
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
          if (sourcePileType == null) {
            sourcePileType = p;
          } else if (destPileType == null) {
            destPileType = p;
          }
        }
      } else {
        number = getPileNumber(newString, sc);
      }

      if (sourcePileNumber == Integer.MAX_VALUE) {
        sourcePileNumber = number;
      } else if (cardIndex == Integer.MAX_VALUE) {
        cardIndex = number;
      } else if (destPileNumber == Integer.MAX_VALUE) {
        destPileNumber = number;
      }

      if (sourcePileType != null && destPileType != null
              && sourcePileNumber != Integer.MAX_VALUE
              && destPileNumber != Integer.MAX_VALUE && cardIndex != Integer.MAX_VALUE) {
        try {
          model.move(sourcePileType, (sourcePileNumber - 1), (cardIndex - 1),
                  destPileType, (destPileNumber - 1));
          if (model.isGameOver()) {
            ap = ap.append("\n").append(model.getGameState()).append("\nGame Over.");
            return;
          } else {
            ap = ap.append("\n").append(model.getGameState());
          }
        } catch (Exception e) {
          try {
            ap.append("\nInvalid move. Try again. ").append(e.getMessage()).append(":" +
                    sourcePileType + " " + destPileType + " " + sourcePileNumber +
                    " " + cardIndex + " " + destPileNumber);
          } catch (IOException e1) {
            throw new IllegalStateException("State not valid");
          }
        }
        resetVal();
      }
    }
  }*/

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
    PileType destPileType = null;
    int sourcePileNumber = Integer.MAX_VALUE;
    int destPileNumber = Integer.MAX_VALUE;
    int cardIndex = Integer.MAX_VALUE;
    
    
    int number;

    Scanner sc = new Scanner(this.rd);
    int count = 0;
    int j = 0;
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
          model.move(sourcePileType, sourcePileNumber - 1, cardIndex - 1, destPileType, destPileNumber - 1);
          try {
            this.ap.append("\n").append(model.getGameState());
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        } catch (IllegalArgumentException | IllegalStateException iae) {
          try {
            this.ap.append("\n").append("Invalid move. Try again. ").append(iae.getMessage());
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        }

      }

      if (model.isGameOver()) {
        try {
          this.ap.append(model.getGameState()).append("\nGame over.");
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
      j += 1;
    }
  }

//  private void resetVal() {
//    sourcePileNumber = Integer.MAX_VALUE;
//    sourcePileType = null;
//    destPileNumber = Integer.MAX_VALUE;
//    destPileType = null;
//    cardIndex = Integer.MAX_VALUE;
//  }

  /**
   * Private method to get the source, destination pile number and card index.
   *
   * @param s is the string.
   * @return the number.
   */
  private int getPileNumber(String s, Scanner sc) {
//    int number = Integer.MAX_VALUE;
//    while (true) {
//      try {
//        number = Integer.parseInt(s);
//        return number;
//      } catch (Exception ne) {
//        if (s.toUpperCase().equals("Q")) {
//          try {
//            ap = ap.append("\nGame quit prematurely.");
//            break;
//          } catch (IOException e) {
//            throw new IllegalStateException("Not valid state");
//          }
//        } else {
//          return getPileNumber(sc.next(), sc);
//        }
//      }
//    }

    if (s.matches("\\s*[Q|q]+\\s*")) {
      quit = true;
      return -77;
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
      /*try {
        ap.append(pileType);
      } catch (IOException e) {
        throw new IllegalStateException("pata nai");
      }*/
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

  public static void main(String[] args) {
//    Readable readable = new StringReader("C1 1 F1 C2 1 F1 C3 1 F1 C4 1 F1 C5 1 F1 " +
//            "C6 1 F1 C7 1 F1 C8 1 F1 C9 1 F1 C10 1 F1 C11 1 F1 C12 1 F1 C13 1 F1 " +
//            "C14 1 F2 C15 1 F2 C16 1 F2 C17 1 F2 C18 1 F2 C19 1 F2 C20 1 F2 C21 1 F2 " +
//            "C22 1 F2 C23 1 F2 C24 1 F2 C25 1 F2 C26 1 F2 C27 1 F3 C28 1 F3 C29 1 F3 " +
//            "C30 1 F3 C31 1 F3 C32 1 F3 C33 1 F3 C34 1 F3 C35 1 F3 C36 1 F3 C37 1 F3 " +
//            "C38 1 F3 C39 1 F3 C40 1 F4 C40 1 F4 C41 1 F4 C42 1 F4 C43 1 F4 C44 1 F4 " +
//            "C45 1 F4 C46 1 F4 C47 1 F4 C48 1 F4 C49 1 F4 C50 1 F4 C51 1 F4 C52 1 F4 C1 1 F1");
    Readable readable = new StringReader("C1 0 F1");
    Appendable appendable = new StringBuffer();
    IFreecellController freecellController = new FreecellController(readable, appendable);
    FreecellOperations model = FreecellModel.getBuilder().cascades(52).opens(4).build();
    freecellController.playGame(model.getDeck(), model, false);
    System.out.println(appendable);
  }
}
