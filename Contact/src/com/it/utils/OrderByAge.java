package com.it.utils;

import java.util.Comparator;

import com.it.contact.Person;

public class OrderByAge implements Comparator<Person> {

	public int compare(Person o1, Person o2) {
		int age1=Integer.parseInt(o1.getAge());
		int age2=Integer.parseInt(o2.getAge());		
		return age1-age2;
	}

}
