/**
 * Classe principale du serveur
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
import java.util.ArrayList;

import projet2A.commonFiles.User;

public class Main{
	public static Logger log;
	static ServeurIn sin;
	static ArrayList<String> listeFiles = new ArrayList<String>();
	static ArrayList<User> listeUsers = new ArrayList<User>();
	
	public static void main(String[] args) {
		log = new Logger();
		loadFile();
		//listeUsers.add(new User("louis", "test"));
		//saveFiles();
		sin = new ServeurIn(8080);
		sin.start();
	}
	
	@SuppressWarnings("unchecked")
	public static void loadFile(){
		log.INFO("projet2A.Serveur.Main.java:loadFile:34", "Chargement des Utilisateurs sur le serveur");
		ObjectInputStream ois;
		try {
			File fichier =  new File("fileserv/listeUser.ser");
			ois = new ObjectInputStream(new FileInputStream(fichier));
			listeUsers = (ArrayList<User>)ois.readObject();
	        ois.close();
		} catch (IOException|ClassNotFoundException e) {
			log.ERROR("projet2A.Serveur.Main.java:loadFile:37", e.getMessage());
		}
		log.INFO("projet2A.Serveur.Main.java:loadFile:44", listeUsers.size() + " utilisateurs chargés");
		log.INFO("projet2A.Serveur.Main.java:loadFile:45", "Chargement des index de fichiers");
		try {
			File fichier =  new File("fileserv/listeFichier.ser");
			ois = new ObjectInputStream(new FileInputStream(fichier));
			listeFiles = (ArrayList<String>)ois.readObject();
	        ois.close();
		} catch (IOException|ClassNotFoundException e) {
			log.ERROR("projet2A.Serveur.Main.java:loadFile:48", e.getMessage());	
		}
		log.INFO("projet2A.Serveur.Main.java:loadFile:54", listeFiles.size() + " index chargés");
	}
	
	public static void saveFiles(){
		log.INFO("projet2A.Serveur.Main.java:saveFiles:58", "Sauvegarde des index fichiers du serveur");
		try {
			File fichier =  new File("fileserv/listeFichier.ser");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listeFiles);
			oos.close();
			log.INFO("projet2A.Serveur.Main.java:saveFiles:65", "Succès");
		} catch (IOException e) {
			log.ERROR("projet2A.Serveur.Main.java:saveFiles:63", e.getMessage());
		}
	}
	public static void saveUsers(){
		try {
			File fichier = new File("fileserv/listeUser.ser");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listeUsers);
			oos.close();
			log.INFO("projet2A.Serveur.Main.java:saveUsers:75", "Succès");
		} catch (IOException e) {
			log.ERROR("projet2A.Serveur.Main.java:saveUsers:73", e.getMessage());
		}
	}
	
	public static int addNewUser(String name, String pass){
		if(estInscrit(name)){
			return 1;
		}
		listeUsers.add(new User(name, pass));
		File dossier = new File("fileserv/UsersFiles/" + name);
		dossier.mkdir();
		saveUsers();
		return 0;
	}
	public static boolean estInscrit(String name){
		for(int i=0;i<listeUsers.size();i++){
			if(listeUsers.get(i).getId().equals(name)){
				return true;
			}
		}
		return false;
	}
}
