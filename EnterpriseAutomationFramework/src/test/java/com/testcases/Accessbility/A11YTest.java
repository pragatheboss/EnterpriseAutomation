package com.testcases.Accessbility;

import java.lang.reflect.Method;
//import java.util.Properties;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.actiondriver.A11Y;
import com.actiondriver.Web;
import com.objectrepository.OR;
import com.utility.Constants;
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
			
			screenshotsLocation+=testMethod.getName()+"//";
			
			//Launch
			Library.tcStartTime.put(testMethod.getName(),Library.getCurrentTimeStamp());
			
			//Initiate WebDriver
			A11Y.initiateWebDriver();
			Wait();
			
			//Navigate to URL
			A11Y.navigateURL();
			Wait();
			A11Y.a11yanalyze();
			
			
			//Validate SignIn and Click SignIn
			Web.Click(driver,OR.AutoPrac.ObjectRep.linkSignIn,true,20);
			Wait();
			A11Y.a11yanalyze();
			
			
			//Validate Email and Type Mail Id
			Web.sendKeys(driver, OR.AutoPrac.ObjectRep.txtEMail, A11Y.email,true,20);
			Wait();

			//Validate Password and Type Password
			Web.sendKeys(driver, OR.AutoPrac.ObjectRep.txtPassword, A11Y.password,true,20);
			Wait();
			
			//Click SignIn
			Web.Click(driver,OR.AutoPrac.ObjectRep.btnSignIn,true,20);
			Wait();
			A11Y.a11yanalyze();
			

			//Enter (Dress) in Search Text Box 
			Web.Click(driver,OR.AutoPrac.ObjectRep.txtSearch,true,20);
			Wait();
			
			Web.sendKeys(driver, OR.AutoPrac.ObjectRep.txtSearch, "Dress",true,20);
			Wait();
					
			//Click Search
			Web.Click(driver,OR.AutoPrac.ObjectRep.btnSearchSubmit,true,20);
			Wait();
			A11Y.a11yanalyze();
			
			System.out.println("Before AddtoCart");
			
			//Change to Grid View
			Web.Click(driver,OR.AutoPrac.ObjectRep.btnGridView,true,20);
			Wait();
			
			//Add to Cart
			Web.Click(driver,OR.AutoPrac.ObjectRep.btnAddtoCart,true,20);
			Wait();
			A11Y.a11yanalyze();
			
			System.out.println("After AddtoCart");
			
			//Proceed to CheckOut
			Web.Click(driver,OR.AutoPrac.ObjectRep.btnProceedToCheckout,true,20);
			Wait();
			A11Y.a11yanalyze();
			
			//GetTotalAmount
			Web.getText(driver,OR.AutoPrac.ObjectRep.txtTotalAmount,true,20);
			Wait();
			
			//Proceed To Checkout in SignIn Page
			Web.Click(driver,OR.AutoPrac.ObjectRep.btnProceedToCheckoutSignIn,true,20);
			Wait();
			A11Y.a11yanalyze();
			
			//Proceed To Checkout in Process Address Page
			Web.Click(driver,OR.AutoPrac.ObjectRep.btnProceedToCheckoutProcessAddress,true,20);
			Wait();
			A11Y.a11yanalyze();

			//Click Terms Check box
			Web.Click(driver,OR.AutoPrac.ObjectRep.checkboxTerms,true,20);
			Wait();
			A11Y.a11yanalyze();
			
			//Proceed To Checkout in Process Shipping Page
			Web.Click(driver,OR.AutoPrac.ObjectRep.btnProceedToCheckoutShipping,true,20);
			Wait();
			A11Y.a11yanalyze();
			
			//Pay by Bank Wire
			Web.Click(driver,OR.AutoPrac.ObjectRep.lnkPaybyBankWire,true,20);
			Wait();
			
			//Confirm Order
			Web.Click(driver,OR.AutoPrac.ObjectRep.btnConfirmOrder,true,20);
			Wait();
			A11Y.a11yanalyze();
			
			//Get Order Confirmation
			String OrderText= Web.getText(driver,OR.AutoPrac.ObjectRep.txtOrder,true,20);
			boolean Orderplaced=false;
			
			if(OrderText.contains("Your order on My Store is complete")) 
			{
				Library.logResult("Order on My Store is complete", "Your order on My Store is complete", "Order on My Store is complete ", Constants.PASS);
				Orderplaced=true;
				
			}
			else
			{
				Library.logResult("Order on My Store is complete", "Your order on My Store is complete", "Order on My Store is Not complete ", Constants.FAIL);
				Orderplaced=false;
			}
			
			//SendMail
			if(Orderplaced)
			{
				//Library.sendEmail();
			}
			
			}
			catch(Exception ex)
			{
				logger.error("Exception Message from Test"+ex.getMessage());
				Library.logResult("Verify Exceptions", "No Exceptions",ex.getMessage(), Constants.FAIL);
			}
			finally
			{
				logger.info("Completed Execution of Test Class : "+this.getClass().getName());
				logger.info("Completed Execution of Test case : Method : "+testMethod.getName());
						
				Library.tcEndTime.put(testMethod.getName(),Library.getCurrentTimeStamp());
				
				logger.info("Test case Starting Time : "+Arrays.asList(tcStartTime));
				logger.info("Test case Ending Time : "+Arrays.asList(tcEndTime));		
				
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
