package edu.grinnell.csc207.textadventure;

import java.util.Scanner;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;
import edu.grinnell.csc207.textadventure.parser.Parser;
import edu.grinnell.csc207.textadventure.rooms.DormRoom;

/**
 * The main class for the text adventure game.
 */
public class Main {

  public static void main(String[] args) {
    Game game = new Game(new DormRoom());

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
