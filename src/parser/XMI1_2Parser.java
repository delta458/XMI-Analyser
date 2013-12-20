package parser;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMI1_2Parser extends XMIParser {

	public XMI1_2Parser(NodeList nodeLst) {
		super(nodeLst);
	}

	public void init() {
		System.out.println("XMI Version 1.2");
		result = new HashMap<String, Integer>();

		//Parse through nodeLst
		for (int s = 0; s < nodeLst.getLength(); s++) {
			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
				if(fstNode.getNodeName().startsWith("UML:") && !fstNode.getNodeName().contains(".")) {
					
					
					
					if (result.containsKey(fstNode.getNodeName())) {
						result.put(fstNode.getNodeName(), result.get(fstNode.getNodeName())+1);
					} else {
						result.put(fstNode.getNodeName(), 1);
					}
				//	System.out.println(fstNode.getNodeName());
				}
			}

		}
		System.out.println(result.toString());
		

	}

	@Override
	public HashMap<String, Integer> getList() {
		return result;
	}

}
