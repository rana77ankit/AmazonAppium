package com.amazon;

import java.io.IOException;

public class searchProduct extends driverConfig {

	public static void searchTV() throws IOException {
		getWebdriverWait("menuBar", "id");		
		sendText(getInputData("searchTV", "userData"));
		
		selectProduct(getInputData("selectTV", "userData"));
	}
	
}