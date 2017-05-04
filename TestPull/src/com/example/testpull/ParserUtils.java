package com.example.testpull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ParserUtils {

	private List<Person> persons = new ArrayList<Person>();

	private Person person;

	public List<Person> pull(InputStream is) {
		try {
			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(is, "utf-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					// if (parser.getName().equals("person")) {
					// person = new Person();
					// person.setId(parser.getAttributeValue(0));
					// }
				} else if (eventType == XmlPullParser.START_TAG) {
					if (parser.getName().equals("person")) {
						person = new Person();
						person.setId(parser.getAttributeValue(0));
					}
					// if (null != person) {
					// if (parser.getName().equals("name")) {
					// person.setName(parser.nextText());
					// } else if (parser.getName().equals("age")) {
					// person.setAge(parser.nextText());
					// } else if (parser.getName().equals("sex")) {
					// person.setSex(parser.nextText());
					// }
					// }
				} else if (eventType == XmlPullParser.TEXT) {
					if (null != person) {
						if (parser.getName().equals("name")) {
							person.setName(parser.nextText());
						}
						if (parser.getName().equals("age")) {
							person.setAge(parser.nextText());
						}
						if (parser.getName().equals("sex")) {
							person.setSex(parser.nextText());
						}
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if (null != person)
						persons.add(person);
				}
				parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return persons;
	}
}
