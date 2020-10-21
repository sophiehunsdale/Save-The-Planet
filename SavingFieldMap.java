package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class will produce the layout for the savingField
 *
 */
public class SavingFieldMap {

	private HashMap<Integer, SavingField> currentSavingField;


	public SavingFieldMap() {
		this.setCurrentSavingField(new HashMap<Integer, SavingField>());
	}

	/**
	 * 
	 * @param SavingField
	 */
	public SavingFieldMap(ArrayList<SavingField> SavingField) {

		this();
		int loop = 0;
		for (SavingField sf : SavingField) {
			this.addSavingField(loop + 1, sf);
			loop++;
		}
	}

	/**
	 * Getter for the HashMap currentSavingField
	 * 
	 * @return the currentSavingField
	 */
	public HashMap<Integer, SavingField> getCurrentSavingField() {
		return currentSavingField;
	}

	/**
	 * Setter for the HaspMap currentSavingField
	 * 
	 * @param currentSavingField the currentSavingField to set
	 */
	public void setCurrentSavingField(HashMap<Integer, SavingField> currentSavingField) {
		this.currentSavingField = currentSavingField;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void addSavingField(int key, SavingField value) {

		this.getCurrentSavingField().put(key, value);
	}

	/**
	 * 
	 * @param choice
	 * @return
	 */
	public boolean contains(int choice) {
		return this.getCurrentSavingField().containsKey(choice);
	}

	/**
	 * 
	 * @param choice
	 * @return
	 */
	public SavingField getChosenSavingField(int choice) {
		return this.getCurrentSavingField().get(choice);
	}

	/**
	 * 
	 * @return
	 */
	public int size() {
		return this.getCurrentSavingField().size();
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		String menu = "";

		Iterator it = this.getCurrentSavingField().entrySet().iterator();

		while (it.hasNext()) {

			menu += it.next().toString() + "\n";
		}

		return menu;
	}

}