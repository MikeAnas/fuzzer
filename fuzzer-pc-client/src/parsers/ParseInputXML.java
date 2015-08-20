package parsers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import actions.ChanceOverflowException;
import actions.Input;
import actions.InputList;

/**
 * This class parses XML documents that contain inputs to input objects and one
 * inputList. This inputList can be returned by getInputs() and added to an
 * Action object.
 * 
 * @author Kees Lampe
 *
 */
public class ParseInputXML extends ParseXML {

	private InputList inputList;

	/**
	 * Gets the rootNode of the XML file and then visits all its children by
	 * calling getAllInputs
	 * 
	 * @param xmlFile
	 * @throws FileNotCompatibleException
	 * @throws ChanceOverflowException
	 */
	public ParseInputXML(String xmlFile) throws FileNotCompatibleException,
			ChanceOverflowException {
		Node root = setUpParser(xmlFile);
		if (root.getNodeName().equals("inputs")) {
			addAllInputs(root);
		} else {
			throw new FileNotCompatibleException("Incompatible nodename");
		}
	}

	/**
	 * Adds all inputs to the inputList as an input object.
	 * 
	 * @param node
	 * @throws ChanceOverflowException
	 */
	private void addAllInputs(Node node) throws ChanceOverflowException {
		NodeList list = node.getChildNodes();
		inputList = new InputList();
		for (int i = 0; i < list.getLength(); i++) {
			Node childNode = list.item(i);
			if (childNode.getNodeName().equals("input")) {
				Input input = new Input(childNode.getTextContent(),
						getAttributeValueChance(childNode));
				inputList.addInput(input);
			}
		}
	}

	public InputList getInputs() {
		return inputList;
	}

}