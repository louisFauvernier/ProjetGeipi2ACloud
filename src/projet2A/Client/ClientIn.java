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

import projet2A.commonFiles.Fichier;

public class ClientIn extends Thread {
	private ServerSocket socket;
	private Socket s;
	private BufferedReader in;
	private String message_distant = "";
	
	public ClientIn(int i){
		try {
			socket = new ServerSocket(i);
			s = socket.accept();
		} catch (IOException e) {
			System.out.println("[!] FATAL  : " + e.getMessage());
			System.exit(0);
		}
		try {
			in = new BufferedReader (new InputStreamReader (s.getInputStream()));
		} catch (IOException e) {
			System.out.println("[-] Erreur : ");
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			while(!(message_distant = connected()).equals("close_connexion")){
				traitementDemande(message_distant);
			}
			s.close();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("[-] Erreur : ");
			e.printStackTrace();
			try {
				s.close();
			} catch (IOException e1) {
				System.out.println("[-] Erreur : ");
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Fonction de test pour savoir si le client est toujours connecté
	 * 
	 * @return message envoyé par le client ou si perte de connexion, demande de fermeture de connexion
	 * @throws IOException
	 */
	public String connected() throws IOException{
		String message = in.readLine();
		if(message == null){ // Si message == null, alors perte de connexion avec le client
			System.out.println("[-] ERREUR : Perte de connexion avec le serveur");
			return "close_connexion";
		}
		else
			return message; //Sinon on retourne le message envoyé par le client pour le traiter
	}
	
	public void traitementDemande(String message_distant){
		if(message_distant.equals("send")){
			try {
				Main.cout.send();
			} catch (IOException e) {
				System.out.println("[-] ERREUR : " + e.getMessage());
			}
		}
		if(message_distant.equals("sync_ok")){
			Main.cout.close();
			System.out.println("Synchronisé");
			System.exit(0);
		}
		System.out.println("Message du serveur : " + message_distant);
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
