package edu.grinnell.csc207.textadventure.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player's inventory.
 */
public class Inventory {

  /** List of items the player holds. */
  private List<Item> items;

  /** Create an empty inventory. */
  public Inventory() {
    this.items = new ArrayList<>();
  }

  /** Add an item to the inventory. */
  public void add(Item item) {
    items.add(item);
  }

  /** Remove an item by name. */
  public boolean remove(String name) {
    return items.removeIf(i -> i.getName().equalsIgnoreCase(name));
  }

  /** Check if the inventory contains an item with this name. */
  public boolean hasItem(String name) {
    for (Item item : items) {
      if (item.getName().equalsIgnoreCase(name)) {
        return true;
      }
    }
    return false;
  }

  /** List items as a string. */
  public String listItems() {
    if (items.isEmpty()) {
      return "empty";
    }

    StringBuilder sb = new StringBuilder();
    
    for (Item item : items) {
      sb.append(item.getName()).append(", ");
    }
    return sb.substring(0, sb.length() - 2);
  }
}
