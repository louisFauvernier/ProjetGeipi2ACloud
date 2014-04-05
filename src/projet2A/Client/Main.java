/**
 * Classe principale du client
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import projet2A.commonFiles.Fichier;
import projet2A.commonFiles.User;

public class Main {
	public static HashMap<String, Fichier> listeFile = new HashMap<String, Fichier>();
	public static ClientOut cout;
	public ClientIn cin;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		loadIndexFile();
		System.out.println(listeFile.size() + " fichiers indexés");
		saveFiles();
		ClientOut cout = new ClientOut("127.0.0.1");
		cout.start();
		ClientIn cin = new ClientIn(8081, cout);
		cin.start();
	}
	
	@SuppressWarnings("unchecked")
	public static void loadIndexFile(){
		ObjectInputStream ois;
		try {
			File fichier =  new File("files/listeFichier.ser");
			ois = new ObjectInputStream(new FileInputStream(fichier));
			listeFile = (HashMap<String, Fichier>)ois.readObject();
	        ois.close();
	        listFile();
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void listFile(){
		String[] listeFichiers = new File("files").list();
		try{
			for(int i=0;i<listeFichiers.length;i++){
				if(!listeFichiers[i].equals("listeFichier.ser")){
					if(listeFile.containsKey(listeFichiers[i]))
						listeFile.get(listeFichiers[i]).updateFile();
					else
						listeFile.put(listeFichiers[i], new Fichier("files/" + listeFichiers[i]));
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}	
	
	public static void saveFiles(){
		try {
			File fichier =  new File("files/listeFichier.ser");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listeFile);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();		
		}
	}
}
