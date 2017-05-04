package com.it.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Service extends Thread {
	Socket socket;

	public Service(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		OutputStream os = null;
		PrintStream ps = null;
		InputStream is = System.in;
		BufferedReader br = null;

		try {
			os = socket.getOutputStream();
			ps = new PrintStream(os);
			br = new BufferedReader(new InputStreamReader(is));
			while (true) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				ps.println(InetAddress.getLocalHost().getHostName() + ":"
						+ br.readLine() + "\t\t" + format.format(new Date()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}
}
