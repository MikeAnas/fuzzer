package actions;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import parsers.FileNotCompatibleException;

/**
 * 
 * @author michel
 *
 */
public class InputListTest {

	Input input = new Input("input", 0.3);

	@Test
	public void createValidInputList() {
		InputList inputList = new InputList();
		assertNotNull(inputList);
	}

	@Test
	public void addInputToInputList() throws ChanceOverflowException {
		input.setChance(2);

		InputList inputList = new InputList();
		assertNotNull(inputList);

		inputList.addInput(input);
		assertEquals("input", inputList.getOneInputValue());
	}

	@Test
	public void retrieveRandomInputFromList() throws ChanceOverflowException {
		Input input2 = new Input("input2", 0.3);
		Input input3 = new Input("input3", 0.4);

		InputList inputList = new InputList();
		assertNotNull(inputList);

		inputList.addInput(input);
		inputList.addInput(input2);
		inputList.addInput(input3);

		ArrayList<String> expected = new ArrayList<String>();
		expected.add("input");
		expected.add("input2");
		expected.add("input3");

		assertTrue(expected.contains(inputList.getOneInputValue()));
	}

	@Test
	public void setMissingChanceForInput() throws ChanceOverflowException {
		Input input2 = new Input("input2", 0.0);

		InputList inputList = new InputList();
		assertNotNull(inputList);

		inputList.addInput(input2);

		assertEquals("input2", inputList.getOneInputValue());
	}

	@Test(expected = ChanceOverflowException.class)
	public void addInputsWithTotalChanceOverOne()
			throws ChanceOverflowException {
		Input input2 = new Input("input2", 0.9);

		InputList inputList = new InputList();
		assertNotNull(inputList);

		inputList.addInput(input);
		inputList.addInput(input2);
	}

	@Test
	public void addVerySmallChanceInput() throws ChanceOverflowException {
		Input input2 = new Input("input2", 0.0000001);

		InputList inputList = new InputList();
		assertNotNull(inputList);

		inputList.addInput(input2);
		assertEquals("input2", inputList.getOneInputValue());
	}

	@Test
	public void rightChancesAfterGetOneInputCall()
			throws FileNotCompatibleException, ChanceOverflowException {
		InputList inputList = new InputList();
		inputList.addInput(new Input("100", 0.8));
		inputList.addInput(new Input("123", 0.1));
		inputList.addInput(new Input("text", 0.0));
		inputList.addInput(new Input("123", 0.0));
		inputList.addInput(new Input("0", 0.0));
		inputList.addInput(new Input("null", 0.0));

		inputList.getOneInputValue();
		ArrayList<Input> inputArrayList = inputList.getInputList();

		ArrayList<Input> expected = new ArrayList<Input>();
		expected.add(new Input("100", 0.8));
		expected.add(new Input("123", 0.1));
		expected.add(new Input("text", 0.025));
		expected.add(new Input("123", 0.025));
		expected.add(new Input("0", 0.025));
		expected.add(new Input("null", 0.025));

		for (int i = 0; i < expected.size(); i++) {
			Input input = inputArrayList.get(i);
			Input expectedInput = expected.get(i);
			assertEquals(expectedInput.getChance(), input.getChance(), 0.00001);
			assertEquals(expectedInput.getValue(), input.getValue());
		}
	}

}
