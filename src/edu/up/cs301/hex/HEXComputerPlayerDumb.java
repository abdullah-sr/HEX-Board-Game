package edu.up.cs301.hex;

import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * This is a really dumb computer player that always just makes a random move
 * it's so stupid that it sometimes tries to make moves on non-blank spots.
 * 
 */
public class HEXComputerPlayerDumb extends HEXComputerPlayer
{

	private static final int L_ONN = 1;
	private static final int L_OFF = 2;

	private int[][] mCells = {
			//1      2     3       4       5     6c     7      8     9      10     11
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //1
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //2
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //3
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //4
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF }, //5
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF }, //6
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF }, //7
			{ L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF }, //8
			{ L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF }, //9 
			{ L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF }, //10
			{ L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF }, //11
			{ L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF }, //12
			{ L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN }, //13
			{ L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN }, //14 center
			{ L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN }, //15
			{ L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF }, //16
			{ L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF }, //17
			{ L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF }, //18
			{ L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF }, //19
			{ L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF }, //20 
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF }, //21
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF }, //22
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF }, //23
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //24
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }}; //25  NOT USED
	/**
	 * Constructor for the HEXComputerPlayerDumb class
	 */
	public HEXComputerPlayerDumb(String name) {
		// invoke superclass constructor
		super(name); 
	}

	/**
	 * Called when the player receives a game-state (or other info) from the
	 * game.
	 * 
	 * @param gameInfo
	 * 		the message from the game
	 */
	@Override
	protected void receiveInfo(GameInfo info) {

		// if it was a "not your turn" message, just ignore it
		if (info instanceof NotYourTurnInfo) return;


		int xVal = 0;
		int yVal = 0;

		// pick x and y positions at random place on board
		while (mCells[yVal][xVal] != L_ONN) {
			xVal = (int)(11*Math.random());
			yVal = (int)(24*Math.random());  
		}



		// delay for two seconds to make opponent think we're thinking
		sleep(2000);

		// Submit our move to the game object. We haven't even checked it it's
		// our turn, or that that position is unoccupied. If it was not our turn,
		// we'll get a message back that we'll ignore. If it was an illegal move,
		// we'll end up here again (and possibly again, and again). At some point,
		// we'll end up randomly picking a move that is legal.
		game.sendAction(new HEXMoveAction(this, xVal, yVal)); 
	}
}
