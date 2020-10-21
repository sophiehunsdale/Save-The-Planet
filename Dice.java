package game;

/**
 * This method rolls two dice with random numbers between 1 - 6. Investors will
 * be given the roll total to move positions accordingly.
 */
public class Dice {

	private int dice1, dice2, rollTotal;

	/**
	 * The upper limit for the dice has been set at 6
	 */
	public final int UPPER_DICE_LIMIT = 6;
	public final int UPPER_DICE_LIMIT_TOTAL=12;

	/**
	 * This method will return the total roll as a sum of dice1 and dice2 using a
	 * random generator. 1 is being added to ensure 0 is not returned and numbers
	 * are between 1-6.
	 * @return 
	 */


	/**
	 * Default constructor for the dice
	 */
	public Dice() {
		roll();
	}

	/**
	 * Getter for dice1
	 * 
	 * @return
	 */
	public int getDice1() {
		return dice1;
	}

	/**
	 * Setter for dice1
	 * 
	 * @param dice1
	 */
	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}

	/**
	 * Getter for dice2
	 * 
	 * @return
	 */
	public int getDice2() {
		return dice2;
	}

	/**
	 * Setter for dice2
	 * 
	 * @param dice2
	 */
	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}

	/**
	 * Getter for the rollTotal
	 * 
	 * @return
	 */
	public int getRollTotal() {
		return rollTotal;
	}

	/**
	 * Setter for the rollTotal
	 * 
	 * @param rollTotal
	 * @throws Exception 
	 */
	public void setRollTotal(int rollTotal) throws Exception {
		if(rollTotal>=2 && rollTotal<=UPPER_DICE_LIMIT_TOTAL) {
			this.rollTotal = rollTotal;
		}else {
			throw new Exception();
		}
				
	}
	/**
	 * 
	 * @return
	 */
	public int roll() {
		this.setDice1((int) (UPPER_DICE_LIMIT * Math.random() + 1));
		this.setDice2((int) (UPPER_DICE_LIMIT * Math.random() + 1));
		rollTotal = dice1 + dice2;
		System.out.println("Dice 1: " + dice1 + "\nDice 2: " + dice2);
		return rollTotal;
		
	}

}