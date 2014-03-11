package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientListening extends Thread {
	private Socket s;
	
	public ClientListening(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		try {
			while(!s.isClosed()) {
				BufferedReader buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String message = buffer.readLine();
				if(message != null) {
					String[] infos = message.split(" SAYS ");
					if(infos.length == 2) {
						System.out.println(infos[0]+": "+infos[1]);
					}
				}
			}
		} catch (SocketException e) {
			System.out.println("Closing socket!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
