/**
 * Classe User : repr�sente un utilisateur du cloud
 * 
 * @author FAUVERNIER Louis, MENET Nicolas
 */

package projet2A.commonFiles;

import java.io.Serializable;

public class User  implements Serializable{
	private static final long serialVersionUID = 3243208518271848867L;
	private String id;
	private String password;
	
	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
