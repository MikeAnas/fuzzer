package parsers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import actions.ChanceOverflowException;
import actions.Input;

public class ParseInputXMLTest {

	@Test
	public void createInputParserNotNull() throws FileNotCompatibleException,
			ChanceOverflowException {
		ParseInputXML inputParser = new ParseInputXML(
				"test/resources/inputTest.xml");
		assertNotNull(inputParser);
	}

	@Test
	public void createValidInputParser() throws FileNotCompatibleException,
			ChanceOverflowException {
		ParseInputXML parser = new ParseInputXML("test/resources/inputTest.xml");
		ArrayList<Input> inputList = parser.getInputs().getInputList();
		ArrayList<Input> expected = new ArrayList<Input>();
		expected.add(new Input("100", 0.8));
		expected.add(new Input("123", 0.1));
		expected.add(new Input("text", 0.0));
		expected.add(new Input("123", 0.0));
		expected.add(new Input("0", 0.0));
		expected.add(new Input("null", 0.0));

		for (int i = 0; i < expected.size(); i++) {
			Input input = inputList.get(i);
			Input expectedInput = expected.get(i);
			assertEquals(expectedInput.getChance(), input.getChance(), 0.0);
			assertEquals(expectedInput.getValue(), input.getValue());
		}
	}

	@Test(expected = FileNotCompatibleException.class)
	public void createInvalidInputParser() throws FileNotCompatibleException,
			ChanceOverflowException {
		new ParseInputXML("test/resources/notExistingFile.xml");
	}

	@Test(expected = FileNotCompatibleException.class)
	public void parseInvalidInputXML() throws FileNotCompatibleException,
			ChanceOverflowException {
		new ParseInputXML("test/resources/inputTestInvalid.xml");
	}

	@Test
	public void retrieveInputList() throws FileNotCompatibleException,
			ChanceOverflowException {
		ParseInputXML inputParser = new ParseInputXML(
				"test/resources/inputTest.xml");
		assertNotNull(inputParser);
		assertNotNull(inputParser.getInputs());
	}

	@Test(expected = ChanceOverflowException.class)
	public void parseInputsWithAccumulatedChanceGreaterThanOne()
			throws FileNotCompatibleException, ChanceOverflowException {
		new ParseInputXML("test/resources/inputTestChance.xml");
	}
}
