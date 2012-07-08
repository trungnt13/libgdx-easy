package org.ege.utils.xml;

public class Xml {
	static final XmlResource $RESOURCE = new XmlResource();
	
	public Xml(){
	}
	
	public String getElement(int id){
		return $RESOURCE.getElement(id);
	}
}
