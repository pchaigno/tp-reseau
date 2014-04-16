package server;

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
		if(message!=null) {
			PrintWriter srv = null;
			try {
				srv = new PrintWriter(this.clientSocket.getOutputStream(), true);
			} catch(IOException e) {
				e.printStackTrace();
				return;
			}
			
			Matcher m = Pattern.compile("CALL (\\w+)( (\\w+))*").matcher(message);
			if(m.find()) {
				String method = m.group(1);
			    switch(method) {
				    case "test":
				    	srv.println("RPLY "+method+" "+Login.test());
				    	break;
				    case "login":
				    	String name = m.group(3);
				    	srv.println("RPLY "+method+" "+Login.login(name));
				    	break;
				    default:
				    	srv.println("NONE");
			    }
			}
		}
	}
}
