package edu.grinnell.csc207.textadventure.rooms;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.parser.Command;

/**
 * A location in the text adventure game.
 */
public interface Room {

  /**
   * Returns the text shown when the player is in this room.
   *
   * @return description of this room
   */
  String getDescription();

  /**
   * Handle a command issued while the player is in this room.
   *
   * @param command the command the player typed
   * @param game    the overall game state
   */
  void handle(Command command, Game game);
}
