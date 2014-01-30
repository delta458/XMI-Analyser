package parser;

import java.util.HashMap;

import org.w3c.dom.NodeList;

/**
 * @author Ifraimov David
 * A parent class providing the constructor and needed methods for the different XMI-Parsers. 
 */
public abstract class XMIParser {

	NodeList nodeLst;
	HashMap<String, Integer> result;

	public XMIParser(NodeList nodeLst) {
		this.nodeLst = nodeLst;
	}

	public abstract void init();

	public abstract Object getList();

}
