package tcp;

import java.net.*; 
import java.io.*;
import java.util.*;

public class ServeurHttp {
	static String entete = "";
	static String racine = "www";  // sous r�pertoire racine du serveur
	static String nomServeur = "-- Serveur HTTP Java --";
	static String enteteReponse = "";
	static int port = 8888;  // port par d�faut
	static long totalProcessTime = 0;

	static synchronized void addExecutionTime(long processTime) {
		totalProcessTime += processTime;
	}
	
	static void message(String msg) {
		System.err.println(msg);
	}

	static void erreur(String msg) {
		message("Erreur: "+msg);
	}

	static void erreur() {
		erreur(null);
	}

	static String typeMime(String nom) {
		if (nom.matches(".*\\.html$")) {
			return "text/html";
		}
		if (nom.matches(".*\\.gz$")) {
			return "application/gzip";
		}
		return "text/plain";
	}

	static String erreur400() {
		String msg = null;
		msg += "HTTP/1.0 400 Bad Request\n";
		msg += "Date: "+date()+"\n";
		msg += "Server: "+entete+"\n";
		msg += "Content-type: text/html\n\n";
		msg += "<HEAD><TITLE>Mauvaise requ�te</TITLE></HEAD>\n";
		msg += "<BODY><H1>Mauvaise requ�te</H1>\n";
		msg += "Votre butineur a envoy� une requ�te que ce serveur ne peut pas (encore) traiter.<P>\n</BODY>\n";
		return msg;
	}

	static void usage() {
		message("Usage :\n java ServeurHttp [port]\n");
	}

	static String date() {
		// ou System.currentTimeMillis()
		Date d = new Date();
		return d.toString();
	}

	public static void main (String argv[]) throws IOException, InterruptedException {
		ServerSocket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		List<ServerThread> threads = new LinkedList<ServerThread>();

		if (argv.length == 1 ) {
			port = Integer.parseInt(argv[0]);
		} else if (argv.length >= 1) {
			usage();
		}

		// Cr�ation de la socket
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			erreur("Impossible d'ouvrir le port "+port+":"+e);
		}

		// Inits time and request count:
		int nbRequests = 0;
		
		while(nbRequests < 50) { // Attente de connexion
			Socket s = null;
			try {
				s=socket.accept();
			} catch (IOException e) {
				erreur("accept "+e);
			}
			nbRequests++;
			ServerThread thread = new ServerThread(s);
			threads.add(thread);
			thread.start();
			
		}
		
		for(ServerThread thread: threads) {
			thread.join();
		}
		
		System.out.println("Execution time: "+totalProcessTime);
	}
}