package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class UMLTypeExtractor {

	/*
	 * This class is able to parse Zargo UML files
	 * Zargo UML Files are produced by ArgoUML
	 * uml.version = '1.4'
	 * xmi.version = '1.2'
	 */
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		// Folder containing the Zargo UML files
		File f = new File("models/");
		File[] fileArray = f.listFiles();

		// File for saving the statistics for the processed models
		File summary = new File("result/zargo-xmi.txt");
		summary.delete();
		summary.createNewFile();
		FileWriter writer = new FileWriter(summary, true);

		for (int i = 0; i < fileArray.length; i++) {
			File file = fileArray[i];

			System.out.println("******************");
			System.out.println(file.getName());

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			// Get all elements contained in the XML file
			NodeList nodeLst = doc.getElementsByTagName("*");

			// Store for all element types and their instance cardinality
			HashMap<String, Integer> result = new HashMap<String, Integer>();
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					if (!fstNode.getNodeName().startsWith("UML:"))
						continue;
					Element fstElmnt = (Element) fstNode;
					if (!(fstElmnt.hasAttribute("xmi.idref") || fstElmnt.getNodeName().contains("."))) {
						if (result.containsKey(fstElmnt.getNodeName())) {
							result.put(fstElmnt.getNodeName(),
									result.get(fstElmnt.getNodeName()) + 1);
						} else {
							result.put(fstElmnt.getNodeName(), 1);
						}
					}
				}

			}

			// Store information in result file
			Set<Entry<String, Integer>> set = result.entrySet();
			for (Entry<String, Integer> e : set) {
				String fileName = file.getName().substring(0,
						file.getName().indexOf("."));
				String elementName = e.getKey().substring(
						e.getKey().indexOf(":") + 1, e.getKey().length());
				writer.write("zargo/" + fileName + ";" + elementName + ";"
						+ e.getValue() + "\n");
			}

			System.out.println(result);

		}
		writer.flush();
		writer.close();
	}
}
