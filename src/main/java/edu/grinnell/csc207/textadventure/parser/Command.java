package edu.grinnell.csc207.textadventure.parser;

/**
 * A parsed command with a type and (optional) argument.
 */
public class Command {
  private String type;
  private String argument;

  /**
   * Create a new Command.
   *
   * @param type     the command type (one of CommandType.*)
   * @param argument the argument, or "" if none
   */
  public Command(String type, String argument) {
    this.type = type;
    this.argument = argument;
  }

  /**
   * Get the type of this command.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Get the argument of this command.
   *
   * @return the argument string
   */
  public String getArgument() {
    return argument;
  }
}
