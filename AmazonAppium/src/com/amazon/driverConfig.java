package com.amazon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;

public class driverConfig {
	static AndroidDriver driver;

	public void createDriverInstance() throws MalformedURLException {
		try {
			File appDir = new File("apk");
			File app = new File(appDir, "Amazon_shopping.apk");
			Assert.assertEquals(true, app.exists());
			
			//Check userData.properties file for the data changes
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("automationName", getFileData("automationName", "userData"));
			capabilities.setCapability("platformName", getFileData("platformName", "userData")); 
			capabilities.setCapability("platformVersion", getFileData("platformVersion", "userData"));
			capabilities.setCapability("deviceName", getFileData("automationName", "userData"));
			capabilities.setCapability("udid", getFileData("udid", "userData"));
			capabilities.setCapability("app", app.getAbsolutePath());
			
			capabilities.setCapability("appPackage", getFileData("appPackage", "userData"));
			capabilities.setCapability("appActivity", getFileData("appActivity", "userData"));
	
			capabilities.setCapability("noReset", Boolean.parseBoolean(getFileData("noReset", "userData")));
			
			driver = new AndroidDriver (new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}
	
	public static void getWebdriverWait(String locator, String type) throws IOException{
		String waitLocator = getFileData(locator, "locators");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		switch(type) {
			case "className" : 	wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className(waitLocator)));
						   	  	break;
			case "xpath"     :	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(waitLocator)));
								break;
			case "id"        :  wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id(waitLocator)));
			                    break;			
		}
	}
	
	public static void isElementVisible(String locator, String type) throws IOException{
		String waitLocator = getFileData(locator, "locators");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		switch(type) {
			case "className" : 	wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.className(waitLocator)));
						   	  	break;
			case "xpath"     :	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitLocator)));
								break;
			case "id"        :  wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(waitLocator)));
			                    break;			
		}
	}

	protected static Boolean isLocatorDisplyed(String locator, String type) {
		Boolean visiblity = null;
		switch(type) {
			case "xpath"     :	visiblity = driver.findElement(By.xpath(locator)).isDisplayed();
								break;
		}
		return visiblity;
	}

	public static void clickButton(String locator, String type) throws IOException {
		String clickButton = getFileData(locator, "locators");
		switch(type) {
			case "class" : 	driver.findElementByClassName(clickButton).click();
						   	break;
			case "xpath" : 	driver.findElement(By.xpath(clickButton)).click();
							break;
			case "id" : 	driver.findElementById(clickButton).click();
							break;
		}
	}
	
	public static String getFileData(String locator, String fileName) throws IOException  {
		File classpathRoot = new File(System.getProperty("user.dir"));
		String activeFile = "/inputs/"+fileName+".properties";
		File app_prop = new File(classpathRoot, activeFile);
		
		Properties prop=new Properties();
		FileInputStream fileInput = new FileInputStream(app_prop);
		prop.load(fileInput);
		
		return (prop.getProperty(locator));
	}
	
	
	public static String getTextFromLocator(String locator, String type) {
		String getValue = null;
		switch(type) {
			case "class" : 	getValue = driver.findElementByClassName(locator).getText();
						   	break;
			case "xpath" : 	getValue = driver.findElement(By.xpath(locator)).getText();
							break;
			case "id" : 	getValue = driver.findElementById(locator).getText();
							break;
		}		
		
		return getValue;		
	}
	
	
	public static void sendText(String username, String password) throws IOException {
		
		getWebdriverWait("email", "xpath");
		MobileElement el3 = (MobileElement) driver.findElementById("ap_email_login");
		el3.setValue(username);
		MobileElement submit = (MobileElement) driver.findElement(By.xpath("//android.widget.Button[@text='Continue']"));
		submit.click();		

		getWebdriverWait("password", "id");
		MobileElement el5 = (MobileElement) driver.findElementById("ap_password");
		el5.setValue(password);
		MobileElement el6 = (MobileElement) driver.findElementById("signInSubmit");
		el6.click();
		
		getWebdriverWait("amazonLogo", "xpath");
	}
	
	public static void sendText(String srchTV) throws IOException {		

		getWebdriverWait("menuBar", "xpath");
		MobileElement element1 = (MobileElement) driver.findElementById("com.amazon.mShop.android.shopping:id/rs_search_src_text");
		element1.setValue(srchTV);
		((AndroidDriver<MobileElement>) driver).pressKey(new KeyEvent(AndroidKey.ENTER));		
		
		getWebdriverWait("menuBar", "xpath");
	}
	
	public static void selectProduct(String inputData) throws IOException, InterruptedException {
		
		getWebdriverWait("menuBar", "xpath");

		MobileElement el1 = (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'VU 163 cm')]"));
		el1.click();
		Thread.sleep(2000);
		
		getWebdriverWait("menuBar", "xpath");
		swipeUp(driver);
	}
	
	public static void addToCart() throws IOException {
		MobileElement element = (MobileElement)driver.findElementById("add-to-cart-button");
		element.click();
		getWebdriverWait("menuBar", "xpath");
		

		MobileElement cart = (MobileElement)driver.findElementByAccessibilityId("Cart");
		cart.click();
		getWebdriverWait("proceedToBuy", "xpath");
	}

	protected static void GetProductData() {
		String TVName = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'VU 163 cm')]")).getText().replaceAll("(?<=TV).*$", "");
		String TVPrice = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'₹')]")).getText().trim();
		String[] TVPriceSplit = TVPrice.replaceAll("[^0-9,. ]", "").split(" ");
		
		Properties properties = new Properties();
		try(OutputStream outputStream = new FileOutputStream("inputs/setData.properties")) {  
		    properties.setProperty("expectedName", TVName);
		    properties.setProperty("expectedPrice", TVPriceSplit[0]);
		    properties.store(outputStream, null);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	//Check on cart and validate price and name of prooduct
	protected static void validateProductDetails() throws IOException {
		String actualName = driver.findElement(By.xpath("(//android.widget.TextView[contains(@text,'VU')])[1]")).getText().replaceAll("(?<=TV).*$", "");
		String actualPrice = driver.findElement(By.xpath("((//android.widget.TextView[contains(@text,'VU')])[1]/parent::android.view.View/following-sibling::*/*)[1]")).getText().replaceAll("[^0-9,.]", "");
		
		String expectedName = getFileData("expectedName", "setData");
		String expectedPrice = getFileData("expectedPrice", "setData");
		
		Assert.assertEquals(actualName, expectedName);
		Assert.assertEquals(actualPrice, expectedPrice);
		
	}

	//Function to Swipe up
	public static void swipeUp(AndroidDriver driver) throws IOException{
		try {
			int heightOfScreen = driver.manage().window().getSize().getHeight();
			int widthOfScreen = driver.manage().window().getSize().getWidth();
			
			// To get 1.2 times of width
			int x = (int) (widthOfScreen * 1.2);
			// To get 15% of height
			int y = (int) (heightOfScreen * 0.15);

			TouchAction swipe;
			boolean addToCart = false;
			do {
				 new TouchAction(driver)
						.press(PointOption.point(0,x))
						.waitAction()
						.moveTo(PointOption.point(0,y))
						.release()
						.perform();

				addToCart = isLocatorDisplyed(getFileData("addToCart", "locators") , "xpath");
				
			} while(addToCart != true);
		}
		catch(NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	public void quitDriver() {
		driver.closeApp();
	}
}
