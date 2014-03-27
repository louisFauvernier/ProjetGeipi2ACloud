/**
 * Classe ServeurOut : Communication vers le client
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServeurOut{
	private Socket s;
	private PrintWriter out;

	public ServeurOut(String adresse, int port) {
		try{
			s = new Socket(adresse, port);
			out = new PrintWriter(s.getOutputStream());
		} catch (UnknownHostException e){
			Main.log.ERROR(
					"projet2A.Serveur.Main.ServeurOut.java:ServeurOut:20",
					e.getMessage());
		} catch (IOException e){
			Main.log.ERROR(
					"projet2A.Serveur.Main.ServeurOut.java:ServeurOut:21",
					e.getMessage());
		}
	}

	/**
	 * Fonction de chargement d'un fichier sérializé depuis le disque
	 * 
	 * @param name
	 *            : Nom du fichier à charger
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
	 * @param message
	 *            : Message à envoyer
	 */
	public void sendMessage(String message){
		out.println(message);
		out.flush();
	}
}
