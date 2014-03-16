/**
 * Classe principale du serveur
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

public class Main{
	static ServeurIn sin;
	public static void main(String[] args) {
		sin = new ServeurIn(8080);
		sin.start();
	}
}
