package edu.up.cs301.hex;

import edu.up.cs301.game.GameHumanPlayer;

/**
 * A human (i.e., GUI) version of a Hex player.  This is an abstract
 * class and should be subclassed to "fill out" the behavior.
 * 
 * We made no edits to the code provided for this class.
 * 
 */

public abstract class HEXHumanPlayer extends GameHumanPlayer implements HEXPlayer
{
	/**
	 * HexHumanPlayer constructor.
	 */
	public HEXHumanPlayer(String name) {
		// invoke the superclass constructor
		super(name);
	}
}


