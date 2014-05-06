/**
 * Classe ClientIn
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import projet2A.commonFiles.Constantes;
import projet2A.commonFiles.Fichier;

public class ClientIn extends Thread{
	private ServerSocket socket;
	private Socket s;
	private ObjectInputStream in;
	private Object message_distant;
	private ClientOut cout;

	public ClientIn(int i, ClientOut cout) {
		try{
			socket = new ServerSocket(i);
			s = socket.accept();
			this.cout = cout;
		} catch (IOException e){
			System.out.println("[!] FATAL  : " + e.getMessage());
			System.exit(0);
		}
		try{
			in = new ObjectInputStream(s.getInputStream());
		} catch (IOException e){
			System.out.println("[-] Erreur : ");
			e.printStackTrace();
		}
	}

	public void run(){
		System.out.println("Execution du client ...");
		try{
			while (!(message_distant = connected()).equals(Constantes.CLIENT_CLOSECONNEXION)){
				traitementDemande(message_distant);
			}
			System.out.println("[+] INFO : Déconnexion du serveur");
			cout.close();
			s.close();
		} catch (IOException e){
			System.out.println("[-] Erreur : ");
			e.printStackTrace();
			try{
				s.close();
			} catch (IOException e1){
				System.out.println("[-] Erreur : ");
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Fonction de test pour savoir si le client est toujours connecté
	 * 
	 * @return message envoyé par le client ou si perte de connexion, demande de
	 *         fermeture de connexion
	 * @throws IOException
	 */
	public Object connected(){
		try{
			Object message = in.readObject();
			if(message == null){
				System.out.println("[-] ERREUR : Perte de connexion avec le serveur");
				return "close_connexion";
			} else
				return message;
		} catch (Exception e){
			cout.close();
			return "close_connexion";
		}
	}

	public void traitementDemande(Object message_distant){
		if(message_distant.getClass().equals(Constantes.CLIENT_LOGIN.getClass())){
			if(message_distant.equals(Constantes.CLIENT_ID)){
				cout.sendMessage("id " + Main.login + " " + Main.password);
			}
			else if(message_distant.equals(Constantes.CLIENT_SYNC_OK)){
				cout.sendState(Constantes.CLIENT_LOGOUT);
				cout.sendState(Constantes.CLIENT_CLOSECONNEXION);
			}
			else if (message_distant.equals(Constantes.USER_SUCCESS_LOGIN)){
				cout.sendState(Constantes.CLIENT_SYNC);
			}
			else if(message_distant.equals(Constantes.USER_FAILED_LOGIN)){
				System.out.println("[-] ERREUR : Mauvais login/pasword");
			}
			else if (message_distant.equals(Constantes.USER_SUCCESS_CREATE)){
				System.out.println("Compte créé avec succès");
				cout.close();
			}
			else if (message_distant.equals(Constantes.USER_FAILED_CREATE)){
				System.out.println("[-] ERREUR : Essayer avec un autre nom d'utilisateur");
				cout.close();
			}
			else if (message_distant.equals(Constantes.FILE_SEND_LIST)){
				try{
					cout.send();
				} catch (IOException e){
					System.out.println("[-] ERREUR : " + e.getMessage());
				}
			}
			else{
				System.out.println("Serveur envoie " + message_distant);
			}
		}
		else{
			String message = (String) message_distant;
			if(message.startsWith("send")){
				String[] tmp = message.split(" ");
				cout.sendFile(Main.listeFile.get(tmp[1]));
			}
			else if(message.startsWith("receive")){
				try{
					Fichier f = rcvFichier();
					saveFile(f);
				} catch (ClassNotFoundException e){
					System.out.println(e.getMessage());
				} catch (IOException e){
					System.out.println(e.getMessage());
				}
			}
			else{
				System.out.println("Serveur envoie " + message);
			}
		}
		
	}

	/**
	 * Fonction de reception d'un fichier depuis le serveur
	 * 
	 * @return
	 */
	public Fichier rcvFichier() throws IOException, ClassNotFoundException{
		Object objetRecu = in.readObject();
		Fichier f = (Fichier) objetRecu;
		return f;
	}

	/**
	 * Fonction d'enregistrement du fichier sur l'ordinateur (local)
	 * 
	 * @return
	 * @throws IOException 
	 */
	public void saveFile(Fichier f) throws IOException{
		System.out.println("Ecriture du fichier " + f.getName() + "(" + f.getTaille() + "/"  +f.getContenu().length + ")");
		FileOutputStream fos = new FileOutputStream("files/"+f.getName());
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		int b = 0, c = 4096;
		for(int i=0;i<f.getContenu().length;i++){
			b = f.getContenu()[i];
			if(b%c==0)
				System.out.println("Copie en cours : " + i + "/");
            bos.write(b);
        }
        bos.flush();
        bos.close();
        fos.flush();  
        fos.close();
        if(Main.listeFile.containsKey(f.getName()))
        	Main.listeFile.remove(f.getName());
        Main.listeFile.put(f.getName(),	f);
    	Main.saveFiles();
	}
}
