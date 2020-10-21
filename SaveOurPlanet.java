package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class contains the main method for playing Save Our Planet
 *
 */
public class SaveOurPlanet {

	/**
	 * Instance variables for SaveOurPlanet class
	 */
	private static final int LOWER_INVESTOR_LIMIT = 2;
	private static final int UPPER_INVESTOR_LIMIT = 4;

	public static ArrayList<Investor> Investors = new ArrayList<Investor>();

	public static GameBoard SaveOurPlanet = Positions.defaultBoard;

	public static Dice dice1, dice2, rollTotal;

	/**
	 * the BoardOptions each Investor chooses from on their turn
	 */
	public static BoardOptions turnMenu = new BoardOptions();

	/**
	 * the BoardOptions each Investor chooses from in order to make yes/no decisions
	 */
	public static BoardOptions Menu = new BoardOptions(Choice.YES, Choice.NO, Choice.CLOSE_MENU);

	/**
	 * flag for checking if the current turn has ended
	 */
	public static boolean turnComplete = true;
	/**
	 * flag for checking if the game is still being played
	 */
	public static boolean running = false;

	/**
	 * flag for checking if the win condition has been met
	 */
	public static boolean winner = false;

	/**
	 * Scanner object that will receive all user input
	 */
	public static Scanner inputScanner;

	/**
	 * This is the main method for playing the Save Our Planet game
	 * 
	 * @param args
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws NumberFormatException {
		// scanner to capture Investor's input
		inputScanner = new Scanner(System.in);

		enterInvestors();

		beginGame();

		// outer loop. when a Investor wins or quits, condition is set to false.
		while (running) {

			// iterates through list of Investors in same order each round
			for (Investor investor : Investors) {

				checkWinnerCondition();

				if (winner) {
					System.out
							.println("Thank you for playing SaveOurPlanet Warrior " + investor.getInvestorName() + "!");
					inputScanner.close();
					break;
				}

				// ensures a bankrupt Investor cannot take a turn
				if (investor.hasNoGreen()) {
					System.out.println(investor.getInvestorName() + " has no green left, onto the next Investor..");
				} else {

					startTurn();

					System.out.printf("%n%s's turn :) !%n", investor.getInvestorName());
					viewFunds();
					rollDice();
					showRolledNumber();

					// start Travel SaveOurPlanet using rolled number
					travelBoard(investor, rollTotal.getRollTotal());

					// the game checks if the Investor went bankrupt after moving
					if (investor.hasNoGreen()) {
						// if so, set turnComplete to true
						System.out.printf(
								"You no longer have any Green left and cannot help to save the planet anymore..."
										+ investor.getInvestorName());
						completeTurn();
					}

				}

				while (!turnComplete) {
					getBoardOptionsFor(investor);

					System.out.printf("%n%s 's turn!%n", investor.getInvestorName());
					takeTurn(investor);

					try {
						int choice = Integer.valueOf(inputScanner.nextLine());

						if (turnMenu.containsKey(choice)) {
							Choice result = turnMenu.getBoardOptions(choice);

							switch (result) {
							case FINISH_TURN:
								System.out.printf("%nFinishing %s's turn.%n", investor.getInvestorName());
								completeTurn();
								break;
							case DISPLAY_BOARD:
								showGameBoard(investor);
								break;
							case BUY_ECO_BUBBLE:
								buyEcoBubble(investor);
								break;
							case BUY_ECO_DOME:
								buyEcoDome(investor);
								break;
							case QUIT_GAME:
								quitGame();
								break;

							default:
								System.out.println("Not yet implemented");
							}

						} else {
							System.out.println("Not possible, try again :)");
						}

					} catch (NumberFormatException ex) {

						System.out.println("Please enter a number next time.");
					}

					if (!running) {
						completeTurn();
					}

					checkWinnerCondition();
				}
			}
			// If the game has ended
			if (!running) {
				System.out.println("Thank you for playing Save Our Planet!");
				inputScanner.close();
				break;
			}

		}
	}

	/**
	 * prompts each Investor to enter their investorName, validates their input,
	 * then adds a new Investor object to the GameController. prompts the Investor
	 * to enter again if the input is invalid.
	 * 
	 * @throws NumberFormatException
	 */
	public static void enterInvestors() throws NumberFormatException {
		System.out.println("Please enter how many investors are going to help save our planet (between 2-4)");

		try {

			int playerNo = Integer.valueOf(inputScanner.nextLine());

			if ((playerNo >= LOWER_INVESTOR_LIMIT) && (playerNo <= UPPER_INVESTOR_LIMIT)) {

				for (int inv = 0; inv < playerNo; inv++) {

					boolean investorEntered = false;
					// continues until each Investor has entered their name
					while (!investorEntered) {

						System.out.printf("%nInvestor %d, Please enter your Investor name: ", inv + 1);
						String investorName = inputScanner.nextLine();

						if (validateInvestorName(investorName)) {

							addInvestor(investorName);
							System.out.printf("%n%s is helping to save our planet!%n", investorName);
							investorEntered = true;

						} else {

							System.out.println("This Investor name has been taken, can you think of another? :)");
						}
					}
				}

			} else {
				// if number is not between 2-4, they will be asked to enter again
				System.out.println("Invalid number of Investors, can only be between 2-4 players!");
				enterInvestors();
			}
		} catch (NumberFormatException ex) {
			// if input is not in number format, they will be asked to enter again
			System.out.println("Invalid input. Please enter a number between 2-4");
			enterInvestors();

		} catch (Exception exception) {
			// any other type of exception dealt with here, they will be asked to enter
			// again
			System.out.println("There has been an issue, please try again");
			enterInvestors();
		}
	}

	/**
	 * method to create a new dice for the game
	 */
	public static void rollDice() {
		rollTotal = new Dice();
	}

	/**
	 * method to display the dice roll to the investor during their turn
	 */
	public static void showRolledNumber() {

		System.out.println("Moving " + rollTotal.getRollTotal() + " spaces :) - - - >");
	}

	/**
	 * method which fills the BoardOptions with the specific BoardOptions this
	 * Investor can take during their current turn in regards to buying an Eco
	 * Bubble or an Eco Dome
	 * 
	 * @param investor
	 */
	public static void getBoardOptionsFor(Investor investor) {
		turnMenu.reset();
		// if the investor is able to buy an Eco Bubble this option is displayed
		if (investor.canBuyEcoBubble(SaveOurPlanet)) {
			turnMenu.addChoice(Choice.BUY_ECO_BUBBLE);
		}

		// if the investor is able to buy an Eco Dome this option is displayed
		if (investor.canBuyEcoDome(SaveOurPlanet)) {
			turnMenu.addChoice(Choice.BUY_ECO_DOME);
		}

	}

	/**
	 * This method informs the Investor on their go where they are and provides them
	 * with the relevant BoardOptions they can take
	 * 
	 * @param investor
	 */
	public static void takeTurn(Investor investor) {
		System.out.println("You are on " + SaveOurPlanet.find(investor).getPositionName());
		viewFunds();
		System.out.println("What would you like to do? Enter a number to make your decision:");
		viewBoardOptions();
	}

	/**
	 * This method will inform how much Green Investors have currently during their
	 * turn
	 */
	public static void viewFunds() {

		String investorBalances = "";

		for (Investor investor : Investors) {

			investorBalances += investor.showCurrentGreen() + "\n";

		}

		System.out.println(investorBalances);

	}

	/**
	 * This method will display the relevant BoardOptions to the Investor
	 */
	public static void viewBoardOptions() {
		HashMap<Integer, Choice> BoardOptions = turnMenu.getCurrentBoardOptions();

		Iterator BoardOptionsIt = BoardOptions.entrySet().iterator();

		while (BoardOptionsIt.hasNext()) {
			System.out.println(BoardOptionsIt.next());
		}
	}

	/**
	 * This method will display options within the choice BoardOptions for YES, NO,
	 * and CLOSE_MENU for the Investor
	 */
	public static void viewChoice() {
		System.out.println(Menu);
	}

	/**
	 * This method will display the quit game message to the Investor to ensure they
	 * want to quit the game
	 * 
	 * @throws NumberFormatException
	 */
	public static void quitGame() throws NumberFormatException {

		boolean decisionMade = false;

		Menu.getBoardOptions();

		do {

			System.out.println(
					"Are you sure you don't want to help saving our planet anymore? All your hard work will be lost!");
			System.out.println("Select an option to make your decision: \n");

			viewChoice();

			try {

				int choice = Integer.valueOf(inputScanner.nextLine());

				if (Menu.containsKey(choice)) {
					Choice result = Menu.getBoardOptions(choice);

					switch (result) {

					case YES:
						System.out.println("Abondoning mission of saving our planet : ");
						System.out.println("Final green: ");
						viewFunds();
						finishGame();
						decisionMade = true;
						terminate();
						break;
					case NO:
						System.out.println("OK, let's keep saving our planet!");
						Menu.getBoardOptions();
						decisionMade = true;
						break;
					default:
						System.out.println("Uh Oh, something went wrong!");
						Menu.getBoardOptions();
						decisionMade = true;
						break;

					}

				} else {
					System.out.println("Not possible, please choose again");
				}

			} catch (NumberFormatException ex) {

				System.out.println("Enter a number to make your decision: ");
			}

		} while (!decisionMade);

	}

	/**
	 * final terminating programme when one player quits, they all quit.
	 */
	public static void terminate() {
		System.out.println("Thanks for doing your part!");
		System.exit(0);
	}

	/**
	 * allows them to view yes or no menu on what to do about quitting game
	 */
	public static void viewMenu() {
		System.out.println(Menu);
	}

	/*
	 * This method prints a list of all the Position on the game SaveOurPlanet,
	 * along with their distance in number of Position from the current Investor.
	 * Informs the Investor of which Position they are currently on.
	 * 
	 * @param investor
	 */
	public static void showGameBoard(Investor investor) {

		System.out.println("Below are the positions on the Save Our Planet board: ");
		
		for (Position position : SaveOurPlanet.getPositions()) {
			
			System.out.printf(position.getPositionName());
			int distance = SaveOurPlanet.getSpacesFrom(investor, position);
			String PositionsAway;
			if (distance == 0) {
				PositionsAway = String.format(" < - - - - - - - - - - - - You are here!\n");
			} else {
				PositionsAway = String.format(", %d positions away\n", SaveOurPlanet.getSpacesFrom(investor, position));
			}

			System.out.print(PositionsAway);

		}

	}

	/**
	 * This method will allow an Investor to buy an Eco Bubble
	 * 
	 * @param investors
	 * @throws NumberFormatException
	 */
	public static void buyEcoBubble(Investor investor) throws NumberFormatException {

		// flag to check if the Investor has exited the Menu
		boolean selected = false;

		// outer savingFieldMap Menu
		do {

			SavingFieldMap savingFieldMap = new SavingFieldMap(SaveOurPlanet.SavingFieldsInOwnedGroups(investor));

			int exitMenu = (savingFieldMap.size() + 1);

			System.out.printf(
					"\nEnter a number to select the Saving Field where you want to buy an Eco Bubble, or enter %d to exit: \n",
					exitMenu);

			Iterator<SavingField> currentSavingFieldValues = savingFieldMap.getCurrentSavingField().values().iterator();

			int optionNumber = 1;

			do {
				System.out.println(optionNumber + "=" + currentSavingFieldValues.next().getPositionName());
				optionNumber++;
			} while (currentSavingFieldValues.hasNext());

			// this is so the Investor doesn't have to select a position to exit the Menu
			System.out.printf("%d=Exit Menu\n", exitMenu);

			try {

				int choice = Integer.valueOf(inputScanner.nextLine());

				// if the position chosen is valid
				if (savingFieldMap.contains(choice)) {
					SavingField sf = savingFieldMap.getChosenSavingField(choice);

					// flag to check if the Investor confirmed their purchase
					boolean verified = false;

					do {
						int currentBubbles = sf.getEcoBubble();
						int bubblesRemaining = 3 - currentBubbles;
						
						exitMenu = bubblesRemaining + 1;

						System.out.printf(
								"\n%s has %d Eco Bubbles on it, you can have up to %d more. Each one will cost G%d.\n",
								sf.getSavingFieldTypes(), currentBubbles, bubblesRemaining, 10);
						System.out.printf("Enter the number you would like to buy, or %d to exit: \n", exitMenu);
						viewFunds();

						for (int b = 0; b < bubblesRemaining; b++) {
							System.out.printf("%d=%d Eco Bubbles\n", b + 1, b + 1);
						}
						
						System.out.printf("%d=Exit Menu\n", exitMenu);

						try {

							int decision = Integer.valueOf(inputScanner.nextLine());

							int totalGreen = 10 * decision;
							
							if (totalGreen> investor.getGreen() || investor.getGreen()<10) {
								System.out.printf(
										"You don't have enough Green to buy that many Eco Bubbles! Try less bubbles or Enter %d to exit:",exitMenu);
									verified=true;
									
							} else if (decision <= bubblesRemaining && decision > 0) {

								System.out.printf("\n%s bought %d Eco Bubbles on %s for G%d!\n",
										investor.getInvestorName(), decision, sf.getSavingFieldTypes(), totalGreen);
								System.out.println(investor.showGreenUpdate(-totalGreen));
								investor.updateGreen(-totalGreen);
								sf.setEcoBubble(sf.getEcoBubble() + decision);
								verified = true;

							} else if (decision == exitMenu) {

								System.out.println("Returning to turn Menu...");
								verified=true;
								
								
							} else {
								buyEcoDome(investor);
							}

						} catch (NumberFormatException ex) {

							System.out.println("You must enter a number! Please choose again. ");
						}

					} while (!verified);

				} else if (choice == exitMenu) {
					System.out.println("Returning to turn Menu...");
					selected = true;
				} else {
					System.out.println("No such position!");
				}

			} catch (NumberFormatException ex) {
				System.out.println("Please select an option: ");
			}

		} while (!selected);

	}

	/**
	 * This method allows Investors to buy Eco Domes for a Saving Field if they
	 * already have three Eco Bubbles bought for the Saving Field
	 * 
	 * @param investor
	 * @throws NumberFormatException
	 */
	public static void buyEcoDome(Investor investor) throws NumberFormatException {

		Menu.getAllBoardOptions();
		// boolean checks if the Investor has exited the menu
		boolean selected = false;

		// outer savingFieldMap Menu
		do {

			SavingFieldMap savingFieldMap = new SavingFieldMap(SaveOurPlanet.SavingFieldWithThreeEcoBubbles(investor));

			int exitMenu = (savingFieldMap.size() + 1);
			System.out.printf(
					"\nEnter a number to select the Saving Field where you want to buy Eco Dome, or enter %d to exit: \n",
					exitMenu);

			Iterator<SavingField> currentSavingFieldValues = savingFieldMap.getCurrentSavingField().values().iterator();

			int optionNumber = 1;

			do {
				System.out.println(optionNumber + "=" + currentSavingFieldValues.next().getPositionName());
				optionNumber++;
			} while (currentSavingFieldValues.hasNext());

			System.out.printf("%d=Exit Menu\n", exitMenu);

			try {

				int choice = Integer.valueOf(inputScanner.nextLine());

				// if the position chosen is valid
				if (savingFieldMap.contains(choice)) {
					SavingField sf = savingFieldMap.getChosenSavingField(choice);

					// flag to check if the Investor approved their purchase
					boolean approved = false;

					do {

						int green = 20;

						System.out.printf("%nWould you like to buy Eco Dome on %s for G%d?\n", sf.getPositionName(),
								green);
						System.out.printf("This will boost the investment cost of %s by G%d!\n", sf.getPositionName(),
								green);
						System.out.println("Please enter a number to choose:");
						viewFunds();

						viewChoice();
						try {

							int decision = Integer.valueOf(inputScanner.nextLine());

							if (Menu.containsKey(decision)) {
								Choice result = Menu.getBoardOptions(decision);

								switch (result) {

								case YES:
									// buys the Eco Dome
									System.out.printf("\n%s bought an Eco Dome on %s for G%d ! \n",
											investor.getInvestorName(), sf.getPositionName(), green);
									System.out.println(investor.showGreenUpdate(-green));
									investor.updateGreen(-green);
									sf.setEcoDome(true);
									approved = true;
									selected = true;
									break;
								case NO:
									approved = true;
									System.out.println("Returning to position menu...");
									break;
								case CLOSE_MENU:
									System.out.println("Returning to turn menu...");
									approved = true;
									selected = true;
									break;
								default:
									System.out.println("Something went wrong here!");
									approved = true;
									selected = true;
								}

							} else {
								System.out.println("Not possible!");
							}

						} catch (NumberFormatException ex) {

							System.out.println("Please enter a number to select:");
						}

					} while (approved == false);

				} else if (choice == exitMenu) {
					System.out.println("Returning to turn menu...");
					selected = true;
				} else {
					System.out.println("No such position!");
				}

			} catch (NumberFormatException ex) {
				System.out.println("Please enter a number to select:");
			}

		} while (!selected);

	}

	public static void startTurn() {
		turnComplete = false;
	}

	public static void completeTurn() {
		turnComplete = true;
	}

	public static void beginGame() {
		running = true;
	}

	public static void finishGame() {
		running = false;
	}

	/**
	 * This method will start the SaveOurPlanet use case for the Investor passed to
	 * the method
	 * 
	 * @param investor
	 */
	public static void travelBoard(Investor investor, int distance) {

		moveInvestor(investor, distance);
		showNewLocation(investor);
		startPositionChoice(investor);
	}

	public static void moveInvestor(Investor investors, int distance) {

		// SaveOurPlanet calculates index Investor will move to
		int targetPositionIndex = SaveOurPlanet.getDestination(investors, distance);
		if (investors.willCollectGreen(targetPositionIndex)) {
			// if the Position is the collect green Position, this is false, to make sure
			// Investor doesn't collect twice
			// if true, the Investor's funds are increased

			SaveOurPlanet.getPositionAt(0).PositionChoice(investors, inputScanner, Menu, SaveOurPlanet);
		}
		// we can now set the Investor's Position
		investors.setLocation(targetPositionIndex);

	}

	/**
	 * This method informs the Investor what new position they have landed on
	 * 
	 * @param investor
	 */
	public static void showNewLocation(Investor investor) {
		System.out.printf("\nYou landed on %s!\n", SaveOurPlanet.find(investor).getPositionName());
	}

	/**
	 * This method shows the Position relating to the Investor's position, then
	 * calls the positionChoice() method.
	 * 
	 * @param investor
	 */
	public static void startPositionChoice(Investor investor) {

		Position currentPosition = SaveOurPlanet.find(investor);

		currentPosition.PositionChoice(investor, inputScanner, Menu, SaveOurPlanet);

	}

	/**
	 * The boolean is true if there are no Investors who have the investorName
	 * passed to the method
	 * 
	 * @param investorName
	 * @return
	 */
	public static boolean validateInvestorName(String investorName) {

		return !getInvestorNames().contains(investorName.toLowerCase());
	}

	/**
	 * This method adds a Investor using a validated investorName.
	 * 
	 * @param investorName
	 */
	public static void addInvestor(String investorName) {

		Investor thisInvestor = new Investor(investorName.toLowerCase()); // to not allow same names LM);
		Investors.add(thisInvestor);

	}

	/**
	 * This creates an ArrayList of the Investor names playing the current game
	 * 
	 * @return
	 */
	public static ArrayList<String> getInvestorNames() {

		ArrayList<String> investorNames = new ArrayList<String>();

		for (Investor investor : Investors) {
			investorNames.add(investor.getInvestorName());
		}

		return investorNames;
	}

	/**
	 * This method will check if any Investors have no Green left and are therefore
	 * out of the game and also checks if there is only one Investor left in the
	 * game
	 */
	public static void checkWinnerCondition() {

		ArrayList<Investor> investorsInGame = new ArrayList<Investor>();

		// checking if any Investors have run out of Green
		for (Investor investor : Investors) {
			if (!investor.hasNoGreen()) {
				investorsInGame.add(investor);
			}
		}

		if (investorsInGame.size() == 1) {
			winner = true;
			completeTurn();
			finishGame();
			winnerDeclared(investorsInGame.get(0));
		}
	}

	/**
	 * This method will declare which Investor has won the game and how much Green
	 * they had left
	 * 
	 * @param investor
	 */
	public static void winnerDeclared(Investor investor) {
		System.out.printf("\n%s has saved our planet!!\n", investor.getInvestorName());
		System.out.println("Final green: ");
		viewFunds();

	}

}