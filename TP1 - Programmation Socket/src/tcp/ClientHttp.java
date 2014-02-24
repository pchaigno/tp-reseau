package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientHttp {
	
	public static String getPage(String page) throws IOException {
		Socket s = new Socket("127.0.0.1", 8888);
		PrintWriter srv = new PrintWriter(s.getOutputStream(), true);
		srv.println("GET "+page+" HTTP/1.1");
		
		BufferedReader clientInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String pageHTML = "";
		String line;
		while((line = clientInput.readLine()) != null) {
			pageHTML += line+"\n";
		}
		
		s.close();
		return pageHTML;
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		String pageHTML = "";
		for(int i=0; i<10; i++) {
			pageHTML = ClientHttp.getPage("/");
			//System.out.println(pageHTML);
		}
		//System.out.println("end");
		//System.out.println(pageHTML);
	}
}