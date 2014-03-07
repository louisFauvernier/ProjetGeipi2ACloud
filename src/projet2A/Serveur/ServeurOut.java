/**
 * Classe ServeurOut : Communication vers le client
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServeurOut {
	private String Name;
	private int Port;
	private Socket s;
	private PrintWriter out;
	
	public ServeurOut(String name, int port){
		this.Name = name;
		this.Port = port;
		try {
			s = new Socket(name, port+1);
			out = new PrintWriter(s.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		out.println(message);
		out.flush();
	}
}
