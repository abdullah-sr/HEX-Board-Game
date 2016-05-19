package edu.up.cs301.hex;

import edu.up.cs301.game.GameComputerPlayer;

/**
 * A computerized (automated) hex player. This is an abstract class,
 * that may be subclassed to allow different strategies.
 * 
 * We made no edits to the code provided for this class.
 * 
 * @author Steven R. Vegdahl 
 * @version 2 July 2001
 */

public abstract class HEXComputerPlayer extends GameComputerPlayer
    implements HEXPlayer {
    /**
     * instance variable that tells which piece am I playing (red or blue).
     * This is set once the player finds out which player they are, in the
     * 'initAfterReady' method.
     */
    protected char piece;

    /**
     * Constructor for objects of class HEXComputerPlayer
     */
    public HEXComputerPlayer(String name) {
        // invoke superclass constructor
        super(name);
    }// constructor
    
}// class HEXComputerPlayer
