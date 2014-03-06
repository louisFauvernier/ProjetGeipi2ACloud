/**
 * Classe ServeurOut
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.net.Socket;

public class ServeurOut extends Thread {
	private String Name;
	private int Port;
	private Socket s;
	
	public ServeurOut(String name, int port){
		this.Name = name;
		this.Port = port;
	}
	
	public void run(){
		// TODO
	}
	
	/**
	 * Fonction de chargement d'un fichier sérializé depuis le disque
	 * 
	 * @param name : Nom du fichier à charger
	 */
	public void loadSerializedFile(String name){
		// TODO
	}
	
	/**
	 * Fonction d'envoie du fichier au client
	 */
	public void sendFile(){
		// TODO
	}
	
	/**
	 * Fonction d"envoie de message au client
	 * 
	 * @param message : Message à envoyer
	 */
	public void sendMessage(String message){
		// TODO
	}

}
