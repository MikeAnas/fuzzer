package parsers;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import actions.Action;
import actions.ActionSet;
import actions.ChanceOverflowException;

/**
 * This class parses XML documents that contain actionSets and actions to
 * iAction objects. The rootActionSet corresponds to the actionSet that contains
 * everything in the XML file.
 * 
 * @author Kees Lampe
 * 
 */
public class ParseActionXML extends ParseXML {

	private ActionSet rootActionSet;

	/**
	 * Gets the rootNode of the XML file and starts visiting all its child nodes
	 * recursively.
	 * 
	 * @param xmlFile
	 * @throws FileNotCompatibleException
	 */
	public ParseActionXML(String xmlFile) throws FileNotCompatibleException {
		Node root = setUpParser(xmlFile);
		if (root.getNodeName().equals("actionset")) {
			rootActionSet = new ActionSet(getAttributeValue(root, "order"),
					getAttributeValueTimes(root));
			visitRecursively(root, rootActionSet);
		} else {
			throw new FileNotCompatibleException("Incompatible nodename");
		}
	}

	/**
	 * Checks for each childNode of node whether this is an actionSet action or
	 * file node. If the childNode is one of these nodes it is added as a
	 * subAction to the actionSetNode.
	 * 
	 * @param node
	 *            The current node that is being visited.
	 * @param actionSetNode
	 *            The ActionSet object that corresponds to node.
	 * @throws FileNotCompatibleException
	 */
	private void visitRecursively(Node node, ActionSet actionSetNode)
			throws FileNotCompatibleException {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node childNode = list.item(i);
			addToActionSet(childNode, actionSetNode);
		}
	}

	/**
	 * Adds the given childnode to the actionset, depending on the name of the
	 * node.
	 * 
	 * @param childNode
	 *            The childnode to be added
	 * @param actionSetNode
	 *            The actionSetNode to be added to
	 * @throws FileNotCompatibleException
	 */
	private void addToActionSet(Node childNode, ActionSet actionSetNode)
			throws FileNotCompatibleException {
		if (childNode.getNodeName().equals("actionset")) {
			addSubActionSet(childNode, actionSetNode);
		} else if (childNode.getNodeName().equals("action")) {
			addSubAction(childNode, actionSetNode);
		} else if (childNode.getNodeName().equals("file")) {
			addSubFile(childNode, actionSetNode);
		}
	}

	/**
	 * Create new ActionSet from childNode and adds it as a subAction to
	 * ActionSetNode. Then visit the children of the newly created actionNode
	 * recursively.
	 * 
	 * @param childNode
	 *            The node which is being added as an subActionSet
	 * @param actionSetNode
	 *            The parent actionSetNode.
	 * @throws FileNotCompatibleException
	 */
	private void addSubActionSet(Node childNode, ActionSet actionSetNode)
			throws FileNotCompatibleException {
		ActionSet childActionNode = new ActionSet(getAttributeValue(childNode,
				"order"), getAttributeValueTimes(childNode));
		actionSetNode.addSubAction(childActionNode);
		visitRecursively(childNode, childActionNode);
	}

	/**
	 * Create a new Action node from the childNode and add it as an SubAction to
	 * actionNode. To add the input values to this Action addValuesToAction() is
	 * called.
	 * 
	 * @param childNode
	 *            The node which is being added as an subAction.
	 * @param actionSetNode
	 *            The parent actionSetNode.
	 * @throws FileNotCompatibleException
	 */
	private void addSubAction(Node childNode, ActionSet actionSetNode)
			throws FileNotCompatibleException {
		try {
			Action childActionNode = new Action(
					getAttributeValueChance(childNode), getAttributeValue(
							childNode, "operation"), getAttributeValue(
							childNode, "id"));
			addValuesToAction(childNode, childActionNode);
			actionSetNode.addSubAction(childActionNode);
		} catch (ChanceOverflowException e) {
			throw new FileNotCompatibleException(
					"Incompatible: Total chance is larger than 1");
		}
	}

	/**
	 * This method is called when a Action is created to add one ore multiple
	 * values to it. If there is a file as value parse it with ParseInputXML to
	 * an InputList with Input objects. And add this InputList object to the
	 * action object. If there isn't a file but just text set this text as the
	 * value for the action object.
	 * 
	 * @param node
	 *            The node of the action (which child contains the value).
	 * @param actionNode
	 *            The action object created from the node by addAction()
	 * @throws FileNotCompatibleException
	 * @throws ChanceOverflowException
	 * @throws DOMException
	 */
	private void addValuesToAction(Node node, Action actionNode)
			throws FileNotCompatibleException, DOMException,
			ChanceOverflowException {
		NodeList childNodes = node.getChildNodes();
		String nodeText = node.getTextContent();
		if (childNodes.getLength() != 0
				&& childNodes.item(0).getNodeName().equals("file")) {
			ParseInputXML inputxml = new ParseInputXML(childNodes.item(0)
					.getAttributes().getNamedItem("name").getNodeValue());
			actionNode.setInputsValues(inputxml.getInputs());
		} else if (!nodeText.equals("")) {
			actionNode.setValue(nodeText);
		}
	}

	/**
	 * Add the rootActionSet of a file node to as a subAction and visit it
	 * recursively.
	 * 
	 * @param childNode
	 *            The node which is the name file.
	 * @param actionSetNode
	 *            The parent actionSetNode.
	 * @throws FileNotCompatibleException
	 */
	private void addSubFile(Node childNode, ActionSet actionSetNode)
			throws FileNotCompatibleException {
		ParseActionXML newFile = new ParseActionXML(getAttributeValue(
				childNode, "name"));
		actionSetNode.addSubAction(newFile.getRootActionSet());
		visitRecursively(childNode, newFile.getRootActionSet());
	}

	public ActionSet getRootActionSet() {
		return rootActionSet;
	}

}