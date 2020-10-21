package game;

/**
 * This enum ensures only valid Choices are displayed to Investors when
 * appropriate throughout game play
 *
 */
public enum Choice {

	FINISH_TURN, DISPLAY_BOARD, QUIT_GAME, BUY_ECO_BUBBLE, BUY_ECO_DOME, YES, NO, CLOSE_MENU;

	@Override
	public String toString() {

		switch (this) {

		case FINISH_TURN:
			return "Finish Turn";
		case DISPLAY_BOARD:
			return "Display Board";
		case BUY_ECO_BUBBLE:
			return "Buy Eco Bubble";
		case BUY_ECO_DOME:
			return "Buy Eco Dome";
		case NO:
			return "No";
		case YES:
			return "Yes";
		case QUIT_GAME:
			return "Quit Game";
		case CLOSE_MENU:
			return "Close Menu";
		default:
			return "Choice";

		}
	}
}