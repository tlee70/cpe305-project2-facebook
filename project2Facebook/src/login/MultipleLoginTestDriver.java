package login;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.HashMap;

import profile.AccountModel;

public class MultipleLoginTestDriver {
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
			myAcc.addFriend(acc4);
			AccountModel acc5 = new AccountModel("Caleb Barber", image);
			myAcc.addFriend(acc5);
			AccountModel acc6 = new AccountModel("Dylan Kirkby", image);
			myAcc.addFriend(acc6);

			HashMap<MultipleLoginModel, AccountModel> map = new HashMap<MultipleLoginModel, AccountModel>();
			
			MultipleLoginModel	myLogin	= new MultipleLoginModel("tlee70", "terriblePassword");
			map.put(myLogin, myAcc);
			MultipleLoginModel	login1	= new MultipleLoginModel("samHaire", "hairysam");
			map.put(login1, acc1);
			MultipleLoginModel	login2	= new MultipleLoginModel("stevenMeissner", "phteven");
			map.put(login2, acc2);
			MultipleLoginModel	login3	= new MultipleLoginModel("taylorWood", "M0rn1ngW00d");
			map.put(login3, acc3);
			MultipleLoginModel	login4	= new MultipleLoginModel("yesMoreno", "corruptingInfluence");
			map.put(login4, acc4);
			MultipleLoginModel	login5	= new MultipleLoginModel("calebBarber", "B@rb3r5hop");
			map.put(login5, acc5);
			MultipleLoginModel	login6	= new MultipleLoginModel("dylanKirkby", "quirkyKirkby");
			map.put(login6, acc6);
			
			LoginView	view 	= new LoginView();
			MultipleLoginController	controller = new MultipleLoginController(view, map);
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
		
	}
}
