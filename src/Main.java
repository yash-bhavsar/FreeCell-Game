import java.util.List;

public class Main {

  public static void main(String[] args) {
    FreecellModel f = new FreecellModel();

    List<Card> deck = f.getDeck();
    f.startGame(deck, true);
    System.out.println(f.getGameState());
//    f.getGameState();
  }
}
