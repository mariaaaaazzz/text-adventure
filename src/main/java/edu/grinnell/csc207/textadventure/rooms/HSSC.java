package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.game.Item;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * HSSC building.
 */
public class HSSC implements Room {

  private Room east;

  private boolean shieldAvailable = true;

  public HSSC(Room east) {
    this.east = east;
  }

  @Override
  public String getDescription() {
    return
        "You enter HSSC. The hallways are quiet, and the lights flicker overhead.\n"
      + "You hear the soft buzz of vending machines somewhere nearby.\n"
      + (shieldAvailable ? "A battered shield leans against a wall display.\n" : "")
      + "\n"
      + "Exits:\n"
      + "  - east (back to your dorm)\n"
      + "\n"
      + "Possible actions:\n"
      + "  - look\n"
      + "  - wait\n"
      + (shieldAvailable ? "  - pickup shield\n" : "")
      + "  - go east\n"
      + "  - status\n";
  }

  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    if (type.equals(CommandType.WAIT)) {
      System.out.println("You wait. The building feels emptier than it should.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.LOOK)) {
      System.out.println(getLookDescription());
      System.out.println();
      return;
    }

    if (type.equals(CommandType.PICKUP)) {
      if (arg.equalsIgnoreCase("shield") && shieldAvailable) {
        System.out.println("You pick up the shield. It’s heavier than it looks.");
        game.getInventory().add(new Item("shield", false));
        game.giveShield();
        shieldAvailable = false;
        System.out.println();
        return;
      }

      System.out.println("You can't pick that up.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.GO)) {
      if (arg.equalsIgnoreCase("east")) {
        System.out.println("You walk back toward your dorm...");
        game.setCurrentRoom(east);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      System.out.println("You can't go \"" + arg + "\" from here.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.TALK)) {
      System.out.println("You talk to yourself. No one answers.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.ATTACK)) {
      System.out.println("You swing at the air. Nothing happens.");
      System.out.println();
      return;
    }

    System.out.println("That command does not apply here.");
    System.out.println();
  }

  @Override
  public String getLookDescription() {
    return
        "You look around the halls of HSSC.\n"
      + "Posters about experiments and equations line the walls.\n"
      + "Some papers are scattered across the floor, hastily dropped.\n"
      + "Whatever happened here, it happened fast.\n";
  }

  @Override
  public void reset() {
    shieldAvailable = true;
  }
}
