/**
 * Classe principale du serveur
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

public class Main{
	public static void main(String[] args) {
		ServeurIn sin = new ServeurIn(8080);
		sin.start();
	}
}
