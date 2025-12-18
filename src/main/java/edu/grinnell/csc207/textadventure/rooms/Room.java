package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;

public interface Room {
  String getDescription();
  void handle(Command command, Game game);
  String getLookDescription();

  default void reset() {
    // rooms without state don't need to do anything
  }
}
