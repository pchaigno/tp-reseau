package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	public static final int PORT = 8080;
	private List<Socket> connections;
	
	public Server() {
		this.connections = new ArrayList<Socket>();
	}
	
	public void removeConnection(Socket s) {
		this.connections.remove(s);
	}

	public List<Socket> getConnection() {
		return this.connections;
	}
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		ServerSocket socket = new ServerSocket(PORT);
		while(true) {
			Socket s;
			s = socket.accept();
			server.connections.add(s);
			new MessageThread(server, s).start();
		}
	}
}
