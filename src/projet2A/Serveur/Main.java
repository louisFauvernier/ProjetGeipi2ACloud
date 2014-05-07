/**
 * Classe principale du serveur
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import projet2A.commonFiles.User;

public class Main{
	public static Logger log;
	static ServeurIn sin;
	static ArrayList<String> listeFiles = new ArrayList<String>();
	static ArrayList<User> listeUsers = new ArrayList<User>();
	
	public static void main(String[] args){
		log = new Logger();
		loadFile();
		//RAZServer();
		sin = new ServeurIn(8000);
		sin.start();
		run();
	}
	
	public static void run(){
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(isr);
		String commande = "";
		while(true){
			try {
				commande = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(commande.equals("exit")){
				while(sin.isConnect()){
					System.out.println("En attente de fermetrue des connexions");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.exit(0);
			}
			else if(commande.equals("cat users")){
				int n= listeUsers.size();
				System.out.println(n + " utilisateur(s) est/sont enregistré(s) sur le serveur \n ---");
				for(int i=0;i<n;i++){
					System.out.println("-> " + listeUsers.get(i).getId());
				}
			}
			else if(commande.equals("cat files")){
				liste(new File("fileserv/UsersFiles"));
			}
			else if(commande.equals("about")){
				System.out.println("\t\t --==  A propos de GeipiDrive Serveur ==--");
				System.out.println("GeipiDrive Server est un serveur de fichiers dévellopé par FAUVERNIER Louis et MENET Nicolas");
				System.out.println(" -> Version 1.00 build 38");
				System.out.println(" -> Programme sous license GPLv3");
			}
			else{
				System.out.println("Commande inconnue");
			}
		}
	}
	
	public static void liste(File f){
		if(f.isDirectory()){
			String[] listeFichiers = new File(f.getAbsolutePath()).list();
			for(int i=0;i<listeFichiers.length;i++){
				liste(new File(f.getAbsolutePath()+"/" + listeFichiers[i]));
			}
		}
		else
			System.out.println("-->" + f.getAbsolutePath());
	}
	
	@SuppressWarnings("unchecked")
	public static void loadFile(){
		log.INFO("projet2A.Serveur.Main.java:loadFile:38", "Chargement des Utilisateurs sur le serveur");
		ObjectInputStream ois;
		try {
			File fichier =  new File("fileserv/listeUser.ser");
			ois = new ObjectInputStream(new FileInputStream(fichier));
			listeUsers = (ArrayList<User>)ois.readObject();
	        ois.close();
		} catch (IOException|ClassNotFoundException e) {
			log.ERROR("projet2A.Serveur.Main.java:loadFile:39", e.getMessage());
		}
		log.INFO("projet2A.Serveur.Main.java:loadFile:45", listeUsers.size() + " utilisateurs chargés");
		log.INFO("projet2A.Serveur.Main.java:loadFile:46", "Chargement des index de fichiers");
		try {
			File fichier =  new File("fileserv/listeFichier.ser");
			ois = new ObjectInputStream(new FileInputStream(fichier));
			listeFiles = (ArrayList<String>)ois.readObject();
	        ois.close();
		} catch (IOException|ClassNotFoundException e) {
			log.ERROR("projet2A.Serveur.Main.java:loadFile:49", e.getMessage());	
		}
		log.INFO("projet2A.Serveur.Main.java:loadFile:55", listeFiles.size() + " index chargés");
	}
	
	public static void saveFiles(){
		log.INFO("projet2A.Serveur.Main.java:saveFiles:58", "Sauvegarde des index fichiers du serveur");
		try {
			File fichier =  new File("fileserv/listeFichier.ser");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listeFiles);
			oos.close();
			log.INFO("projet2A.Serveur.Main.java:saveFiles:65", listeFiles.size() + " index sauvegardés");
		} catch (IOException e) {
			log.ERROR("projet2A.Serveur.Main.java:saveFiles:63", e.getMessage());
		}
	}
	public static void saveUsers(){
		log.INFO("projet2A.Serveur.Main.java:saveFiles:72", "Sauvegarde des utilisateurs du serveur");
		try {
			File fichier = new File("fileserv/listeUser.ser");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listeUsers);
			oos.close();
			log.INFO("projet2A.Serveur.Main.java:saveUsers:79", "Succès");
		} catch (IOException e) {
			log.ERROR("projet2A.Serveur.Main.java:saveUsers:77", e.getMessage());
		}
	}
	
	public static int addNewUser(String name, String pass){
		if(estInscrit(name)!= -1){
			log.ERROR("projet2A.Serveur.Main.java:addNewUser:86", "Echec de la création de l'utilisateur identifiant déjà utilisé ("+name+")");
			return 1;
		}
		listeUsers.add(new User(name, pass));
		File dossier = new File("fileserv/UsersFiles/" + name);
		dossier.mkdir();
		log.INFO("projet2A.Serveur.Main.java:addNewUser:90", "Création de l'utilisateur " + name);
		saveUsers();
		return 0;
	}
	public static int estInscrit(String name){
		for(int i=0;i<listeUsers.size();i++){
			if(listeUsers.get(i).getId().equals(name)){
				return i;
			}
		}
		return -1;
	}
	public static void RAZServer(){
		System.out.println("SCRIPT DE REMISE A ZERO DU SERVEUR! TOUTES LES DONNEES SONT EFFACEES");
		listeFiles = new ArrayList<String>();
		listeUsers = new ArrayList<User>();
		saveUsers();
		saveFiles();
		String[] listeFichiers = new File("fileserv/UsersFiles").list();
		for(int i=0;i<listeFichiers.length;i++){
			delete(new File("fileserv/UsersFiles/" + listeFichiers[i]));
		}
		log.INFO("projet2A.Serveur.Main.java:RAZServer:113", "Ajout d'un utilisateur par defaut");
		addNewUser("louis", "test");
		
	}
	public static void delete(File f){
		System.out.println("Supression de " + f.getAbsolutePath());
		if(f.isDirectory()){
			String[] listeFichiers = new File(f.getAbsolutePath()).list();
			for(int i=0;i<listeFichiers.length;i++){
				delete(new File(f.getAbsolutePath()+"/" + listeFichiers[i]));
			}
		}
		f.delete();
	}
}
