/**
 * Classe principale du client
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static SystemTray sysTray;
	private static Image iconImage;
	private static TrayIcon trayIcon;
	private static PopupMenu menu;
	private static MenuItem item1;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		loadIndexFile();
		saveFiles();
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(isr);
		
		if(SystemTray.isSupported()){
			sysTray = SystemTray.getSystemTray();
			iconImage = Toolkit.getDefaultToolkit().getImage("src/projet2A/Client/maj.png");
			menu = new PopupMenu();
			item1 = new MenuItem("Exit");
			menu.add(item1);
			item1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				  System.exit(0);
				  }
				});
			trayIcon = new TrayIcon(iconImage, "GeipiDrive", menu);
			try {
				sysTray.add(trayIcon);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
		ClientOut cout = new ClientOut("127.0.0.1");
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
				if(cout.connect() == 1){
					ClientIn cin = new ClientIn(8001, cout);
					cin.start();
					cout.sendState(Constantes.CLIENT_LOGIN);
				}
				else{
					Notification("Impossible d'�tablir une connexion au serveur", "Echec de connexion");
				}
			}
			else if(action.equals("2")){
				System.out.print("Entrez votre identifiant :");
				login = in.readLine();
				System.out.print("Entrez votre mot de passe :");
				password = in.readLine();
				if(cout.connect() == 1){
					ClientIn cin = new ClientIn(8001, cout);
					cin.start();
					cout.createUser(login, password);
				}
				else{
					Notification("Impossible d'�tablir une connexion au serveur", "Echec de connexion");
				}
			}
			else if(action.equals("3")){
				if(SystemTray.isSupported()){
					sysTray.remove(trayIcon);
				}
				System.exit(0);
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
	 public static void Notification(String Text, String Title){
		 trayIcon.displayMessage(Title,Text,TrayIcon.MessageType.INFO);
	 }
}
