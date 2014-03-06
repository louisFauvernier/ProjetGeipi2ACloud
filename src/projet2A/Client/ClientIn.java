/**
 * Classe ClientIn
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */


package projet2A.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import projet2A.commonFiles.Fichier;

public class ClientIn extends Thread {
	private Socket socket;
	private BufferedReader in;
	
	public ClientIn(String serverIp, int i) throws UnknownHostException, IOException{
		socket = new Socket(serverIp, i);
		in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
	}
	
	public void run(){
		// TODO
	}
	
	/**
	 * Fonction de reception d'un fichier depuis le serveur
	 * @return
	 */
	public Fichier rcvFichier(){
		// TODO
		return null;
	}
	
	/**
	 * Fonction d'enregistrement du fichier sur l'ordinateur (local)
	 * @return
	 */
	public void saveFile(){
		// TODO
	}
}
