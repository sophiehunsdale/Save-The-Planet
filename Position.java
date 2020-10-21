package game;

import java.util.HashMap;
import java.util.Scanner;

/**
 * This method creates a class for creating a Position which will be used as a
 * super class when creating the Go Square, Serene Green and Saving Fields
 *
 */
public abstract class Position {
	

	public HashMap<Integer, SavingFieldTypes> currentSavingFieldType;

	// each position must have a positionName
	public String positionName;

	/**
	 * Default constructor for Position
	 */
	public Position() {

	}

	/**
	 * Constructor with arguments for Position
	 * 
	 * @param positionName
	 */
	public Position(String positionName) {
		this.setPositionName(positionName);
	}

	/**
	 * Getter for positionName
	 * 
	 * @return
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * Setter for positionName
	 * 
	 * @param positionName
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	/*
	 *  getter for current saving field type
	 *  @return the current saving field type
	 */
	public HashMap<Integer, SavingFieldTypes> getCurrentSavingFieldType() {
		return currentSavingFieldType;
	}
	/*setter for current saving field type
	 *  *  @param currentSavingFieldType the current Saving Field Type to set
	 */
	public void setCurrentSavingFieldType(HashMap<Integer, SavingFieldTypes> currentSavingFieldType) {
	 this.currentSavingFieldType = currentSavingFieldType;
	}

	/**
	 * 
	 * @param Investor
	 * @param investorInput
	 * @param option
	 * @param saveOurPlanet
	 */
	public void PositionChoice(Investor Investor, Scanner investorInput, BoardOptions option, GameBoard saveOurPlanet) {

	}
}