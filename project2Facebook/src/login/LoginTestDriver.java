package login;

public class LoginTestDriver {
	public static void main(String[] args) {
		LoginModel	model	= new LoginModel("tlee70", "terriblePassword");
		LoginView	view 	= new LoginView();
		LoginController	controller = new LoginController(view, model);
		
	}
}
