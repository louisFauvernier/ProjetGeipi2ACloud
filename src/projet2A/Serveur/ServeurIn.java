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
			System.out.print("[-] ERREUR : ");
			e.printStackTrace();
		}
		while(true){
			try {
				s = socket.accept();
				System.out.println("[+] INFO : Client Connect�");
				sout = new ServeurOut(s.getInetAddress().getHostAddress(),8081);
				String message_distant = "";
				in = new BufferedReader (new InputStreamReader (s.getInputStream()));
				while(!(message_distant = connected()).equals("close_connexion")){
					traitementDemande(message_distant);
				}
				s.close();
				System.out.println("[+] INFO : Client d�connect�");
			} catch (IOException e) {
				System.out.print("[-] ERREUR : ");
				e.printStackTrace();
			}
		}
	}
	
	public String connected() throws IOException{
		String message = in.readLine();
		if(message == null)
			return "close_connexion";
		else
			return message;
	}
	/**
	 * Fonction qui traite la demande du client
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
	 * @param f : Fichier Serialize � enregistrer sur le disque
	 */
	public void saveSerializedFile(Fichier f){
		// TODO
	}
}
