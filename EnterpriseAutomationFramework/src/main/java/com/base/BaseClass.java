package com.base;


/*import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;*/
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.actiondriver.Web;
import com.objectrepository.OR;
//import com.utility.Constants;
//import com.utility.Library;
//import com.utility.Library;


public class BaseClass extends Web {
	
	@BeforeTest
	public void loadProp()throws Exception
	{
		System.out.println("Before Test Method");
		Web.loadprop(prop);
	}
			
	@Test
	public void loadConfig() throws Exception 
	{
		
	logger.info("**************TEST STARTS**************************");
	
	//Launch
	
	//Initiate WebDriver
	Web.initiateWebDriver();
	
	//Navigate to URL
	Web.navigateURL();
	
	//Sign-In
		
	//Validate SignIn and Click SignIn
	boolean linkSignIn= Web.doesObjectExist(driver, OR.AutoPrac.ObjectRep.linkSignIn, 20);
	if(linkSignIn)
	{
		Web.Click(driver,OR.AutoPrac.ObjectRep.linkSignIn,true,20);
		Thread.sleep(5000);
	}
		
	//Validate Email and Type Mail Id
	boolean txtEMail= Web.doesObjectExist(driver, OR.AutoPrac.ObjectRep.txtEMail, 20);
	if(txtEMail)
	{
		Web.sendKeys(driver, OR.AutoPrac.ObjectRep.txtEMail, Web.email,true,20);
		Thread.sleep(5000);
	}
	
	
	//Validate Password and Type Password
	boolean txtPassword= Web.doesObjectExist(driver, OR.AutoPrac.ObjectRep.txtPassword, 20);
	if(txtPassword)
	{
		Web.sendKeys(driver, OR.AutoPrac.ObjectRep.txtPassword, Web.password,true,20);
		Thread.sleep(5000);
	}
	

	//Click SignIn
	boolean btnSignIn= Web.doesObjectExist(driver, OR.AutoPrac.ObjectRep.btnSignIn, 20);
	if(btnSignIn)
	{
		Web.Click(driver,OR.AutoPrac.ObjectRep.btnSignIn,true,20);
		Thread.sleep(5000);
	}
	
	//Enter (Dress) in Search Text Box 
	boolean txtSearch= Web.doesObjectExist(driver, OR.AutoPrac.ObjectRep.txtSearch, 20);
	if(txtSearch)
	{
	
	Web.Click(driver,OR.AutoPrac.ObjectRep.txtSearch,true,20);
	Thread.sleep(5000);
	
	Web.sendKeys(driver, OR.AutoPrac.ObjectRep.txtSearch, "Dress",true,20);
	Thread.sleep(5000);
	
	}
		
	//Click Search
	boolean btnSearchSubmit= Web.doesObjectExist(driver, OR.AutoPrac.ObjectRep.btnSearchSubmit, 20);
	if(btnSearchSubmit)
	{
	Web.Click(driver,OR.AutoPrac.ObjectRep.btnSearchSubmit,true,20);
	Thread.sleep(5000);
	}
	
	
	System.out.println("Before AddtoCart");
	
	//Add to Cart
	boolean btnAddtoCart= Web.doesObjectExist(driver, OR.AutoPrac.ObjectRep.btnAddtoCart, 20);
	if(btnAddtoCart)
	{
	Web.Click(driver,OR.AutoPrac.ObjectRep.btnAddtoCart,true,20);
	Thread.sleep(5000);
	}
	
	System.out.println("After AddtoCart");
	
	}
	
}
	


	
