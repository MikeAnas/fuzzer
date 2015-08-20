package actions;

/**
 * A simple input class. This holds one value which can be set with a certain
 * chance. Inputs are part of an InputList.
 */
public class Input {

	private double chance = 0.0f;
	private String value;

	public Input(String value) {
		this.value = value;
	}

	public Input(String value, double chance) {
		this.value = value;
		setChance(chance);
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		if (chance >= 0 && chance <= 1)
			this.chance = chance;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
