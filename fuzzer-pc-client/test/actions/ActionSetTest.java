package actions;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import communication.ActionInstruction;

public class ActionSetTest {

	@Test
	public void createValidActionSet() {
		ActionSet actionSet = new ActionSet("RANDOM", 2);
		assertNotNull(actionSet);
	}

	@Test
	public void addSubActionToActionSet() throws ChanceOverflowException {
		ActionSet actionSet = new ActionSet("RANDOM", 8);
		assertNotNull(actionSet);

		assertEquals(actionSet.getSubActions().size(), 0);
		actionSet.addSubAction(new Action(0.2, "operation", "90"));
		actionSet.getSubActions();
		assertEquals(actionSet.getSubActions().size(), 1);
	}

	@Test
	public void addRandomSubActionsToActionSet() throws ChanceOverflowException {
		ActionSet actionSet = new ActionSet("RANDOM", 8);
		assertNotNull(actionSet);

		Action subAction = new Action(0.4, "operation", "90");
		actionSet.addSubAction(subAction);
		actionSet.addSubAction(new Action(0.6, "op", "1234"));

		ArrayList<ActionInstruction> actionInstruction = actionSet
				.createActionInstructionList();
		assertEquals(16, actionInstruction.size());
		assertTrue(actionInstruction.toString().contains(subAction.toString()));
	}

	@Test
	public void addInorderSubActionsToActionSet()
			throws ChanceOverflowException {
		ActionSet actionSet = new ActionSet("INORDER", 8);
		assertNotNull(actionSet);

		Action subAction = new Action(0.2, "operation", "90");
		actionSet.addSubAction(subAction);
		actionSet.addSubAction(new Action(0.3, "op", "2345"));

		ArrayList<ActionInstruction> actionInstruction = actionSet
				.createActionInstructionList();
		assertEquals(16, actionInstruction.size());

		assertEquals(actionInstruction.get(0).toString(), subAction.toString());
	}

	@Test
	public void addOneSubActionToActionSet() throws ChanceOverflowException {
		ActionSet actionSet = new ActionSet("ONE", 8);
		assertNotNull(actionSet);

		Action subAction = new Action(0.2, "operation", "90");
		actionSet.addSubAction(subAction);

		ArrayList<ActionInstruction> actionInstruction = actionSet
				.createActionInstructionList();
		assertEquals(8, actionInstruction.size());
	}

	@Test
	public void addNoSubActionToActionSet() throws ChanceOverflowException {
		ActionSet actionSet = new ActionSet("NONE", 8);
		assertNotNull(actionSet);

		Action subAction = new Action(0.2, "operation", "90");
		actionSet.addSubAction(subAction);

		ArrayList<ActionInstruction> actionInstruction = actionSet
				.createActionInstructionList();
		assertEquals(0, actionInstruction.size());
	}

	@Test
	public void actionSetToString() throws ChanceOverflowException {
		ActionSet actionSet = new ActionSet("ONE", 8);
		assertNotNull(actionSet);

		Action subAction = new Action(0.2, "operation", "90");
		actionSet.addSubAction(subAction);

		String expected = "ActionSet times=8 ["
				+ System.getProperty("line.separator")
				+ " ; Action{ chance=0.2 operation=operation id=90}"
				+ System.getProperty("line.separator") + "]";
		assertEquals(expected, actionSet.toString());
	}

}
