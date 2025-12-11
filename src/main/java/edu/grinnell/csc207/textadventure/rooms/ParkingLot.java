package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * A dark, eerie parking lot where the haunting seems strongest.
 */
public class ParkingLot implements Room {

  @Override
  public String getDescription() {
    return
        "You step into the parking lot. The night feels unnaturally cold.\n"
      + "Rows of empty cars stretch into the darkness.\n"
      + "A faint humming noise seems to echo from everywhere and nowhere.\n"
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
      System.out.println("You wait. The humming grows louder for a moment, then fades.");
    } else if (type.equals(CommandType.GO)) {
      if (arg.equalsIgnoreCase("south")) {
        System.out.println("You head back toward the building...");
        game.setCurrentRoom(new DormRoom());
        System.out.println(game.getCurrentRoom().getDescription());
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
