/**
 * Classe ServeurIn
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import projet2A.commonFiles.Fichier;

public class ServeurIn extends Thread {
	private int Port;
	private ServerSocket socket;
	private Socket s;
	private ServeurOut sout;
	private BufferedReader in;
	
	public ServeurIn(int port){
		this.Port = port;
	}
	
	public void run(){
		try {
			System.out.println("[+] INFO : Lancement du serveur");
			socket = new ServerSocket(this.Port);
		} catch (IOException e) {
			System.out.print("[!] FATAL  : " + e.getMessage());
			System.exit(0);
		}
		while(true){
			try {
				s = socket.accept();
				System.out.println("[+] INFO : Client Connecté");
				sout = new ServeurOut(s.getInetAddress().getHostAddress(),8081);
				String message_distant = "";
				in = new BufferedReader (new InputStreamReader (s.getInputStream()));
				while(!(message_distant = connected()).equals("close_connexion")){
					traitementDemande(message_distant);
				}
				s.close();
				System.out.println("[+] INFO : Client déconnecté");
			} catch (IOException e) {
				System.out.print("[-] ERREUR : ");
				e.printStackTrace();
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
			System.out.println("[-] ERREUR : Perte de connexion avec le client");
			return "close_connexion";
		}
		else
			return message; //Sinon on retourne le message envoyé par le client pour le traiter
	}
	/**
	 * Fonction qui traite la demande envoyée par le client
	 * @param msg
	 */
	public void traitementDemande(String msg){
		System.out.println("Client (" + s.getInetAddress() + ") demande : " + msg);
		sout.sendMessage("ok");
	}
	
	/**
	 * Fonction de reception d'un fichier depuis un client
	 * 
	 * @return : Objet Fichier 
	 */
	public Fichier rcvFile(){
		// TODO
		return null;
	}
	
	/**
	 * Fonction de sauvegarde d'un fichier serialize sur le disque
	 * 
	 * @param f : Fichier Serialize à enregistrer sur le disque
	 */
	public void saveSerializedFile(Fichier f){
		// TODO
	}
}
