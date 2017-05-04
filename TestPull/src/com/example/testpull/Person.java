package com.example.testpull;

public class Person {

	public String name;
	public String age;
	public String sex;
	public String id;

	public Person() {
	}

	public Person(String name, String age, String sex, String id) {
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", sex=" + sex
				+ ", id=" + id + "]";
	}

}
