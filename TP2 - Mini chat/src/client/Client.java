package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException {
		Socket s = new Socket("127.0.0.1", 8080);
		new ClientListening(s).start();
		String message = null;
		while(true) {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
			message = buffer.readLine();
			if(message != null) {
				PrintWriter srv = new PrintWriter(s.getOutputStream(), true);
				srv.println(message);
				if("QUIT".equals(message)) {
					break;
				}
			}
		}
		s.close();
	}
}
