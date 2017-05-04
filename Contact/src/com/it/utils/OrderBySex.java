package com.it.utils;

import java.util.Comparator;

import com.it.contact.Person;

public class OrderBySex implements Comparator<Person> {

	public int compare(Person o1, Person o2) {
		return o1.getSex().compareTo(o2.getSex());
	}

}
