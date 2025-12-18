package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

public class Shaw implements Room {

  private Room north;

  private boolean hasMedicine = true;
  private boolean zombieActive = false;
  private boolean zombieTriggered = false;
  private boolean clueGiven = false;

  public Shaw(Room north) {
    this.north = north;
  }

  @Override
  public String getDescription() {
    return
        "You step into SHAW. It smells like disinfectant, and the air feels cold.\n"
      + "A row of empty chairs sits under harsh fluorescent lighting.\n"
      + (hasMedicine ? "A small medical kit sits on a nearby counter.\n" : "")
      + "\n"
      + "Exits:\n"
      + "  - north (back to your dorm)\n"
      + "\n"
      + "Possible actions:\n"
      + "  - look\n"
      + "  - wait\n"
      + (hasMedicine ? "  - pickup medicine\n" : "")
      + (zombieActive ? "  - attack zombie\n" : "")
      + "  - go north\n"
      + "  - status\n";
  }

  @Override
  public void handle(Command command, Game game) {
    String type = command.getType();
    String arg = command.getArgument();

    // WAIT — zombie appears on FIRST wait
    if (type.equals(CommandType.WAIT)) {

      if (!zombieTriggered) {
        zombieTriggered = true;
        zombieActive = true;

        System.out.println("You wait... and realize you're not alone.");
        System.out.println("A figure is standing behind the chairs.");
        System.out.println("Its eyes are red.");
        System.out.println();

        printActions();   // only actions, but INCLUDE pickup medicine if available
        System.out.println();
        return;
      }

      System.out.println("You wait. A clock ticks loudly. No one comes.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.LOOK)) {
      System.out.println(getLookDescription());
      System.out.println();
      return;
    }

    if (type.equals(CommandType.PICKUP)) {
      if (arg.equalsIgnoreCase("medicine") && hasMedicine) {
        System.out.println("You grab the medical kit and patch yourself up.");
        game.heal(10);
        hasMedicine = false;
        System.out.println("Your health increases to " + game.getHealth() + ".");
        System.out.println();
        return;
      }
      System.out.println("You can't pick that up.");
      System.out.println();
      return;
    }

    if (type.equals(CommandType.ATTACK)) {
      if (!arg.equalsIgnoreCase("zombie") || !zombieActive) {
        System.out.println("That command does not apply here.");
        System.out.println();
        return;
      }

      if (game.getInventory().weaponCount() >= 1 && game.getHealth() >= 20) {
        System.out.println("You fight the zombie. It drops to the floor.");
        System.out.println();
        game.damage(15);

        zombieActive = false;
        clueGiven = true;

        System.out.println("In the silence afterward, you spot a torn note.");

        game.giveClue("shaw");
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

    if (type.equals(CommandType.GO) && arg.equalsIgnoreCase("north")) {
      if (north == null) {
        System.out.println("You can't go north from here.");
        System.out.println();
        return;
      }
      System.out.println("You leave SHAW and head back toward your dorm...");
      game.setCurrentRoom(north);
      System.out.println(game.getCurrentRoom().getDescription());
      System.out.println();
      return;
    }

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
    if (hasMedicine) {
      System.out.println("  - pickup medicine");
    }
    if (zombieActive) {
      System.out.println("  - attack zombie");
    }
    System.out.println("  - go north");
    System.out.println("  - status");
  }

  @Override
  public String getLookDescription() {
    return
        "You look around more carefully.\n"
      + "The chairs are spotless, almost unnaturally so.\n"
      + "A dark stain marks the corner near the wall.\n";
  }

  @Override
  public void reset() {
    hasMedicine = true;
    zombieActive = false;
    zombieTriggered = false;
    clueGiven = false;
  }
}
