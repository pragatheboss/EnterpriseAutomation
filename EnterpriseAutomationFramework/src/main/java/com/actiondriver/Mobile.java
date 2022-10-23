package com.actiondriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utility.Constants;
import com.utility.Library;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Mobile extends Library{

	
	public static Properties prop=new Properties();
	public static FileInputStream ip; 
	public static String browser="";
	public static String url="";
	
	public static String email="";
	public static String password="";
	
	public static String platform="";
	
	public static final Logger logger = Logger.getLogger(Web.class.getName());
	
	public static String chromedriver =(OS.contains("MAC") ? "./src/test/resources/drivers/chromedriver" : OS.contains("LINUX")?"./src/test/resources/drivers/chromedriver_linux"
         :System.getProperty("user.dir")+"/Drivers");
	
	public static final ChromeDriverService chromeservice = new ChromeDriverService.Builder().usingDriverExecutable(new File(chromedriver)).usingAnyFreePort().build();
	
	public static AppiumDriver driver=null;
	
	
	
	//Load Properties
		public static void loadprop(Properties prop) throws IOException
		{
			//prop=new Properties();
			ip = new FileInputStream("./src/test/resources/Config.properties");
			prop.load(ip);
			
			browser= prop.getProperty("Browser");
			url= prop.getProperty("URL");
			email= prop.getProperty("Email");
			password= prop.getProperty("Password");
			platform= prop.getProperty("Platform");
				
		}
		
		//Wait
		public void Wait() throws Exception
		{
			Thread.sleep(5000);
		}
		
		//Initiate WebDriver
		public static synchronized void initiateWebDriver() throws IOException
		{
			
				DesiredCapabilities cap= new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME,"Pixel");
				cap.setCapability(MobileCapabilityType.UDID,"emulator-5554");
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
				cap.setCapability(MobileCapabilityType.PLATFORM_VERSION,"11");
				cap.setCapability(MobileCapabilityType.BROWSER_NAME,"CHROME");
				cap.setCapability("chromedriverExecutable","/Users/praga/eclipse-workspace/sample/src/test/resources/chromedriver");
				
				URL url = new URL("http://127.0.01:4723/wd/hub");
					
				driver = new AndroidDriver(url,cap);
				System.out.println("Inside Appium");
				ChromeOptions options=new ChromeOptions();
				options.addArguments("start-maximized");
				driver.manage().window().maximize();
			
		}
		
		//Navigate to URL
		public static void navigateURL()throws Exception
		{
					
			try 
			{
			if(!Library.isInvalid(url))
			{
				driver.get(url);
				logger.info("Navigated to url:	"+ url + " successfully");
				Library.logResult("User should be navigated to the URL"+ url,"Navigation should be successful","Navigation successful",Constants.PASS);		
					
			}
			}
			catch(Exception ex)
			{
				logger.info("Error occured while navigating to the url"+ url + " "+ex.getMessage());
				Library.logResult("User should be navigated to the URL"+ url,"Navigation should be successful","Navigation failed", Constants.FAIL);
			}
		}
		
		
		//Getting a WebElement
		public static WebElement getWebElement(AppiumDriver driver, String locatorTypeAndValue, boolean throwException, int numberofSeconds) throws Exception
		{
			By by= getByObject(locatorTypeAndValue);
			JavascriptExecutor js=(JavascriptExecutor) driver;
			WebElement we;
			Duration waitTime;
			waitTime= Duration.ofSeconds(20);
			try 
			{
				WebDriverWait wait= new WebDriverWait(driver,waitTime);
				we = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				js.executeScript("arguments[0].scrollIntoView();",we);
				//Thread.sleep(100);
				js.executeScript("window.scrollBy(0,-300)");
				//js.executeScript("window.scrollTo(arguments[0],arguments[1])",we.getLocation().x+150,we.getLocation().y+150);
				
				return we;
				
			}catch(Exception ex)
			{
				if(throwException)
				{
				StringBuilder exceptionMessage = new StringBuilder();
				exceptionMessage = exceptionMessage.append(System.lineSeparator());
				exceptionMessage = exceptionMessage.append("There was an issue with the WebElement to be located by the locator:");
				exceptionMessage = exceptionMessage.append(System.lineSeparator());
				exceptionMessage = exceptionMessage.append(locatorTypeAndValue);
				exceptionMessage = exceptionMessage.append(System.lineSeparator());
				exceptionMessage = exceptionMessage.append("Clue:Either WebElemnt does not exist in App or locator information in OR.Java is incorrect. See below for more information");
				exceptionMessage = exceptionMessage.append(System.lineSeparator());
				exceptionMessage = exceptionMessage.append(ex.getMessage());
				throw new Exception (exceptionMessage.toString());
				}
				else
				{
					return null;
				}
			}
		}
			
		
		//Highlight	
		public static void highlight(WebDriver driver,WebElement element) throws Exception
		{
			if(enableHighlighting)
			{
				JavascriptExecutor js=(JavascriptExecutor) driver;
				for(int i=1;i<=1;i++) 
				{
					js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid red;');",element);
					Thread.sleep(100);
					js.executeScript("arguments[0].setAttribute('style','');",element);
					Thread.sleep(100);
					js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid red;');",element);
					Thread.sleep(100);
				}
			}
		}
		
		
		//UnHighlight	
			public static void unHighlight(WebDriver driver,WebElement element) throws Exception
			{
					JavascriptExecutor js=(JavascriptExecutor) driver;
					js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid red;');",element);
					Thread.sleep(100);
					js.executeScript("arguments[0].setAttribute('style','');",element);
					Thread.sleep(100);		
					
			}
				
		//Highlight	for ScreenShot
			public static void highlightForScreenshot(WebDriver driver,WebElement element) throws Exception
			{
				JavascriptExecutor js=(JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid red;');",element);
			}
			
		
		//Take ScreenShot
			public static String takeScreenshot(WebDriver driver,String imageName)throws Exception
			{
							
				int i=1;
				
				File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				File destinationFile= new File(screenshotsLocation+String.valueOf(i)+"_"+imageName);			
				try 
				{
				FileUtils.copyFile(srcFile,destinationFile);
				i++;
				} 
				catch (Exception e) 
				{
				e.printStackTrace();
				}
				
				return destinationFile.getAbsolutePath();
			}
			
		
		public static boolean Click(AppiumDriver driver, String locatorTypeAndValue, boolean throwException, int numberofSeconds) throws Exception
		{
			WebElement we=getWebElement(driver,locatorTypeAndValue,throwException,numberofSeconds);
			if(we!=null)
			{
				highlight(driver,we);
				if(loglibraryActionWithScreenshots)
				{
					highlightForScreenshot(driver,we);
					logResult("Click on Object	"+locatorTypeAndValue,"	Click successfully","Clicked successfully",Constants.PASS,driver);
					unHighlight(driver,we);
				}
				we.click();
				return true;
			}
			else
			{
				return false;
			}
			
		
		}
		
		

		//Getting an Object
		public static By getByObject(String locatorTypeAndValue)
		{
			By by;
			
			String locatorType=locatorTypeAndValue.split("~")[0];
			String locatorValue=locatorTypeAndValue.split("~")[1];
			
			if(locatorType.equals(Constants.CLASSNAME)) 
				{by =By.className(locatorValue);}
		 else if(locatorType.equals(Constants.CSSSELECTOR)) 
				{by =By.cssSelector(locatorValue);}
		 else if(locatorType.equals(Constants.ID)) 
				{by =By.id(locatorValue);}
		 else if(locatorType.equals(Constants.LINKTEXT)) 
				{by =By.linkText(locatorValue);}
		 else if(locatorType.equals(Constants.NAME)) 
				{by =By.name(locatorValue);}
		 else if(locatorType.equals(Constants.PARTIALLINKTEXT)) 
				{by =By.partialLinkText(locatorValue);}
		 else if(locatorType.equals(Constants.TAGNAME)) 
				{by =By.tagName(locatorValue);}
		 else if(locatorType.equals(Constants.XPATH)) 
				{by =By.xpath(locatorValue);
				}
			else		
				{return null;}
			
		return by;
		}
		
		
		
		public static String getText(AppiumDriver driver, String locatorTypeAndValue, boolean throwException, int numberofSeconds) throws Exception
		{
			String returnValue="";
			WebElement we=getWebElement(driver,locatorTypeAndValue,throwException,numberofSeconds);
			
			if(we!=null)
			{
				highlight(driver,we);
				if(loglibraryActionWithScreenshots)
				{
					highlightForScreenshot(driver,we);
					logResult("Reading from Text Object"+ locatorTypeAndValue.replaceAll("~","="),"	Read Text Successfully","Text Reading Successfull",Constants.PASS,driver);
					unHighlight(driver,we);
				}
				returnValue=we.getText().toString();
				System.out.println("Text is: "+returnValue);
				return returnValue;
			}
			else
			{
				return "";
			}
			
			
		}
		
		//Click
		public static boolean sendKeys(AppiumDriver driver, String locatorTypeAndValue, String inputText, boolean throwException, int numberofSeconds) throws Exception
		{
			WebElement we=getWebElement(driver,locatorTypeAndValue,throwException,numberofSeconds);
			if(we!=null)
			{
				highlight(driver,we);
				we.clear();
				we.sendKeys(inputText);
				if(loglibraryActionWithScreenshots)
				{
					highlightForScreenshot(driver,we);
					logResult("Enter Text	"+inputText+ "	into Object"+ locatorTypeAndValue.replaceAll("~","="),"	Text Entry Successful","Text entered Successfully",Constants.PASS,driver);
					unHighlight(driver,we);
				}
				return true;
			}
			else
			{
				return false;
			}
			
		
		}
		

		

}
