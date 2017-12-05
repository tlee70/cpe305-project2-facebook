package tests;

import org.junit.*;
import static org.junit.Assert.*;

import org.json.simple.JSONObject;

import login.LoginModel;

public class LoginModelTester {
	@Test
	public void verifyTest() {
		LoginModel login = new LoginModel("Name", "Password");
		
		assertTrue(login.verify("Name", "Password"));
		
		assertFalse(login.verify("name", "Password"));
		assertFalse(login.verify("Name", "password"));
		
	}
	
	@Test
	public void equalsTest() {
		LoginModel login = new LoginModel("Name", "Password");
		
		assertFalse(login.equals(null));
		
		assertFalse(login.equals("Green"));
		
		assertFalse(login.equals(new LoginModel("name","password")));
		
		assertTrue(login.equals(new LoginModel("Name", "Password")));
	}
	
	@Test
	public void toJSONTest() {
		String expected = "{\r\n\t\"username\": \"Name\",\r\n\t\"password\": \"Password\"\r\n}";
		
		LoginModel login = new LoginModel("Name", "Password");
		
		assertEquals(expected, login.toJSON());
	}
	
}
