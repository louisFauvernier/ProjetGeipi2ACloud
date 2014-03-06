/**
 * Classe Fichier
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.commonFiles;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Fichier extends File implements Serializable{
	private static final long serialVersionUID = 7154639632477217376L;
	private long crc32=0;
	private int version=0;
	private byte[] contenu;
	
	public Fichier(String name) throws IOException{
		super(name);
		updateFile();
	}
	
	public long getCRC32(){
		return this.crc32;
	}
	public int getTaille(){
		return (int) this.length();
	}
	public byte[] getContenu() {
		return contenu;
	}
	
	public void updateFile() throws IOException{
		long Crc32 = getCRC32(this.getName());
		if(this.crc32 == Crc32)
			System.out.println("Fichiers identique : Mise à jour annulée");
		else{
			this.crc32 = Crc32;
			contenu = new byte[this.getTaille()];
			int i=0, cpt=0;
			FileInputStream fis = new FileInputStream(this.getName());
			BufferedInputStream bis = new BufferedInputStream(fis);
			while((i=bis.read())!=-1){
				contenu[cpt] = (byte) i;
				cpt++;
			}
			bis.close();
			this.version++;
		}		
	}
	
	public static long getCRC32(String name) throws IOException {
		FileInputStream in = new FileInputStream(name);
		Checksum somme_controle = new CRC32();
		for (int b = in.read(); b != -1; b = in.read()) {
			somme_controle.update(b);
		}
		return somme_controle.getValue();
	}
}
