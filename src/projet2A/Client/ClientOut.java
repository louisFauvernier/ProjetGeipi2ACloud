/**
 * Classe ClientOut
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */


package projet2A.Client;

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
		// TODO
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
