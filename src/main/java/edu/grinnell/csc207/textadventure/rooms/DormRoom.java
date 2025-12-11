package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * The starting room of the game: your dorm room.
 */
public class DormRoom implements Room {

  @Override
  public String getDescription() {
    return
        "You wake up in your dorm room. The air feels wrong.\n"
      + "The campus is eerily silent, like everyone vanished at once.\n"
      + "\n"
      + "You can see:\n"
      + "  - Your bed, sheets tangled.\n"
      + "  - The door to the hallway to the north.\n"
      + "\n"
      + "Maybe you could TRY:\n"
      + "  - wait\n"
      + "  - go north\n";
  }

  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    if (type.equals(CommandType.WAIT)) {
      System.out.println("You lie still, listening. The silence presses in. Nothing changes.");
    } else if (type.equals(CommandType.GO)) {
      if (arg.equalsIgnoreCase("north")) {
        System.out.println("You try the door to the hallway. It creaks ominously but won't quite budge... yet.");
        // Later: game.setCurrentRoom(new Hallway());
      } else {
        System.out.println("You can't go \"" + arg + "\" from here.");
      }
    } else if (type.equals(CommandType.LOOK)) {
      System.out.println(getDescription());
    } else {
      System.out.println("That doesn't seem to do anything in here.");
    }
  }
}
