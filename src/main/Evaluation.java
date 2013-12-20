package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import parser.XMI1_2Parser;
import parser.XMI2_0Parser;
import parser.XMI2_1Parser;
import parser.XMIParser;


/**
 * This class checks which Parser should be initiated based on the xmi-format.
 * @author Ifraimov David
 *
 */
public class Evaluation {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

		File f = new File("models/");
		File[] fileArray = f.listFiles();

		for (int i = 0; i < fileArray.length; i++) {

			File file = fileArray[i];

			System.out.println("******************");
			System.out.println(file.getName());

			String outputFilename = "result/output-" + file.getName();
			File summary = new File(outputFilename.replace(outputFilename.substring(outputFilename.indexOf(".")),".txt"));
			
			summary.delete();
			summary.createNewFile();
			FileWriter writer = new FileWriter(summary, true);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			NodeList nodeLst = doc.getElementsByTagName("*");

			//Iterate through nodeList and search for xmi.version
			//If found, start the parser and go to next file
			for (int s = 0; s < nodeLst.getLength(); s++) {
				
				Node fstNode = nodeLst.item(s);

				if(fstNode.hasAttributes() && fstNode.getAttributes().getLength() > 1) {

					Node xmiVersion;

					if(file.getName().contains("uml2") || file.getName().contains("emx") || file.getName().contains("mdxml")) {
						xmiVersion = fstNode.getAttributes().getNamedItem("xmi:version");
					} else {
						xmiVersion = fstNode.getAttributes().getNamedItem("xmi.version");
					}

					if(!fstNode.getNodeName().contains("meta") && xmiVersion != null) {
						
						XMIParser parser = null;
						if(xmiVersion.getTextContent().equals("1.2")) {
							parser = new XMI1_2Parser(nodeLst);
						} else if (xmiVersion.getTextContent().equals("2.0")) {
							parser = new XMI2_0Parser(nodeLst);
						} else if (xmiVersion.getTextContent().equals("2.1")) {
							parser = new XMI2_1Parser(nodeLst);
						} else {
							System.out.println("Unknown version! " + xmiVersion.getTextContent());
						}
						
						parser.init();
						writer.write(parser.getList().toString().replace(", ", "\n").replace("{", "").replace("}", ""));
					
						
					}
				}
			}
			
			writer.flush();
			writer.close();
			
		}
	}

}
