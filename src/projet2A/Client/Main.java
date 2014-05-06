/**
 * Classe principale du client
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.HashMap;

import projet2A.commonFiles.Constantes;
import projet2A.commonFiles.Fichier;

public class Main {
	public static HashMap<String, Fichier> listeFile = new HashMap<String, Fichier>();
	public static ClientOut cout;
	public ClientIn cin;
	public static String login, password;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		loadIndexFile();
		saveFiles();
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(isr);
		while(true){
			System.out.println(" -= GeipiDrive Client =-");
			System.out.println("1 pour se connecter");
			System.out.println("2 pour s'incrire");
			System.out.println("3 pour quitter");
			String action = in.readLine();
			if(action.equals("1")){
				System.out.print("Entrez votre identifiant :");
				login = in.readLine();
				System.out.print("Entrez votre mot de passe :");
				password = in.readLine();
				ClientOut cout = new ClientOut("127.0.0.1");
				if(cout.connect() == 1){
					ClientIn cin = new ClientIn(8001, cout);
					cin.start();
					cout.sendState(Constantes.CLIENT_LOGIN);
				}
			}
			else if(action.equals("2")){
				System.out.print("Entrez votre identifiant :");
				login = in.readLine();
				System.out.print("Entrez votre mot de passe :");
				password = in.readLine();
				ClientOut cout = new ClientOut("127.0.0.1");
				if(cout.connect() == 1){
					ClientIn cin = new ClientIn(8001, cout);
					cin.start();
					cout.createUser(login, password);
				}
			}
			else if(action.equals("3")){
				break;
			}
		}
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
