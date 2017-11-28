package profile;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OwnProfileTestDriver {
	public static void main(String[] args) {
		try {
			Image image = ImageIO.read(new File("//home/tlee70/git/cpe305-project2-facebook/project2Facebook/pics/blank.png"));
			AccountModel myAcc = new AccountModel("Tim Lee", image);
			
			myAcc.addFriend(new AccountModel("Sam Haire", image));
			myAcc.addFriend(new AccountModel("Steven Meissner", image));
			myAcc.addFriend(new AccountModel("Taylor Wood", image));
			myAcc.addFriend(new AccountModel("Yesenia Moreno", image));
			myAcc.addFriend(new AccountModel("Caleb Barber", image));
			myAcc.addFriend(new AccountModel("Dylan Kirkby", image));
			
			OwnProfileView op_view = new OwnProfileView();
			OwnProfileController op_ctrl = new OwnProfileController(op_view, myAcc);
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
}
