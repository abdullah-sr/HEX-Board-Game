package edu.up.cs301.hex;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import edu.up.cs301.animation.AnimationSurface;
import edu.up.cs301.animation.Animator;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.game.util.GameTimer;


/**
 * A GUI that allows a human to play Hex. Moves are made by clicking
 * regions on a canvas
 *
 */
public class HEXHumanPlayer1 extends HEXHumanPlayer implements Animator {

	private static final int BOARD_WIDTH = 25;
	private static final int BOARD_HEIGHT = 11;

	private static final int L_ONN = 1;
	private static final int L_OFF = 2;

	private static final int NUM_HEX_CORNERS = 6;
	private static int CELL_RADIUS = 30;
	private static final int CELL_HEIGHT = 51;

	private static Path thisHex = null;
	private static int xCord;
	private static int yCord;


	// game board cells array
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

	private int[] mCornersX = new int[NUM_HEX_CORNERS];
	private int[] mCornersY = new int[NUM_HEX_CORNERS];

	private static HexGrid mCellMetrics = new HexGrid(CELL_RADIUS);

	// the game's state
	protected HEXState state;
	// protected HEXLocalGame localGame; // not used

	// the current activity
	private Activity myActivity;

	// the offset from the left and top to the beginning of our "middle square";
	// one of these will always be zero
	protected float hBase;
	protected float vBase;

	// the size of one edge of our "middle square", or -1 if we have not
	// determined size
	protected float fullSquare;

	// our animation surface. (We're not actually doing moving animation, but
	// this surface is a convenient way to draw our image.)
	private AnimationSurface surface;

	/**
	 * constructor
	 *
	 * @param name
	 *            the player's name
	 */
	public HEXHumanPlayer1(String name) {

		super(name);
	}

	/**
	 * Callback method, called when player gets a message
	 *
	 * @param info
	 *            the message
	 */
	@Override
	public void receiveInfo(GameInfo info) {
		if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
			// if the move was out of turn or otherwise illegal, flash the
			// screen
			surface.flash(Color.GREEN, 50);
		} else if (!(info instanceof HEXState))
			// if we do not have a HEXState, ignore
			return;
		else {
			// update our 'state' variable with the new state
			this.state = (HEXState) info;
			Log.i("human player", "receiving");
		}
	}

	/**
	 * sets the current player as the activity's GUI
	 * @param activity
	 *                 
	 */
	public void setAsGui(GameMainActivity activity) {

		// remember our activity
		myActivity = activity;

		// Load the layout resource for the new configuration
		activity.setContentView(R.layout.hex_human_player1and2);

		// set the animator (us) for the animation surface
		surface = (AnimationSurface) myActivity.findViewById(R.id.padding8Bot);
		surface.setAnimator(this);
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// "erase" the previous GUI's notion of what the screen size is
		fullSquare = -1;

		// if we have state, "simulate" that it just came from the game, and
		// hence draw it on the GUI
		if (state != null) {
			receiveInfo(state);
		}
	}

	/**
	 * returns the GUI's top view
	 *
	 * @return the GUI's top view
	 */
	@Override
	public View getTopView() {
		return myActivity.findViewById(R.id.top_gui_layout);
	}

	/**
	 * @return the time interval for the animation
	 */
	public int interval() {
		// 50 milliseconds, or 20 times per second
		return 50;
	}

	/**
	 * @return the animation's background color
	 */
	public int backgroundColor() {
		return Color.WHITE;
	}

	/**
	 * @return whether the animation should be pause
	 */
	public boolean doPause() {
		// never tell the animation to pause
		return false;
	}

	/**
	 * @return whether the animation should quit
	 */
	public boolean doQuit() {
		// never tell the animation to quit
		return false;
	}

	/**
	 * perform any initialization that needs to be done after the player knows
	 * what their game-position and opponents' names are.
	 */
	protected void initAfterReady() {
		myActivity.setTitle("Hex: " + allPlayerNames[0] + " vs. "
				+ allPlayerNames[1]);
	}

	/**
	 * callback method, called whenever it's time to draw the next animation
	 * frame
	 *
	 * @param g
	 *            the canvas to draw on
	 */

	public void tick(Canvas g) {

		Paint black = new Paint();
		black.setColor(Color.BLACK);
		// if the full square size is outdated our variables that relate
		// to the dimensions of the animation surface
		if (fullSquare < 0) {
			updateDimensions(g);
		}

		Paint blueSide = new Paint();
		blueSide.setARGB(255, 59, 123, 235);

		// top left blue
		g.drawRect(20, 20, 620, 300, blueSide);

		// bottom right blue
		g.drawRect(620, 300, 1255, 620, blueSide);

		Paint redSide = new Paint();
		redSide.setARGB(255, 250, 45, 48);

		// top right red
		g.drawRect(620, 20, 1255, 300, redSide);

		// bottom left red
		g.drawRect(20, 300, 620, 620, redSide);

		Paint p = new Paint();


		// i is our x  -- width
		// j is our y --- height

		for (int j = 0; j < mCells[0].length; j++) {
			for (int i = 0; i < mCells.length; i++) {

				//if the cell is "on"
				if (mCells[i][j] == L_ONN) {
					thisHex = new Path();
					mCellMetrics.setCellIndex(i, j);

					if (state == null) {
						return;
					}
					//figures out the corners of the cell
					mCellMetrics.computeCorners(mCornersX, mCornersY);

					//moves around the corners of the hex
					thisHex.moveTo(mCornersX[5], mCornersY[5]);

					//draws out the hex lines to form the board
					for (int q = 0; q < mCornersX.length; ++q) {
						thisHex.lineTo(mCornersX[q], mCornersY[q]);
					}


					//sets the color to fill the current hex, according to which player claimed space
					if (state.getPiece(i,j) == '0') {
						p.setStyle(Style.FILL);
						p.setColor(Color.BLUE);

					} else if (state.getPiece(i,j) == '1') {

						p.setStyle(Style.FILL);
						p.setColor(Color.RED);
					} else {
						p.setStyle(Style.FILL);
						p.setColor(Color.WHITE); 

					}

					//draws hex, and draws outline around
					g.drawPath(thisHex, p);
					p.setStyle(Style.STROKE);
					p.setColor(Color.BLACK);
					g.drawPath(thisHex,p);

				}//if
			} // for i
		}// for j

		p.setColor(Color.BLACK);
		p.setTextSize(18.0f);

		//tells human player what color they are and if
		//they are first or second to move
		if(this.playerNum == 0) {
			g.drawText("You are blue!",1000.0f, 50.0f, p);
			g.drawText("Blue moves first!",1000.0f, 100.0f, p);
		}

		else if(this.playerNum == 1) {
			g.drawText("You are red!",1000.0f, 50.0f, p);
			g.drawText("Red moves second!",1000.0f, 100.0f, p);
		}


	}//tick method

	/**
	 * helper-method to convert from a percentage to a horizontal pixel location
	 *
	 * @param percent
	 *            the percentage across the drawing square
	 * @return the pixel location that corresponds to that percentage
	 */
	protected float h(float percent) {
		return hBase + percent * fullSquare / 100;
	}

	/**
	 * helper-method to convert from a percentage to a vertical pixel location
	 *
	 * @param percent
	 *            the percentage down the drawing square
	 * @return the pixel location that corresponds to that percentage
	 */
	protected float v(float percent) {
		return vBase + percent * fullSquare / 100;
	}

	/**
	 * update the instance variables that relate to the drawing surface
	 *
	 * @param g
	 *            an object that references the drawing surface
	 */
	private void updateDimensions(Canvas g) {

		// initially, set the height and width to be that of the
		// drawing surface
		int width = g.getWidth();
		int height = g.getHeight();

		// Set the "full square" size to be the minimum of the height and
		// the width. Depending on which is greater, set either the
		// horizontal or vertical base to be partway across the screen,
		// so that the "playing square" is in the middle of the screen on
		// its long dimension
		if (width > height) {
			fullSquare = height;
			vBase = 0;
			hBase = (width - height) / (float) 2.0;
		} else {
			fullSquare = width;
			hBase = 0;
			vBase = (height - width) / (float) 2.0;
		}

	}

	/**
	 * callback method when the screen it touched. We're looking for a screen    
	 * touch (which we'll detect on the "up" movement" onto a hex piece)
	 *
	 * @param event
	 *            the motion event that was detected
	 */
	public void onTouch(MotionEvent event) {

		// ignore if not an "up" event
		if (event.getAction() != MotionEvent.ACTION_UP) return;

		// get the x and y coordinates of the touch-location;
		// convert them to points
		int x = (int) event.getX();
		int y = (int) event.getY();

		//find which hex cell the user has touched
		mCellMetrics.setCellByPoint(x,y);
		Point p = new Point(mCellMetrics.getIndexI(),
				mCellMetrics.getIndexJ());

		//make sure it's in on our board at all
		if ((p.x < 0) || (p.x >= mCells.length) || (p.y < 0) || (p.y >= mCells[p.x].length)) {
			surface.flash(Color.YELLOW, 50);  //tell the user selection is incorrect
			return;
		}

		//make sure it's valid cell on  board
		if (mCells[p.x][p.y] != L_ONN) 
		{
			surface.flash(Color.YELLOW, 50); //tell the user selection is incorrect

		}
		else
		{
			//create and send an action to the game
			HEXMoveAction action = new HEXMoveAction(this, p.y, p.x);
			Log.i("onTouch", "Human player sending HEXMA ...");
			game.sendAction(action);
		}


	}


	/**
	 * maps a point from the canvas' pixel coordinates to "hex" coordinates
	 *
	 * @param x
	 *            the x pixel-coordinate
	 * @param y
	 *            the y pixel-coordinate
	 * @return a Point whose components are in the range 0-24, indicating the
	 *         column and row of the corresponding hex on the
	 *         board, or null if the point does not correspond to a hex
	 */
	public Point mapPixelToSquare(int x, int y) {

		// loop through each square and see if we get a "hit"; if so, return
		// the corresponding Point in hex coordinates
		for (int i = 0; i < BOARD_WIDTH; ++i) {
			for (int j = 0; j < BOARD_HEIGHT; ++j) {

				float left = 0;
				float right = 0;
				float top = 0;
				float bottom = 0;

				//if in even column
				if (i % 2 == 0) {
					left = (i * (CELL_RADIUS) + 5);
					right = ((CELL_RADIUS * 2) - 5)
							+ (((CELL_RADIUS * 2) - 5) * i);
					top = (j * CELL_HEIGHT);
					bottom = ((j * CELL_HEIGHT) + CELL_HEIGHT);

				}

				//if odd column
				else if (i % 2 != 0) {

					left = ((CELL_RADIUS * 2) * i);
					right = (left + CELL_RADIUS);
					top = ((j * CELL_HEIGHT) + (CELL_HEIGHT / 2));
					bottom = (top + CELL_HEIGHT);

				}

				//means it is a legal point, and returns the hex coordinate on board
				if (x > left && x < right && y > top && y < bottom) {
					xCord = i;
					yCord = j;
					return new Point(i, j);

				}
			}
		}

		// no match: return null
		return null;
	}

	public static int getMappedX() {
		return xCord;
	}

	public static int getMappedY() {
		return yCord;
	}

	public void tick(GameTimer timer) {
		// don't care
	}

}