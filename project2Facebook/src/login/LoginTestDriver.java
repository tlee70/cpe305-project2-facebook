package login;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import profile.AccountModel;

public class LoginTestDriver {
	public static void main(String[] args) {
		try {
			Image image = ImageIO.read(new File("C:/Users/Tim/Pictures/Pics/Symbol.png"));
			//Image image = ImageIO.read(new File("//home/tlee70/git/cpe305-project2-facebook/project2Facebook/pics/blank.png"));
			AccountModel myAcc = new AccountModel("Tim Lee", image);
		
			myAcc.addFriend(new AccountModel("Sam Haire", image));
			myAcc.addFriend(new AccountModel("Steven Meissner", image));
			myAcc.addFriend(new AccountModel("Taylor Wood", image));
			myAcc.addFriend(new AccountModel("Yesenia Moreno", image));
			myAcc.addFriend(new AccountModel("Caleb Barber", image));
			myAcc.addFriend(new AccountModel("Dylan Kirkby", image));
			
			LoginModel	model	= new LoginModel("tlee70", "terriblePassword");
			LoginView	view 	= new LoginView();
			LoginController	controller = new LoginController(view, model, myAcc);
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
		
	}
}