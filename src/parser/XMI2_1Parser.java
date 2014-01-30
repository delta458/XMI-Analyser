package parser;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMI2_1Parser extends XMIParser {

	public XMI2_1Parser(NodeList nodeLst) {
		super(nodeLst);
	}

	public void init() {
		System.out.println("XMI Version 2.1");
		result = new HashMap<String, Integer>();
		//Parse through nodeLst
		for (int s = 0; s < nodeLst.getLength(); s++) {
			Node fstNode = nodeLst.item(s);

			Node temp = fstNode.getAttributes().getNamedItem("xmi:type");
			if(temp!=null) {
			System.out.println(s + ": TYPE: " + temp.getNodeType() + ", NAME: " + temp.getNodeName() + ", ITEM: " + temp.getNodeValue());

				if (result.containsKey(temp.getNodeValue())) {
					result.put(temp.getNodeValue(), result.get(temp.getNodeValue())+1);
					
				} else {
					result.put(temp.getNodeValue(), 1);
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
