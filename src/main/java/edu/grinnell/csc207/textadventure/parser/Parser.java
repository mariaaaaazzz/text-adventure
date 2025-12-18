package edu.grinnell.csc207.textadventure.parser;

import java.util.Scanner;

/**
 * Turns user input into Command objects.
 */
public class Parser {

  private Scanner in;

  public Parser(Scanner in) {
    this.in = in;
  }

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

    // quit
    if (lower.equals("quit") || lower.equals("exit")) {
      return new Command(CommandType.QUIT, "");
    }

    // status
    if (lower.equals("status")) {
      return new Command(CommandType.STATUS, "");
    }

    // see clue
    if (lower.equals("see clue")) {
      return new Command(CommandType.SEE_CLUE, "");
    }

    // wait
    if (lower.equals("wait")) {
      return new Command(CommandType.WAIT, "");
    }

    // look
    if (lower.equals("look")) {
      return new Command(CommandType.LOOK, "");
    }

    // go <dir>
    if (lower.startsWith("go ")) {
      String arg = line.substring(3).trim();
      if (arg.isEmpty()) {
        return new Command(CommandType.UNKNOWN, "");
      }
      return new Command(CommandType.GO, arg);
    }

    // talk <something>
    if (lower.startsWith("talk ")) {
      String arg = line.substring(5).trim();
      if (arg.isEmpty()) {
        return new Command(CommandType.TALK, "");
      }
      return new Command(CommandType.TALK, arg);
    }


    // "talk"
    if (lower.equals("talk")) {
      return new Command(CommandType.TALK, "");
    }

    // "attack"
    if (lower.equals("attack")) {
      return new Command(CommandType.ATTACK, "");
    }



    // pickup <item>
    if (lower.startsWith("pickup ")) {
      String arg = line.substring(7).trim();
      if (arg.isEmpty()) {
        return new Command(CommandType.PICKUP, "");
      }
      return new Command(CommandType.PICKUP, arg);
    }

    // use <item>
    if (lower.startsWith("use ")) {
      String arg = line.substring(4).trim();
      if (arg.isEmpty()) {
        return new Command(CommandType.USE, "");
      }
      return new Command(CommandType.USE, arg);
    }

    // attack <something>
    if (lower.startsWith("attack ")) {
      String arg = line.substring(7).trim();
      if (arg.isEmpty()) {
        return new Command(CommandType.ATTACK, "");
      }
      return new Command(CommandType.ATTACK, arg);
    }

    return new Command(CommandType.UNKNOWN, line);
  }
}
