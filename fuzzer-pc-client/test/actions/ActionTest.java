package actions;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import communication.ActionInstruction;

public class ActionTest {

	@Test
	public void createNewAction() throws ChanceOverflowException {
		Action action = new Action(0.4, "operation", "8");
		assertNotNull(action);
	}

	@Test(expected = ChanceOverflowException.class)
	public void createNewActionWithNegativeChance()
			throws ChanceOverflowException {
		new Action(-0.9, "operation", "8");
	}

	@Test(expected = ChanceOverflowException.class)
	public void createNewActionWithChanceGreaterThanOne()
			throws ChanceOverflowException {
		new Action(12.4, "operation", "8");
	}

	@Test
	public void createActionInstructionList() throws ChanceOverflowException {
		Action action = new Action(0.0, "operation", "id");
		ArrayList<ActionInstruction> actionInstructionList = action
				.createActionInstructionList();
		assertNotNull(actionInstructionList);
	}

	@Test
	public void actionToStringWithoutValue() throws ChanceOverflowException {
		Action action = new Action(0.3, "test operation 456", "23");
		String expected = "Action{ chance=0.3 operation=test operation 456 id=23}";

		assertEquals(expected, action.toString());
	}

	@Test
	public void actionToStringWithSetValue() throws ChanceOverflowException {
		Action action = new Action(0.2, "test operation 789", "7");
		action.setValue("value 123");
		String expected = "Action{ chance=0.2 operation=test operation 789 id=7 value=value 123}";

		assertEquals(expected, action.toString());
	}

	@Test
	public void actionToStringWithSetInputsValues()
			throws ChanceOverflowException {
		Action action = new Action(0.2, "test operation 101", "90");
		action.setInputsValues(new InputList());
		String expected = "Action{ chance=0.2 operation=test operation 101 id=90 value=random}";

		assertEquals(expected, action.toString());
	}
}
