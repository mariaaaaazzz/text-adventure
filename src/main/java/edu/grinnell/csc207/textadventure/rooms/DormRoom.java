package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * The starting room of the game: your dorm room.
 */
public class DormRoom implements Room {

  private Room north;

  public DormRoom(Room north) {
    this.north = north;
  }

  public void setNorth(Room north) {
    this.north = north;
  } 


  @Override
  public String getDescription() {
    return
      "You wake up in your dorm room. \n"
      + "The campus is eerily silent, as if you are abandoned by everyone.\n"
      + "\n"
      + "You can see:\n"
      + "  - Your bed, sheets tangled.\n"
      + "  - The door to the hallway to the north.\n"
      + "\n"
      + "Maybe you could try:\n"
      + "  - wait\n"
      + "  - go north\n";
  }


  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    if (type.equals(CommandType.WAIT)) {
      System.out.println(
        "You lie still, listening. You almost fall back asleep. Nothing happens."
      );

    } else if (type.equals(CommandType.GO)) {

      if (arg.equalsIgnoreCase("north")) {
        if (north == null) {
          System.out.println("The door won't budge.");
          return;
        }

        System.out.println(
          "You try the door to the hallway. "
          + "It makes a clicking sound as it unlocks, and you step out into the hall."
        );

        game.setCurrentRoom(north);
        System.out.println(game.getCurrentRoom().getDescription());

      } else {
        System.out.println("You can't go \"" + arg + "\" from here.");
      }

    } else if (type.equals(CommandType.LOOK)) {
      System.out.println(getDescription());

    } else {
      System.out.println("I don't understand that command.");
    }
  }
}

