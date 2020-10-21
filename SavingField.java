package game;

import java.util.Scanner;

/**
 * This class is a sub class of the Position class which will take care of what
 * happens when an investor lands on a Saving Field
 */

public class SavingField extends Position {

	private String positionName;
	private Investor ownerOfLocation;
	private int positionCost, landOnlyInvestment, ecoBubble;
	private boolean ecoDome;
	private SavingFieldTypes SavingFieldTypes;

	/**
	 * Default constructor for SavingField
	 */
	public SavingField() {

	}

	public SavingField(String positionName) {
		super(positionName);
	}
	/**
	 * Constructor with arguments for SavingField
	 * 
	 * @param investorName:     The name of the investor
	 * @param PositionCost:     The cost to buy the position
	 * @param SavingFieldTypes: The type of Saving Field
	 */
	public SavingField(String positionName, int PositionCost, SavingFieldTypes SavingFieldTypes) {
		super(positionName);
		this.setOwnerOfLocation(null);
		this.setPositionCost(PositionCost);
		this.setSavingFieldTypes(SavingFieldTypes);
		this.setLandOnlyInvestment(0);
		this.setEcoBubble(0);
		this.setEcoDome(false);
	}

	/**
	 * Constructor with arguments for SavingField
	 * 
	 * @param ownerOfLocation
	 * @param investorName
	 * @param positionCost
	 * @param landOnlyInvestment
	 * @param ecoBubble
	 * @param ecoDome
	 */
	public SavingField(Investor ownerOfLocation, String positionName, int positionCost, int landOnlyInvestment,
			int ecoBubble, boolean ecoDome) {
		super();
		this.setOwnerOfLocation(ownerOfLocation);
		this.setPositionName(positionName);
		this.setPositionCost(positionCost);
		this.setLandOnlyInvestment(landOnlyInvestment);
		this.setEcoBubble(ecoBubble);
		this.setEcoDome(ecoDome);
	}

	/**
	 * When an investor lands on this position, this method will check for three
	 * things. 1. does the investor own the position 2. if not owned does the
	 * investor have enough Green to purchase the position 3. if the investor owns
	 * the entire field, they will be informed that they can buy an Eco Bubble.
	 * Otherwise the process to pay rent will be started
	 * 
	 * @param investor
	 * @param input
	 * @param menu
	 * @param SaveOurPlanet
	 */
	@Override
	public void PositionChoice(Investor investor, Scanner input, BoardOptions menu, GameBoard SaveOurPlanet) {
		// checks if the investor already owns the SavingField
		if (this.ownedBy(investor)) {
			System.out.println("You already own this Saving Field.");
		} else if (this.getOwnerOfLocation() == null) {
			// start buy SavingField
			// first checks the investor has enough Green to purchase
			if (investor.getGreen() < this.getPositionCost()) {
				System.out.println("You don't have enough Green to purchase this Saving Field!");
			} else {
				this.finalisePurchase(investor, input, menu);
				// checks if the investor owns all the Saving Fields of this type
				// informs the investor they can now buy Eco Bubbles for the Saving Field
				if (investor.ownsSavingFieldTypes(SaveOurPlanet, this.getSavingFieldTypes())) {
					System.out.printf(
							"%nYou now own all Saving Fields in the %s type!%nYou can now buy Eco Bubbles for these Saving Fields: %n",
							this.getSavingFieldTypes().name());

					for (SavingField SavingField : SaveOurPlanet.SavingFieldsInGroup(this.getSavingFieldTypes())) {
						System.out.println(SavingField.getPositionName());
					}
				}
			}

		} else {
			// start pay rent
			System.out.printf("\n%s owns this Saving Field!\n", this.getOwnerOfLocation().getInvestorName());
			investor.payInvestment(this.getOwnerOfLocation(), this.getPositionCost(), this);
		}

	}

	/**
	 * This method returns if Investor is a SavingField ownerOfLocation or not
	 * 
	 * @param investor
	 * @return
	 */
	public boolean ownedBy(Investor investor) {

		if (this.getOwnerOfLocation() != null) {
			return (this.getOwnerOfLocation().equals(investor));
		} else {
			return false;
		}

	}

	/**
	 * Getter for positionCost
	 * 
	 * @return the PositionCost
	 */
	public int getPositionCost() {
		return positionCost;
	}

	/**
	 * Setter for positionCost
	 * 
	 * @param PositionCost the PositionCost to set
	 */
	public void setPositionCost(int positionCost) throws IllegalArgumentException{
		if(positionCost>0) {
				this.positionCost = positionCost;	
		}else {
			throw new IllegalArgumentException("Invalid Cost");
		}
	}

	/**
	 * Getter for savingFieldTypes
	 * 
	 * @return the SavingFieldTypes
	 */
	public SavingFieldTypes getSavingFieldTypes() {
		return SavingFieldTypes;
	}

	/**
	 * Setter for savingFieldTypes
	 * 
	 * @param SavingFieldTypes the SavingFieldTypes to set
	 */
	public void setSavingFieldTypes(SavingFieldTypes savingFieldTypes) {
		this.SavingFieldTypes = savingFieldTypes;
	}
	
	/**
	 * Getter for ownerOfLocation
	 * 
	 * @return ownerOfLocation
	 */
	public Investor getOwnerOfLocation() {
		return ownerOfLocation;
	}

	/**
	 * Setter for ownerOfLocation
	 * 
	 * @param ownerOfLocation the ownerOfLocation to set
	 */
	public void setOwnerOfLocation(Investor ownerOfLocation) {
		this.ownerOfLocation = ownerOfLocation;
	}

	/**
	 * Getter for landOnlyInvestment
	 * 
	 * @return the landOnlyInvestment
	 */
	public int getLandOnlyInvestment() {
		return landOnlyInvestment;
	}

	/**
	 * Setter for landOnlyInvestment
	 * 
	 * @param landOnlyInvestment the landOnlyInvestment to set
	 */
	public void setLandOnlyInvestment(int landOnlyInvestment) {
		this.landOnlyInvestment = landOnlyInvestment;
	}

	/**
	 * Getter for ecoBubble
	 * 
	 * @return the ecoBubble
	 */
	public int getEcoBubble() {
		return ecoBubble;
	}

	/**
	 * Setter for ecoBubble
	 * 
	 * @param ecoBubble the ecoBubble to set
	 */
	public void setEcoBubble(int ecoBubble) {
	
			this.ecoBubble = ecoBubble;
			
	}

	/**
	 * Getter for ecoDome boolean
	 * 
	 * @return the ecoDome
	 */
	public boolean isEcoDome() {
		return ecoDome;
	}

	/**
	 * Setter for ecoDome boolean
	 * 
	 * @param ecoDome the ecoDome to set
	 */
	public void setEcoDome(boolean EcoDome) {
		this.ecoDome = EcoDome;
	}

	/**
	 * Getter for investorName
	 * 
	 * @return the investorName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * Setter for investorName
	 * 
	 * @param investorName the investorName to set
	 */
	public void setPositionName(String positionName)throws IllegalArgumentException {
		if(positionName.length() >0) {
			this.positionName = positionName;
		}else if(positionName.length()<=0) {
			throw new IllegalArgumentException("Must have a name");
		}
	}

	/**
	 * This method allows an investor to buy a position
	 * 
	 * @param investor
	 * @param input
	 * @param menu
	 */
	public void finalisePurchase(Investor investor, Scanner input, BoardOptions menu) {

		boolean decisionMade = false;

		menu.getBoardOptions();

		do {
			System.out.printf("\nWould you like to buy %s for G%d?\n", this.getPositionName(), this.getPositionCost());
			System.out.println(menu);

			try {

				int choice = Integer.valueOf(input.nextLine());

				if (menu.containsKey(choice)) {
					Choice result = menu.getBoardOptions(choice);

					switch (result) {

					case YES:
						System.out.printf("\n%s bought %s for G%d!", investor.getInvestorName(), this.getPositionName(),
								this.getPositionCost());
						System.out.println(investor.showGreenUpdate(-this.getPositionCost()));
						this.setOwnerOfLocation(investor);
						investor.updateGreen(-this.getPositionCost());
						decisionMade = true;
						break;
					case NO:
						System.out.println("OK, let's keep saving our planet!");
						decisionMade = true;
						break;
					default:
						System.out.println("Something went wrong here!");
						menu.getBoardOptions();
						decisionMade = true;
						break;
					}

				} else {
					System.out.println("Not possible, choose again");
				}

			} catch (NumberFormatException ex) {

				System.out.println("You must enter a number! Please choose again.");
			}

		} while (!decisionMade);
	}
}