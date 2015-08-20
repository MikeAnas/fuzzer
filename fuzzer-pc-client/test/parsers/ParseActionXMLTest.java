package parsers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import actions.Action;
import actions.ActionSet;
import actions.ChanceOverflowException;
import actions.IAction;

public class ParseActionXMLTest {

	@Test
	public void createActionParserNotNull() throws FileNotCompatibleException {
		ParseActionXML inputParser = new ParseActionXML(
				"test/resources/actionTest.xml");
		assertNotNull(inputParser);
	}

	@Test
	public void createValidActionParser() throws FileNotCompatibleException,
			ChanceOverflowException {
		ParseActionXML parser = new ParseActionXML(
				"test/resources/actionTestSimpleOneActionSet.xml");
		ArrayList<IAction> actionList = parser.getRootActionSet()
				.getSubActions();
		ArrayList<IAction> expected = new ArrayList<IAction>();

		expected.add(new Action(1.0, "click", "id1"));
		expected.add(new Action(1.0, "insert", "id2"));

		for (int i = 0; i < expected.size(); i++) {
			Action action = (Action) actionList.get(i);
			Action expectedAction = (Action) expected.get(i);
			assertEquals(expectedAction.toString(), action.toString());
		}
	}

	@Test
	public void actionSetWithSubActionSetTest()
			throws FileNotCompatibleException, ChanceOverflowException {
		ParseActionXML parser = new ParseActionXML(
				"test/resources/actionTestOneSubActionSet.xml");
		ArrayList<IAction> actionList = parser.getRootActionSet()
				.getSubActions();
		ArrayList<IAction> subActionList = ((ActionSet) actionList.get(0))
				.getSubActions();

		Action expectedAction = new Action(1.0, "insert", "id3");
		assertEquals(expectedAction.toString(), actionList.get(1).toString());

		ArrayList<IAction> expectedSubActionList = new ArrayList<IAction>();

		expectedSubActionList.add(new Action(1.0, "click", "id1"));
		expectedSubActionList.add(new Action(1.0, "insert", "id2"));

		for (int i = 0; i < expectedSubActionList.size(); i++) {
			Action action = (Action) subActionList.get(i);
			Action expectedSubAction = (Action) expectedSubActionList.get(i);
			assertEquals(expectedSubAction.toString(), action.toString());
		}
	}

	@Test
	public void createActionParserNestedInputNotNull()
			throws FileNotCompatibleException {
		ParseActionXML inputParser = new ParseActionXML(
				"test/resources/actionTestNestedInput.xml");
		assertNotNull(inputParser);
	}

	@Test
	public void withSubFileNotNull() throws FileNotCompatibleException {
		ParseActionXML inputParser = new ParseActionXML(
				"test/resources/actionTestSubFile.xml");
		assertNotNull(inputParser);
	}

	@Test
	public void withOneInputValue() throws FileNotCompatibleException {
		ParseActionXML inputParser = new ParseActionXML(
				"test/resources/actionTestOneInput.xml");
		assertNotNull(inputParser);
	}

	@Test(expected = FileNotCompatibleException.class)
	public void createInvalidActionParserDueTotalChance()
			throws FileNotCompatibleException {
		new ParseActionXML("test/resources/actionTestInvalidNestedInput.xml");
	}

	@Test(expected = FileNotCompatibleException.class)
	public void createInvalidActionParser() throws FileNotCompatibleException {
		new ParseActionXML("test/resources/notExistingFile.xml");
	}

	@Test(expected = FileNotCompatibleException.class)
	public void parseInvalidInputXML() throws FileNotCompatibleException {
		new ParseActionXML("test/resources/inputTestInvalid.xml");
	}

}
