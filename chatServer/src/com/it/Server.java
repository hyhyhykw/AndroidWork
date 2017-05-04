package com.it;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Server {

	static BufferedReader reader;
	static int num = 1;
	static ClientModle modle;
	static List<ClientModle> sockets = new ArrayList<ClientModle>();
	private static String chatName;
	static String strflag;
	static String strchat;
	static String strname;
	static String personalName;
	static DB db = new DB();

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(5678);
		while (true) {
			Socket s = server.accept();
			new ServerThread(s, num).start();
			num++;
		}
	}

	public static class ServerThread extends Thread {
		Socket socket;
		int num;
		BufferedReader in;

		public ServerThread(Socket currentscoket, int num) {
			this.num = num;
			this.socket = currentscoket;
			System.out.println("--------" + sockets.size());

		}

		public void run() {
			String address = socket.getInetAddress().getHostAddress();
			System.out.println("address:" + address);
			try {
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream(), "utf-8"));
				while (true) {
					String text = in.readLine();
					if (text == null) {
						break;
					}
					System.out.println("text:" + text);
					getClientJson(text);
					chatName = strname;
					if (strflag.equals("-2")) {// 注册
						PrintWriter out = new PrintWriter(
								socket.getOutputStream());
						if (!db.isExits(strname)) {
							out.println(regster("4"));// 注册成功
							out.flush();
							db.insertuser(chatName, strchat);
							break;
						}
						out.println(regster("5"));// 注册失败
						out.flush();
					}
					if (strflag.equals("2")) {// 登陆
						PrintWriter out = new PrintWriter(
								socket.getOutputStream());
						if (!db.isLogin(strname, strchat)) {
							out.println(loginFail());
							out.flush();
							break;
						}
						ClientModle modle = new ClientModle();
						modle.setName(strname);
						System.out.println(strname);
						modle.setSocket(socket);
						sockets.add(modle);
						out.println(sendOnLineOfName());
						out.flush();
						sendMessage("2", chatName + "上线", chatName);
					}
					if (strflag.equals("0")) {// 离线
						System.out.println("客户端" + address + "走了");
						removeOffLine(chatName);
						sendMessage("0", "客户端" + chatName + "下线了", chatName);
						break;
					}
					if (strflag.equals("3")) {// 私聊
						sendPersonalMessage("3", strchat, personalName, strname);
					}
					// if (strflag.equals("6")) {// 表情
					// sendImg("6", strchat, strname);
					// }
					// 聊天
					System.out.println(address + "：" + strchat);
					if (strflag.equals("1")) {
						sendMessage("1", chatName + "说：" + strchat, strname);
					}
				}
			} catch (IOException e) {
				System.out.println("客户端" + address + "下线了");
				// e.printStackTrace();
			} finally {
				sockets.remove(socket);
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除离线用户
	 * 
	 * @param name
	 */
	private static void removeOffLine(String name) {
		for (int i = 0; i < sockets.size(); i++) {
			if (name.equals(sockets.get(i).getName())) {
				sockets.remove(i);
				break;
			}
		}

	}

	/**
	 * 得到客户端json信息
	 * 
	 * @param str
	 */
	private static void getClientJson(String str) {
		JSONObject jsonObject = JSONObject.fromObject(str);
		strflag = jsonObject.getString("type");
		strchat = jsonObject.getString("chatContent");
		strname = jsonObject.getString("name");
		if (strflag.equals("3")) {
			personalName = jsonObject.getString("personalName");
		}
	}

	/**
	 * 将在线的名字组织成json格式
	 * 
	 * @return
	 */
	private static String getJSON() {
		String name[] = new String[sockets.size()];
		for (int i = 0; i < sockets.size(); i++) {
			name[i] = sockets.get(i).getName();
		}
		JSONArray json = JSONArray.fromObject(name);
		return json.toString();
	}

	/**
	 * 登陆成功向客户端发送在线名字
	 */
	private static String sendOnLineOfName() {
		String str[] = { "1", getJSON() };// 1标识登录成功
		JSONArray json = JSONArray.fromObject(str);
		return json.toString();
	}

	/**
	 * 登陆失败
	 * 
	 * @return
	 */
	private static String loginFail() {
		String str[] = { "-1", "失败" };// *- 1标识登录失败
		JSONArray json = JSONArray.fromObject(str);
		return json.toString();
	}

	/**
	 * 注册
	 * 
	 * @param flag
	 *            4注册成功 5注册失败
	 * @return
	 */
	private static String regster(String flag) {
		String str[] = { flag, "注册" };
		JSONArray json = JSONArray.fromObject(str);
		return json.toString();
	}

	/**
	 * 聊天室表情转发
	 * 
	 * @param type
	 * @param imgId
	 * @param strname
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private static void sendImg(String type, String imgId, String strname) {
		String imgPath = "F:/serverimg/emoji_00" + imgId + ".png";
		System.out.println(imgPath);
		FileInputStream fis;
		try {
			for (int i = 0; i < sockets.size(); i++) {
				String addr = sockets.get(i).getName();
				if (addr.equals(strname)) {
					System.out.println("arrr:" + addr);
					chatName = addr;
					continue;
				} else {
					chatName = strname;
				}

				PrintWriter out = new PrintWriter(sockets.get(i).getSocket()
						.getOutputStream());
				DataOutputStream dos = new DataOutputStream(sockets.get(i)
						.getSocket().getOutputStream());
				out.println(Util.getJSonStr(type, imgId, chatName));
				out.flush();
				fis = new FileInputStream(imgPath);
				int size = fis.available();
				System.out.println("size = " + size);
				byte[] data = new byte[size];
				fis.read(data);
				dos.writeInt(size);
				dos.write(data);
				dos.flush();
				dos.close();
				fis.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 聊天室信息转发
	 * 
	 * @param str
	 * @param strname
	 * @throws IOException
	 */
	private static void sendMessage(String type, String str, String strname)
			throws IOException {
		for (int i = 0; i < sockets.size(); i++) {
			String addr = sockets.get(i).getName();
			if (addr.equals(strname)) {
				System.out.println("arrr:" + addr);
				chatName = addr;
				continue;
			} else {
				chatName = strname;
			}

			System.out.println(i);
			PrintWriter out = new PrintWriter(sockets.get(i).getSocket()
					.getOutputStream());
			out.println(Util.getJSonStr(type, str, chatName));
			out.flush();

		}
	}

	/**
	 * 私聊信息转发
	 * 
	 * @param type
	 *            3 私聊
	 * @param str
	 *            聊天内容
	 * @param personalName
	 *            被私聊者姓名
	 * @param strname主动者姓名
	 * @throws IOException
	 */
	private static void sendPersonalMessage(String type, String str,
			String personalName, String strname) throws IOException {
		for (int i = 0; i < sockets.size(); i++) {
			String addr = sockets.get(i).getName();
			if (addr.equals(personalName)) {
				System.out.println("personalName:" + addr);
				PrintWriter out = new PrintWriter(sockets.get(i).getSocket()
						.getOutputStream());
				out.println(Util.getPersonalJson(type, str, strname,
						personalName));
				out.flush();

				break;
			}
		}
	}
}
