package game;

import java.util.Scanner;

/**
 * This class is a subclass of Position. This class represents the Go Square of
 * Save Our Planet
 *
 */
public class MotherEarth extends Position {

	/**
	 * Green is the currency used in the Save Our Planet game
	 */
	private int green;

	/**
	 * default constructor for MotherEarth
	 */
	public MotherEarth() {

	}

	/**
	 * Constructor with arguments for MotherEarth
	 * 
	 * @param positionName
	 * @param green
	 */
	public MotherEarth(String positionName, int green) {
		super(positionName);
		this.setGreen(green);

	}

	/**
	 * Getter for green (currency in Save Our Planet)
	 * 
	 * @return
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * Setter for green (currency in Save Our Planet)
	 * 
	 * @param green
	 */
	public void setGreen(int green) {
		this.green = 5;
	}

	/**
	 * PositionChoice method inherited from Position Class
	 */
	@Override
	public void PositionChoice(Investor Investor, Scanner investorInput, BoardOptions option, GameBoard saveOurPlanet) {
		this.passingMotherEarth(Investor);
	}

	/**
	 * This method will give each Investor the allocated amount of Green for passing
	 * the Go position
	 * 
	 * @param Investor
	 */
	public void passingMotherEarth(Investor Investor) {
		System.out.println("Have some Green to continue to help save the planet!");
		System.out.println(Investor.getInvestorName() + " has been given G" + this.getGreen());
		Investor.collectGreen(this.getGreen());

	}

}