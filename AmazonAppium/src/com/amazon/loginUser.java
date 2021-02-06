package com.amazon;

import java.io.IOException;

public class loginUser extends driverConfig {
	public static void login() throws IOException {
		getWebdriverWait("signIn","xpath");
		clickButton("signIn","xpath");
		
		sendText(getInputData("username", "userData"), getInputData("password", "userData"));
	}
}
