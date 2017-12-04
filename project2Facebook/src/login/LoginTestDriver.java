package login;

import profile.FacebookDatabase;
import profile.AccountModel;

public class LoginTestDriver {
	public static void main(String[] args) {
		AccountModel myAcc = new AccountModel("Tim Lee");
	
		AccountModel acc1 = new AccountModel("Sam Haire");
		myAcc.addFriend(acc1);
		AccountModel acc2 = new AccountModel("Steven Meissner");
		myAcc.addFriend(acc2);
		AccountModel acc3 = new AccountModel("Taylor Wood");
		myAcc.addFriend(acc3);
		AccountModel acc4 = new AccountModel("Yesenia Moreno");
		//myAcc.addFriend(acc4);\
		AccountModel acc5 = new AccountModel("Caleb Barber");
		//myAcc.addFriend(acc5);
		AccountModel acc6 = new AccountModel("Dylan Kirkby");
		myAcc.addFriend(acc6);

		FacebookDatabase map = new FacebookDatabase();
			
		LoginModel	myLogin	= new LoginModel("tlee70", "terriblePassword");
		map.put(myLogin, myAcc);
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
			
		LoginView	view 	= new LoginView();
		new LoginController(view, map);
		
	}
}
