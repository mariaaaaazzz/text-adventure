package edu.grinnell.csc207.textadventure.game;

/**
 * A simple item in the game.
 */
public class Item {

  private String name;

  /** Create an item with the given name. */
  public Item(String name) {
    this.name = name;
  }

  /** Get the name of this item. */
  public String getName() {
    return this.name;
  }
}
