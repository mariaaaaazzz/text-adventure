package edu.grinnell.csc207.textadventure.parser;

/**
 * CommandType acts as a collection of constant strings
 * representing all command types.
 */
public class CommandType {

  public static final String WAIT = "WAIT";
  public static final String GO = "GO";
  public static final String TALK = "TALK";
  public static final String PICKUP = "PICKUP";
  public static final String USE = "USE";
  public static final String ATTACK = "ATTACK";
  public static final String LOOK = "LOOK";
  
  // when the parser cannot understand the command.
  public static final String UNKNOWN = "UNKNOWN";
}
