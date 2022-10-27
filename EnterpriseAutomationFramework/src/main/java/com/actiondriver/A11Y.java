package com.actiondriver;

import com.utility.Constants;
import com.utility.Library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;


import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.deque.axe.AXE;


public class A11Y extends Library{

	//WebDriver driver;
	private static Logger logger =Logger.getLogger(A11Y.class.getName());
	private static final URL axejs_url =A11Y.class.getResource("/axe.min.js");
	
	public static Properties prop=new Properties();
	public static FileInputStream ip; 
	
	public static String browser="";
	public static String url="";
	
	private static String chromedriver =(OS.contains("MAC") ? "./src/test/resources/drivers/chromedriver" : OS.contains("LINUX")?"./src/test/resources/drivers/chromedriver_linux"
            :System.getProperty("user.dir")+"/Drivers");
	
	public static final ChromeDriverService chromeservice = new ChromeDriverService.Builder().usingDriverExecutable(new File(chromedriver)).usingAnyFreePort().build();
	
	public static RemoteWebDriver driver = null;
	
	public static String filename="./src/test/resources/A11YLogs.txt";
	
	
	//Load Properties
		public static void loadprop(Properties prop) throws IOException
		{
			//prop=new Properties();
			ip = new FileInputStream("./src/test/resources/Config.properties");
			prop.load(ip);
			
			browser= prop.getProperty("Browser");
			url= prop.getProperty("URL");
			
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
			
			System.out.println("Driver:"+driver);
			
		}
		
		//Accessibility analyse all errors
		public static void a11yanalyze() throws IOException
		{
			
			JSONObject responseJSON =new AXE.Builder(driver, axejs_url).analyze();
			JSONArray violations=responseJSON.getJSONArray("violations");
			Writer wr = new FileWriter(filename,true);
			try
			{
				if(violations.length() ==0)
				{
					System.out.println("no errors");
				}
				else
				{
					AXE.writeResults("a11yanalyze", responseJSON);
					logger.info(AXE.report(violations));
					wr.write(url+"\r\n"+new Exception().getStackTrace()[0].getMethodName()+"\r\n");
					wr.write("********************************************************************************+\r\n");
					wr.write(AXE.report(violations));
					//Assert.assertTrue(false, AXE.report(violations));
					wr.flush();
					wr.close();
				}
			}
			catch(Exception ex)
			{
				logger.throwing("A11Y","a11yanalyze",ex);
				Library.logResult("Uncaught Error","Accessibilty Errors",new Exception().initCause(ex).getCause().toString(),Constants.FAIL);
			}
			
		}
		
		//Wait
		public void Wait() throws Exception
		{
			Thread.sleep(5000);
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

}
