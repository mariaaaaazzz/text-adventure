package edu.grinnell.csc207.textadventure.game;

/**
 * A simple item in the game.
 */
public class Item {

  private String name;
  private boolean weapon;

  public Item(String name) {
    this(name, false);
  }

  public Item(String name, boolean weapon) {
    this.name = name;
    this.weapon = weapon;
  }

  public String getName() {
    return this.name;
  }

  public boolean isWeapon() {
    return this.weapon;
  }
}
