package com.it;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test {
	static String xml = "test.xml";

	public static void main(String[] args) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(xml);
		// NodeList nodeList=doc.getElementsByTagName("sex");
		//
		// System.out.println(nodeList.getLength());
		// for (int i = 0; i < nodeList.getLength(); i++) {
		// System.out.println(nodeList.item(i).getTextContent());
		// }
		// List<Person> pers=getContent(doc);
		// for (Person person : pers) {
		// System.out.println(person);
		// }
		add(doc);
	}

	public static List<Person> getContent(Document doc) {
		List<Person> pers = new ArrayList<>();
		Element element = doc.getDocumentElement();
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element perElement = (Element) node;
				String id = perElement.getAttribute("id");

				NodeList nameList = perElement.getElementsByTagName("name");
				String name = nameList.item(0).getTextContent();

				NodeList ageList = perElement.getElementsByTagName("age");
				String age = ageList.item(0).getTextContent();

				NodeList sexList = perElement.getElementsByTagName("sex");
				String sex = sexList.item(0).getTextContent();

				Person per = new Person(name, age, sex, id);
				pers.add(per);
			}
		}
		return pers;
	}

	public static void add(Document doc) throws TransformerException {
		NodeList perList = doc.getElementsByTagName("person");
		Node perNode = perList.item(1);
		Element weightElement = doc.createElement("weight");
		weightElement.setTextContent("63");
		perNode.appendChild(weightElement);

		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();

		
		transformer.transform(new DOMSource(doc), new StreamResult(xml));
	}

}
