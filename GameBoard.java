package game;

import java.util.ArrayList;

public class GameBoard {
	private ArrayList<Position> Positions = new ArrayList<Position>();

	/**
	 * default constructor for GameBoard
	 */
	public GameBoard() {

	}

	/**
	 * Constructor with arguments for the GameBoard
	 * 
	 * @param startPosition
	 * @param dropletSquare
	 * @param powerPlaza
	 * @param thePlastWorks
	 * @param bottleBankArcade
	 * @param paperPlace
	 * @param greenTravelStation
	 * @param afforestationValley
	 * @param amazingAllotments
	 * @param solarVillage
	 * @param windyWillows
	 */
	public GameBoard(MotherEarth startPosition, SavingField dropletSquare, SavingField powerPlaza,
			SavingField thePlastWorks, SavingField bottleBankArcade, SavingField paperPlace, SereneGreen sereneGreen,
			SavingField greenTravelStation, SavingField afforestationValley, SavingField amazingAllotments,
			SavingField solarVillage, SavingField windyWillows) {
		this.getPositions().add(startPosition);
		this.getPositions().add(dropletSquare);
		this.getPositions().add(powerPlaza);
		this.getPositions().add(thePlastWorks);
		this.getPositions().add(bottleBankArcade);
		this.getPositions().add(paperPlace);
		this.getPositions().add(sereneGreen);;
		this.getPositions().add(greenTravelStation);
		this.getPositions().add(afforestationValley);
		this.getPositions().add(amazingAllotments);
		this.getPositions().add(solarVillage);
		this.getPositions().add(windyWillows);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method will return the destination of the Investor
	 * 
	 * @param investor
	 * @param distance
	 * @return
	 */
	public int getDestination(Investor investor, int distance) {

		int currentPosition = investor.getLocation();
		int destination = currentPosition + distance;

		if (destination >= this.getPositions().size()) {
			int difference = this.getPositions().size() - currentPosition;
			destination = distance - difference;
		}

		return destination;

	}

	/**
	 * This method will return the distance between the Investor and each of the
	 * other positions on the game board
	 * 
	 * @param investor
	 * @param Position
	 * @return
	 */
	public int getSpacesFrom(Investor investor, Position position) {

		int investorLocation = investor.getLocation();
		int positionIndex = this.getPositionIndex(position);
		int spacesFrom = positionIndex - investorLocation;

		if (spacesFrom >= 0) {
			return spacesFrom;
		} else {
			return spacesFrom + this.getPositions().size();
		}

	}

	/**
	 * This method will create an array list of the SavingFields
	 * 
	 * @return
	 */
	public ArrayList<SavingField> getSavingFields() {

		ArrayList<SavingField> savingFields = new ArrayList<SavingField>();

		for (Position position : this.getPositions()) {
			if (position.getClass().equals(SavingField.class)) {
				savingFields.add((SavingField) position);
			}
		}

		return savingFields;
	}

	/**
	 * This method will create an arrayList for the SavingFields owned by an
	 * Investor
	 * 
	 * @param investor
	 * @return
	 */
	public ArrayList<SavingField> SavingFieldOwnedBy(Investor investor) {

		ArrayList<SavingField> savingFieldOwnedBy = new ArrayList<SavingField>();

		for (SavingField position : this.getSavingFields()) {
			if (position.ownedBy(investor)) {
				savingFieldOwnedBy.add(position);
			}
		}

		return savingFieldOwnedBy;
	}

	/**
	 * This method will create an arrayList for the SavingFields which are owned in
	 * a group by SavingFieldType
	 * 
	 * @param savingFieldType
	 * @return
	 */
	public ArrayList<SavingField> SavingFieldsInGroup(SavingFieldTypes savingFieldType) {

		ArrayList<SavingField> savingFields = new ArrayList<SavingField>();

		for (SavingField position : this.getSavingFields()) {
			if (position.getSavingFieldTypes().equals(savingFieldType)) {
				savingFields.add(position);
			}
			
		}

		return savingFields;
	}

	/**
	 * This method will create an arrayList for the Investors who have developed a
	 * SavingField
	 * 
	 * @param investor
	 * @return
	 */
	public ArrayList<SavingField> SavingFieldDevelopedBy(Investor investor) {

		ArrayList<SavingField> savingFieldDevelopedBy = new ArrayList<SavingField>();

		for (SavingField savingField : this.SavingFieldsInOwnedGroups(investor)) {
			if (savingField.getEcoBubble() < 3 && !savingField.isEcoDome()) {
				savingFieldDevelopedBy.add(savingField);
			}
		}

		return savingFieldDevelopedBy;
	}

	/**
	 * This method will create an arrayList for the SavingFields which an Investor
	 * owns in a group by SavingFieldType
	 * 
	 * @param investor
	 * @return
	 */
	public ArrayList<SavingField> SavingFieldsInOwnedGroups(Investor investor) {

		ArrayList<SavingField> savingFieldsInOwnedGroups = new ArrayList<SavingField>();

		for (SavingFieldTypes savingFieldType : SavingFieldTypes.values()) {
			
			if (investor.SavingFieldsOwnedBy(this, savingFieldType)) {
				savingFieldsInOwnedGroups.addAll(this.SavingFieldsInGroup(savingFieldType));
			}
			
	
		}
		return savingFieldsInOwnedGroups;
	}

	
	/**
	 * This method will create an ArrayList of SavingFields which have three
	 * EcoBubbles developed on them
	 * 
	 * @param investor
	 * @return
	 */
	public ArrayList<SavingField> SavingFieldWithThreeEcoBubbles(Investor investor) {

		ArrayList<SavingField> savingFieldWithThreeEcoBubbles = new ArrayList<SavingField>();

		for (SavingField position : this.SavingFieldsInOwnedGroups(investor)) {
			if (position.getEcoBubble() == 3 && !position.isEcoDome()) {
				savingFieldWithThreeEcoBubbles.add(position);
			}
		}

		return savingFieldWithThreeEcoBubbles;

	}

	/**
	 * This method will create an arrayList of SavingFields which an Investor has
	 * developed by buying one or more Eco Bubbles for
	 * 
	 * @param investor
	 * @return
	 */
	public ArrayList<SavingField> SavingFieldWithEcoBubble(Investor investor) {

		ArrayList<SavingField> savingFieldWithEcoBubble = new ArrayList<SavingField>();

		for (SavingField position : this.SavingFieldsInOwnedGroups(investor)) {
			if (position.getEcoBubble() > 0) {
				savingFieldWithEcoBubble.add(position);
			}
		}

		return savingFieldWithEcoBubble;
	}

	/**
	 * This method will create an arrayList which an Investor has developed by
	 * buying an Eco Dome for
	 * 
	 * @param investor
	 * @return
	 */
	public ArrayList<SavingField> SavingFieldWithecoDome(Investor investor) {

		ArrayList<SavingField> savingFieldWithEcoDome = new ArrayList<SavingField>();

		for (SavingField position : this.SavingFieldsInOwnedGroups(investor)) {
			if (position.isEcoDome()) {
				savingFieldWithEcoDome.add(position);
			}
		}

		return savingFieldWithEcoDome;
	}

	public Position getPositionAt(int index) {

		return this.getPositions().get(index);
	}

	public int getPositionIndex(Position Position) {

		return this.getPositions().indexOf(Position);
	}

	public SavingFieldMap createSavingFieldMapFor(Investor investor) {

		return new SavingFieldMap(this.SavingFieldOwnedBy(investor));
	}

	public Position find(Investor investor) {

		return this.getPositionAt(investor.getLocation());
	}

	/**
	 * Getter for the arrayList of Positions
	 * 
	 * @return
	 */
	public ArrayList<Position> getPositions() {
		return Positions;
	}

	/**
	 * Setter for the arrayList of Positions
	 * 
	 * @param Positions
	 */
	public void setPositions(ArrayList<Position> Positions) {
		this.Positions = Positions;
	}

}