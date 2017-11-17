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
	
	public boolean verify(String username, String password) {
		if (!this.username.equals(username))
			return false;
		if (!this.password.equals(password))
			return false;
		
		return true;
	}
}
