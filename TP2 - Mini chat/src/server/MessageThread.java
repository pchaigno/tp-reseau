package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageThread extends Thread {
	private Server server;
	private Socket clientSocket;
	private String name;
	private boolean running;
	
	public MessageThread(Server server, Socket clientSocket) {
		this.server = server;
		this.clientSocket = clientSocket;
		this.name = null;
		this.running = true;
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
		if(message!=null && message.startsWith("HELO ")) {
			this.name = message.substring(5);
			System.out.println(this.name+" joins");
			message = null;
			
			while(this.running) {
				try {
					BufferedReader buffer = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
					message = buffer.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				if(message != null) {
					if(message.startsWith("MESS ")) {
						message = message.substring(5);
						System.out.println("Message received: "+message);
						for(Socket s: this.server.getConnection()) {
							if(!this.clientSocket.equals(s)) {
								try {
									PrintWriter srv = new PrintWriter(s.getOutputStream(), true);
									srv.println(this.name+" SAYS "+message);
								} catch(IOException e) {
									e.printStackTrace();
								}
							}
						}
					} else if(message.equals("QUIT")) {
						System.out.println(this.name+" quits");
						this.running = false;
					}
				}
			}
		}
	}
}
