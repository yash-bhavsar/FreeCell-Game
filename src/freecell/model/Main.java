package freecell.model;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    FreecellOperations fc = new FreecellModel(8, 4);
    List<Card> l = fc.getDeck();
    try {
      fc.startGame(l, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(fc.getGameState());
  }
}
