/**
 * Classe Droits : Permission des utilisateurs
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.commonFiles;

public class Droits {
	private boolean read = false;
	private boolean write = false;
	private User user;
	
	public Droits(boolean read, boolean write, User user) {
		this.read = read;
		this.write = write;
		this.user = user;
	}
	
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public boolean isWrite() {
		return write;
	}
	public void setWrite(boolean write) {
		this.write = write;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
