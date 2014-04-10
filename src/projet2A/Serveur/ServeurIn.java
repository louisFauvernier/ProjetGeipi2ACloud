/**
 * Classe ServeurIn
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import projet2A.commonFiles.Constantes;
import projet2A.commonFiles.Fichier;

public class ServeurIn extends Thread {
	private int Port;
	private ServerSocket socket;
	private Socket s;
	private ServeurOut sout;
	private ObjectInputStream in;
	private String user = "";
	
	public ServeurIn(int port){
		this.Port = port;
	}
	
	public void run(){
		try {
			Main.log.INFO("projet2A.Serveur.ServeurIn.java:run:37", "Lancement du serveur");
			socket = new ServerSocket(this.Port);
		} catch (IOException e) {
			StackTraceElement s = e.getStackTrace()[e.getStackTrace().length-1];
			Main.log.FATAL(s.getFileName()+":"+s.getMethodName()+":"+s.getLineNumber() , e.getMessage());
			System.exit(0);
		}
		while(true){
			Main.log.INFO("projet2A.Serveur.ServeurIn.java:run:45", "En attente d'un client");
			try {
				s = socket.accept();
				Main.log.INFO("projet2A.Serveur.ServeurIn.java:run:48", "Client Connecté");
				sout = new ServeurOut(s.getInetAddress().getHostAddress(),8081);
				Object message_distant = "";
				in = new ObjectInputStream(s.getInputStream());
				while(!(message_distant = connected()).equals(Constantes.CLIENT_CLOSECONNEXION)){
					traitementDemande(message_distant);
				}
				sout.sendState(Constantes.CLIENT_CLOSECONNEXION);
				this.user = "";
				in.close();
				s.close();
				Main.log.INFO("projet2A.Serveur.ServeurIn.java:run:59", "Client déconnecté");
			} catch (IOException e) {
				StackTraceElement s = e.getStackTrace()[e.getStackTrace().length-1];
				Main.log.FATAL("projet2A.Serveur.ServeurIn.java:run:" + s.getLineNumber(), e.getMessage());
				this.user = "";
			}
		}
	}
	
	/**
	 * Fonction de test pour savoir si le client est toujours connecté
	 * 
	 * @return message envoyé par le client ou si perte de connexion, demande de fermeture de connexion
	 * @throws IOException
	 */
	public Object connected(){
		try{
			Object message = in.readObject();
			if(message == null){ // Si message == null, alors perte de connexion avec le client
				Main.log.ERROR("projet2A.Serveur.Main.ServeurIn.java:connected:70", "Perte de connexion avec le client");
				return Constantes.CLIENT_CLOSECONNEXION;
			}
			else
				return message; //Sinon on retourne le message envoyé par le client pour le traiter	
		}
		catch (Exception e){
			return Constantes.CLIENT_CLOSECONNEXION;
		}
	}
	
	/**
	 * Fonction qui traite la demande envoyée par le client
	 * @param msg
	 */
	public void traitementDemande(Object msg){
		if(msg.getClass().equals(Constantes.CLIENT_LOGIN.getClass())){
			if(msg.equals(Constantes.CLIENT_LOGIN) && this.user.equals("")){
				sout.sendState(Constantes.CLIENT_ID);
			}
			else if(this.user == ""){
				sout.sendState(Constantes.LOGIN_REQUIRED);
			}
			else if(msg.equals(Constantes.CLIENT_SYNC)){
				rcvFileList();
			}
			else if(msg.equals(Constantes.CLIENT_LOGOUT)){
				this.user = "";
				sout.sendState(Constantes.USER_SUCCESS_LOGOUT);
			}
			else{
				System.out.println("Client envoie " + msg);
			}
		}
		else{
			String message = (String) msg;
			if(message.startsWith("id")){
				String tmp[] = message.split(" ");
				int i = Main.estInscrit(tmp[1]);
				if(i == -1 || !Main.listeUsers.get(i).getPassword().equals(tmp[2])){
					sout.sendState(Constantes.USER_FAILED_LOGIN);					
				}
				else{
					sout.sendState(Constantes.USER_SUCCESS_LOGIN);
					this.user = tmp[1];
				}
			}
			else if(message.startsWith("create user")){
				System.out.println("Client (" + s.getInetAddress() + ") demande : créer utilisateur");
				String[] tmp = message.split(" ");
				if(Main.addNewUser(tmp[2], tmp[3])==0)
					sout.sendState(Constantes.USER_SUCCESS_CREATE);
				else
					sout.sendState(Constantes.USER_FAILED_CREATE);
			}
			else{
				System.out.println("Client envoie " + msg);
			}
		}
	}
	
	/**
	 * Fonction de reception d'un fichier depuis un client
	 * 
	 * @return : Objet Fichier 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Fichier rcvFile() throws IOException, ClassNotFoundException{
		Object objetRecu = in.readObject();
		Fichier f = (Fichier) objetRecu;
		return f;
	}
	
	/**
	 * Fonction de sauvegarde d'un fichier serialize sur le disque
	 * 
	 * @param f : Fichier Serialize à enregistrer sur le disque
	 * @throws IOException 
	 */
	public void saveSerializedFile(Fichier f) throws IOException{
		Main.log.INFO("projet2A.Serveur.ServeurIn.java:saveSerializedFile:162", "Sauvegarde du fichier");
		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(new File("fileserv/UsersFiles/" + this.user +"/"+f.getName()+".ser")));
		System.out.println(f.getTaille() + " " + f.getContenu().length);
		oos.writeObject(f);
		oos.flush();
		oos.close();
		int m;
		if((m = getIndex("fileserv/UsersFiles/"+ this.user + "/" + f.getName() + ".ser"))!= -1){
			Main.listeFiles.remove(m);
		}
		Main.listeFiles.add("fileserv/UsersFiles/"+ this.user + "/" + f.getName() + ".ser");
		Main.saveFiles();
	}
	
	@SuppressWarnings("unchecked")
	public void rcvFileList(){
		int index;
		try {
			sout.sendState(Constantes.FILE_SEND_LIST);
			Object objetRecu = in.readObject();
			HashMap<String, Fichier> listeFile = (HashMap<String, Fichier>) objetRecu;
			for(Iterator<String> ii = listeFile.keySet().iterator(); ii.hasNext();) {
				String key = (String)ii.next();
				Fichier f = listeFile.get(key);
				System.out.println("Lecture du cache client -> " + f.getName() + " version:" + f.getVersion());
				index = getIndex("fileserv/UsersFiles/"+ this.user + "/" + f.getName() + ".ser");
				if(index == -1){
					System.out.println("Fichier non existant");
					sout.sendMessage("send " + f.getName());
					saveSerializedFile(rcvFile());
				}
				else{
					Fichier temp = DeserializeFichier(new File("fileserv/UsersFiles/"+ this.user + "/" + f.getName() + ".ser"));
					if(f.getVersion() > temp.getVersion()){
						System.out.println("Version client plus récente");
						sout.sendMessage("send " + f.getName());
						saveSerializedFile(rcvFile());
					}
					else if(f.getVersion() == temp.getVersion())
						System.out.println("Version client identique");
					else{
						System.out.println("Version serveur plus récente");
						sout.sendMessage("receive " + temp.getName());
						sout.sendFile(temp);
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			Main.log.ERROR("projet2A.Serveur.ServeurIn.java:rcvFileList:224", e.getMessage());
		}
		sout.sendState(Constantes.CLIENT_SYNC_OK);
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