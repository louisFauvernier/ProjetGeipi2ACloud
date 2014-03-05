/**
 * Classe Serveur
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.net.ServerSocket;
import java.net.Socket;

import projet2A.Fichier;

public class Serveur extends Thread {
	private String Name;
	private int Port;
	private ServerSocket socket;
	private Socket s;
	
	public Serveur(String name, int port){
		this.Name = name;
		this.Port = port;
	}
	
	public void run(){
		// TODO
	}
	
	/**
	 * Fonction de r�ception d'un fichier depuis un client
	 * 
	 * @return : Objet Fichier 
	 */
	public Fichier rcvFile(){
		// TODO
		return null;
	}
	
	/**
	 * Fonction de sauvegarde d'un fichier s�rializ� sur le disque
	 * 
	 * @param f : Fichier S�rializ� � enregistrer sur le disque
	 */
	public void saveSerializedFile(Fichier f){
		// TODO
	}
	
	/**
	 * Fonction de chargement d'un fichier s�rializ� depuis le disque
	 * 
	 * @param name : Nom du fichier � charger
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
	 * @param message : Message � envoyer
	 */
	public void sendMessage(String message){
		// TODO
	}

}
