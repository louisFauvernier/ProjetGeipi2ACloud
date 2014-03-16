/**
 * Classe principale du client
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import projet2A.commonFiles.Fichier;

public class Main {
	static HashMap<String, Fichier> listeFile = new HashMap<String, Fichier>();
	static ClientOut cout;
	static ClientIn cin;
	
	public static void main(String[] args) throws UnknownHostException, IOException {		
		String[] listeFichiers = new File("files").list();
		for(int i=0;i<listeFichiers.length;i++){
			listeFile.put(listeFichiers[i], new Fichier("files/" + listeFichiers[i]));
		}
		cout = new ClientOut("127.0.0.1");
		cout.start();
		cin = new ClientIn(8081);
		cin.start();
	}
}
