/**
 * Classe ClientOut
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import projet2A.commonFiles.Constantes;
import projet2A.commonFiles.Fichier;

public class ClientOut{
	private Socket socket;
	private int port = 8000;
	private String adresse;
	private ObjectOutputStream out;
	
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
	
	public int connect(){
		int nbtest = 5;
		while(nbtest>0){ //Tentative de 5 connexions au maximum
			try {
				socket = new Socket(adresse, port);
				System.out.println("[+] INFO : Connecté au serveur");
				return 1;
			} catch (IOException e) {
				if(nbtest==1){
					if(socket == null){
						System.out.println("[!] FATAL  : Impossible d'établir la connexion au serveur " + this.adresse); //Echec des 5 tentatives, arrêt du programme;
						return 0;
					}
					else{
						System.out.println("[-] ERREUR : " + e.getMessage() + ", Nouvel essai dans 5 secondes...");
					}
				}
				try {
					Thread.sleep(5000); //Pause de 5 secondes avant nouvel essai
					nbtest--;
				} catch (InterruptedException e1) {
					System.out.println("[!] FATAL  : " + e1.getMessage());
					System.exit(0);
				}
			}
		}
	return 0;
	}
	
	public void close(){
		try{
			out.writeObject(Constantes.CLIENT_CLOSECONNEXION);
			out.flush();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Envoie du fichier f au serveur
	 * @param f
	 */
	public void sendFile(Fichier f){
		try{
			System.out.println("Send " + f.getName() + " (" + f.getTaille() + "/" + f.getContenu().length + ")");
			out.writeObject(f);
			out.flush();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	public String[] listeFichiers(String repertoire){
		return new File(repertoire).list();
	}
	
	public void send() throws IOException{
		out.writeObject(Main.listeFile);
	    out.flush();
	}
	
	public void sendMessage(String message){
		try{
			out.writeObject(new String(message));
			out.flush();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	public void sendState(Integer message){
		try{
			out.writeObject(message);
			out.flush();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	public void createUser(String name, String pass){
		try{
			out.writeBytes("create user " + name + " " + pass);
			out.flush();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}	
	}
}
