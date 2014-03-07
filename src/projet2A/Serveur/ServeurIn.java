/**
 * Classe ServeurIn
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import projet2A.commonFiles.Fichier;

public class ServeurIn extends Thread {
	private String Name;
	private int Port;
	private ServerSocket socket;
	private Socket s;
	
	public ServeurIn(String name, int port){
		this.Name = name;
		this.Port = port;
	}
	
	public void run(){
		try {
			socket = new ServerSocket(this.Port);
		} catch (IOException e) {
			System.out.print("[-] ERREUR : ");
			e.printStackTrace();
		}
		while(true){
			try {
				s = socket.accept();
				System.out.println("Client Connecté");
				String message_distant = "";
				BufferedReader in = new BufferedReader (new InputStreamReader (s.getInputStream()));
				while(!(message_distant = in.readLine()).equals("close_connexion")){
					traitementDemande(message_distant);
				}
				s.close();
				System.out.println("Client déconnecté");
			} catch (IOException e) {
				System.out.print("[-] ERREUR : ");
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Fonction qui traite la demande du client
	 * @param msg
	 */
	public void traitementDemande(String msg){
		System.out.println("Client ask : " + msg);
	}
	
	/**
	 * Fonction de reception d'un fichier depuis un client
	 * 
	 * @return : Objet Fichier 
	 */
	public Fichier rcvFile(){
		// TODO
		return null;
	}
	
	/**
	 * Fonction de sauvegarde d'un fichier serialize sur le disque
	 * 
	 * @param f : Fichier Serialize à enregistrer sur le disque
	 */
	public void saveSerializedFile(Fichier f){
		// TODO
	}
}
