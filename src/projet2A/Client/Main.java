/**
 * Classe principale du client
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Client;

import java.io.IOException;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		ClientOut cout = new ClientOut("127.0.0.1");
		cout.start();
		ClientIn cin = new ClientIn(8081);
		cin.start();
	}
}
