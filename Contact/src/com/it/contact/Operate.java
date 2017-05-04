package com.it.contact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Vector;

import com.it.utils.OrderByAge;
import com.it.utils.OrderByName;
import com.it.utils.OrderBySex;

public class Operate {
	private static Vector<Person> v = new Vector<Person>();

	public static void readFile() throws IOException, ClassNotFoundException {
		File file = new File("E:/Java/Tel");
		File[] files = file.listFiles();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		for (File file2 : files) {
			if (file2.isFile()) {
				fis = new FileInputStream(file2);
				ois = new ObjectInputStream(fis);
				Person person = (Person) ois.readObject();
				v.add(person);
			}
		}
		ois.close();
	}

	public static void writeFile() throws IOException {
		File file = null;
		for (Person person : v) {
			String name = person.getName();
			String str = name + ".txt";
			file = new File("E:/Java/Tel", str);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(person);
			oos.close();
		}
	}

	// 璇诲彇鏂囦欢
	// public static void readFile() {
	// File file = new File("TelNumber.txt");
	// if (!file.exists()) {
	// try {
	// file.createNewFile();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// Scanner input = null;
	// try {
	// input = new Scanner(file);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// while (input.hasNext()) {
	// String name = input.next();
	// String age = input.next();
	// String sex = input.next().toUpperCase();
	// String telNum = input.next();
	// String address = input.next();
	// v.add(new Person(name, age, sex, telNum, address));
	// }
	// input.close();
	// }
	//
	// // 鍐欏叆鏂囦欢
	// public static void writeFile() {
	// File file = new File("TelNumber.txt");
	// if (!file.exists()) {
	// try {
	// file.createNewFile();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// PrintWriter writer = null;
	// try {
	// writer = new PrintWriter(file);
	// } catch (FileNotFoundException e1) {
	// e1.printStackTrace();
	// }
	//
	// for (int i = 0; i < v.size(); i++) {
	// writer.println(v.get(i).getName() + "\t" + v.get(i).getAge() + "\t" +
	// v.get(i).getSex() + "\t"
	// + v.get(i).getTelNum() + "\t" + v.get(i).getAddress());
	// }
	// writer.flush();
	// writer.close();
	// }

	public static int addLogic() {// 鐢ㄦ埛娣诲姞淇℃伅涓氬姟閫昏緫鎺у埗
		int num = 1;
		while (num == 1) {
			Menu.addMenu();
			int subNum = TelNoteRegex.menuRegex(1, 3);
			while (subNum != 3) {
				switch (subNum) {
				case 1:
					Operate.addOperation();
					break;
				case 2:
					Operate.showAll();
					break;
				}
				Menu.addMenu();
				subNum = TelNoteRegex.menuRegex(1, 3);
			}
			Menu.mainMenu();
			num = TelNoteRegex.menuRegex(1, 6);
		}
		return num;
	}

	// 鐢ㄦ埛鏌ヨ淇℃伅涓氬姟閫昏緫鎺у埗
	public static int searchLogic() {
		int num = 2;
		while (num == 2) {
			Menu.searchMenu();
			int subNum = TelNoteRegex.menuRegex(1, 7);
			while (subNum != 7) {
				switch (subNum) {
				case 1:
					Operate.searchByName();
					break;
				case 2:
					Operate.searchByAge();
					break;
				case 3:
					Operate.searchBySex();
					break;
				case 4:
					Operate.searchByTelNum();
					break;
				case 5:
					Operate.searchByAdd();
					break;
				case 6:
					Operate.showAll();
					break;
				}
				Menu.searchMenu();
				subNum = TelNoteRegex.menuRegex(1, 7);
			}
			Menu.mainMenu();
			num = TelNoteRegex.menuRegex(1, 7);

		}
		return num;
	}

	// 淇敼淇℃伅涓氬姟閫昏緫鎺у埗
	public static int modifyLogicLogic() {
		int num = 3;
		while (num == 3) {
			Menu.modifyMenu();
			int subNum = TelNoteRegex.menuRegex(1, 3);
			while (subNum != 3) {
				switch (subNum) {
				case 1:
					Operate.showAll();
					break;
				case 2:
					Operate.modify();
					break;
				}
				Menu.modifyMenu();
				subNum = TelNoteRegex.menuRegex(1, 3);
			}
			Menu.mainMenu();
			num = TelNoteRegex.menuRegex(1, 6);
		}
		return num;
	}

	// 鍒犻櫎淇℃伅涓氬姟閫昏緫鎺у埗
	public static int deleteLogic() {
		int num = 4;
		while (num == 4) {
			Menu.deleteMenu();
			int subNum = TelNoteRegex.menuRegex(1, 4);
			while (subNum != 4) {
				switch (subNum) {
				case 1:
					Operate.showAll();
					break;
				case 2:
					Operate.delete();
					break;
				case 3:
					Operate.deleteAll();
					break;
				}
				Menu.deleteMenu();
				subNum = TelNoteRegex.menuRegex(1, 4);
			}
			Menu.mainMenu();
			num = TelNoteRegex.menuRegex(1, 6);
		}
		return num;

	}

	// 鎺掑簭淇℃伅涓氬姟閫昏緫鎺у埗
	public static int orderLogic() {
		int num = 5;
		while (num == 5) {
			Menu.orderMenu();
			int subNum = TelNoteRegex.menuRegex(1, 5);
			while (subNum != 5) {
				switch (subNum) {
				case 1:
					Operate.orderName();
					break;
				case 2:
					Operate.orderAge();
					break;
				case 3:
					Operate.orderSex();
					break;
				case 4:
					Operate.showAll();
					break;
				}
				Menu.orderMenu();
				subNum = TelNoteRegex.menuRegex(1, 5);
			}
			Menu.mainMenu();
			num = TelNoteRegex.menuRegex(1, 6);
		}
		return num;
	}

	// 娣诲姞鏂扮敤鎴蜂俊鎭�
	public static void addOperation() {
		String name = TelNoteRegex.nameRegex();
		String age = TelNoteRegex.ageRegex();
		String sex = TelNoteRegex.sexRegex();
		String telNum = TelNoteRegex.telNumRegex();
		String address = TelNoteRegex.addressRegex();
		Person person = new Person(name, age, sex, telNum, address);
		v.add(person);
	}

	public static void showAll() {// 鏌ヨ鍏ㄩ儴鐢ㄦ埛淇℃伅
		if (v.size() == 0 || v == null) {
			System.out.println("褰撳墠璁板綍涓虹┖");
		}
		for (int i = 0; i < v.size(); i++) {
			v.get(i).setId(i + 1);
			System.out.println(v.get(i));
		}
	}

	// 鎸夊鍚嶆煡璇㈢敤鎴蜂俊鎭�
	public static void searchByName() {
		String name = TelNoteRegex.nameRegex().toLowerCase();
		Vector<Person> vector = new Vector<Person>();
		int count = 0;
		for (int i = 0; i < v.size(); i++) {
			if (!v.get(i).getName().toLowerCase().equals(name)) {
				count++;
			} else {
				vector.add(v.elementAt(i));
			}
		}
		if (count == v.size()) {
			System.out.println("娌℃湁姝や汉璁板綍!");
		} else {
			for (int i = 0; i < vector.size(); i++) {
				vector.get(i).setId(i + 1);
				System.out.println(vector.get(i));
			}
		}
	}

	// 鎸夊勾榫勬煡璇㈢敤鎴蜂俊鎭�
	public static void searchByAge() {
		String age = TelNoteRegex.ageRegex();
		Vector<Person> vector = new Vector<Person>();
		int count = 0;
		for (int i = 0; i < v.size(); i++) {
			if (!v.get(i).getAge().equals(age)) {
				count++;
			} else {
				vector.add(v.elementAt(i));
			}
		}
		if (count == v.size()) {
			System.out.println("娌℃湁鏌ユ壘鍒扮浉鍏宠褰�!");
		} else {
			for (int i = 0; i < vector.size(); i++) {
				vector.get(i).setId(i + 1);
				System.out.println(vector.get(i));
			}
		}
	}

	// 鎸夋�у埆鏌ヨ鐢ㄦ埛淇℃伅
	public static void searchBySex() {
		String sex = TelNoteRegex.sexRegex();
		Vector<Person> vector = new Vector<Person>();
		int count = 0;
		for (int i = 0; i < v.size(); i++) {
			if (!v.get(i).getSex().equals(sex)) {
				count++;
			} else {
				vector.add(v.elementAt(i));
			}
		}
		if (count == v.size()) {
			System.out.println("娌℃湁鏌ユ壘鍒扮浉鍏宠褰�!");
		} else {
			for (int i = 0; i < vector.size(); i++) {
				vector.get(i).setId(i + 1);
				System.out.println(vector.get(i));
			}
		}
	}

	// 鎸夌數璇濆彿鐮佹煡璇㈢敤鎴蜂俊鎭�
	public static void searchByTelNum() {
		String telNum = TelNoteRegex.telNumRegex();
		Vector<Person> vector = new Vector<Person>();
		int count = 0;
		for (int i = 0; i < v.size(); i++) {
			if (!v.get(i).getTelNum().equals(telNum)) {
				count++;
			} else {
				vector.add(v.elementAt(i));
			}
		}
		if (count == v.size()) {
			System.out.println("娌℃湁鏌ユ壘鍒扮浉鍏宠褰�!");
		} else {
			for (int i = 0; i < vector.size(); i++) {
				vector.get(i).setId(i + 1);
				System.out.println(vector.get(i));
			}
		}
	}

	// 鎸夊湴鍧�鏌ヨ鐢ㄦ埛淇℃伅
	public static void searchByAdd() {
		String address = TelNoteRegex.addressRegex().toLowerCase();
		Vector<Person> vector = new Vector<Person>();
		int count = 0;
		for (int i = 0; i < v.size(); i++) {
			if (!v.get(i).getAddress().toLowerCase().equals(address)) {
				count++;
			} else {
				vector.add(v.elementAt(i));
			}
		}
		if (count == v.size()) {
			System.out.println("娌℃湁鏌ユ壘鍒扮浉鍏宠褰�!");
		} else {
			for (int i = 0; i < vector.size(); i++) {
				vector.get(i).setId(i + 1);
				System.out.println(vector.get(i));
			}
		}
	}

	// 淇敼鎸囧畾璁板綍淇℃伅
	public static void modify() {
		if (v.size() == 0 || v == null) {
			System.out.println("褰撳墠璁板綍涓虹┖,鏃犳硶淇敼");
		} else {
			System.out.print("璇疯緭鍏ヨ褰曞簭鍙�");
			int i = TelNoteRegex.menuRegex(1, v.size());
			Menu.subModifyMenu();
			int num = TelNoteRegex.menuRegex(1, 6);
			switch (num) {
			case 1:
				String name = TelNoteRegex.nameRegex();
				v.get(i - 1).setName(name);
				break;
			case 2:
				String age = TelNoteRegex.ageRegex();
				v.get(i - 1).setAge(age);
				break;
			case 3:
				String sex = TelNoteRegex.sexRegex();
				v.get(i - 1).setSex(sex);
				break;
			case 4:
				String telNum = TelNoteRegex.telNumRegex();
				v.get(i - 1).setTelNum(telNum);
				break;
			case 5:
				String address = TelNoteRegex.addressRegex();
				v.get(i - 1).setAddress(address);
				break;
			}
			return;
		}
	}

	// 鍒犻櫎鎸囧畾鐢ㄦ埛淇℃伅
	public static void delete() {
		if (v.size() == 0 || v == null) {
			System.out.println("褰撳墠璁板綍涓虹┖锛屾棤娉曟搷浣�");
		} else {
			System.out.print("璇疯緭鍏ヨ鍒犻櫎鐨勫簭鍙�,");
			int i = TelNoteRegex.menuRegex(1, v.size());
			while (i > v.size() || i < 1) {
				System.out.println("璁板綍涓嶅瓨鍦�,璇烽噸鏂伴�夋嫨");
				i = TelNoteRegex.menuRegex(1, v.size());
			}
			v.remove(i - 1);
			System.out.println("鎿嶄綔鎴愬姛,璇ヨ褰曞凡琚垹闄�");
		}
	}

	// 鍒犻櫎鍏ㄩ儴鐢ㄦ埛淇℃伅
	public static void deleteAll() {
		v.clear();
		System.out.println("鎿嶄綔鎴愬姛,褰撳墠璁板綍涓虹┖");
	}

	// 鎸夌敤鎴峰鍚嶆帓搴忎俊鎭�
	public static void orderName() {
		Collections.sort(v, new OrderByName());
	}

	// 鎸夌敤鎴峰勾榫勬帓搴忎俊鎭�
	public static void orderAge() {
		Collections.sort(v, new OrderByAge());
	}

	// 鎸夌敤鎴锋�у埆鎺掑簭淇℃伅
	public static void orderSex() {
		Collections.sort(v, new OrderBySex());
	}

}
