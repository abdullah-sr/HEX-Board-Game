package edu.up.cs301.hex;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * Contains the state of a Hex game.  Sent by the game when
 * a player wants to enquire about the state of the game.  (E.g., to display
 * it, or to help figure out its next move.)
 * 
 */
public class HEXState extends GameState
{
	private static final long serialVersionUID = 7552321013488624386L;

	// the 24x11 array of char that represents the red's and blue's on the board
	char [][] board;

	// an int that tells whose move it is
	private int playerToMove;

	/**
	 * Constructor for objects of class HEXState
	 */
	public HEXState()
	{
		// initialize the state to be a brand new game
		board = new char[24][11];
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 11; j++) {
				board[i][j] = ' ';
			}
		}

		// make it player 0's move
		playerToMove = 0;
	}// constructor

	/**
	 * Copy constructor for class HEXState
	 *  
	 * @param original
	 * 		the HEXState object that we want to copy
	 */
	public HEXState(HEXState original)
	{
		// create a new 24x11 array, and copy the values from
		// the original
		board = new char[24][11];
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 11; j++) {
				board[i][j] = original.board[i][j];
			}
		}

		// copy the player-to-move information
		playerToMove = original.playerToMove;
	}

	/**
	 * Find out which piece is on a square
	 * 
	 * @param row
	 *		the row being queried
	 * @param col
	 * 		the column being queried
	 * @return
	 * 		the piece at the given square; ' ' if no piece there;
	 * 		'?' if it is an illegal square
	 */
	public char getPiece(int row, int col) {
		// if we're out of bounds or anything, return '?';
		if (board == null || row < 0 || col < 0) return '?';
		if (row >= board.length || col >= board[row].length) return '?';

		// return the character that is in the proper position
		return board[row][col];
	}

	/**
	 * Sets a piece on a square
	 * 
	 * @param row
	 * 		the row being queried
	 * @param
	 * 		col the column being queried
	 * @param
	 * 		piece the piece to place
	 */
	public void setPiece(int row, int col, char piece) {
		// if we're out of bounds or anything, return;
		if (board == null || row < 0 || col < 0) return;
		if (row >= board.length || col >= board[row].length) return;

		// return the character that is in the proper position
		board[row][col] = piece;
	}

	public char[][] getBoard()
	{
		return board;
	}


	/**
	 * Tells whose move it is.
	 * 
	 * @return the index (0 or 1) of the player whose move it is.
	 */
	public int getWhoseMove() {
		return playerToMove;
	}

	/**
	 * set whose move it is
	 * @param id
	 * 		the player we want to set as to whose move it is
	 */
	public void setWhoseMove(int id) {
		playerToMove = id;
	}	

}
