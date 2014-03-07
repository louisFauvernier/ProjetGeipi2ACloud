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
		ClientIn cin = new ClientIn(8081);
		cin.start();
		ClientOut cout = new ClientOut("Client");
		cout.start();
		cin.stop();
	}
}
