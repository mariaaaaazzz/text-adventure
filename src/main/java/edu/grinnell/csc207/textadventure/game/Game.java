package edu.grinnell.csc207.textadventure.game;

import edu.grinnell.csc207.textadventure.rooms.Room;

/**
 * Represents the overall state of the game.
 */
public class Game {

  /* Starting room (for restart) */
  private final Room startingRoom;

  /* Current room */
  private Room currentRoom;

  /* Inventory */
  private Inventory inventory;

  /* Health system */
  private int health;

  /* Shield system (blocks one lethal hit) */
  private boolean shieldReady;
  private boolean shieldUsed;

  /* Clue tracking */
  private boolean clueFromShaw;
  private boolean clueFromNoyce;
  private boolean clueFromKistle;

  /* Game over flag */
  private boolean gameOver;

  /**
   * Create a new game.
   */
  public Game(Room startingRoom) {
    this.startingRoom = startingRoom;
    this.currentRoom = startingRoom;
    this.inventory = new Inventory();
    this.health = 100;
    this.gameOver = false;
    this.shieldReady = false;
    this.shieldUsed = false;
  }

  /* -------------------------
   * Room control
   * ------------------------- */

  public Room getCurrentRoom() {
    return currentRoom;
  }

  public void setCurrentRoom(Room room) {
    this.currentRoom = room;
  }

  /* -------------------------
   * Inventory
   * ------------------------- */

  public Inventory getInventory() {
    return inventory;
  }

  /* -------------------------
   * Health
   * ------------------------- */

  public int getHealth() {
    return health;
  }

  public void heal(int amount) {
    if (amount <= 0) {
      return;
    }
    health = Math.min(100, health + amount);
  }

  /**
   * Apply damage to the player.
   * Zombie lethal attacks use damage(9999).
   */
  public void damage(int amount) {

    // Shield blocks ONE lethal zombie hit
    if (amount >= 9999 && hasUsableShield()) {
      shieldUsed = true;
      System.out.println("Your shield absorbs the zombie’s attack and shatters!");
      System.out.println();
      return;
    }

    health -= amount;
    if (health < 0) {
      health = 0;
    }
  }

  /* -------------------------
   * Shield
   * ------------------------- */

  public void giveShield() {
    shieldReady = true;
    shieldUsed = false;
  }

  public boolean hasUsableShield() {
    return shieldReady && !shieldUsed;
  }

  /* -------------------------
   * Clues
   * ------------------------- */

  public void giveClue(String source) {
    if (source.equalsIgnoreCase("shaw")) {
      clueFromShaw = true;
    } else if (source.equalsIgnoreCase("noyce")) {
      clueFromNoyce = true;
    } else if (source.equalsIgnoreCase("kistle")) {
      clueFromKistle = true;
    }
  }

  public boolean hasAllClues() {
    return clueFromShaw && clueFromNoyce && clueFromKistle;
  }

  public int clueCount() {
    int count = 0;
    if (clueFromShaw) {
      count++;
    }
    if (clueFromNoyce) {
      count++;
    }
    if (clueFromKistle) {
      count++;
    }
    return count;
  }

  public String clueProgressString() {
    return "(" + clueCount() + "/3)";
  }

  /* -------------------------
   * Game lifecycle
   * ------------------------- */

  public boolean isGameOver() {
    return gameOver;
  }

  public void endGame() {
    gameOver = true;
  }

  /**
   * Reset the game after death.
   */
  public void restart() {
    inventory.clear();
    health = 100;
    shieldReady = false;
    shieldUsed = false;
    clueFromShaw = false;
    clueFromNoyce = false;
    clueFromKistle = false;
    gameOver = false;

    // IMPORTANT: put player back at start
    currentRoom = startingRoom;
  }
}
