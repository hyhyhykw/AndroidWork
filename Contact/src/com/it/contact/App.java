package com.it.contact;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Operate.readFile();
		Menu.mainMenu();
		int num = TelNoteRegex.menuRegex(1, 6);
		while (num != 6) {
			switch (num) {
			case 1:
				num = Operate.addLogic();
				break;
			case 2:
				num = Operate.searchLogic();
				break;
			case 3:
				num = Operate.modifyLogicLogic();
				break;
			case 4:
				num = Operate.deleteLogic();
				break;
			case 5:
				num = Operate.orderLogic();
				break;
			}
		}
		Operate.writeFile();
		System.exit(0);
	}
}
