package com.it;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class TestSax {

	static String xml = "test.xml";

	public static void main(String[] args) throws Exception, SAXException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		XMLReader xmlReader = parser.getXMLReader();
		MyContentHandler handler = new MyContentHandler();
		xmlReader.setContentHandler(handler);
		xmlReader.parse(xml);
		List<Person> persons = handler.getData();
		for (Person person : persons) {
			System.out.println(person);
		}
	}
}

class MyContentHandler extends DefaultHandler {

	List<Person> pers = new ArrayList<>();

	boolean isStart = false;
	Person person;
	String element;

	public List<Person> getData() {
		return pers;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("startDocument-----------");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		System.out.println("startElement----------" + qName);
		if (qName.equals("person")) {
			person = new Person();
			String id = attributes.getValue("id");
			person.setId(id);
		}
		isStart = true;
		element = qName;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		if (element.equals("name") && isStart) {
			String name = new String(ch, start, length);
			person.setName(name);
		}
		if (element.equals("age") && isStart) {
			String age = new String(ch, start, length);
			person.setAge(age);
		}
		if (element.equals("sex") && isStart) {
			String sex = new String(ch, start, length);
			person.setSex(sex);
		}
		System.out.println("characters------" + new String(ch, start, length));
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		System.out.println("endElement-------" + qName);
		isStart = false;
		if (qName.equals("person")) {
			pers.add(person);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		System.out.println("endDocument-------");
	}
}
