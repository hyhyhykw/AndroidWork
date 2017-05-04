package com.it.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receive {
	public static void main(String[] args) throws IOException {
		DatagramSocket recrive = new DatagramSocket(6666);
		byte[] bs = new byte[1024];
//		InetAddress address =InetAddress.getLocalHost();
//		byte []bs2=address.getAddress();
//		for (byte b : bs2) {
//			System.out.println(b);
//		}
		DatagramPacket packet = new DatagramPacket(bs, 0, bs.length);
		int len = packet.getLength();
		recrive.receive(packet);
		System.out.println(new String(bs, 0, len));

		recrive.close();
	}
}
