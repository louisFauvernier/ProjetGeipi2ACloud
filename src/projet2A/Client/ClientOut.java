/**
 * Classe ClientOut
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import projet2A.commonFiles.Fichier;

public class ClientOut extends Thread{
	private Socket socket;
	private int port = 8080;
	private String adresse;
	private PrintWriter out;
	
	public ClientOut(String adresse) {
		this.adresse = adresse;
	}
	public ClientOut(int port, String adresse) {
		this.port = port;
		this.adresse = adresse;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public int getPort() {
		return port;
	}
	public void setport(int port) {
		this.port = port;
	}
	
	public void run(){
		int nbtest = 5;
		while(nbtest>0){ //Tentative de 5 connexions au maximum
			try {
				socket = new Socket(adresse, port);
				System.out.println("[+] INFO : Connect� au serveur");
				break;
			} catch (IOException e) {
				if(nbtest==1){
					if(socket == null){
						System.out.println("[!] FATAL  : Impossible d'�tablir la connexion au serveur " + this.adresse); //Echec des 5 tentatives, arr�t du programme;
						System.exit(0);
					}
				}
				System.out.println("[-] ERREUR : " + e.getMessage() + ", Nouvel essai dans 5 secondes...");
				try {
					sleep(5000); //Pause de 5 secondes avant nouvel essai
					nbtest--;
				} catch (InterruptedException e1) {
					System.out.println("[!] FATAL  : " + e1.getMessage());
					System.exit(0);
				}
			}
		}
		try{
			out = new PrintWriter(socket.getOutputStream());
			out.println("sync");
			out.flush();
		} catch (IOException e) {
			System.out.println("[!] FATAL  : " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void close(){
		out.println("close_connexion");
		out.flush();
	}
	
	/**
	 * Cr�ation d'un objet fichier (uniquement pour un nouveau fichier)
	 * @param name Nom du fichier
	 * @return objet correspondant au fichier
	 */
	public Fichier newFile(String name){
		// TODO
		return null;
	}
	
	/**
	 * Envoie du fichier f au serveur
	 * @param f
	 */
	public void sendFile(Fichier f){
		// TODO
	}
	
	public String[] listeFichiers(String r�pertoire){
		return new File(r�pertoire).list();
	}
	
	public void send() throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(Main.listeFile);
	    out.flush();
	}
}
