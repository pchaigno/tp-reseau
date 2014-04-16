package dns;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class DnsServer {
	public static final int PORT = 8888;
	public static Map<String, Server> servers = new HashMap<String, Server>();
	
	public static boolean removeByIP(String ip) {
		for(String name: servers.keySet()) {
			if(servers.get(name).ip.equals(ip)) {
				servers.remove(name);
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(PORT);
		while(true) {
			Socket s;
			s = socket.accept();
			new SubServerThread(s).start();
		}
	}
}
