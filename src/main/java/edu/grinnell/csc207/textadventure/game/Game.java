package edu.grinnell.csc207.textadventure.game;

import edu.grinnell.csc207.textadventure.rooms.Room;

/**
 * Represents the overall state of the game.
 */
public class Game {

  /** The room the player is currently in. */
  private Room currentRoom;

  /** The player's inventory. */
  private Inventory inventory;

  /** Whether the game is over. */
  private boolean gameOver;

  /**
   * Create a new game that starts in the given room.
   *
   * @param startingRoom the initial room
   */
  public Game(Room startingRoom) {
    this.currentRoom = startingRoom;
    this.inventory = new Inventory();
    this.gameOver = false;
  }

  /** Get the current room. 
   * @return the current room
  */
  public Room getCurrentRoom() {
    return this.currentRoom;
  }

  /** Change the current room. 
   * @param newRoom the new current room
  */
  public void setCurrentRoom(Room newRoom) {
    this.currentRoom = newRoom;
  }

  /** Get the inventory. 
   * @return the inventory
  */
  public Inventory getInventory() {
    return this.inventory;
  }

  /** Is the game over? 
   * @return true if the game is over, false otherwise
  */
  public boolean isGameOver() {
    return this.gameOver;
  }

  /** End the game. */
  public void endGame() {
    this.gameOver = true;
  }
}
