package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * Noyce building (main area).
 */
public class Noyce implements Room {

  private Room west;
  private Room north;
  private Room east;

  private boolean zombieActive = false;
  private boolean zombieTriggered = false;
  private boolean clueGiven = false;

  public Noyce(Room west) {
    this.west = west;
  }

  public void setNorth(Room north) {
    this.north = north;
  }

  public void setEast(Room east) {
    this.east = east;
  }

  @Override
  public String getDescription() {
    return
        "You enter Noyce. The lobby is dim, and your footsteps echo.\n"
      + "To the north is a door labeled \"Kistle\".\n"
      + "To the east you smell food drifting from the JRC.\n"
      + "\n"
      + "Exits:\n"
      + "  - west (back to your dorm)\n"
      + "  - north (Kistle)\n"
      + "  - east (JRC)\n"
      + "\n"
      + "Possible actions:\n"
      + "  - look\n"
      + "  - wait\n"
      + (zombieActive ? "  - attack zombie\n" : "")
      + "  - go west / north / east\n"
      + "  - status\n";
  }

  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    // WAIT (zombie appears on FIRST wait only)
    if (type.equals(CommandType.WAIT)) {

      if (!zombieTriggered) {
        zombieTriggered = true;
        zombieActive = true;

        System.out.println("You wait... and the air shifts.");
        System.out.println("A zombie steps out from the hallway.");
        System.out.println();

        printActions();
        System.out.println();
        return;
      }

      System.out.println("You wait. Something about this building feels watched.");
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
        System.out.println("You fight the zombie. It collapses.");
        System.out.println();
        game.damage(15);

        zombieActive = false;
        clueGiven = true;

        System.out.println("Behind it, you find a folded scrap of paper.");

        game.giveClue("noyce");
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

    // GO
    if (type.equals(CommandType.GO)) {

      if (arg.equalsIgnoreCase("west")) {
        System.out.println("You head back toward your dorm...");
        game.setCurrentRoom(west);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      if (arg.equalsIgnoreCase("north")) {
        if (north == null) {
          System.out.println("That door is locked tight for now.");
          System.out.println();
          return;
        }
        System.out.println("You push through the door into Kistle...");
        game.setCurrentRoom(north);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      if (arg.equalsIgnoreCase("east")) {
        if (east == null) {
          System.out.println("You can't get into the JRC right now.");
          System.out.println();
          return;
        }
        System.out.println("You follow the smell of food into the JRC...");
        game.setCurrentRoom(east);
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
    System.out.println("  - attack zombie");
    System.out.println("  - go west / north / east");
    System.out.println("  - status");
  }

  @Override
  public String getLookDescription() {
    return
        "You take in the space around you.\n"
      + "The hallway lights hum softly, some flickering on and off.\n"
      + "Footprints trail across the floor, but they don’t all go the same direction.\n";
  }

  @Override
  public void reset() {
    zombieActive = false;
    zombieTriggered = false;
    clueGiven = false;
  }
}
