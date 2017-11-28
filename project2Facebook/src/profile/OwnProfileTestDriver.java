package profile;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OwnProfileTestDriver {
	public static void main(String[] args) {
		try {
			Image image = ImageIO.read(new File("C:/Users/Tim/Pictures/Pics/symbol.png"));
			AccountModel myAcc = new AccountModel("Tim Lee", image);
			OwnProfileView op_view = new OwnProfileView();
			OwnProfileController op_ctrl = new OwnProfileController(op_view, myAcc);
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
}
