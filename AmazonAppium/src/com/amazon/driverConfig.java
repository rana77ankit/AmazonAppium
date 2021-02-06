package com.amazon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

public class driverConfig {
	static AndroidDriver driver;

	public void createDriverInstance() throws MalformedURLException {
		try {
			File appDir = new File("apk");
			File app = new File(appDir, "Amazon_shopping.apk");

			Assert.assertEquals(true, app.exists());
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			capabilities.setCapability("BROWSER_NAME", "Android");
			capabilities.setCapability("automationName", "Appium");
			capabilities.setCapability("platformName","Android");
			capabilities.setCapability("platformVersion", "9.1.0");
			capabilities.setCapability("deviceName","Honor Play");
			capabilities.setCapability("udid", "CRV7N18829001395");
			capabilities.setCapability("app", app.getAbsolutePath());
			
			capabilities.setCapability("appPackage", "com.amazon.mShop.android.shopping");
			capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
	
			capabilities.setCapability("noReset", false);
			
			driver = new AndroidDriver (new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		

	}
	
	public static void getWebdriverWait(String locator, String type) throws IOException{
		String waitLocator = getInputData(locator, "locators");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		switch(type) {
			case "className" : 	wait.until(ExpectedConditions.presenceOfElementLocated(By.className(waitLocator)));
						   	  	break;
			case "xpath"     :	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(waitLocator)));
								break;
			case "id"        :  wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id(waitLocator)));
			                    break;			
		}
	}
	
	public static void clickButton (String locator, String type) throws IOException {
		String clickButton = getInputData(locator, "locators");
		switch(type) {
			case "class" : 	driver.findElement(By.className(clickButton)).click();
						   	break;
			case "xpath" : 	driver.findElement(By.xpath(clickButton)).click();
							break;
			case "id" : 	driver.findElement(By.xpath(clickButton)).click();
							break;
		}
	}
	
	public static String getInputData(String locator, String fileName) throws IOException  {
		File classpathRoot = new File(System.getProperty("user.dir"));
		String activeFile = "/inputs/"+fileName+".properties";
		File app_prop = new File(classpathRoot, activeFile);
		
		Properties prop=new Properties();
		FileInputStream fileInput = new FileInputStream(app_prop);
		prop.load(fileInput);
		
		return (prop.getProperty(locator));
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

		getWebdriverWait("menuBar", "id");
		MobileElement el1 = (MobileElement) driver.findElementById("com.amazon.mShop.android.shopping:id/rs_search_src_text");
		el1.setValue(srchTV);
		driver.findElement(By.xpath("(//*[@resource-id='com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_text'])[1]")).click();
		
		
		getWebdriverWait("menuBar", "id");
	}
	
	public static void selectProduct(String inputData) throws IOException {
		
		getWebdriverWait("menuBar", "id");

		MobileElement el1 = (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[@text='VU 163 cm (65 Inches) 4K Ultra HD Smart Android LED TV 65PM (Black) (2020 Model)']"));
		el1.click();
		
		getWebdriverWait("menuBar", "id");
		swipeUp(driver);
		addToCart();
	}
	
	public static void addToCart() throws IOException {
		MobileElement element = (MobileElement)driver.findElementById("add-to-cart-button");
		element.click();
		getWebdriverWait("menuBar", "id");
		

		MobileElement cart = (MobileElement)driver.findElementByAccessibilityId("Cart");
		cart.click();
		getWebdriverWait("proceedToBuy", "xpath");
	}

	//Function to Swipe up
	public static void swipeUp(AndroidDriver driver){
		try {
			int heightOfScreen = driver.manage().window().getSize().getHeight();
			int widthOfScreen = driver.manage().window().getSize().getWidth();        

			int middleHeightOfScreen = heightOfScreen/2;
			// To get 85% of width

			int x = (int) (widthOfScreen * 1.2);
			// To get 25% of height
			int y = (int) (heightOfScreen * 0.17);
			System.out.println(x+"::"+y);
			System.out.println(widthOfScreen+"::"+heightOfScreen);

			boolean addToCart = false;
			while(addToCart != true) {
				TouchAction swipe = new TouchAction(driver)
						.press(PointOption.point(0,1300))
						.waitAction()
						.moveTo(PointOption.point(0,380))
						.release()
						.perform();
				
				MobileElement element = (MobileElement)driver.findElementById("add-to-cart-button");
				addToCart = element.isDisplayed();
			}
		}
		catch(NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	public void quitDriver() {
		driver.closeApp();
	}
}
