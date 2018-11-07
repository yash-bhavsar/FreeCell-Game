package freecell.model;

import java.util.List;

public class FreecellMultiMoveModel extends FreecellModel {

  /**
   * Instantiates a new Freecell model.
   *
   * @param noOfCascadePiles the no of cascade piles
   * @param noOfOpenPiles    the no of open piles
   */
  public FreecellMultiMoveModel(int noOfCascadePiles, int noOfOpenPiles) {
    super(noOfCascadePiles, noOfOpenPiles);
  }

  /**
   * Move a card from the given source pile to the given destination pile, if the move is valid.
   *
   * @param sourceType       the type of the source pile see @link{freecell.model.PileType}
   * @param sourcePileNumber the pile number of the given type, starting at 0
   * @param cardIndex        the index of the card to be moved from the source pile, starting at 0
   * @param destinationType  the type of the destination pile (see
   * @param destPileNumber   the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible {@link PileType})
   * @throws IllegalStateException    if a move is attempted before the game has starts
   */
  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex,
                   PileType destinationType, int destPileNumber) {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game not started yet so cannot make a move.");
    }

    if (sourceType == PileType.CASCADE && destinationType == PileType.CASCADE) {
      moveCascadeToCascade(sourcePileNumber, cardIndex, destPileNumber);
    } else if (sourceType == PileType.CASCADE && destinationType == PileType.OPEN) {
      moveCascadeToOpen(sourcePileNumber, cardIndex, destPileNumber);
    } else if (sourceType == PileType.CASCADE && destinationType == PileType.FOUNDATION) {
      moveCascadeToFoundation(sourcePileNumber, cardIndex, destPileNumber);
    } else if (sourceType == PileType.OPEN && destinationType == PileType.CASCADE) {
      moveOpenToCascade(sourcePileNumber, cardIndex, destPileNumber);
    } else if (sourceType == PileType.OPEN && destinationType == PileType.FOUNDATION) {
      moveOpenToFoundation(sourcePileNumber, cardIndex, destPileNumber);
    } else if (sourceType == PileType.OPEN && destinationType == PileType.OPEN) {
      moveOpenToOpen(sourcePileNumber, cardIndex, destPileNumber);
    }
  }

  /**
   * Move a card from Cascade pile to other Cascade pile, if the move is valid.
   *
   * @param sourcePileNumber the pile number of the given type, starting at 0
   * @param cardIndex        the index of the card to be moved from the source pile, starting at 0
   * @param destPileNumber   the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible {@link PileType})
   */
  protected void moveCascadeToCascade(int sourcePileNumber, int cardIndex, int destPileNumber) {
    if (sourcePileNumber >= 0 && sourcePileNumber < noOfCascadePiles && destPileNumber >= 0
            && destPileNumber < noOfCascadePiles) {
      if (cardIndex <= this.cascade.get(sourcePileNumber).size() - 1 && cardIndex >= 0) {
        int size = this.cascade.get(sourcePileNumber).size();
        List<Card> sublist = this.cascade.get(sourcePileNumber).subList(cardIndex, size);
        int number = (int) ((countEmptyOpenPiles() + 1) * Math.pow(2, countEmptyCascadePiles()));
        if (checkBuild(sublist) && sublist.size() <= number) {
          Card c = sublist.get(0);
          if (!this.cascade.get(destPileNumber).isEmpty()) {
            Card dPLCard = this.cascade.get(destPileNumber).getLast();
            if (((dPLCard.getNumber() - 1) == c.getNumber()) && checkAlternateSuit(dPLCard, c)) {
              this.cascade.get(destPileNumber).addAll(sublist);
              this.cascade.get(sourcePileNumber).subList(cardIndex, size).clear();
            } else {
              throw new IllegalArgumentException("Invalid Card");
            }
          } else {
            this.cascade.get(destPileNumber).addAll(sublist);
            this.cascade.get(sourcePileNumber).removeAll(sublist);
          }
        } else {
          throw new IllegalArgumentException("Not enough moves");
        }
      } else {
        throw new IllegalArgumentException("Invalid card index");
      }
    } else {
      throw new IllegalArgumentException("Invalid pile number");
    }
  }

  private int countEmptyOpenPiles() {
    int noOfEmptyOpenPiles = 0;
    for (int i = 0; i < this.noOfOpenPiles; i++) {
      if (this.open.get(i) == null) {
        noOfEmptyOpenPiles += 1;
      }
    }
    return noOfEmptyOpenPiles;
  }

  private int countEmptyCascadePiles() {
    int noOfEmptyCascadePiles = 0;
    for (int i = 0; i < this.noOfCascadePiles; i++) {
      if (this.cascade.get(i).isEmpty()) {
        noOfEmptyCascadePiles += 1;
      }
    }
    return noOfEmptyCascadePiles;
  }

  private boolean checkBuild(List<Card> cardList) {
    for (int i = 0; i < cardList.size(); i++) {
      if (i != (cardList.size() - 1)) {
        Card c1 = cardList.get(i);
        Card c2 = cardList.get(i + 1);
        if (!(((c1.getNumber() - 1) == c2.getNumber()) && checkAlternateSuit(c1, c2))) {
          return false;
        }
      } else {
        return true;
      }
    }
    return true;
  }

  /**
   * Returns a new builder of Free cell game.
   *
   * @return new FreecellOperationsBuilderImpl object.
   */
  public static FreecellOperationsBuilder getBuilder() {
    return new FreecellOperationsBuilderImpl();
  }

  /**
   * The type Freecell operations builder.
   */
  public static class FreecellOperationsBuilderImpl implements FreecellOperationsBuilder {

    /**
     * The Cascade piles.
     */
    int cascadePiles;
    /**
     * The Open piles.
     */
    int openPiles;

    /**
     * Instantiates a new Freecell operations builder.
     */
    public FreecellOperationsBuilderImpl() {
      this.cascadePiles = 8;
      this.openPiles = 4;
    }

    /**
     * Cascades freecell operations builder.
     *
     * @param c the number of cascade piles in the game.
     * @return the freecell operations builder
     */
    @Override
    public FreecellOperationsBuilder cascades(int c) {
      if (c < 4) {
        throw new IllegalArgumentException("Cascades cannot be less than 4");
      }
      this.cascadePiles = c;
      return this;
    }

    /**
     * Opens freecell operations builder.
     *
     * @param o the number of open piles in the game.
     * @return the freecell operations builder
     */
    @Override
    public FreecellOperationsBuilder opens(int o) {
      if (o < 1) {
        throw new IllegalArgumentException("Open piles cannot be less than 1");
      }
      this.openPiles = o;
      return this;
    }

    /**
     * Return a new free cell model with specified cascade and open piles.
     *
     * @return the freecell Model object.
     */
    @Override
    public FreecellOperations<Card> build() {
      return new FreecellMultiMoveModel(this.cascadePiles, this.openPiles);
    }
  }
}
