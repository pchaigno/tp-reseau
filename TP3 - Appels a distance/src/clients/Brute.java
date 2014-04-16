package clients;

import java.io.IOException;

public class Brute {

	public static void main(String[] args) throws IOException, InterruptedException {
		for(int i=0; i<100; i++) {
			new SubThread().start();
		}
		Thread thread = new SubThread();
		thread.start();
		thread.join();
	}
}
