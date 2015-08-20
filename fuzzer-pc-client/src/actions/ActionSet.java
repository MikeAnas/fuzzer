package actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import communication.ActionInstruction;

/**
 * An actionSet holds several subActions these subActions can be actionSets
 * themselves or actions. The createActionInstructionList method recursively
 * calls all createActionInstructionList methods of all subActions. And returns
 * a ArrayList of ActionInstruction that can be send to the server. Note
 * ActionInstruction can have their send boolean set to false, then they will be
 * added to the ArrayList but not send to the server.
 * 
 * @author Kees Lampe
 *
 */
public class ActionSet implements IAction {

	private ArrayList<IAction> subActions;
	private String order;
	private int times;

	public ActionSet(String order, int times) {
		subActions = new ArrayList<IAction>();
		this.order = order;
		this.times = times;
	}

	public void addSubAction(IAction subAction) {
		subActions.add(subAction);
	}

	/**
	 * Creates an ActionInstructionList containing all subActions in a RANDOM /
	 * INORDER or ONE order RANDOM: actionInstructionList is appended with all
	 * subActions in RANDOM order. INORDER: all subActions are called in order.
	 * ONE: one randomly chosen subActions is called.
	 * 
	 * This is done x times. Default value of times is 0.
	 */
	public ArrayList<ActionInstruction> createActionInstructionList() {
		ArrayList<ActionInstruction> actionInstructionList = new ArrayList<ActionInstruction>();
		for (int i = 0; i < times; i++) {
			if (order.equals("RANDOM")) {
				addAllSubActionsInRandomOrder(actionInstructionList);
			} else if (order.equals("INORDER")) {
				addAllSubActionsInOrder(actionInstructionList);
			} else if (order.equals("ONE")) {
				addOneSubAction(actionInstructionList);
			}
		}
		return actionInstructionList;
	}

	/**
	 * actionInstructionList is appended with all subActions in RANDOM order
	 * 
	 * @param actionInstructionList
	 */
	private void addAllSubActionsInRandomOrder(
			ArrayList<ActionInstruction> actionInstructionList) {
		ArrayList<IAction> subActionsShuffel = new ArrayList<IAction>(
				subActions);
		Collections.shuffle(subActionsShuffel);
		for (IAction action : subActionsShuffel) {
			actionInstructionList.addAll(action.createActionInstructionList());
		}
	}

	/**
	 * actionInstructionList is appended with all subActions INORDER
	 * 
	 * @param actionInstructionList
	 */
	private void addAllSubActionsInOrder(
			ArrayList<ActionInstruction> actionInstructionList) {
		for (IAction action : subActions) {
			actionInstructionList.addAll(action.createActionInstructionList());
		}
	}

	/**
	 * actionInstructionList is appended with ONE subAction chosen at random.
	 * 
	 * @param actionInstructionList
	 */
	private void addOneSubAction(
			ArrayList<ActionInstruction> actionInstructionList) {
		Random rand = new Random();
		int randInt = rand.nextInt(subActions.size());
		actionInstructionList.addAll(subActions.get(randInt)
				.createActionInstructionList());
	}

	public String toString() {
		String res = "";
		for (IAction subAction : subActions) {
			res = res + " ; " + subAction.toString()
					+ System.getProperty("line.separator");
		}
		return "ActionSet times=" + times + " ["
				+ System.getProperty("line.separator") + res + "]";
	}

	public ArrayList<IAction> getSubActions(){
		return subActions;
	}
}
