package edu.grinnell.csc207.textadventure;

import java.util.Scanner;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.CommandType;
import edu.grinnell.csc207.textadventure.parser.Parser;
import edu.grinnell.csc207.textadventure.rooms.DormRoom;
import edu.grinnell.csc207.textadventure.rooms.HSSC;
import edu.grinnell.csc207.textadventure.rooms.JRC;
import edu.grinnell.csc207.textadventure.rooms.Kistle;
import edu.grinnell.csc207.textadventure.rooms.Noyce;
import edu.grinnell.csc207.textadventure.rooms.ParkingLot;
import edu.grinnell.csc207.textadventure.rooms.Shaw;

public class Main {

  public static void main(String[] args) {

    DormRoom dorm = new DormRoom(null);

    // ParkingLot already contains the stranger/zombie logic.
    ParkingLot lot = new ParkingLot(dorm);

    HSSC hssc = new HSSC(dorm);
    Shaw shaw = new Shaw(dorm);

    Noyce noyce = new Noyce(dorm);
    Kistle kistle = new Kistle(noyce);
    JRC jrc = new JRC(noyce);

    dorm.setNorth(lot);
    dorm.setWest(hssc);
    dorm.setSouth(shaw);
    dorm.setEast(noyce);

    noyce.setNorth(kistle);
    noyce.setEast(jrc);

    Game game = new Game(dorm);

    System.out.println(game.getCurrentRoom().getDescription());
    System.out.println();

    Scanner in = new Scanner(System.in);
    Parser parser = new Parser(in);

    while (!game.isGameOver()) {
      Command cmd = parser.nextCommand();

      // quit works everywhere
      if (cmd.getType().equals(CommandType.QUIT)) {
        System.out.println("Goodbye!");
        System.out.println();
        game.endGame();
        break;
      }

      // status works everywhere
      if (cmd.getType().equals(CommandType.STATUS)) {
        System.out.println("Health: " + game.getHealth() + " / 100");
        System.out.println("Inventory: " + game.getInventory().listItems());
        System.out.println("Weapons: " + game.getInventory().weaponCount());
        System.out.println();
        continue;
      }

      // see clue works everywhere (only after 3 clues)
      if (cmd.getType().equals(CommandType.SEE_CLUE)) {
        if (!game.hasAllClues()) {
          System.out.println("You don't have enough clue pieces yet.");
          System.out.println();
        } else {
          System.out.println("You piece the clues together.");
          System.out.println("The message is clear: the SAFE EXIT in the JRC is the SOUTH door.");
          System.out.println();
        }
        continue;
      }

      game.getCurrentRoom().handle(cmd, game);

      // warnings
      if (game.getHealth() > 0 && game.getHealth() < 20) {
        System.out.println("!!! CRITICAL WARNING: Your health is below 20 (" + game.getHealth() + ").");
        System.out.println();
      } else if (game.getHealth() > 0 && game.getHealth() < 50) {
        System.out.println("! Warning: Your health is below 50 (" + game.getHealth() + ").");
        System.out.println();
      }

      // death -> restart
      if (game.getHealth() == 0) {
        System.out.println("You are dead. Everything goes black.");
        System.out.println("-------------------------------");
        System.out.println("RESTARTING...");
        System.out.println("-------------------------------");
        System.out.println();

        game.restart();

        // reset rooms with state
        shaw.reset();
        kistle.reset();
        noyce.reset();
        lot.reset();

        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
      }
    }

    in.close();
  }
}
