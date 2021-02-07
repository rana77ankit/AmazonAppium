package com.amazon;

import java.io.IOException;

public class searchProduct extends driverConfig {

	public static void searchTV() throws IOException, InterruptedException {
		getWebdriverWait("amazonLogo", "xpath");
		sendText(getFileData("searchTV", "userData"));
		
		//Get Product data from product search screen
		GetProductData();
		
		//Search product on SearchBar
		selectProduct(getFileData("selectTV", "userData"));

		//Add product to cart
		addToCart();	

		
		//Validate product information on search page
		validateProductDetails();
	}
	
}