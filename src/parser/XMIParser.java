package parser;

import java.util.HashMap;

import org.w3c.dom.NodeList;

public abstract class XMIParser {

	NodeList nodeLst;
	HashMap<String, Integer> result;

	public XMIParser(NodeList nodeLst) {
		this.nodeLst = nodeLst;
	}

	public abstract void init();

	public abstract Object getList();

}
