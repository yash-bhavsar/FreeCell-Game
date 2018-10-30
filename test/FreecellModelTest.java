import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import freecell.model.Card;
import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;
import freecell.model.Suit;

public class FreecellModelTest {
  private FreecellOperations fcoDefault;
  private FreecellOperations fcoOpen4Cascade6;

  @Before
  public void setUp() {
    fcoDefault = FreecellModel.getBuilder().build();
    fcoOpen4Cascade6 = FreecellModel.getBuilder().cascades(6).opens(4).build();
  }



}