/**
 * Classe ServeurOut : Communication vers le client
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import projet2A.commonFiles.Fichier;

public class ServeurOut{
	private Socket s;
	private ObjectOutputStream out;

	public ServeurOut(String adresse, int port) {
		try{
			s = new Socket(adresse, port);
			out = new ObjectOutputStream(s.getOutputStream());
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
	 * Fonction d'envoie du fichier au client
	 */
	public void sendFile(Fichier f){
		try{
			System.out.println("Send " + f.getName());
			out.writeObject(f);
			out.flush();
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Fonction d"envoie de message au client
	 * 
	 * @param message : Message à envoyer
	 */
	public void sendMessage(String message){
		try{
			out.writeObject(new String(message));
			out.flush();
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendState(Integer message){
		try{
			out.writeObject(message);
			out.flush();
		} catch (IOException e){
			Main.log.ERROR("projet2A.Serveur.Main.ServeurOut.java:sendState:70", e.getMessage());
		}
	}
}
