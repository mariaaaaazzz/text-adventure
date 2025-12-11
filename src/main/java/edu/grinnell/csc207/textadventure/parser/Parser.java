package edu.grinnell.csc207.textadventure.parser;

import java.util.Scanner;

/**
 * Turns user input into Command objects.
 */
public class Parser {

  /** Source of user input. */
  private Scanner in;

  /**
   * Create a new parser.
   *
   * @param in the Scanner to read from
   */
  public Parser(Scanner in) {
    this.in = in;
  }

  /**
   * Read a line from the user and convert it to a Command.
   *
   * @return a Command representing what the user typed
   */
  public Command nextCommand() {
    System.out.print("> ");
    if (!in.hasNextLine()) {
      return new Command(CommandType.UNKNOWN, "");
    }

    String line = in.nextLine().trim();
    if (line.isEmpty()) {
      return new Command(CommandType.UNKNOWN, "");
    }

    String lower = line.toLowerCase();

    // "wait"
    if (lower.equals("wait")) {
      return new Command(CommandType.WAIT, "");
    }

    // "go north"
    if (lower.startsWith("go ")) {
      String arg = line.substring(3).trim();
      if (arg.isEmpty()) {
        return new Command(CommandType.UNKNOWN, "");
      }
      return new Command(CommandType.GO, arg);
    }

    // "look"
    if (lower.equals("look")) {
      return new Command(CommandType.LOOK, "");
    }

    // Everything else is unknown
    return new Command(CommandType.UNKNOWN, line);
  }
}
