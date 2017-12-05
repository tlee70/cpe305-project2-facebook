package tests;

import org.junit.*;
import static org.junit.Assert.*;

import profile.AccountModel;

public class AccountModelTester {
	@Test
	public void toJSONTester() {
		String expected = "{\r\n\t\"name\": \"Name\",\r\n\t\"picturePath\": \"pics/blank.png\"\r\n}";
				
		AccountModel account = new AccountModel("Name", "pics/blank.png");
		
		assertEquals(expected, account.toJSON());
	}

	
}
