package tcp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket s;
	
	public ServerThread(Socket s) {
		this.s = s;
	}
	
	@Override
	public void run() {
		long startTimeProcess;
		PrintWriter out = null;
		BufferedReader in = null;
		
		// Journalisation
		System.out.println("["+ServeurHttp.date()+"] Connexion :"
				+s.getInetAddress().getHostName()
				+":"+s.getPort()+" ("
				+s.getInetAddress().getHostAddress()+") ");
		
		// Stats
		startTimeProcess = System.currentTimeMillis();
		
		// Traitement de la requ�te
		try {
			out = new PrintWriter(s.getOutputStream());
			//		out.println(entete); out.flush();
		} catch (IOException e) {
			ServeurHttp.erreur("Ecriture socket "+e);
		}

		try {
			in = new BufferedReader(new	InputStreamReader(s.getInputStream()));   
		} catch (IOException e) {
			ServeurHttp.erreur("Lecture socket "+e);
		}
		
		String requete = null;
		File fichier = null;

		try {
			requete = in.readLine();
		} catch (IOException e) {
			ServeurHttp.erreur("lecture "+e);
		}
		System.out.println(requete);
		// d�coupage HTTP 1.0
		// GET url HTTP/1.0
		String reqHTTP[] = requete.split("\\s");
		// En premi�re approximation, on ne regarde que le 2�me �lt
		String reponse = null;	
		if (reqHTTP.length != 3) {
			reponse = ServeurHttp.erreur400();
			out.println(reponse);
			out.flush();
			//  Rajouter statistique erreur 400
		} else {
			// Default is index.html
			if(reqHTTP[1].equals("/")) {
				reqHTTP[1] = "/index.html";
			}
			fichier = new File(ServeurHttp.racine+reqHTTP[1]);
			if (!fichier.exists()) {
				reponse =  "HTTP/1.0 404 Not Found\n";
				reponse += "Date: "+ServeurHttp.date()+"\n";
				reponse += "Server: "+ServeurHttp.nomServeur+"\n"; 
				reponse += "Content-type: text/html\n\n";
				reponse += "<HEAD><TITLE>Fichier Non Trouv�</TITLE></HEAD>\n\n";
				reponse += "<BODY><H1>Fichier Non Trouv�</H1>\n";
				reponse += "La ressource <i>"+reqHTTP[1]+"</i> n'est pas pr�sente sur ce serveur.<P>\n\n</BODY>\n";
				// R�ponse au client
				out.println(reponse);
				out.flush();
				//  Rajouter statistique erreur 404
				
			} else {
				//  Rajouter statistique acc�s � ce fichier reqHTTP[1]
				reponse = "HTTP/1.0 200 OK\r\n";
				reponse += "Date: "+ServeurHttp.date()+"\r\n";
				reponse += "Server: "+ServeurHttp.nomServeur+"\r\n";
				// D�terminer le type mime d'apr�s l'extension
				reponse += "Content-type: "+ServeurHttp.typeMime(reqHTTP[1])+" \r\n\r\n";
				// R�ponse (partielle) au client
				out.println(reponse); out.flush();
				// Transfert du fichier (�ventuellement binaire)
				FileInputStream f = null;
				try {
					f = new FileInputStream(fichier);
				} catch(IOException e) { 
					ServeurHttp.erreur("lecture ressource 1"+e); 
					/*try {
						s.close();
					} catch (IOException e2) { 
						System.err.println("Impossible fermer la socket"+e2);
					}
					continue;*/
				}
				int lu = -1;

				try {
					lu = f.read();
				} catch (IOException e) { 
					ServeurHttp.erreur("lecture ressource 2 "+e);
					/*try {
						s.close();
					} catch (IOException e2) { 
						System.err.println("Impossible fermer la socket");
					}
					continue;*/
				}
				while (lu != -1) {
					out.write(lu);
					try {
						lu = f.read();
					} catch(IOException e) {
						ServeurHttp.erreur("lecture ressource 3 "+e);
					}
				}
				out.flush();
			}
		}
		
		try {
			s.close();
		} catch (IOException e) { 
			System.err.println("Impossible fermer la socket "+e);
		}

		// Compute execution time:
		ServeurHttp.addExecutionTime(System.currentTimeMillis() - startTimeProcess);
	}
}