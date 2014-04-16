package clients;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;


public class SubThread extends Thread {
	
	public static String random() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i<40; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		return output;
	}
	
	public static int randomPort() {
		Random random = new Random();
		return random.nextInt(45000)+5000;
	}
	
	public void run() {
		while(true) {
			Socket s = null;
			try {
				//s = new Socket("volvic", 50001);
				s = new Socket("127.0.0.1", 8888);
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				return;
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				return;
			}
			PrintWriter srv = null;
			try {
				srv = new PrintWriter(s.getOutputStream(), true);
				srv.println("BIND "+random()+" "+randomPort());
				//System.out.println("BIND "+random()+" "+randomPort());
			} catch(IOException e) {
				e.printStackTrace();
				return;
			}
			String message = null;
			try {
				BufferedReader buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));
				message = buffer.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(message != null) {
				System.out.println(message);
			}
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}