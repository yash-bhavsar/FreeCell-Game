package freecell.model;

import java.util.LinkedList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    LinkedList<Integer> l = new LinkedList<>();
    l.add(1);
    l.add(2);
    l.add(3);
    l.add(4);
    l.add(5);
    l.add(6);
    l.add(7);
    l.add(8);
    l.add(9);
    System.out.println(l.size());
    List<Integer> l2 = l.subList(8,9);
    System.out.println(l2);
  }
}
