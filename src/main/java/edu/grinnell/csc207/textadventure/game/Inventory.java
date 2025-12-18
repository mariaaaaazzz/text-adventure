package edu.grinnell.csc207.textadventure.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player's inventory.
 */
public class Inventory {

  private List<Item> items;

  public Inventory() {
    this.items = new ArrayList<>();
  }

  public void add(Item item) {
    items.add(item);
  }

  public boolean remove(String name) {
    return items.removeIf(i -> i.getName().equalsIgnoreCase(name));
  }

  public boolean hasItem(String name) {
    for (Item item : items) {
      if (item.getName().equalsIgnoreCase(name)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasWeapon() {
    return weaponCount() > 0;
  }

  public int weaponCount() {
    int count = 0;
    for (Item item : items) {
      if (item.isWeapon()) {
        count++;
      }
    }
    return count;
  }

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

  public void clear() {
    items.clear();
  }
}
