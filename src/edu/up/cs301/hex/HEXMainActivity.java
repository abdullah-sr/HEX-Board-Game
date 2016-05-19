package edu.up.cs301.hex;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * This is the primary activity for Hex game.
 * 
 */
public class HEXMainActivity extends GameMainActivity {

	public static final int PORT_NUMBER = 5213;

	/**
	 * a Hex game is for two players. The default is human vs. computer
	 */
	@Override
	public GameConfig createDefaultConfig() {

		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

		playerTypes.add(new GamePlayerType("Local Human Player") {
			public GamePlayer createPlayer(String name) {
				return new HEXHumanPlayer1(name);
			}
		});

		// dumb computer player
		playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
			public GamePlayer createPlayer(String name) {
				return new HEXComputerPlayerDumb(name);
			}
		});

		// smarter computer player
		playerTypes.add(new GamePlayerType("Computer Player (smart)") {
			public GamePlayer createPlayer(String name) {
				return new HEXComputerPlayerSmart(name);
			}
		});

		// Create a game configuration class for Hex
		GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Hex", PORT_NUMBER);

		// Add the default players
		defaultConfig.addPlayer("Human", 0); // human player
		defaultConfig.addPlayer("Computer", 2); // smart computer player is default

		// Set the initial information for the remote player
		defaultConfig.setRemoteData("Remote Player", "", 0); // human player

		//done!
		return defaultConfig;

	}//createDefaultConfig


	/**
	 * createLocalGame
	 * 
	 * Creates a new game that runs on the server tablet,
	 * 
	 * @return a new, game-specific instance of a sub-class of the LocalGame
	 *         class.
	 */
	@Override
	public LocalGame createLocalGame() {
		return new HEXLocalGame();
	}

}
