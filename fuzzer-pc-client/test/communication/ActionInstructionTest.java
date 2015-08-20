package communication;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ActionInstructionTest {

	ActionInstruction actionInstruction;

	@Before
	public void init() {
		String[] params = { "String", "String" };
		actionInstruction = new ActionInstruction("method", params, true,
				"string");
		assertNotNull(actionInstruction);
	}

	@Test
	public void getSendProperty() {
		assertEquals(true, actionInstruction.getSend());
	}

	@Test
	public void getToStringProperty() {
		assertEquals("string", actionInstruction.toString());
	}

}
