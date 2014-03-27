/**
 * Classe ServeurIn
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

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
			Main.log.INFO("projet2A.Serveur.ServeurIn.java:run:35", "Lancement du serveur");
			socket = new ServerSocket(this.Port);
		} catch (IOException e) {
			StackTraceElement s = e.getStackTrace()[e.getStackTrace().length-1];
			Main.log.FATAL(s.getFileName()+":"+s.getMethodName()+":"+s.getLineNumber() , e.getMessage());
			System.exit(0);
		}
		while(true){
			Main.log.INFO("projet2A.Serveur.ServeurIn.java:run:42", "En attente d'un client");
			try {
				s = socket.accept();
				Main.log.INFO("projet2A.Serveur.ServeurIn.java:run:46", "Client Connecté");
				sout = new ServeurOut(s.getInetAddress().getHostAddress(),8081);
				String message_distant = "";
				in = new BufferedReader (new InputStreamReader (s.getInputStream()));
				while(!(message_distant = connected()).equals("close_connexion")){
					traitementDemande(message_distant);
				}
				sout.sendMessage("close_connexion");
				s.close();
				Main.log.INFO("projet2A.Serveur.ServeurIn.java:run:55", "Client déconnecté");
			} catch (IOException e) {
				StackTraceElement s = e.getStackTrace()[e.getStackTrace().length-1];
				Main.log.FATAL(s.getFileName() + ":" + s.getMethodName() + ":"
						+ s.getLineNumber(), e.getMessage());

			}
		}
	}
	
	/**
	 * Fonction de test pour savoir si le client est toujours connecté
	 * 
	 * @return message envoyé par le client ou si perte de connexion, demande de fermeture de connexion
	 * @throws IOException
	 */
	public String connected(){
		try{
			String message = in.readLine();
			if(message == null){ // Si message == null, alors perte de connexion avec le client
				Main.log.ERROR("projet2A.Serveur.Main.ServeurIn.java:connected:70", "Perte de connexion avec le client");
				return "close_connexion";
			}
			else
				return message; //Sinon on retourne le message envoyé par le client pour le traiter	
		}
		catch (Exception e){
			return "close_connexion";
		}
	}
	
	/**
	 * Fonction qui traite la demande envoyée par le client
	 * @param msg
	 */
	public void traitementDemande(String msg){
		if(msg.equals("sync")){
			System.out.println("Client (" + s.getInetAddress() + ") demande : " + msg);
			sout.sendMessage("send");
			rcvFileList();
		}
		if(msg.startsWith("create user")){
			System.out.println("Client (" + s.getInetAddress() + ") demande : créer utilisateur");
			String[] tmp = msg.split(" ");
			if(Main.addNewUser(tmp[2], tmp[3])==0)
				sout.sendMessage("user created");
			else
				sout.sendMessage("user already exist");
		}
		else if (!msg.equals("nothing")){
			System.out.println("Client (" + s.getInetAddress() + ") demande : " + msg);
			sout.sendMessage("ok");
		}
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
	
	@SuppressWarnings("unchecked")
	public void rcvFileList(){
		int index;
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(s.getInputStream());
			Object objetRecu = in.readObject();
			HashMap<String, Fichier> listeFile = (HashMap<String, Fichier>) objetRecu;
			in.close();
			for(Iterator<String> ii = listeFile.keySet().iterator(); ii.hasNext();) {
				String key = (String)ii.next();
				Fichier f = listeFile.get(key);
				System.out.println("Lecture du cache client -> " + f.getName() + " version:" + f.getVersion());
				index = getIndex("fileserv/filesave/" + f.getName() + ".ser");
				if(index == -1){
					System.out.println("Fichier non existant");
					sout.sendMessage("send " + f.getName());
				}
				else{
					Fichier temp = DeserializeFichier(new File("fileserv/filesave/" + f.getName() + ".ser"));
					if(f.getVersion() > temp.getVersion()){
						System.out.println("Version client plus récente");
						sout.sendMessage("send " + f.getName());
					}
					else if(f.getVersion() == temp.getVersion()){
						System.out.println("Version client identique");
					}
					else{
						System.out.println("Version serveur plus récente");
						sout.sendMessage("retrieve " + f.getName());
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			Main.log.ERROR("projet2A.Serveur.ServeurIn.java:rcvFileList:152", e.getMessage());
		}
		sout.sendMessage("synch_ok");
	}
	
	public int getIndex(String str){
		for(int i=0;i<Main.listeFiles.size();i++){
			if(Main.listeFiles.get(i).equals(str)){
				return i;
			}
		}
		return -1;
	}
	
	public static Fichier DeserializeFichier(File file) throws IOException, ClassNotFoundException{
		ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file));
		Fichier out = (Fichier)ois.readObject();		
        ois.close();
        return out;
	}
}
