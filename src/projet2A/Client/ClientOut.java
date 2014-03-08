/**
 * Classe ClientOut
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import projet2A.commonFiles.Fichier;

public class ClientOut extends Thread{
	private Socket socket;
	private int i = 8080;
	private String name;
	
	public ClientOut(String name) {
		this.name = name;
	}
	public ClientOut(int port, String name) {
		this.i = port;
		this.name = name;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	
	public void run(){
		int nbtest = 5;
		while(nbtest>0){ //Tentative de 5 connexions au maximum
			try {
				socket = new Socket(name, i);
				System.out.println("[+] INFO : Connecté au serveur");
				break;
			} catch (IOException e) {
				System.out.println("[-] ERREUR : " + e.getMessage() + ", Nouvel essai dans 5 secondes...");
				try {
					sleep(5000); //Pause de 5 secondes avant nouvel essais
					nbtest--;
				} catch (InterruptedException e1) {
					System.out.println("[!] FATAL  : " + e1.getMessage());
					System.exit(0);
				}
			}
		}
		if(socket == null){
			System.out.println("[!] FATAL  : Impossible d'établir la connexion au serveur"); //Echec des 5 tentatives, arrêt du programme;
			System.exit(0);
		}					
		try{
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println("Test");
			out.flush();
			sleep(1000);
			out.println("hallo");
			out.flush();
			sleep(1000);
			out.println("close_connexion");
			out.flush();
			out.close();
			socket.close();
			System.out.println("[+] INFO : Déconnecté du serveur");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("[!] FATAL  : " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Création d'un objet fichier (uniquement pour un nouveau fichier)
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
}
