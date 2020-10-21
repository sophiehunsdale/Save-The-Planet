package game;

public class Investor {

	private String investorName;
	private int green;
	private int location;

	/**
	 * Default constructor set up for testing
	 */
	public Investor() {
		this.setInvestorName("Tester");
		this.setGreen(200);
		this.setLocation(0);
	}

	/**
	 * Constructor
	 * 
	 * @param investorName
	 */
	public Investor(String investorName) {
		this.setInvestorName(investorName);
		this.setGreen(200);
		this.setLocation(0);

	}

	/**
	 * Constructor with args
	 * 
	 * @param investorName
	 * @param green
	 * @param location
	 */
	public Investor(String investorName, int green, int location) {
		this.setInvestorName(investorName);
		this.setGreen(green);
		this.setLocation(location);
	}

	/**
	 * Green is the money in this game
	 * 
	 * @param greenTotal
	 */
	public void updateGreen(int greenTotal) {
		this.setGreen(this.getGreen() + greenTotal);

	}

	/**
	 * 
	 * @param greenTotal
	 * @return
	 */
	public String showGreenUpdate(int greenTotal) {
		return String.format("\n%s updated to G%d", this.showCurrentGreen(), (this.getGreen() + greenTotal));
	}

	/**
	 * 
	 * @param green
	 */
	public void collectGreen(int green) {
		System.out.println(this.showGreenUpdate(green));
		this.updateGreen(green);
	}

	/**
	 * 
	 * @param investor
	 * @param greenTotal
	 */
	public void invest(Investor investor, int greenTotal) {
		this.updateGreen(-greenTotal);
		investor.updateGreen(greenTotal);
	}

	/**
	 * This method determines how much an investment will cost and then processes
	 * the payment between two Investors
	 * 
	 * @param ownerOfLocation
	 * @param greenTotal
	 * @throws
	 */
	public void payInvestment (Investor ownerOfLocation, int greenTotal, SavingField sf) {
		int investmentCost;
		int numberOfEcoBubble = sf.getEcoBubble();
			
		switch (sf.getSavingFieldTypes()) {
		case REDUCE:
			if (sf.isEcoDome()) {
				investmentCost = 51; // eco dome
			} else if (numberOfEcoBubble == 3) {
				investmentCost = 31; // 3 eco bubbles
			} else if (numberOfEcoBubble == 2) {
				investmentCost = 21; // 2 eco bubbles
			} else if (numberOfEcoBubble == 1) {
				investmentCost = 11; // 1 eco bubble
			} else {
				investmentCost = 1; // land only investment
			}
			break;
		case RECYCLE:
			if (sf.isEcoDome()) {
				investmentCost = 55;
			} else if (numberOfEcoBubble == 3) {
				investmentCost = 35;
			} else if (numberOfEcoBubble == 2) {
				investmentCost = 25;
			} else if (numberOfEcoBubble == 1) {
				investmentCost = 15;
			} else {
				investmentCost = 5; // land only investment
			}
			break;
		case REBUILD:
			if (sf.isEcoDome()) {
				investmentCost = 60;
			} else if (numberOfEcoBubble == 3) {
				investmentCost = 40;
			} else if (numberOfEcoBubble == 2) {
				investmentCost = 30;
			} else if (numberOfEcoBubble == 1) {
				investmentCost = 20;
			} else {
				investmentCost = 10; // land only investment
			}
			break;
		case RETHINK:
			if (sf.isEcoDome()) {
				investmentCost = 70;
			} else if (numberOfEcoBubble == 3) {
				investmentCost = 50;
			} else if (numberOfEcoBubble == 2) {
				investmentCost = 40;
			} else if (numberOfEcoBubble == 1) {
				investmentCost = 30;
			} else {
				investmentCost = 20; // land only investment
			}
			break;
		default:
			throw new IllegalArgumentException("Sorry, there has been an error");
		}
		if(!ownerOfLocation.hasNoGreen()) {
		System.out.printf("\n%s paid G%d on an investment to %s!", this.getInvestorName(), investmentCost,
				ownerOfLocation.getInvestorName());
		System.out.println(this.showGreenUpdate(-investmentCost));
		System.out.println(ownerOfLocation.showGreenUpdate(investmentCost));
		this.invest(ownerOfLocation, investmentCost);
		}else {
			System.out.printf("\n%s paid G%d on an investment to our Planet!", this.getInvestorName(), investmentCost);
			System.out.println(this.showGreenUpdate(-investmentCost));
			
		}

	}

	public String showCurrentGreen() {

		return String.format("%s's Green: G%d", this.getInvestorName(), this.getGreen());
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasNoGreen() {

		return (this.getGreen() < 0);
	}

	/**
	 * returns true if the Investor owns at least one savingFieldType
	 * 
	 * @param investor
	 * @return
	 */
	public boolean canBuy(GameBoard SaveOurPlanet) {

		int SavingFieldTypesOwned = 0;
		// iterates over each constant in SavingFieldTypes
		for (SavingFieldTypes savingFieldTypes : SavingFieldTypes.values()) {

			if (this.SavingFieldsOwnedBy(SaveOurPlanet, savingFieldTypes)) {
				SavingFieldTypesOwned++;
			}
		}

		return SavingFieldTypesOwned > 0;
	}

	public boolean canBuyEcoBubble(GameBoard saveOurPlanet) {

		if (!this.canBuy(saveOurPlanet)) {
			return false;
		} else {
			return saveOurPlanet.SavingFieldDevelopedBy(this).size() > 0;
		}
	}

	public boolean canBuyEcoDome(GameBoard saveOurPlanet) {

		if (!this.canBuy(saveOurPlanet)) {
			return false;
		} else {
			return saveOurPlanet.SavingFieldWithThreeEcoBubbles(this).size() > 0;
		}
	}

	public boolean ownsSavingFieldTypes(GameBoard SaveOurPlanet, SavingFieldTypes savingFieldTypes) {
		return (SaveOurPlanet.SavingFieldOwnedBy(this)
				.containsAll(SaveOurPlanet.SavingFieldsInGroup(savingFieldTypes)));
	}

	public boolean SavingFieldsOwnedBy(GameBoard saveOurPlanet, SavingFieldTypes savingFieldTypes) {
		return (saveOurPlanet.SavingFieldOwnedBy(this)
				.containsAll(saveOurPlanet.SavingFieldsInGroup(savingFieldTypes)));
	}

	/**
	 * returns true if the Investor will pass the Go Position while moving to target
	 * Position returns false if the target Position is the Collect Green Position,
	 * to avoid activating it twice
	 * 
	 * @param investor:            the Investor
	 * @param targetPositionIndex: int, where Investor will move to
	 * @return boolean
	 */
	public boolean willCollectGreen(int targetPositionIndex) {

		if (targetPositionIndex == 0) {
			return false;
		} else {
			// if the Investor collects green, the index of the target Position will always
			// be smaller than index of their current position
			return (this.getLocation() > targetPositionIndex);
		}

	}

	/**
	 * Getter for investorName
	 * 
	 * @return
	 */
	public String getInvestorName() {
		return investorName;
	}

	/**
	 * Setter for investorName This includes a business rule that the investor name
	 * cannot be left blank
	 * 
	 * @param investorName
	 * @throws IllegalArgumentException
	 */
	public void setInvestorName(String investorName) throws IllegalArgumentException {

		if (investorName.toLowerCase().length() > 0) {
			this.investorName = investorName;
		} else {
			throw new IllegalArgumentException("Invalid name");
		}
	}

	/**
	 * Getter for green
	 * 
	 * @return
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * Setter for green
	 * 
	 * @param green
	 */
	public void setGreen(int green) {
		if (this.green < 0) {
			SaveOurPlanet.checkWinnerCondition();
		} else {
			this.green = green;
		}
	}

	/**
	 * Getter for location
	 * 
	 * @return
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * Setter for location
	 * 
	 * @param location
	 * @throws IllegalArgumentException
	 */
	public void setLocation(int location) throws IllegalArgumentException {
		if (location < 0) {
			throw new IllegalArgumentException("Cannot set location as a negative position!");
		} else {
			this.location = location;
		}
	}

}