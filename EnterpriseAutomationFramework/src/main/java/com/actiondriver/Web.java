package com.actiondriver;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.text.SimpleDateFormat;
import java.time.Duration;
/*import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;*/
import java.util.Properties;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.objectrepository.OR;
//import com.google.common.io.Files;
import com.utility.Constants;
import com.utility.Library;


public class Web extends Library 
{

	public static Properties prop=new Properties();
	public static FileInputStream ip; 
	public static String browser="";
	public static String url="";
	
	public static String email="";
	public static String password="";
	

		
	public static final Logger logger = Logger.getLogger(Web.class.getName());
	

	
	
	
	private static String chromedriver =(OS.contains("WINDOWS") ? "./src/test/resources/drivers/chromedriver.exe" : OS.contains("LINUX")?"./src/test/resources/drivers/chromedriver_linux"
			:System.getProperty("user.dir")+"/Drivers");
	
	public static final ChromeDriverService chromeservice = new ChromeDriverService.Builder().usingDriverExecutable(new File(chromedriver)).usingAnyFreePort().build();
	
	public static RemoteWebDriver driver = null;
	
	/*public static WebDriver getDriver() {return driver.get();}
	
	static void setWebDriver(RemoteWebDriver rwdriver) 
	{
		driver.set(rwdriver);
		
	}*/
	

	//Load Properties
	public static void loadprop(Properties prop) throws IOException
	{
		//prop=new Properties();
		ip = new FileInputStream("./src/test/resources/Config.properties");
		prop.load(ip);
		
		browser= prop.getProperty("Browser");
		System.out.println("browser"+browser);
		
		url= prop.getProperty("URL");
		System.out.println("URL"+url);
		
		email= prop.getProperty("Email");
		System.out.println("Email"+email);
		
		password= prop.getProperty("Password");
		System.out.println("Password"+password);
		
		
	}
	
	//Getting a WebElement
	public static WebElement getWebElement(WebDriver driver, String locatorTypeAndValue, boolean throwException, int numberofSeconds) throws Exception
	{
		By by= getByObject(locatorTypeAndValue);
		//System.out.println("by"+by);
		WebElement we;
		Duration waitTime;
		waitTime= Duration.ofSeconds(20);
		//System.out.println("waitTime"+waitTime);
		try 
		{
			WebDriverWait wait= new WebDriverWait(driver,waitTime);
			//System.out.println("wait"+wait);
			we = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
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
	   else	if(locatorType.equals(Constants.ID)) 
			{by =By.id(locatorValue);}
	   else	if(locatorType.equals(Constants.LINKTEXT)) 
			{by =By.linkText(locatorValue);}
	   else	if(locatorType.equals(Constants.NAME)) 
			{by =By.name(locatorValue);}
	   else	if(locatorType.equals(Constants.PARTIALLINKTEXT)) 
			{by =By.partialLinkText(locatorValue);}
	   else	if(locatorType.equals(Constants.TAGNAME)) 
			{by =By.tagName(locatorValue);}
	   else	if(locatorType.equals(Constants.XPATH)) 
			{by =By.xpath(locatorValue);
			}
		else		
			{return null;}
		
	return by;
	}
	
	//Initiate WebDriver
	public static synchronized void initiateWebDriver() throws IOException
	{	
		switch(browser)
		{
		case "CHROME":
			/*if(Library.testExecutionChannel.equalsIgnoreCase(Constants.GRID))*/
			System.setProperty("webdriver.chrome.driver",chromedriver);
			ChromeOptions options=new ChromeOptions();
			options.addArguments("start-maximized");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			logger.info("Chromedriver");	
			
			break;
			
		default:
			logger.info("Wrong browsername passed. Please use proper Browser Name");
			break;
		}
		
		System.out.println("Driver:		"+driver);
		
	
		
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
			/*int min=10;
			int max=10000;
			int random_int=(int) Math.floor(Math.random()*(max-min+1)+min);
			TakesScreenshot ts= ((TakesScreenshot) driver);
			File sourceFile= ts.getScreenshotAs(OutputType.FILE);
			Path sourcePath=sourceFile.toPath();
			String file = screenshotsLocation+String.valueOf(random_int)+"_"+imageName;
			System.out.println("filepath"+file);
			File destinationFile= new File("C:\\Users\\pragadeeswaran.s\\eclipse-workspace\\enterpriseautomationframework\\Screenshots\\"+imageName);
			Path destinationPath=sourceFile.toPath();
			Files.copy(sourcePath,destinationPath,java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			
			return "File://"+destinationFile.getAbsolutePath();*/
			
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
	
	//Click
	public static boolean Click(WebDriver driver, String locatorTypeAndValue, boolean throwException, int numberofSeconds) throws Exception
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
	
	//Check if Object Exist
	public static boolean doesObjectExist(WebDriver driver, String locatorTypeAndValue, int numberofSeconds) throws Exception
	{
		By by= getByObject(locatorTypeAndValue);
		
		Duration waitTime;
		waitTime= Duration.ofSeconds(numberofSeconds);
		boolean objectFound;
		try 
		{
			WebDriverWait wait= new WebDriverWait(driver,waitTime);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
			objectFound=true;
			WebElement we=getWebElement(driver,locatorTypeAndValue,true,numberofSeconds);
			highlight(driver,we);
		}
		catch(Exception ex)
		{
			objectFound=false;
		}
		return objectFound;
	}

	//Click
		public static boolean sendKeys(WebDriver driver, String locatorTypeAndValue, String inputText, boolean throwException, int numberofSeconds) throws Exception
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