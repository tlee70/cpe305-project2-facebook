package profile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import login.LoginModel;

public class FacebookDatabase extends HashMap<LoginModel, AccountModel> {

	public void saveState() {
		File file = new File("files/login_data.txt");
		
		String newLineChar = System.getProperty("line.separator");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			String newlineChar = System.getProperty("line.separator");
			
			writer.write("[");
			writer.flush();
			
			Iterator<Map.Entry<LoginModel,AccountModel>> iterator = entrySet().iterator();
			while(iterator.hasNext()) {
				writer.write(newlineChar + "\t{");
				
				Map.Entry<LoginModel, AccountModel> pair = iterator.next();
				LoginModel login = pair.getKey();
				AccountModel account = pair.getValue();
				
				writer.write(newLineChar + "\t\t\"LoginModel\":");
				String loginStr = login.toJSON().replaceAll("(?m)^", "\t\t");
				writer.write(loginStr + ",");
				
				writer.write(newLineChar + "\t\t\"AccountModel\":");
				String accountStr = account.toJSON().replaceAll("(?m)^", "\t\t");
				writer.write(accountStr);
				
				writer.write(newLineChar + "\t}");
				
				if (iterator.hasNext())  {
					writer.write(",");
				}
				writer.flush();
			}
			
			writer.write(newlineChar + "]");
			writer.flush();
			
			writer.close();
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
			System.out.println(e);
		}
	}
}
