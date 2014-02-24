package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

class Server {
	
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(1234);
		while(true) {
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String message = new String(receivePacket.getData());
			System.out.println("Received: /"+message+"/");
			
			InetAddress ipAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String date = new Date().toString();
			byte[] sendData = date.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
			serverSocket.send(sendPacket);
		}
	}
}