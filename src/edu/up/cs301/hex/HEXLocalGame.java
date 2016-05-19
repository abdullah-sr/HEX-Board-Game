package edu.up.cs301.hex;


import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * The HEXLocalGame class for a simple hex game.  Defines and enforces
 * the game rules; handles interactions with players.
 * 
 */

public class HEXLocalGame extends LocalGame implements HEXGame {

	// the game's state
	private HEXState state;


	//arrays to determine if there is a winner
	boolean[][] player1Marked = new boolean [11][24];
	boolean[][] player2Marked = new boolean [11][24];

	private int row = 0;	// row touched
	private int col = 0;	//col touched
	private int L_ONN = 1;	//if the hex is lit up (drawn) it is L_ONN
	private int L_OFF = 2;

	private int[][] mCells = {
			//1      2     3       4       5     6c     7      8     9      10     11
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //1
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //1
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //1
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //1
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF }, //2
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF }, //3
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF }, //4
			{ L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF }, //5
			{ L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF }, //6 
			{ L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF }, //7
			{ L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF }, //8
			{ L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF }, //9
			{ L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN }, //10
			{ L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN }, //11 center
			{ L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN }, //12
			{ L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF }, //13
			{ L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF }, //14
			{ L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF }, //15
			{ L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF }, //16
			{ L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF }, //17 
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF }, //18
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF }, //19
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF }, //20
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_ONN, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }, //21
			{ L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF, L_OFF }}; //22  NOT USED

	/**
	 * Constructor for the HEXsLocalGame.
	 */
	public HEXLocalGame() {

		// perform superclass initialization
		super();

		// create a new, unfilled-in HexState object
		state = new HEXState();

	}

	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 * 
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	public String checkIfGameOver() {

		if (checkRightSide(0)) // check if there is a marked piece on right side for player 0
			return playerNames[0]+" is the winner.";

		if (checkRightSide(1)) // check if there is a marked piece on right side for player 1
			return playerNames[1]+" is the winner.";

		return null;
	}


	/**
	 * Notify the given player that its state has changed. This should involve sending
	 * a GameInfo object to the player. If the game is not a perfect-information game
	 * this method should remove any information from the game that the player is not
	 * allowed to know.
	 * 
	 * @param p
	 * 		the player to notify
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// make a copy of the state, and send it to the player
		p.sendInfo(new HEXState(state));
	}

	/**
	 * Tell whether the given player is allowed to make a move at the
	 * present point in the game. 
	 * 
	 * @param playerIdx
	 * 		the player's player-number (ID)
	 * @return
	 * 		true if the player is allowed to move
	 */
	public boolean canMove(int playerIdx) {
		return playerIdx == state.getWhoseMove();
	}


	/**
	 * Makes a move on behalf of a player.
	 * 
	 * @param action
	 * 			The move that the player has sent to the game
	 * @return
	 * 			Tells whether the move was a legal one.
	 */
	@Override
	public boolean makeMove(GameAction action) {

		// get the row and column position of the player's move
		HEXMoveAction tm = (HEXMoveAction) action;
		row = tm.getRow();
		col = tm.getCol();

		//If move is not legal, do not make the intended move
		if (isMoveLegal() == false) {
			return false;
		}

		// get the 0/1 id of our player
		int playerId = getPlayerIdx(tm.getPlayer());

		// get the 0/1 id of the player whose move it is
		int whoseMove = state.getWhoseMove();

		if (playerId != whoseMove) {
			return false;
		}

		// place the player's piece on the selected hex
		if (playerId == 0) {
			state.setPiece(col, row, '0');

			//starts algorithm to check for winners
			if(isCellAgainstLeftEdge(row,col, playerId)) {
				player1Marked[row][col] = true;
				MarkUnmarkedNeighbors(row, col, '0', player1Marked);
			}
			else 
				MarkIfNextToMarked(row, col, '0', player1Marked);
		}

		if (playerId == 1){
			state.setPiece(col, row, '1');

			if(isCellAgainstLeftEdge(row,col, playerId)){
				player2Marked[row][col] = true;
				MarkUnmarkedNeighbors(row, col, '1', player2Marked);
			}

			else
				MarkIfNextToMarked(row, col, '1', player2Marked);

		}

		// make it the other player's turn
		state.setWhoseMove(1-whoseMove);

		// return true, indicating the move was made
		return true;
	}


	/**
	 * Determines if the move made is legal. It will make sure that the space
	 * the player selected is not already selected by either of the two players
	 */
	public boolean isMoveLegal() {
		if(state.getPiece(col, row) == ' ') {
			return true;
		}

		return false;
	}

	public boolean getIsMoveLegal() {
		return isMoveLegal();
	}

	public char[][] getLocalBoard(){
		return state.getBoard();
	}


	/**
	 * Checks the neighboring cells to see if one is already "marked"
	 * 
	 * @param row: the row of the current hex
	 * @param col: the column of the current hex
	 * @param playerID: the id of the player who made the move
	 * @param playerArary: the array for the player who's turn it is
	 *
	 */
	public void MarkIfNextToMarked(int row, int col, char playerId, boolean[][] playerArray){
		//for even columns
		if(col % 2 == 0){
			// start at the top and will go clockwise around the neighbor six hexes
			//checking if it is connected to a marked hex
			if(isCellValid(row-1,col) && playerArray[row-1][col]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

			if(isCellValid(row-1,col+1) && playerArray[row-1][col+1]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

			if(isCellValid(row,col+1) && playerArray[row][col+1]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

			if(isCellValid(row+1,col) && playerArray[row+1][col]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

			if(isCellValid(row,col-1) && playerArray[row][col-1]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);

			}

			if(isCellValid(row-1,col-1) && playerArray[row-1][col-1]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

		}


		//for odd columns
		else if (col % 2 != 0){
			// start at the top and will go clockwise around the neighbor six hexes
			//checking if it is connected to a marked hex
			if(isCellValid(row-1,col) && playerArray[row-1][col]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

			if(isCellValid(row,col+1) && playerArray[row][col+1]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

			if(isCellValid(row+1,col+1) && playerArray[row+1][col+1]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

			if(isCellValid(row+1,col) && playerArray[row+1][col]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

			if(isCellValid(row+1,col-1) && playerArray[row+1][col-1]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

			if(isCellValid(row,col-1) && playerArray[row][col-1]){
				playerArray[row][col] = true;
				MarkUnmarkedNeighbors(row, col, playerId, playerArray);
			}

		}
	}

	/**
	 * Checks if cell passed in is a legal cell to claim
	 * 
	 * @param row: the row of the current hex
	 * @param col: the column of the current hex
	 *@return
	 * 	true if it is an L_ONN cell and is inside of the board
	 * else false
	 */
	boolean isCellValid(int row, int col){
		if ((col < 0) || (col >= mCells.length) || (row < 0) || (row >= mCells[row].length))
			return false; // return false if cell is outside the board

		if (mCells[col][row] != L_ONN) 
			return false; // return false if cell isn't on

		return true;
	}


	/**
	 * Checks the "starting" edge for player one or player two
	 * If there is no hex for the correct player against the left edge, the player cannot have won
	 * Checks the whole left edge of the board
	 * 
	 * @param row: the row of the current hex
	 * @param col: the column of the current hex
	 * @param playerID: the id of the player who made the move
	 * @return
	 * 		true if the player does have a piece on the left edge
	 * 		false if the do not
	 *
	 */
	boolean isCellAgainstLeftEdge(int row, int col, int playerId){

		//checks red
		if (playerId == 0){

			if(row == 0 && col == 13) //in first row
				return true;

			int j = 12; //we know it is in the 12th row
			for(int i = 1; i < 6; i++)
			{
				if( row == i && col == j)
					return true;

				if( row == i && col == j-1)
					return true;

				j-=2;
			}
		}

		//checks for blue
		if (playerId == 1){
			if(row == 5 && col == 3) //in first row
				return true;

			int j = 4;
			for(int i = 6; i < 11; i++)
			{
				if( row == i && col == j)
					return true;

				if( row == i && col == j+1)
					return true;

				j+=2;
			}
		}

		return false;
	}

	/**
	 * Checks the "finishing" edge for player one or player two
	 * 
	 * @param playerID: the id of the player who made the move
	 * @return
	 * 		true if the player has a piece on the right side
	 * 		else false
	 */
	//checks the "finish" side for player one or two
	boolean checkRightSide(int playerId){

		if(playerId == 0){
			if(player1Marked[5][23])
				return true;

			int j = 22;
			for(int i = 6; i < 11; i++)
			{
				if(player1Marked[i][j])
					return true;

				if(player1Marked[i][j-1])
					return true;

				j-=2;
			}
		}//if


		if(playerId == 1){
			if(player2Marked[0][13])
				return true;

			int j = 14;
			for(int i = 1; i < 6; i++)
			{
				if(player2Marked[i][j])
					return true;

				if(player2Marked[i][j+1])
					return true;

				j+=2;
			}
		}//if

		return false;

	}

	/**
	 * For a marked piece, it looks around at all neighbors and marks them if they
	 * are "friendly" hexes.
	 * 
	 * @param row: the row of the current hex
	 * @param col: the column of the current hex
	 * @param playerID: the id of the player who made the move
	 * @param playerArary: the array for the player who's turn it is
	 * 
	 */

	public void MarkUnmarkedNeighbors(int row, int col, char playerId ,boolean[][] playerArray) {
		if(col % 2 == 0){
			// start at the top and will go clockwise around the neighbor six hexes
			if(state.getPiece(col,row-1) == playerId && !playerArray[row-1][col]){
				playerArray[row-1][col] = true;
				MarkUnmarkedNeighbors(row-1, col, playerId, playerArray);
			}

			if(state.getPiece(col+1,row-1) == playerId && !playerArray[row-1][col+1]){
				playerArray[row-1][col+1] = true;
				MarkUnmarkedNeighbors(row-1, col+1, playerId, playerArray);
			}

			if(state.getPiece(col+1,row) == playerId && !playerArray[row][col+1]){
				playerArray[row][col+1] = true;
				MarkUnmarkedNeighbors(row, col+1, playerId, playerArray);
			}

			if(state.getPiece(col,row+1) == playerId && !playerArray[row+1][col]){
				playerArray[row+1][col] = true;
				MarkUnmarkedNeighbors(row+1, col, playerId, playerArray);
			}

			if(state.getPiece(col-1,row) == playerId && !playerArray[row][col-1]){
				playerArray[row][col-1] = true;
				MarkUnmarkedNeighbors(row, col-1, playerId, playerArray);
			}

			if(state.getPiece(col-1,row-1) == playerId && !playerArray[row-1][col-1]){
				playerArray[row-1][col-1] = true;
				MarkUnmarkedNeighbors(row-1, col-1, playerId, playerArray);
			}
		}

		else if(col % 2 != 0){
			// start at the top and will go clockwise around the neighbor six hexes
			if(state.getPiece(col,row-1) == playerId && !playerArray[row-1][col]){
				playerArray[row-1][col] = true;
				MarkUnmarkedNeighbors(row-1, col, playerId, playerArray);
			}


			if(state.getPiece(col+1,row) == playerId && !playerArray[row][col+1]){
				playerArray[row][col+1] = true;
				MarkUnmarkedNeighbors(row, col+1, playerId, playerArray);
			}

			if(state.getPiece(col+1,row+1) == playerId && !playerArray[row+1][col+1]){
				playerArray[row+1][col+1] = true;
				MarkUnmarkedNeighbors(row+1, col+1, playerId, playerArray);
			}

			if(state.getPiece(col,row+1) == playerId && !playerArray[row+1][col]){
				playerArray[row+1][col] = true;
				MarkUnmarkedNeighbors(row+1, col, playerId, playerArray);
			}

			if(state.getPiece(col-1,row+1) == playerId && !playerArray[row+1][col-1]){
				playerArray[row+1][col-1] = true;
				MarkUnmarkedNeighbors(row+1, col-1, playerId, playerArray);
			}

			if(state.getPiece(col-1,row) == playerId && !playerArray[row][col-1]){
				playerArray[row][col-1] = true;
				MarkUnmarkedNeighbors(row, col-1, playerId, playerArray);
			}

		}

	}

}