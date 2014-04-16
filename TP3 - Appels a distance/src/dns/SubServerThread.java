package dns;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubServerThread extends Thread {
	private Socket clientSocket;
	
	public SubServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		String message = null;
		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			message = buffer.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		PrintWriter srv = null;
		try {
			srv = new PrintWriter(this.clientSocket.getOutputStream(), true);
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}
			
		if(message!=null) {
			Matcher m = Pattern.compile("LOOK (\\w+)").matcher(message);
			if(m.find()) {
				Server server = DnsServer.servers.get(m.group(1));
				if(server == null) {
					srv.println("NOSV");
				} else {
					srv.println("KOOL "+server.ip+" "+server.port);
				}
			} else {
				
				
				
				
				m = Pattern.compile("BIND (\\w+) (\\d+)").matcher(message);
				if(m.find()) {
					String ip = this.clientSocket.getInetAddress().getHostAddress();
					int port = Integer.parseInt(m.group(2));
					String name = m.group(1);
					if(DnsServer.servers.containsKey(name)) {
						srv.println("USED");
					} else {
						DnsServer.servers.put(name, new Server(port, ip));
						srv.println("OKBD");
					}
				} else {
					
					
					
					m = Pattern.compile("UNBD").matcher(message);
					if(m.find()) {
						String ip = this.clientSocket.getInetAddress().getHostAddress();
						if(DnsServer.removeByIP(ip)) {
							srv.println("OKUB");
						} else {
							srv.println("NOBD");
						}
					}
				}
			}
		}
	}
}
