package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.game.Item;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * The starting room of the game: your dorm room.
 */
public class DormRoom implements Room {

  private Room north;
  private Room south;
  private Room east;
  private Room west;

  private boolean hasKey = true;
  private boolean firstTime = true;

  public DormRoom(Room north) {
    this.north = north;
  }

  public void setNorth(Room north) { this.north = north; }
  public void setSouth(Room south) { this.south = south; }
  public void setEast(Room east)   { this.east = east; }
  public void setWest(Room west)   { this.west = west; }

  @Override
  public String getDescription() {
    if (firstTime) {
      firstTime = false;
      return
          "You wake up in your dorm room.\n"
        + "The campus is eerily silent, as if you are abandoned by everyone.\n"
        + "\n"
        + "You can see:\n"
        + "  - Your bed, sheets tangled.\n"
        + "  - Doors leading north, south, east, and west.\n"
        + (hasKey ? "  - A small metal key on your desk.\n" : "")
        + "\n"
        + "Possible actions:\n"
        + "  - look\n"
        + "  - wait\n"
        + "  - talk\n"
        + "  - attack\n"
        + "  - go north / south / east / west\n"
        + "  - pickup key\n";
    }

    return
        "You are back in your dorm room.\n"
      + "It is still quiet. Too quiet.\n"
      + "\n"
      + "Exits:\n"
      + "  - north, south, east, west\n"
      + (hasKey ? "\nYou notice a small metal key on your desk.\n" : "\n")
      + "\n"
      + "Possible actions:\n"
      + "  - look\n"
      + "  - wait\n"
      + "  - talk\n"
      + "  - attack\n"
      + "  - go north / south / east / west\n"
      + "  - pickup key\n";
  }

  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    if (type.equals(CommandType.WAIT)) {
      System.out.println("You sit on your bed and wait. The silence presses in.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.TALK)) {
      System.out.println("You talk to yourself. No one answers.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.ATTACK)) {
      System.out.println("You swing at the air. It doesn’t make you feel safer.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.PICKUP)) {
      if (arg.equalsIgnoreCase("key") && hasKey) {
        System.out.println("You pick up the small metal key.");
        game.getInventory().add(new Item("key"));
        hasKey = false;
        System.out.println();
      } else if (arg.equalsIgnoreCase("key")) {
        System.out.println("You already picked it up.");
        System.out.println();
      } else {
        System.out.println("You can't pick that up.");
        System.out.println();
      }
      return;
    }

    if (type.equals(CommandType.GO)) {
      if (arg.equalsIgnoreCase("north")) {
        if (north == null) {
          System.out.println("The door won't budge.");
          System.out.println();
          return;
        }
        System.out.println("You head north.");
        game.setCurrentRoom(north);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      if (arg.equalsIgnoreCase("south")) {
        if (south == null) {
          System.out.println("You can't go south from here.");
          System.out.println();
          return;
        }
        System.out.println("You head south.");
        game.setCurrentRoom(south);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      if (arg.equalsIgnoreCase("east")) {
        if (east == null) {
          System.out.println("You can't go east from here.");
          System.out.println();
          return;
        }
        System.out.println("You head east.");
        game.setCurrentRoom(east);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      if (arg.equalsIgnoreCase("west")) {
        if (west == null) {
          System.out.println("You can't go west from here.");
          System.out.println();
          return;
        }
        System.out.println("You head west.");
        game.setCurrentRoom(west);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      System.out.println("You can't go \"" + arg + "\" from here.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.LOOK)) {
      System.out.println(getLookDescription());
      System.out.println();
      return;
    }

    System.out.println("That command does not apply here.");
    System.out.println();
  }

  @Override
  public String getLookDescription() {
    return
        "You glance around your dorm room.\n"
      + "Your window reflects your tired face in the dark glass.\n"
      + "Nothing seems disturbed — yet.\n";
  }

  @Override
  public void reset() {
    hasKey = true;
    firstTime = true;
  }
}
