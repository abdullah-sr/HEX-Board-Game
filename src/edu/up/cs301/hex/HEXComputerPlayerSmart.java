package edu.up.cs301.hex;


import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import android.graphics.Point;

/**
 * A computerized hex player that builds a chain starting from the 
 * appropriate edge (according to it's color), moving toward the opposite side.
 * Continues in a straight path (choosing from the 2 options directly in front)
 * as long as it is able, then moves to the side.
 * If blocked off in front and sides, it spawns another starting cell from edge, 
 * and repeats above process
 * If edge is completely full, it picks a random board placement
 * 
 * 
 */
public class HEXComputerPlayerSmart extends HEXComputerPlayer
{

	private int L_ONN = 1;
	private int L_OFF = 2;
	HEXState myState;
	
	Point[] RedLeftEdge = {
			new Point(5, 3),
			new Point(6, 4),
			new Point(6, 5),
			new Point(7, 6),
			new Point(7, 7),
			new Point(8, 8),
			new Point(8, 9),
			new Point(9, 10),
			new Point(9, 11),
			new Point(10, 12),
			new Point(10, 13)};
	
	Point[] BlueTopEdge = {
			new Point(5, 3),
			new Point(5, 4),
			new Point(4, 5),
			new Point(4, 6),
			new Point(3, 7),
			new Point(3, 8),
			new Point(2, 9),
			new Point(2, 10),
			new Point(1, 11),
			new Point(1, 12),
			new Point(0, 13)};
	int xVal;
	int yVal;
	boolean firstMove = true;

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
	 * constructor for a computer player
	 * 
	 * @param name
	 * 		the player's name
	 */
	public HEXComputerPlayerSmart(String name) {
		// invoke superclass constructor
		super(name);

	}// constructor

	/**
	 * Called when the player receives a game-state (or other info) from the
	 * game.
	 * 
	 * @param gameInfo
	 * 		the message from the game
	 */
	@Override
	protected void receiveInfo(GameInfo info) {

		if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) 
			// if the move was out of turn or otherwise illegal, return
			return;

		if (!(info instanceof HEXState))
			// if we do not have a HEXState, ignore
			return;
		// update our 'state' variable with the new state
		this.myState = (HEXState) info;


		//ignore if not our turn
		if(myState.getWhoseMove() != this.playerNum) return;


		//if computer is player 1
		if (this.playerNum == 1) {

			if(firstMove){ // the first move
				double random = Math.random()* 11;
				xVal = RedLeftEdge[(int) random].x;
				yVal = RedLeftEdge[(int) random].y;
				firstMove = !firstMove;
			}//if first move

			else{

				// in even rows
				if(yVal % 2 == 0){

					//checks cells in front first to see if they are legal moves
					if(myState.getPiece(yVal+1, xVal-1) == ' ' && mCells[yVal+1][xVal-1] == L_ONN){
						xVal -= 1;
						yVal +=1;
					}

					else if(myState.getPiece(yVal, xVal-1) == ' ' && mCells[yVal][xVal-1] == L_ONN){
						xVal -= 1;
					}
					//checks cell to right for a legal move
					else if(myState.getPiece(yVal+1, xVal) == ' ' && mCells[yVal+1][xVal] == L_ONN){
						yVal += 1;
					}
					//if starting row is not full, places a new spawn in a random place in starting row
					else if(!isColumnFull()){ 
						double random = Math.random()* 11;
						xVal = RedLeftEdge[(int) random].x;
						yVal = RedLeftEdge[(int) random].y;
						while(myState.getPiece(yVal, xVal) != ' ')
						{
							double rando = Math.random()* 11;
							xVal = RedLeftEdge[(int) rando].x;
							yVal = RedLeftEdge[(int) rando].y;
						}
					}
					//otherwise, places a random hex
					else{
						while (mCells[yVal][xVal] != L_ONN || myState.getPiece(yVal, xVal) != ' ') { // changed
							xVal = (int)(11*Math.random());
							yVal = (int)(24*Math.random());  
						}
					}

				}//if


				else if(yVal % 2 != 0){

					//checks cells in front first to see if they are legal moves
					if(myState.getPiece(yVal+1, xVal) == ' ' && mCells[yVal+1][xVal] == L_ONN){
						yVal += 1;
					}
					else if(myState.getPiece(yVal, xVal-1) == ' ' && mCells[yVal][xVal-1] == L_ONN){
						xVal -= 1;
					}
					//checks cell to right for a legal move
					else if(myState.getPiece(yVal+1, xVal+1) == ' ' && mCells[yVal+1][xVal+1] == L_ONN){
						xVal += 1;
						yVal += 1;
					}
					//if starting row is not full, places a new spawn in a random place in starting row
					else if (!isColumnFull()){
						double random = Math.random()* 11;
						xVal = RedLeftEdge[(int) random].x;
						yVal = RedLeftEdge[(int) random].y;

						while(myState.getPiece(yVal, xVal) != ' ')
						{
							double rando = Math.random()* 11;
							xVal = RedLeftEdge[(int) rando].x;
							yVal = RedLeftEdge[(int) rando].y;


						}
					}
					//otherwise, places a random hex
					else{
						while (mCells[yVal][xVal] != L_ONN || myState.getPiece(yVal, xVal) != ' ') { // changed
							xVal = (int)(11*Math.random());
							yVal = (int)(24*Math.random());  
						}
					}

				}//else if
			}//else

		}//if player #1

		else if (this.playerNum == 0){

			if(firstMove){ // the first move
				double random = Math.random()* 11;
				xVal = BlueTopEdge[(int) random].x;
				yVal = BlueTopEdge[(int) random].y;
				firstMove = !firstMove;
			}//if first move

			else{

				if(yVal % 2 == 0){

					//checks cells in front first to see if they are legal moves
					if(myState.getPiece(yVal+1, xVal) == ' ' && mCells[yVal+1][xVal] == L_ONN){
						yVal +=1;

					}
					else if(myState.getPiece(yVal, xVal+1) == ' ' && mCells[yVal][xVal+1] == L_ONN){
						xVal += 1;
					}
					//checks cell to right for a legal move
					else if(myState.getPiece(yVal-1, xVal) == ' ' && mCells[yVal-1][xVal] == L_ONN){
						yVal -= 1;
					}
					//if starting row is not full, places a new spawn in a random place in starting row
					else if(!isColumnFull()){ 
						double random = Math.random()* 11;
						xVal = BlueTopEdge[(int) random].x;
						yVal = BlueTopEdge[(int) random].y;
						while(myState.getPiece(yVal, xVal) != ' ')
						{
							double rando = Math.random()* 11;
							xVal = BlueTopEdge[(int) rando].x;
							yVal = BlueTopEdge[(int) rando].y;
						}
					}
					//otherwise, places a random hex
					else{
						while (mCells[yVal][xVal] != L_ONN || myState.getPiece(yVal, xVal) != ' ') { // changed
							xVal = (int)(11*Math.random());
							yVal = (int)(24*Math.random());  
						}

					}//if
				}

				else if(yVal % 2 != 0){

					//checks cells in front first to see if they are legal moves
					if(myState.getPiece(yVal+1, xVal + 1) == ' ' && mCells[yVal+1][xVal+1] == L_ONN){
						xVal += 1;
						yVal += 1;

					}
					else if(myState.getPiece(yVal, xVal+1) == ' ' && mCells[yVal][xVal+1] == L_ONN){
						xVal += 1;
					}
					//checks cell to right for a legal move
					else if(myState.getPiece(yVal-1, xVal+1) == ' ' && mCells[yVal-1][xVal+1] == L_ONN){
						xVal += 1;
						yVal -= 1;
					}
					//if starting row is not full, places a new spawn in a random place in starting row
					else if (!isColumnFull()) {
						double random = Math.random()* 11;
						xVal = BlueTopEdge[(int) random].x;
						yVal = BlueTopEdge[(int) random].y;
						while(myState.getPiece(yVal, xVal) != ' ')
						{
							double rando = Math.random()* 11;
							xVal = BlueTopEdge[(int) rando].x;
							yVal = BlueTopEdge[(int) rando].y;
						}
					}
					//otherwise, places a random hex
					else{
						while (mCells[yVal][xVal] != L_ONN || myState.getPiece(yVal, xVal) != ' ' ) { 
							xVal = (int)(11*Math.random());
							yVal = (int)(24*Math.random());  
						}
					}

				}//else if
			}//else


		}//playerID 0 if

		// delay for 1.3 seconds to make opponent think we're thinking
		sleep(1300);

		// Submit our move to the game object. We haven't even checked it it's
		// our turn, or that that position is unoccupied. If it was not our turn,
		// we'll get a message back that we'll ignore. If it was an illegal move,
		// we'll end up here again (and possibly again, and again). At some point,
		// we'll end up randomly picking a move that is legal.
		game.sendAction(new HEXMoveAction(this, xVal, yVal));
	}

	/**
	 * Checks each starting column, based upon which player the computer is,
	 * and determines if the entire row is full
	 * 
	 * @return rtrn
	 * 		true if column is full
	 */
	public boolean isColumnFull(){
		boolean rtrn = true;

		//checks each array spot for blue player to determine if any are empty
		if (this.playerNum == 0) {
			for (int i = 0; i < BlueTopEdge.length; i++) {
				if (myState.getPiece(BlueTopEdge[i].y, BlueTopEdge[i].x) == ' '){
					rtrn = false;
				}

			}
		}

		//checks each array spot for red player to determine if any are empty
		else if (this.playerNum == 1) {
			for (int i = 0; i < RedLeftEdge.length; i++) {
				if (myState.getPiece(RedLeftEdge[i].y, RedLeftEdge[i].x) == ' '){
					rtrn = false;
				}
			}
		}
		return rtrn;
	}
}