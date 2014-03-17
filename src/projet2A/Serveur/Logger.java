/**
 * Classe d'enregistrement des évènements
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.Serveur;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Logger {
	BufferedWriter output;
	
	public Logger(){
		INFO("projet2A.Serveur.Logger.java:Logger:20", "Démarrage du service de logging");
		try {
			output = new BufferedWriter(new FileWriter("fileserv/serveur.log", true));
			output.write("\t\t\t\t--== New Log Session ==--\n");
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void DEBUG(String locate, String message){
		String write = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ", Locale.FRANCE).format(new Date());
		write += "[DEBUG] ("+ locate + ") : "  + message + "\n";
		System.out.print(write);
	}
	public void INFO(String locate, String message){
		String write = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ", Locale.FRANCE).format(new Date());
		write += "[INFO ] ("+ locate + ") : "  + message + "\n";
		System.out.print(write);
	}
	public void WARNING(String locate, String message){
		String write = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ", Locale.FRANCE).format(new Date());
		write += "[WARN ] ("+ locate + ") : "  + message + "\n";
		System.out.print(write);
		WLog(write);
	}
	public void ERROR(String locate, String message){
		String write = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ", Locale.FRANCE).format(new Date());
		write += "[ERROR] ("+ locate + ") : "  + message + "\n";
		System.out.print(write);
		WLog(write);
	}
	public void FATAL(String locate, String message){
		String write = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ", Locale.FRANCE).format(new Date());
		write += "[FATAL] ("+ locate + ") : "  + message + "\n";
		System.out.print(write);
		WLog(write);
	}	
		
	private void WLog(String message){
		try {;
			output.write(message);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
