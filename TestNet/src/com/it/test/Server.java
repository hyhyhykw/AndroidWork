package com.it.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8888);
		Socket serverSocket = server.accept();
		
		InputStream is = serverSocket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		Service service = new Service(serverSocket);
		service.start();
		boolean flag = false;
		while (!flag) {
			System.out.println(br.readLine());
		}
		server.close();
	}
}
