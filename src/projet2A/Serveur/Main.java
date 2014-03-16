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
import java.util.HashMap;

import projet2A.commonFiles.Fichier;

public class Main{
	static ServeurIn sin;
	static ArrayList<String> listeFiles = new ArrayList<String>();
	static HashMap<String, Fichier> listeFile = new HashMap<String, Fichier>();
	
	public static void main(String[] args) {
		//saveFiles();
		loadFile();
		sin = new ServeurIn(8080);
		sin.start();
	}
	
	public static void loadFile(){
		ObjectInputStream ois;
		try {
			File fichier =  new File("fileserv/listefichier.ser");
			ois = new ObjectInputStream(new FileInputStream(fichier));
			listeFile = (HashMap<String, Fichier>)ois.readObject();
	        ois.close();
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();	
		}
		try {
			File fichier =  new File("fileserv/arraylistefichier.ser");
			ois = new ObjectInputStream(new FileInputStream(fichier));
			listeFiles = (ArrayList<String>)ois.readObject();
	        ois.close();
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();	
		}
	}
	
	public static void saveFiles(){
		try {
			File fichier =  new File("fileserv/listefichier.ser");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listeFile);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			File fichier =  new File("fileserv/arraylistefichier.ser");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listeFiles);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
