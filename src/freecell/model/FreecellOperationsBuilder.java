package freecell.model;

/**
 * The interface Freecell operations builder which consists of methods like opens, cascades and
 * build.
 */
public interface FreecellOperationsBuilder {
  /**
   * Cascades freecell operations builder.
   *
   * @param c the c
   * @return the freecell operations builder
   */
  FreecellOperationsBuilder cascades(int c);

  /**
   * Opens freecell operations builder.
   *
   * @param o the o
   * @return the freecell operations builder
   */
  FreecellOperationsBuilder opens(int o);

  /**
   * Build freecell operations.
   *
   * @param <K> the type parameter
   * @return the freecell operations
   */
  <K> FreecellOperations<K> build();
}
