package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * Encounter with a suspicious stranger who may be a zombie.
 */
public class StrangerEncounter implements Room {

  private Room escapeRoom;

  private boolean talked = false;
  private boolean isZombie = true; // fixed for now; can randomize later

  public StrangerEncounter(Room escapeRoom) {
    this.escapeRoom = escapeRoom;
  }

  @Override
  public String getDescription() {
    return
        "A lone figure stands ahead, partially obscured by shadows.\n"
      + "They seem to be injured, but you can't tell how badly.\n"
      + "\n"
      + "Possible actions:\n"
      + "  - talk stranger\n"
      + "  - attack stranger\n"
      + "  - status\n"
      + "  - go south (escape)\n";
  }

  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    // LOOK
    if (type.equals(CommandType.LOOK)) {
      System.out.println(getLookDescription());
      System.out.println();
      return;
    }

    // TALK (special behavior: determine zombie)
    if (type.equals(CommandType.TALK)) {
      if (!talked) {
        System.out.println("You cautiously speak to the stranger...");

        if (isZombie) {
          System.out.println("They respond with strange, guttural noises.");
          System.out.println("Their eyes glow red, and you notice open wounds on their skin.");
          System.out.println("This is no longer a question — this is a zombie.");
          System.out.println();
        } else {
          System.out.println("They respond shakily, but clearly. They're human.");
          System.out.println();
        }

        talked = true;
      } else {
        System.out.println("You've already learned everything you can from them.");
        System.out.println();
      }
      return;
    }

    // GO (escape)
    if (type.equals(CommandType.GO)) {
      if (arg.equalsIgnoreCase("south")) {
        System.out.println("You decide not to risk it and slowly back away...");
        game.setCurrentRoom(escapeRoom);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }
      System.out.println("You can't go \"" + arg + "\" from here.");
      System.out.println();
      return;
    }

    // ATTACK (allowed always, punished if weak)
    if (type.equals(CommandType.ATTACK)) {
      boolean hasWeapon = game.getInventory().hasWeapon();
      int health = game.getHealth();

      if (!talked) {
        System.out.println("You attack without knowing what you're facing...");
        System.out.println();
      }

      // Forced death conditions
      if (!hasWeapon || health < 20) {
        System.out.println("You choose to attack anyway.");
        System.out.println("You are unprepared (weapon: " + hasWeapon
            + ", health: " + health + ").");
        System.out.println("The zombie overwhelms you.");
        System.out.println();

        game.damage(9999); // triggers death & restart in Main
        return;
      }

      // Successful attack
      System.out.println("You steady yourself and attack.");
      System.out.println("After a violent struggle, the zombie collapses.");
      System.out.println();

      game.damage(15);
      System.out.println("You survive, but you're injured. (health now "
          + game.getHealth() + ")");
      System.out.println();

      game.setCurrentRoom(escapeRoom);
      System.out.println(game.getCurrentRoom().getDescription());
      System.out.println();
      return;
    }

    // DEFAULT
    System.out.println("That command does not apply here.");
    System.out.println();
  }

  @Override
  public String getLookDescription() {
    return
        "You focus on the figure more carefully.\n"
      + "Their posture is wrong — too stiff, too unnatural.\n"
      + "Their breathing, if it exists at all, is impossible to hear.\n"
      + "Every instinct tells you to be careful.\n";
  }
}
