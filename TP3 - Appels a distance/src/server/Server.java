package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static final int PORT = 8080;
	
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(PORT);
		while(true) {
			Socket s;
			s = socket.accept();
			new SubServerThread(s).start();
		}
	}
}
