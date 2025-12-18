package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.game.Item;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

public class ParkingLot implements Room {

  private Room south; // back to dorm

  private int lookCount = 0;

  // Stranger / zombie encounter state
  private boolean strangerActive = false;
  private boolean talked = false;
  private boolean zombieDefeated = false;

  // Knife reward after victory
  private boolean knifeAvailable = false;
  private boolean knifeTaken = false;

  public ParkingLot(Room south) {
    this.south = south;
  }

  @Override
  public String getDescription() {
    String base =
        "You step into the parking lot. A dim light flickers in the distance.\n"
      + "Rows of empty cars surround you.\n"
      + "\n"
      + "Exits:\n"
      + "  - south (back)\n"
      + "\n"
      + "Possible actions:\n"
      + "  - look\n"
      + "  - wait\n";

    if (strangerActive && !zombieDefeated) {
      base +=
          "  - talk stranger\n"
        + "  - attack stranger\n";
    }

    if (knifeAvailable && !knifeTaken) {
      base += "  - pickup knife\n";
    }

    base +=
        "  - go south\n"
      + "  - status\n";

    return base;
  }

  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    // WAIT
    if (type.equals(CommandType.WAIT)) {
      System.out.println("You wait. The humming persists.");
      System.out.println();
      return;
    }

    // LOOK
    if (type.equals(CommandType.LOOK)) {
      lookCount++;

      // 2nd look triggers stranger encounter
      if (!zombieDefeated && !strangerActive && lookCount >= 2) {
        strangerActive = true;

        System.out.println("You look again—something shifts near the cars.");
        System.out.println("A lone figure stands ahead, partially obscured by shadows.");
        System.out.println("They seem to be injured, but you can't tell how badly.");
        System.out.println();

        printActions();
        System.out.println();
        return;
      }

      System.out.println(getLookDescription());
      System.out.println();
      return;
    }

    // TALK STRANGER
    if (type.equals(CommandType.TALK)) {
      if (!arg.equalsIgnoreCase("stranger")) {
        System.out.println("That command does not apply here.");
        System.out.println();
        return;
      }

      if (!strangerActive || zombieDefeated) {
        System.out.println("There’s no stranger here to talk to.");
        System.out.println();
        return;
      }

      if (!talked) {
        System.out.println("You cautiously speak to the stranger...");
        System.out.println("They respond with strange, guttural noises.");
        System.out.println("Their eyes glow red, and you notice open wounds on their skin.");
        System.out.println("This is no longer a question — this is a zombie.");
        System.out.println();
        talked = true;
      } else {
        System.out.println("You've already learned everything you can from them.");
        System.out.println();
      }

      return;
    }

    // ATTACK STRANGER
    if (type.equals(CommandType.ATTACK)) {
      if (!arg.equalsIgnoreCase("stranger")) {
        System.out.println("That command does not apply here.");
        System.out.println();
        return;
      }

      if (!strangerActive || zombieDefeated) {
        System.out.println("There is nothing here to attack.");
        System.out.println();
        return;
      }

      boolean hasWeapon = game.getInventory().hasWeapon();
      int health = game.getHealth();

      if (!talked) {
        System.out.println("You attack without knowing what you're facing...");
        System.out.println();
      }

      if (!hasWeapon || health < 20) {
        System.out.println("You choose to attack anyway.");
        System.out.println("You are unprepared (weapon: " + hasWeapon + ", health: " + health + ").");
        System.out.println("The zombie overwhelms you.");
        System.out.println();

        game.damage(9999);
        return;
      }

      // WIN
      System.out.println("You steady yourself and attack.");
      System.out.println("After a violent struggle, the zombie collapses.");
      System.out.println();

      game.damage(15);

      zombieDefeated = true;
      knifeAvailable = true;

      System.out.println("You survive, but you're injured. (health now " + game.getHealth() + ")");
      System.out.println("As it falls, you notice something in the grass near the curb.");
      System.out.println("A knife.");
      System.out.println();

      // 🔥 THIS IS THE FIX
      printActions();
      System.out.println();
      return;
    }

    // PICKUP KNIFE
    if (type.equals(CommandType.PICKUP)) {
      if (arg.equalsIgnoreCase("knife") && knifeAvailable && !knifeTaken) {
        System.out.println("You pick up the knife. It feels heavy in your hand.");
        game.getInventory().add(new Item("knife", true));
        knifeTaken = true;
        System.out.println();
        return;
      }

      System.out.println("You can't pick that up.");
      System.out.println();
      return;
    }

    // GO SOUTH
    if (type.equals(CommandType.GO)) {
      if (arg.equalsIgnoreCase("south")) {
        if (south == null) {
          System.out.println("You can't go south from here.");
          System.out.println();
          return;
        }
        System.out.println("On your way heading back...");
        game.setCurrentRoom(south);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      System.out.println("You can't go \"" + arg + "\" from here.");
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
    if (strangerActive && !zombieDefeated) {
      System.out.println("  - talk stranger");
      System.out.println("  - attack stranger");
    }
    if (knifeAvailable && !knifeTaken) {
      System.out.println("  - pickup knife");
    }
    System.out.println("  - go south");
    System.out.println("  - status");
  }

  @Override
  public String getLookDescription() {
    return
        "You scan the lot.\n"
      + "Some car doors hang open. Dark smears mark the asphalt.\n"
      + "The flickering light buzzes overhead.\n";
  }

  @Override
  public void reset() {
    lookCount = 0;
    strangerActive = false;
    talked = false;
    zombieDefeated = false;
    knifeAvailable = false;
    knifeTaken = false;
  }
}
