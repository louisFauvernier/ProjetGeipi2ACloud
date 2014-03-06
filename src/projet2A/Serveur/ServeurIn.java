/**
 * Classe ServeurIn
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

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
		// TODO
	}
	
	/**
	 * Fonction de réception d'un fichier depuis un client
	 * 
	 * @return : Objet Fichier 
	 */
	public Fichier rcvFile(){
		// TODO
		return null;
	}
	
	/**
	 * Fonction de sauvegarde d'un fichier sérializé sur le disque
	 * 
	 * @param f : Fichier Sérializé à enregistrer sur le disque
	 */
	public void saveSerializedFile(Fichier f){
		// TODO
	}
}
