package com.it.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket(InetAddress.getLocalHost(), 8888);
		boolean flag = false;
		Service service = new Service(client);
		service.start();
		InputStream is = client.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while (!flag) {
			System.out.println(br.readLine());
		}
		br.close();
		client.close();
	}
}
