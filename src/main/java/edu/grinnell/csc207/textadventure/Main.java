package edu.grinnell.csc207.textadventure;

import java.util.Scanner;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.Parser;
import edu.grinnell.csc207.textadventure.rooms.DormRoom;
import edu.grinnell.csc207.textadventure.rooms.ParkingLot;

/**
 * The main class for the text adventure game.
 */
public class Main {

  public static void main(String[] args) {
    DormRoom dorm = new DormRoom(null);
    ParkingLot lot = new ParkingLot(dorm);

    dorm.setNorth(lot);          // connect dorm to lot
    Game game = new Game(dorm);  // start in the same dorm object

    System.out.println(game.getCurrentRoom().getDescription());

    Scanner in = new Scanner(System.in);
    Parser parser = new Parser(in);

    while (!game.isGameOver()) {
      Command cmd = parser.nextCommand();
      game.getCurrentRoom().handle(cmd, game);
    }

    in.close();
  }
}