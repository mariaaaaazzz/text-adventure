package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.game.Item;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * Kistle: supply/weapons room (clue zombie).
 */
public class Kistle implements Room {

  private Room south;

  private boolean zombieActive = false;
  private boolean zombieTriggered = false;
  private boolean clueGiven = false;

  // Weapon in Kistle
  private boolean weaponAvailable = true;

  public Kistle(Room south) {
    this.south = south;
  }

  @Override
  public String getDescription() {
    return
        "You enter Kistle. Metal shelves line the walls.\n"
      + "Most supplies look like they've been taken already.\n"
      + (weaponAvailable ? "A heavy wrench sits on a lower shelf.\n" : "")
      + "\n"
      + "Exits:\n"
      + "  - south (back to Noyce)\n"
      + "\n"
      + "Possible actions:\n"
      + "  - look\n"
      + "  - wait\n"
      + (weaponAvailable ? "  - pickup wrench\n" : "")
      + (zombieActive ? "  - attack zombie\n" : "")
      + "  - go south\n"
      + "  - status\n";
  }

  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    // PICKUP weapon
    if (type.equals(CommandType.PICKUP)) {
      if (arg.equalsIgnoreCase("wrench") && weaponAvailable) {
        System.out.println("You grab the wrench. It feels solid — usable as a weapon.");
        game.getInventory().add(new Item("wrench", true));
        weaponAvailable = false;
        System.out.println();
        return;
      }

      System.out.println("You can't pick that up.");
      System.out.println();
      return;
    }

    // WAIT (zombie appears on FIRST wait only)
    if (type.equals(CommandType.WAIT)) {

      if (!zombieTriggered) {
        zombieTriggered = true;
        zombieActive = true;

        System.out.println("You wait... shelves creak softly.");
        System.out.println("Something moves between the metal racks.");
        System.out.println("A zombie is here.");
        System.out.println();

        printActions();
        System.out.println();
        return;
      }

      System.out.println("You wait. The room feels too tight, too quiet.");
      System.out.println();
      return;
    }

    // LOOK
    if (type.equals(CommandType.LOOK)) {
      System.out.println(getLookDescription());
      System.out.println();
      return;
    }

    // ATTACK zombie
    if (type.equals(CommandType.ATTACK)) {
      if (!arg.equalsIgnoreCase("zombie")) {
        System.out.println("That command does not apply here.");
        System.out.println();
        return;
      }

      if (!zombieActive) {
        System.out.println("There is nothing here to attack.");
        System.out.println();
        return;
      }

      int weapons = game.getInventory().weaponCount();
      int health = game.getHealth();

      if (weapons >= 1 && health >= 20) {
        System.out.println("You fight the zombie in the tight space and win.");
        System.out.println();
        game.damage(15);

        zombieActive = false;
        clueGiven = true;

        System.out.println("A note falls from a shelf when it collapses.");

        game.giveClue("kistle");
        System.out.println("You take it. Clue piece found " + game.clueProgressString() + ".");
        System.out.println();

        return;
      }

      System.out.println("You try to fight, but you're unprepared.");
      System.out.println("The zombie overwhelms you.");
      System.out.println();
      game.damage(9999);
      return;
    }

    // GO south
    if (type.equals(CommandType.GO)) {
      if (arg.equalsIgnoreCase("south")) {
        System.out.println("You head back into Noyce...");
        game.setCurrentRoom(south);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      System.out.println("You can't go \"" + arg + "\" from here.");
      System.out.println();
      return;
    }

    // TALK default
    if (type.equals(CommandType.TALK)) {
      System.out.println("You talk to yourself. No one answers.");
      System.out.println();
      return;
    }

    System.out.println("That command does not apply here.");
    System.out.println();
  }

  private void printActions() {
    System.out.println("Possible actions:");
    System.out.println("  - look");
    System.out.println("  - wait");
    if (weaponAvailable) {
      System.out.println("  - pickup wrench");
    }
    System.out.println("  - attack zombie");
    System.out.println("  - go south");
    System.out.println("  - status");
  }

  @Override
  public String getLookDescription() {
    return
        "You inspect the shelves.\n"
      + "Most things are missing, but the scrape marks suggest a struggle.\n";
  }

  @Override
  public void reset() {
    zombieActive = false;
    zombieTriggered = false;
    clueGiven = false;
    weaponAvailable = true;
  }
}
