package parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Abstract XMLParser class which is used to parse the action and input XML
 * files
 * 
 * @author michel
 *
 */
public abstract class ParseXML {

	/**
	 * Retrieves the root node from the supplied XML file.
	 * 
	 * @param xmlFile
	 *            The XML file from which the root node must be retrieved
	 * @return Returns the root node of the file, or throws an exception if it
	 *         was not found.
	 * @throws FileNotCompatibleException
	 */
	protected Node setUpParser(String xmlFile)
			throws FileNotCompatibleException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new FileInputStream(new File(xmlFile)));

			return doc.getChildNodes().item(0);
		} catch (ParserConfigurationException | SAXException | IOException
				| DOMException e) {
			throw new FileNotCompatibleException(
					"The file could not be parsed correctly");
		}
	}

	/**
	 * Get attribute value with name attrName and return this if it doesn't
	 * exist return null.
	 * 
	 * @param node
	 * @param attrName
	 * @return Attribute Vale of Node node with attribute name attrName.
	 */
	protected String getAttributeValue(Node node, String attrName) {
		String attrValue = null;
		Node nodeItem = node.getAttributes().getNamedItem(attrName);
		if (nodeItem != null) {
			attrValue = nodeItem.getNodeValue();
		} else {
			System.err.println("Attribute value appears to be null");
		}
		return attrValue;
	}

	/**
	 * return the times value as an int if it is not set return 1.
	 * 
	 * @param node
	 * @return The times value as an int if it is not set return 1.
	 */
	protected int getAttributeValueTimes(Node node) {
		Node nodeItem = node.getAttributes().getNamedItem("times");
		int times = 1;
		if (nodeItem != null) {
			times = Integer.parseInt(nodeItem.getNodeValue());
		}
		return times;
	}

	/**
	 * return chance as double if it is not set return 0.0.
	 * 
	 * @param node
	 * @return The chance as double if it is not set return 0.0.
	 */
	protected double getAttributeValueChance(Node node) {
		String attrName = "chance";
		double chance = 0.0;
		if (node.getAttributes() != null
				&& node.getAttributes().getNamedItem(attrName) != null) {
			chance = Double.parseDouble(node.getAttributes()
					.getNamedItem(attrName).getNodeValue());
		}
		return chance;
	}
}
