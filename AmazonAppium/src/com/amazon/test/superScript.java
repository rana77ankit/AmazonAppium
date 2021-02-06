package com.amazon.test;

import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.amazon.driverConfig;
import com.amazon.searchProduct;
import com.amazon.loginUser;

public class superScript extends driverConfig {

	@BeforeSuite
	public void openAmazonApp() throws MalformedURLException {
		createDriverInstance();
	}
	
	//login user in amazon
	@Test(priority=1)
	public void loginAmazon() throws IOException {
		loginUser.login();
	}
	
	//search item and verify it's details
	@Test(priority=2)
	public void purchaseItem() throws IOException {
		searchProduct.searchTV();
	}

	@AfterSuite
	public void closeAmazonApp() throws MalformedURLException {
		quitDriver();
		System.out.println("end");
	}
	
	
}