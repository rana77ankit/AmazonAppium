package com.amazon;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class loginUser extends driverConfig {
	public static void login() throws IOException {
		
		//Login to amazon
		getWebdriverWait("signIn","xpath");
		clickButton("signIn","xpath");
		sendText(getFileData("username", "userData"), getFileData("password", "userData"));
		
		//Verifing that user is loggedin		
		Boolean condition = isLocatorDisplyed(getFileData("amazonLogo", "locators") , "xpath");
		Assert.assertTrue(condition);
		
	}
}
