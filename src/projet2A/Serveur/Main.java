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
	static ServeurIn sin;
	static ArrayList<String> listeFiles = new ArrayList<String>();
	static ArrayList<User> listeUsers = new ArrayList<User>();
	
	public static void main(String[] args) {
		loadFile();
		//listeUsers.add(new User("louis", "test"));
		//saveFiles();
		sin = new ServeurIn(8080);
		sin.start();
	}
	
	public static void loadFile(){
		System.out.println("[+] INFO : Chargement des Utilisateurs sur le serveur");
		ObjectInputStream ois;
		try {
			File fichier =  new File("fileserv/listeUser.ser");
			ois = new ObjectInputStream(new FileInputStream(fichier));
			listeUsers = (ArrayList<User>)ois.readObject();
	        ois.close();
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();	
		}
		System.out.println("[+] INFO : " + listeUsers.size() + "  utilisateurs chargés");
		System.out.println("[+] INFO : Chargement des index de fichiers");
		try {
			File fichier =  new File("fileserv/listeFichier.ser");
			ois = new ObjectInputStream(new FileInputStream(fichier));
			listeFiles = (ArrayList<String>)ois.readObject();
	        ois.close();
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();	
		}
		System.out.println("[+] INFO : " + listeFiles.size() + "  index chargés");
	}
	
	public static void saveFiles(){
		try {
			File fichier =  new File("fileserv/listeFichier.ser");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listeFiles);
			oos.close();
		} catch (IOException e) {
			System.out.print("[-] ERREUR : ");
			e.printStackTrace();
		}
		try {
			File fichier =  new File("fileserv/listeUser.ser");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listeUsers);
			oos.close();
		} catch (IOException e) {
			System.out.print("[-] ERREUR : ");
			e.printStackTrace();
		}
	}
}
