package login;

public class MultipleLoginModel {
	private String username;
	private String password;
	
	public MultipleLoginModel(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean verify(String username, String password) {
		if (!this.username.equals(username))
			return false;
		if (!this.password.equals(password))
			return false;
		
		return true;
	}
	
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if ( !(other instanceof MultipleLoginModel) ) {
			return false;
		}
		
		MultipleLoginModel otherLogin = (MultipleLoginModel)other;
		return otherLogin.verify(this.username, this.password);
	}
	
	public int hashCode() {
		return (100*username.hashCode()) + password.hashCode();
	}
}
