package actions;

import java.util.ArrayList;

import communication.ActionInstruction;

/**
 * An Action object represents an UI action that can be executed on a device.
 * createActionInstructionList creates an ArrayList containing one!
 * ActionInstruction that represents this action. Note this ActionInstruction
 * can have its send boolean set to false, then it will be added to the
 * ArrayList but not send to the server.
 * 
 * @author Kees Lampe
 *
 */
public class Action implements IAction {

	private double chance;
	private String operation;
	private String id;
	private String value;
	private InputList values;

	public Action(double chance, String operation, String id)
			throws ChanceOverflowException {
		if (chance >= 0 && chance <= 1) {
			this.chance = chance;
			this.operation = operation;
			this.id = id;
		} else {
			throw new ChanceOverflowException(
					"The given chance is not between (or including) 0 and 1");
		}
	}

	/**
	 * Creates an ArrayList containing one! ActionInstruction that represents
	 * this action. Note this ActionInstruction can have its send boolean set to
	 * false, then it will be added to the ArrayList but not send to the server.
	 * This method gets called by the run method of the actionSet of which this
	 * action is a subAction.
	 */
	public ArrayList<ActionInstruction> createActionInstructionList() {
		boolean send = (Math.random() < chance);
		ArrayList<ActionInstruction> ActionInstructionList = new ArrayList<ActionInstruction>();
		ActionInstructionList.add(createActionInstruction(send));
		return ActionInstructionList;
	}

	/**
	 * This method creates an ActionInstruction out of this object. How this
	 * ActionInstrucion is formed depends on the parameters that are set in this
	 * Action object.
	 * 
	 * @return ActionInstruction represents this action
	 */
	private ActionInstruction createActionInstruction(boolean sending) {
		String[] parameters = new String[] {};
		if (id != null) {
			if (value != null) {
				parameters = new String[] { id, value };
			} else if (values != null) {
				this.value = values.getOneInputValue();
				parameters = new String[] { id, value };
			} else {
				parameters = new String[] { id };
			}
		}
		return new ActionInstruction(operation, parameters, sending,
				this.toString());
	}

	public void setInputsValues(InputList inputList) {
		this.values = inputList;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		if (value != null) {
			return toString(value);
		} else if (values != null) {
			return toString("random");
		} else {
			return "Action{ chance=" + chance + " operation=" + operation
					+ " id=" + id + "}";
		}
	}

	private String toString(String value) {
		return "Action{ chance=" + chance + " operation=" + operation + " id="
				+ id + " value=" + value + "}";
	}

}
