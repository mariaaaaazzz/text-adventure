package edu.grinnell.csc207.textadventure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import edu.grinnell.csc207.textadventure.game.Game;
import edu.grinnell.csc207.textadventure.game.Inventory;
import edu.grinnell.csc207.textadventure.game.Item;
import edu.grinnell.csc207.textadventure.rooms.DormRoom;

public class Tests {

  /**
   * Create a fresh game instance for testing.
   *
   * @return a new Game starting in a dorm room
   */
  private Game freshGame() {
    DormRoom dorm = new DormRoom(null);
    return new Game(dorm);
  }

  /**
   * GAME: Health edge cases
   */

  /**
   * Damage should reduce health and clamp at zero.
   */
  @Test
  public void testDamageClampsAtZero() {
    Game game = freshGame();
    assertEquals(100, game.getHealth());

    game.damage(30);
    assertEquals(70, game.getHealth());

    game.damage(9999);
    assertEquals(0, game.getHealth());
  }

  /**
   * Healing should increase health when below max.
   */
  @Test
  public void testHealIncreasesHealth() {
    Game game = freshGame();
    game.damage(40);
    assertEquals(60, game.getHealth());

    game.heal(10);
    assertEquals(70, game.getHealth());
  }

  /**
   * Healing should not increase health beyond 100.
   */
  @Test
  public void testHealIsCappedAt100() {
    Game game = freshGame();
    assertEquals(100, game.getHealth());

    game.heal(25);
    assertEquals(100, game.getHealth(), "Health should be capped at 100");
  }

  /**
   * GAME: Shield edge cases
   */

  /**
   * A shield should block exactly one lethal hit.
   */
  @Test
  public void testShieldBlocksExactlyOneLethalHit() {
    Game game = freshGame();

    assertFalse(game.hasUsableShield());
    assertEquals(100, game.getHealth());

    game.giveShield();
    assertTrue(game.hasUsableShield());

    game.damage(9999);
    assertEquals(100, game.getHealth(), "Shield should absorb the first lethal hit");
    assertFalse(game.hasUsableShield(), "Shield should be spent after blocking once");

    game.damage(9999);
    assertEquals(0, game.getHealth(), "Second lethal hit should no longer be blocked");
  }

  /**
   * A shield should not block non-lethal damage.
   */
  @Test
  public void testShieldDoesNotBlockNonLethalDamage() {
    Game game = freshGame();
    game.giveShield();

    game.damage(10);
    assertEquals(90, game.getHealth(), "Shield should not block normal damage");
    assertTrue(game.hasUsableShield(),
        "Shield should still be usable after non-lethal damage");
  }

  /**
   * GAME: Clue edge cases
   */

  /**
   * Clues should be order-independent and case-insensitive.
   */
  @Test
  public void testCluesOrderIndependentAndCaseInsensitive() {
    Game game = freshGame();

    assertFalse(game.hasAllClues());

    game.giveClue("KiStLe");
    assertFalse(game.hasAllClues());

    game.giveClue("SHAW");
    assertFalse(game.hasAllClues());

    game.giveClue("noyce");
    assertTrue(game.hasAllClues(),
        "All three clues should be recognized regardless of order or case");
  }

  /**
   * Unknown clue sources should not affect game state.
   */
  @Test
  public void testUnknownClueSourceDoesNothing() {
    Game game = freshGame();
    assertFalse(game.hasAllClues());

    game.giveClue("notARealRoom");
    assertFalse(game.hasAllClues(),
        "Unknown clue sources should not accidentally set a clue");
  }

  /**
   * INVENTORY: Edge cases
   */

  /**
   * An empty inventory should report no items and no weapons.
   */
  @Test
  public void testInventoryEmptyListItems() {
    Inventory inv = new Inventory();
    assertEquals("empty", inv.listItems());
    assertFalse(inv.hasWeapon());
    assertEquals(0, inv.weaponCount());
  }

  /**
   * Inventory lookup should be case-insensitive.
   */
  @Test
  public void testInventoryHasItemIsCaseInsensitive() {
    Inventory inv = new Inventory();
    inv.add(new Item("Key"));

    assertTrue(inv.hasItem("key"));
    assertTrue(inv.hasItem("KEY"));
    assertTrue(inv.hasItem("KeY"));
  }

  /**
   * Removing an item should be case-insensitive and return correctly.
   */
  @Test
  public void testInventoryRemoveIsCaseInsensitiveAndReturnsCorrectly() {
    Inventory inv = new Inventory();
    inv.add(new Item("knife", true));

    assertTrue(inv.remove("KNIFE"),
        "Should remove item case-insensitively");
    assertFalse(inv.hasItem("knife"));

    assertFalse(inv.remove("knife"));
  }

  /**
   * Weapon counting should only include weapon items.
   */
  @Test
  public void testWeaponCountAndHasWeapon() {
    Inventory inv = new Inventory();
    inv.add(new Item("shield", false));
    inv.add(new Item("wrench", true));
    inv.add(new Item("knife", true));

    assertEquals(2, inv.weaponCount());
    assertTrue(inv.hasWeapon());
  }

  /**
   * listItems should not end with a trailing comma.
   */
  @Test
  public void testListItemsFormattingNoTrailingComma() {
    Inventory inv = new Inventory();
    inv.add(new Item("key"));
    inv.add(new Item("shield"));
    String s = inv.listItems();

    assertFalse(s.endsWith(", "),
        "listItems should not end with a trailing comma+space");
    assertTrue(s.contains("key"));
    assertTrue(s.contains("shield"));
  }

  /**
   * GAME: Restart edge cases
   */

  /**
   * Restarting the game should reset all game-level state.
   */
  @Test
  public void testRestartResetsInventoryHealthShieldCluesAndGameOver() {
    Game game = freshGame();

    game.damage(70);
    game.giveShield();
    game.giveClue("shaw");

    game.getInventory().add(new Item("key"));
    game.getInventory().add(new Item("knife", true));

    game.endGame();
    assertTrue(game.isGameOver());

    game.restart();

    assertEquals(100, game.getHealth(),
        "restart should restore health");
    assertEquals("empty", game.getInventory().listItems(),
        "restart should clear inventory");
    assertFalse(game.hasUsableShield(),
        "restart should reset shield state");
    assertFalse(game.hasAllClues(),
        "restart should reset clues");
    assertFalse(game.isGameOver(),
        "restart should clear gameOver flag");
  }
}
