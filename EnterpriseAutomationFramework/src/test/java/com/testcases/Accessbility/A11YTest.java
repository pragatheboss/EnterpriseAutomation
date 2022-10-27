package com.testcases.Accessbility;

import java.lang.reflect.Method;
//import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.actiondriver.A11Y;
//import com.actiondriver.Web;
import com.utility.Library;


public class A11YTest extends A11Y{
	
	public ThreadLocal<String> testClassMethod = new ThreadLocal<String>();
	private static Logger logger =Logger.getLogger(A11YTest.class);	
	
	@BeforeTest
	public void loadProp()throws Exception
	{	
		
		A11Y.loadprop(prop);
		System.out.println("Before Test Method");
	}
	
	@Test(enabled=true)
	public void A11YTesting(Method testMethod) throws Exception 
	{
		try {		
			
			logger.info("**************TEST CASE STARTS**************************");
			
			//Launch
			Library.tcStartTime.put(testMethod.getName(),Library.getCurrentTimeStamp());
			
			//Initiate WebDriver
			A11Y.initiateWebDriver();
			Wait();
			
			//Navigate to URL
			A11Y.navigateURL();
			Wait();
			
			//Accessibility errors analysis
			A11Y.a11yanalyze();
			
		}
		catch(Exception ex)
		{
			
		}
			
	}
	

	@AfterTest
	public void afterTest()throws Exception
	{
		System.out.println("After Test Method");		
		
		if(driver!=null)
		driver.quit();
		
		
	}
	

}
