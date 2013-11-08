package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import parser.ZargoUMLParser;

/**
 * This class checks which Parser should be initiated based on the xmi-format.
 * @author Ifraimov David
 *
 */
public class Evaluation {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

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

			NodeList nodeLst = doc.getElementsByTagName("*");
			
			Node fstNode = nodeLst.item(1);
			System.out.println(fstNode.getNodeName());
			if(fstNode.getNodeName().startsWith("XMI") || fstNode.getNodeName().startsWith("argo")) {
				ZargoUMLParser zup = new ZargoUMLParser();
				zup.init();
			} else if (fstNode.getNodeName().startsWith("uml")) {
				System.out.println("EMX!");
			}  else if (fstNode.getNodeName().startsWith("xmi")) {
				System.out.println("MDXMI!");
			} else if (fstNode.getNodeName().startsWith("eAnnotations")) {
				System.out.println("UML2!");
			} else if (fstNode.getNodeName().startsWith("eAnnotations")) {
				System.out.println("UML2!");
			} 

			


		}
		writer.flush();
		writer.close();

	}

}
