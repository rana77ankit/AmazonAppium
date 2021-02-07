package com.amazon;

import java.io.IOException;

public class searchProduct extends driverConfig {

	public static void searchTV() throws IOException {
		getWebdriverWait("amazonLogo", "xpath");
		sendText(getInputData("searchTV", "userData"));
		
		//Get Product data from product search screen
		GetProductData();
		
		//Search product on SearchBar
		selectProduct(getInputData("selectTV", "userData"));

		//Add product to cart
		addToCart();	

		
		//Validate product information on serach page
		validateProductDetails();
	}
	
}