package com.it.contact;

import java.util.Scanner;
import java.util.regex.Pattern;

@SuppressWarnings("resource")
public class TelNoteRegex {
	public static int menuRegex(int min, int max) {// 对菜单输入选项的验证

		Scanner sc = new Scanner(System.in);
		System.out.println("请输入正确的数字,最小是:" + min + "最大是:" + max);
		String str = sc.nextLine();
		boolean flag = true;
		int num = 0;
		while (flag) {
			while (!Pattern.matches("\\d", str)) {
				System.out.println("输入有误,请重新输入：");
				str = sc.nextLine();
			}
			num = Integer.parseInt(str);
			flag = false;
			if (num > max || num < min) {
				System.out.println("没有该选项,请重新选择:");
				str = sc.nextLine();
				flag = true;
			}
		}
		return num;
	}

	public static String nameRegex() {// 对用户输入姓名的验证
		Scanner sc = new Scanner(System.in);
		System.out.println("输入姓名，1-10个字母:");
		String name = sc.nextLine();
		while (!Pattern.matches("[a-zA-Z]{1,10}", name)) {
			System.out.println("输入错误，请重新输入:");
			name = sc.nextLine();
		}
		return name;
	}

	public static String ageRegex() {// 对用户输入年龄的验证
		Scanner sc = new Scanner(System.in);
		System.out.println("输入年龄，1-100：");
		String age = sc.nextLine();
		while (!Pattern.matches("^[1-9]\\d?|100", age)) {
			System.out.println("输入年龄有误,请重新输入:");
			age = sc.nextLine();
		}
		return age;
	}

	public static String sexRegex() {// 对用户输入性别的验证
		Scanner sc = new Scanner(System.in);
		System.out.println("输入性别,男(m或M) 女(f或F):");
		String sex = sc.nextLine();
		while (!Pattern.matches("[mMfF]", sex)) {
			System.out.println("性别输入有误,请重新输入,男(m或M) 女(f或F):");
			sex = sc.nextLine();
		}
		sex = sex.toUpperCase();
		return sex;
	}

	public static String telNumRegex() {// 对用户输入电话号码的验证
		Scanner sc = new Scanner(System.in);
		System.out.println("输入电话号码 6-10位数字:");
		String telNum = sc.nextLine();
		while (!Pattern.matches("\\d{6,10}", telNum)) {
			System.out.println("输入错误,请输入6-10位数字:");
			telNum = sc.nextLine();
		}
		return telNum;
	}

	public static String addressRegex() {// 对用户输入地址的验证
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入地址,1-50个字母和数字组成:");
		String address = sc.nextLine();
		while (!Pattern.matches("[a-zA-Z0-9]{1,50}", address)) {
			System.out.println("地址输入有误,请重新输入:");
			address = sc.nextLine();
		}
		return address;
	}
}
