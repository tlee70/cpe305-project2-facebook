package profile;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import login.LoginModel;

public class OwnProfileTestDriver {
	public static void main(String[] args) {
		try {
			Image image = ImageIO.read(new File("C:/Users/Tim/Pictures/Pics/Symbol.png"));
			//Image image = ImageIO.read(new File("//home/tlee70/git/cpe305-project2-facebook/project2Facebook/pics/blank.png"));
			AccountModel myAcc = new AccountModel("Tim Lee", image);
		
			AccountModel acc1 = new AccountModel("Sam Haire", image);
			myAcc.addFriend(acc1);
			AccountModel acc2 = new AccountModel("Steven Meissner", image);
			myAcc.addFriend(acc2);
			AccountModel acc3 = new AccountModel("Taylor Wood", image);
			myAcc.addFriend(acc3);
			AccountModel acc4 = new AccountModel("Yesenia Moreno", image);
			//myAcc.addFriend(acc4);
			AccountModel acc5 = new AccountModel("Caleb Barber", image);
			//myAcc.addFriend(acc5);
			AccountModel acc6 = new AccountModel("Dylan Kirkby", image);
			myAcc.addFriend(acc6);

			//HashMap<LoginModel, AccountModel> map = new HashMap<LoginModel, AccountModel>();
			
			LoginModel	myLogin	= new LoginModel("tlee70", "terriblePassword");
			/**map.put(myLogin, myAcc);
			LoginModel	login1	= new LoginModel("samHaire", "hairysam");
			map.put(login1, acc1);
			LoginModel	login2	= new LoginModel("stevenMeissner", "phteven");
			map.put(login2, acc2);
			LoginModel	login3	= new LoginModel("taylorWood", "M0rn1ngW00d");
			map.put(login3, acc3);
			LoginModel	login4	= new LoginModel("yesMoreno", "corruptingInfluence");
			map.put(login4, acc4);
			LoginModel	login5	= new LoginModel("calebBarber", "B@rb3r5hop");
			map.put(login5, acc5);
			LoginModel	login6	= new LoginModel("dylanKirkby", "quirkyKirkby");
			map.put(login6, acc6);
			
			new OwnProfileController(new OwnProfileView(), myAcc, map);
			*/
			new OwnProfileController(new OwnProfileView(), myAcc, myLogin, myAcc.getFriends());
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
		
	}
}
