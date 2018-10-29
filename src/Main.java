import java.util.List;

public class Main {

  public static void main(String[] args) {
    FreecellOperations fc = new FreecellModel();
    List<Card> l = fc.getDeck();
    try {
      fc.startGame(l, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
