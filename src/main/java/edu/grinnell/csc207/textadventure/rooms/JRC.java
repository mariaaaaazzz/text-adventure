package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;

/**
 * JRC: food room with locked doors and a zombie-release risk.
 */
public class JRC implements Room {

  private Room west; // back to Noyce

  // Door/room state
  private boolean doorsUnlocked = false;
  private boolean tookFood = false;

  // Zombie door encounter state
  private boolean zombieEncounterActive = false;

  public JRC(Room west) {
    this.west = west;
  }

  @Override
  public String getDescription() {
    if (!doorsUnlocked) {
      return
          "You enter the JRC. The smell of food is strong, but the space is empty.\n"
        + "Three heavy doors line the far wall, each with a keyhole.\n"
        + "\n"
        + "Exits:\n"
        + "  - west (back to Noyce)\n"
        + "\n"
        + "Possible actions:\n"
        + "  - look\n"
        + "  - wait\n"
        + "  - use key\n"
        + "  - go west\n"
        + "  - status\n";
    }

    if (zombieEncounterActive) {
      return
          "The east door is open.\n"
        + "A wave of movement and wet breathing spills into the JRC.\n"
        + "You realize, too late, what you just released.\n"
        + "\n"
        + "Possible actions:\n"
        + "  - attack zombies\n"
        + "  - go west (escape)\n"
        + "  - look\n"
        + "  - status\n";
    }

    // Doors unlocked, normal state
    String actions =
        "You are in the JRC. The three doors stand unlocked.\n"
      + "\n"
      + "Doors:\n"
      + "  - north: Food Supply\n"
      + "  - east: WARNING (you hear faint scratching)\n"
      + "  - south: Safe Exit\n"
      + "\n"
      + "Exits:\n"
      + "  - west (back to Noyce)\n"
      + "\n"
      + "Possible actions:\n"
      + "  - look\n"
      + "  - wait\n";

    // Only show "use clue" if the player already has all clues
    if (true) {
      // We can't access Game here; added in printUnlockedActions instead.
    }

    actions +=
        "  - go north / east / south / west\n"
      + "  - status\n";

    return actions;
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

    // WAIT
    if (type.equals(CommandType.WAIT)) {
      System.out.println("You wait. The air feels stale, like the room is holding its breath.");
      System.out.println();
      return;
    }

    // If zombie encounter is active, only allow ESCAPE or ATTACK (plus look/status handled elsewhere)
    if (zombieEncounterActive) {
      if (type.equals(CommandType.ATTACK)) {
        int health = game.getHealth();
        int weapons = game.getInventory().weaponCount();

        if (!arg.equalsIgnoreCase("zombies") && !arg.equalsIgnoreCase("zombie")) {
          System.out.println("That command does not apply here.");
          System.out.println();
          return;
        }

        if (health > 50 && weapons > 2) {
          System.out.println("You brace yourself and fight through the swarm.");
          System.out.println("It works — barely.");
          System.out.println();

          game.damage(15);
          System.out.println("You survive, but you’re hurt. (health now " + game.getHealth() + ")");
          System.out.println();

          zombieEncounterActive = false;
          printUnlockedActions(game);
          System.out.println();
          return;
        }

        System.out.println("You try to fight, but you’re not strong enough for this.");
        System.out.println("(Requires health > 50 and weapons > 2. You have health "
            + health + ", weapons " + weapons + ".)");
        System.out.println("They overwhelm you.");
        System.out.println();

        game.damage(9999);
        return;
      }

      if (type.equals(CommandType.GO) && arg.equalsIgnoreCase("west")) {
        int health = game.getHealth();

        if (health > 35) {
          System.out.println("You sprint back through the west exit and slam the door behind you.");
          game.setCurrentRoom(west);
          System.out.println(game.getCurrentRoom().getDescription());
          System.out.println();
          zombieEncounterActive = false;
          return;
        }

        System.out.println("You try to run, but your body won’t keep up.");
        System.out.println("(Escape requires health > 35. You have " + health + ".)");
        System.out.println("You collapse. The noise closes in.");
        System.out.println();

        game.damage(9999);
        return;
      }

      System.out.println("That command does not apply here.");
      System.out.println();
      return;
    }

    // USE CLUE (only works if doors unlocked and player has all clues)
    if (type.equals(CommandType.USE)) {
      if (arg.equalsIgnoreCase("clue")) {
        if (!doorsUnlocked) {
          System.out.println("You don't have enough context yet. The doors are still locked.");
          System.out.println();
          return;
        }

        if (!game.hasAllClues()) {
          System.out.println("You don't have enough clue pieces yet.");
          System.out.println();
          return;
        }

        System.out.println("You piece the clues together.");
        System.out.println("The message is clear: the SAFE EXIT is the SOUTH door.");
        System.out.println();
        return;
      }
    }

    // USE KEY to unlock the doors
    if (type.equals(CommandType.USE)) {
      if (arg.equalsIgnoreCase("key")) {
        if (!game.getInventory().hasItem("key")) {
          System.out.println("You reach for a key you don’t have.");
          System.out.println();
          return;
        }

        if (doorsUnlocked) {
          System.out.println("The doors are already unlocked.");
          System.out.println();
          return;
        }

        doorsUnlocked = true;
        System.out.println("You insert the key into the lock panel. Three clicks follow.");
        System.out.println("All three doors unlock.");
        System.out.println();

        // NEW: after unlocking, show door choice UI
        if (game.hasAllClues()) {
          System.out.println("You can now use your clue here: use clue");
          System.out.println();
        }

        printUnlockedActions(game);
        System.out.println();
        return;
      }

      System.out.println("That command does not apply here.");
      System.out.println();
      return;
    }

    // GO directions
    if (type.equals(CommandType.GO)) {

      if (arg.equalsIgnoreCase("west")) {
        System.out.println("You head back into Noyce...");
        game.setCurrentRoom(west);
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        return;
      }

      if (!doorsUnlocked) {
        System.out.println("The doors won’t open. They’re locked tight.");
        System.out.println("Maybe a key would help.");
        System.out.println();
        return;
      }

      // Food door
      if (arg.equalsIgnoreCase("north")) {
        if (tookFood) {
          System.out.println("You check the food supply door again, but it’s been cleaned out.");
          System.out.println();
          return;
        }

        tookFood = true;
        System.out.println("You open the food supply door and find something still usable.");
        game.heal(5);
        System.out.println("Your health increases to " + game.getHealth() + ".");
        System.out.println();
        return;
      }

      // Zombie door
      if (arg.equalsIgnoreCase("east")) {
        System.out.println("You unlock the east door and pull it open...");
        System.out.println("A horrible sound answers you from the dark.");
        System.out.println();
        zombieEncounterActive = true;
        System.out.println(getDescription());
        System.out.println();
        return;
      }

      // Safe escape (win)
      if (arg.equalsIgnoreCase("south")) {
        System.out.println("You open the south door. Cool air hits your face.");
        System.out.println("You slip through quietly and find a safe way off campus.");
        System.out.println("You escaped.");
        System.out.println();
        game.endGame();
        return;
      }

      System.out.println("You can't go \"" + arg + "\" from here.");
      System.out.println();
      return;
    }

    // Default
    System.out.println("That command does not apply here.");
    System.out.println();
  }

  private void printUnlockedActions(Game game) {
    System.out.println("Doors:");
    System.out.println("  - north: Food Supply");
    System.out.println("  - east: WARNING (you hear faint scratching)");
    System.out.println("  - south: Safe Exit");
    System.out.println();
    System.out.println("Possible actions:");
    System.out.println("  - look");
    System.out.println("  - wait");
    if (game.hasAllClues()) {
      System.out.println("  - use clue");
    }
    System.out.println("  - go north / east / south / west");
    System.out.println("  - status");
  }

  @Override
  public String getLookDescription() {
    if (!doorsUnlocked) {
      return
          "You look more closely at the three doors.\n"
        + "Each has a heavy lock, like someone expected trouble.\n"
        + "The east door has faint scratch marks near the bottom.\n";
    }

    if (zombieEncounterActive) {
      return
          "You look toward the shadows beyond the east door.\n"
        + "Red eyes blink in the dark.\n"
        + "Something is moving toward you.\n";
    }

    return
        "You scan the JRC.\n"
      + "The room feels like it used to be safe.\n"
      + "Now, even the quiet feels dangerous.\n";
  }

  @Override
  public void reset() {
    doorsUnlocked = false;
    tookFood = false;
    zombieEncounterActive = false;
  }
}
