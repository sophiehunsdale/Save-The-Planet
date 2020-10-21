package game;

/**
 * This class creates each of the Positions for the game including the Go
 * Square, Saving Fields and Serene Green
 *
 */
public class Positions {

	public static MotherEarth startPosition = new MotherEarth("MotherEarth", 50);
	public static SavingField dropletSquare = new SavingField("Droplet Square", 10, SavingFieldTypes.REDUCE);
	public static SavingField powerPlaza = new SavingField("Power Plaza", 20, SavingFieldTypes.REDUCE);
	public static SavingField thePlastWorks = new SavingField("The PlastWorks", 30, SavingFieldTypes.RECYCLE);
	public static SavingField bottleBankArcade = new SavingField("Bottle Bank Arcade", 40, SavingFieldTypes.RECYCLE);
	public static SavingField paperPlace = new SavingField("Paper Place", 50, SavingFieldTypes.RECYCLE);
	public static SereneGreen sereneGreen = new SereneGreen("Serene Green");
	public static SavingField greenTravelStation = new SavingField("Green Travel Station", 60,
			SavingFieldTypes.REBUILD);
	public static SavingField afforestationValley = new SavingField("Afforestation Valley", 70,
			SavingFieldTypes.REBUILD);
	public static SavingField amazingAllotments = new SavingField("Amazing Allotments", 80, SavingFieldTypes.REBUILD);
	public static SavingField solarVillage = new SavingField("Solar Village", 90, SavingFieldTypes.RETHINK);
	public static SavingField windyWillows = new SavingField("Windy Willows", 100, SavingFieldTypes.RETHINK);
	// Additional positions can be added here in future.

	/**
	 * creates a new GameBoard
	 */
	public static GameBoard defaultBoard = new GameBoard(startPosition, dropletSquare, powerPlaza, thePlastWorks,
			bottleBankArcade, paperPlace, sereneGreen, greenTravelStation, afforestationValley, amazingAllotments, solarVillage,
			windyWillows);

}
