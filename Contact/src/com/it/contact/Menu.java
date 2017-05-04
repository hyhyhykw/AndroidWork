package com.it.contact;

public class Menu {
	public static void mainMenu() {// 主菜单
		System.out.println("*************************");
		System.out.println("**       1 添加记录                **");
		System.out.println("**       2 查找记录                **");
		System.out.println("**       3 修改记录                **");
		System.out.println("**       4 删除记录                **");
		System.out.println("**       5 排序记录                **");
		System.out.println("**       6 退出系统                **");
		System.out.println("*************************");
	}

	public static void addMenu() {// 添加用户子菜单
		System.out.println("*************************");
		System.out.println("**       1 添加新记录                **");
		System.out.println("**       2 查找全记录                **");
		System.out.println("**       3 返回上一级                **");
		System.out.println("*************************");
	}

	public static void searchMenu() {// 查找用户菜单
		System.out.println("*************************");
		System.out.println("**       1 按姓名查找                **");
		System.out.println("**       2 按年龄查找                **");
		System.out.println("**       3 按性别查找                **");
		System.out.println("**       4 按号码查找                **");
		System.out.println("**       5 按住址查找                **");
		System.out.println("**       6 查看全纪录                **");
		System.out.println("**       7 返回上一级                **");
		System.out.println("*************************");
	}

	public static void modifyMenu() {// 修改用户信息主菜单
		System.out.println("*************************");
		System.out.println("**       1 查看全纪录                **");
		System.out.println("**       2 修改指定记录                **");
		System.out.println("**       3 返回上一级                **");
		System.out.println("*************************");
	}

	public static void subModifyMenu() {// 修改用户信息子菜单
		System.out.println("*************************");
		System.out.println("**       1 修改姓名                **");
		System.out.println("**       2 修改年龄                **");
		System.out.println("**       3 修改性别                **");
		System.out.println("**       4 修改号码                **");
		System.out.println("**       5 修改住址                **");
		System.out.println("**       6 返回上一级                **");
		System.out.println("*************************");
	}

	public static void deleteMenu() {// 删除用户信息菜单
		System.out.println("*************************");
		System.out.println("**       1 查看全纪录                **");
		System.out.println("**       2 删除指定记录                **");
		System.out.println("**       3 删除全部记录                **");
		System.out.println("**       4 返回上一级                **");
		System.out.println("*************************");
	}

	public static void orderMenu() {// 排序用户信息菜单
		System.out.println("*************************");
		System.out.println("**       1 按姓名排序                **");
		System.out.println("**       2 按年龄排序                **");
		System.out.println("**       3 按性别排序                **");
		System.out.println("**       4 查看全纪录                **");
		System.out.println("**       5 返回上一级                **");
		System.out.println("*************************");
	}
}
