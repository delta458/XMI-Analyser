package parser;

import java.util.HashMap;

import org.w3c.dom.NodeList;


public class XMI2_1Parser extends XMIParser {
	

	
	public XMI2_1Parser(NodeList nodeLst) {
		super(nodeLst);
	}

	public void init() {
		System.out.println("XMI Version 2.1");
		result = new HashMap<String, Integer>();
	}

	@Override
	public HashMap<String, Integer> getList() {
		return result;
	}

	
}
