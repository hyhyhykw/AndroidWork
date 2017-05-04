package com.it.contact;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Person implements Serializable{
	private int id;
	private String name;
	private String age;
	private String sex;
	private String telNum;
	private String address;

	public Person() {
	}

	public Person(String name, String age, String sex, String telNum, String address) {
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.telNum = telNum;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "序号  " + id + "#\t姓名  " + name + "\t\t年龄  " + age + "\t\t性别  " + sex + "\t\t电话  " + telNum + "\t\t住址  " + address;
	}

}
