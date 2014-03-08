/**
 * Classe ClientIn
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import projet2A.commonFiles.Fichier;

public class ClientIn extends Thread {
	private ServerSocket socket;
	private Socket s;
	private BufferedReader in;
	private String message_distant = "";
	
	public ClientIn(int i) throws UnknownHostException, IOException{
		socket = new ServerSocket(i);
		s = socket.accept();
		in = new BufferedReader (new InputStreamReader (s.getInputStream()));
	}
	
	public void run(){
		while(true){
			try {
				message_distant = in.readLine();
				System.out.println("Message du serveur : " + message_distant);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}			
		}
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
