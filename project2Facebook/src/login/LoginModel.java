package login;

public class LoginModel {
	private String username;
	private String password;
	
	public LoginModel(String username, String password) {
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
	
	public String getPassword() {
		return password;
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
		if ( !(other instanceof LoginModel) ) {
			return false;
		}
		
		LoginModel otherLogin = (LoginModel)other;
		return otherLogin.verify(this.username, this.password);
	}
	
	public int hashCode() {
		return (100*username.hashCode()) + password.hashCode();
	}
	
	public void saveState() {
		
	}
}
