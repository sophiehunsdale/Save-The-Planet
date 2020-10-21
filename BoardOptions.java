package game;

import java.util.HashMap;
import java.util.Iterator;

/**
 * This class houses BoardOptions which are used throughout the game
 *
 */
public class BoardOptions {

	private HashMap<Integer, Choice> currentBoardOptions;

	/**
	 * default constructor
	 */
	public BoardOptions() {
		this.setCurrentBoardOptions(new HashMap<Integer, Choice>());
	}

	/**
	 * constructor takes individual BoardOptions as arguments using Varargs
	 * 
	 * @param BoardOptions
	 */
	public BoardOptions(Choice... BoardOptions) {
		this.setCurrentBoardOptions(new HashMap<Integer, Choice>());
		for (Choice Choice : BoardOptions) {
			this.addChoice(Choice);
		}
	}

	/**
	 * This method holds the BoardOptions which investors can take on their go
	 * 
	 * @param options
	 * @throws IllegalArgumentException
	 */
	public void addChoice(Choice options) throws IllegalArgumentException {

		switch (options) {

		case FINISH_TURN:
			if (this.getCurrentBoardOptions().containsKey(1)) {
				this.getCurrentBoardOptions().replace(1, options);
			} else {
				this.getCurrentBoardOptions().put(1, options);
			}
			break;
		case DISPLAY_BOARD:
			this.getCurrentBoardOptions().put(2, options);
			break;
		case QUIT_GAME:
			if (this.getCurrentBoardOptions().containsKey(3)) {

				this.getCurrentBoardOptions().put(4, options);
			} else {

				this.getCurrentBoardOptions().put(3, options);
			}
			break;
		case BUY_ECO_BUBBLE:
			this.getCurrentBoardOptions().put(4, options);
			break;
		case BUY_ECO_DOME:
			if (this.getCurrentBoardOptions().containsKey(5)) {
				this.getCurrentBoardOptions().put(6, options);
			} else {
				this.getCurrentBoardOptions().put(5, options);
			}
			break;
		case YES:
			this.getCurrentBoardOptions().put(1, options);
			break;
		case NO:
			this.getCurrentBoardOptions().put(2, options);
			break;
		case CLOSE_MENU:
			this.getCurrentBoardOptions().put(3, options);
			break;
		default:
			throw new IllegalArgumentException("Not Possible!");
		}
	}

	/**
	 * This method provides specific options of YES or NO for when an investor needs
	 * to make a single binary decision
	 */
	public void getBoardOptions() {
		this.getCurrentBoardOptions().clear();
		this.addChoice(Choice.YES);
		this.addChoice(Choice.NO);
	}

	/**
	 * This method provides all choice BoardOptions. These are used when buying Eco
	 * Bubbles and Eco Domes
	 */
	public void getAllBoardOptions() {
		this.getCurrentBoardOptions().clear();
		this.addChoice(Choice.YES);
		this.addChoice(Choice.NO);
		this.addChoice(Choice.CLOSE_MENU);
	}

	/**
	 * This method returns the Choice which the Investor has selected through their
	 * input
	 * 
	 * @param choice: the int submitted by the Investor
	 * @return Choice: the specific Choice object for the choice.
	 */
	public Choice getBoardOptions(int choice) {
		return this.getCurrentBoardOptions().get(choice);
	}

	/**
	 * This method returns the current BoardOptions for the Investor
	 * 
	 * @return the currentBoardOptions
	 */
	public HashMap<Integer, Choice> getCurrentBoardOptions() {
		return currentBoardOptions;
	}

	/**
	 * 
	 * @param currentBoardOptions: which currentBoardOptions must to be set
	 */
	public void setCurrentBoardOptions(HashMap<Integer, Choice> currentBoardOptions) {
		this.currentBoardOptions = currentBoardOptions;
	}

	/**
	 * This method resets an investors BoardOptions for their next turn
	 */
	public void reset() {

		this.getCurrentBoardOptions().clear();
		this.addChoice(Choice.FINISH_TURN);
		this.addChoice(Choice.DISPLAY_BOARD);
		this.addChoice(Choice.QUIT_GAME);
	}

	/**
	 * This method provides the option to simplify Choice verification 2
	 * 
	 * @param choice: the int submitted by the Investor
	 * @return boolean: true if there is an Choice which has been mapped to the
	 *         choice.
	 */
	public boolean containsKey(int choice) {
		return this.getCurrentBoardOptions().containsKey(choice);
	}

	/**
	 * This method overrides the toString method
	 */

	@Override
	public String toString() {

		String menu = "";
		Iterator it = this.getCurrentBoardOptions().entrySet().iterator();

		while (it.hasNext()) {
			menu += it.next().toString() + "\n";
		}
		return menu;

	}

}