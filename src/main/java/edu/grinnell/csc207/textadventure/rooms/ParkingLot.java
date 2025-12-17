package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * A dark, eerie parking lot which has alwasy been your least favorite place in the college.
 */
public class ParkingLot implements Room {

  private Room south;


  public ParkingLot(Room south) {
    this.south = south;
  }


  @Override
  public String getDescription() {
    return
        "You step into the parking lot. You see a small, dim light flickering in the distance.\n"
      + "Rows of empty cars surround you, their windows reflecting the faint light.\n"
      + "A faint humming noise fills the air, sending chills down your spine."
      + "\n"
      + "The building entrance lies to the SOUTH.\n"
      + "\n"
      + "Possible actions:\n"
      + "  - wait\n"
      + "  - go south\n";
  }

  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    if (type.equals(CommandType.WAIT)) {
      System.out.println("You wait. The humming persists, sending shivers down your spine.\n");
    } else if (type.equals(CommandType.GO)) {
      if (arg.equalsIgnoreCase("south")) {
        System.out.println("You head back toward the building...");
        game.setCurrentRoom(south);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println("As you leave the parking lot, the humming fades away.");
      } else {
        System.out.println("You can't go \"" + arg + "\" from here.");
      }
    } else if (type.equals(CommandType.LOOK)) {
      System.out.println(getDescription());
    } else {
      System.out.println("Nothing happens.");
    }
  }
}
