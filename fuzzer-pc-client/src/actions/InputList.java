package actions;

import java.util.ArrayList;

/**
 * InputList is a list of all the possible inputs for a value of an action. An
 * Action object can contain an InputList and ask it to return one input value
 * by calling getOneInputValue() This value is calculated by taking into account
 * the chances set by the input objects.
 * 
 * @author Kees Lampe
 *
 */
public class InputList {

	private ArrayList<Input> inputList;
	private double totalChance = 0.0f;

	public InputList() {
		inputList = new ArrayList<Input>();
	}

	public void addInput(Input input) throws ChanceOverflowException {
		if (input.getChance() + totalChance <= 1) {
			inputList.add(input);
			totalChance += input.getChance();
		} else {
			throw new ChanceOverflowException(
					"The accumulated chances are greater than 1");
		}
	}

	/**
	 * Picks a random input out of the InputList but takes chance into account.
	 * 
	 * @return one input value as a String.
	 */
	public String getOneInputValue() {
		setMissingChances();
		double rand = Math.random();
		double sum = 0;
		for (int i = 0; i < inputList.size(); i++) {
			sum += inputList.get(i).getChance();
			if (sum > rand) {
				return inputList.get(i).getValue();
			}
		}
		return inputList.get(inputList.size() - 1).getValue();
	}

	/**
	 * This method is called to give all inputs that don't have a set chance.
	 * The chance they get is the (1 - totalSetChance / sizeNotSetInputs). This
	 * chance is calculated by CalcChanceForUnsetInputs().
	 */
	private void setMissingChances() {
		double unsetChance = CalcChanceForUnsetInputs();
		for (Input input : inputList) {
			if (input.getChance() == 0.0)
				input.setChance(unsetChance);
		}
	}

	/**
	 * Called by addMissingChances()
	 * 
	 * @return The chance that should be set to inputs that don't have a set
	 *         chance yet.
	 */
	private double CalcChanceForUnsetInputs() {
		double countNrOfNotSetChances = 0;
		for (Input input : inputList) {
			if (input.getChance() == 0.0)
				countNrOfNotSetChances++;
		}
		return (1.0 - totalChance) / countNrOfNotSetChances;
	}

	public ArrayList<Input> getInputList() {
		return inputList;
	}

}
