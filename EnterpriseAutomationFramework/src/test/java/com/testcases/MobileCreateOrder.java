package com.testcases;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.actiondriver.Mobile;
import com.actiondriver.Web;
import com.objectrepository.OR;
import com.utility.Constants;
import com.utility.Library;


public class MobileCreateOrder extends Mobile{

	
	public ThreadLocal<String> testClassMethod = new ThreadLocal<String>();
	private static Logger logger =Logger.getLogger(CreateOrder.class);	
	
	@BeforeTest
	public void loadProp()throws Exception
	{	
		//Library.sendEmail();
		Mobile.loadprop(prop);
		System.out.println("Before Test Method");
	}
	
	
@Test(enabled=true)
	
	//@Test
	
	public void createOrder(Method testMethod) throws Exception 
	{
	
	try {		
		
		logger.info("**************TEST CASE STARTS**************************");
		
		screenshotsLocation+=testMethod.getName()+"//";
		
		//Launch
		Library.tcStartTime.put(testMethod.getName(),Library.getCurrentTimeStamp());
		
		//Initiate WebDriver
		Mobile.initiateWebDriver();
		Wait();
		
		//Navigate to URL
		Mobile.navigateURL();
		Wait();
		
		//Validate SignIn and Click SignIn
		Mobile.Click(driver,OR.AutoPrac.ObjectRep.linkSignIn,true,20);
		Wait();
		
		//Validate Email and Type Mail Id
		Mobile.sendKeys(driver, OR.AutoPrac.ObjectRep.txtEMail, Mobile.email,true,20);
		Wait();

		//Validate Password and Type Password
				Mobile.sendKeys(driver, OR.AutoPrac.ObjectRep.txtPassword, Mobile.password,true,20);
				Wait();
				
				//Click SignIn
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.btnSignIn,true,20);
				Wait();
				
				//Enter (Dress) in Search Text Box 
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.txtSearch,true,20);
				Wait();
				
				Mobile.sendKeys(driver, OR.AutoPrac.ObjectRep.txtSearch, "Dress",true,20);
				Wait();
						
				//Click Search
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.btnSearchSubmit,true,20);
				Wait();
				
				System.out.println("Before AddtoCart");
				
				//Change to Grid View -- In Mobile the view is different so the gride view is NA as in Web view
				//Mobile.Click(driver,OR.AutoPrac.ObjectRep.btnGridView,true,20);
				//Wait();
				
				//Add to Cart
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.btnAddtoCart,true,20);
				Wait();
				
				System.out.println("After AddtoCart");
				
				//Proceed to CheckOut
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.btnProceedToCheckout,true,20);
				Wait();
				
				//GetTotalAmount
				Mobile.getText(driver,OR.AutoPrac.ObjectRep.txtTotalAmount,true,20);
				Wait();
				
				//Proceed To Checkout in SignIn Page
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.btnProceedToCheckoutSignIn,true,20);
				Wait();
				
				//Proceed To Checkout in Process Address Page
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.btnProceedToCheckoutProcessAddress,true,20);
				Wait();

				//Click Terms Check box
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.checkboxTerms,true,20);
				Wait();
				
				//Proceed To Checkout in Process Shipping Page
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.btnProceedToCheckoutShipping,true,20);
				Wait();
				
				//Pay by Bank Wire
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.lnkPaybyBankWire,true,20);
				Wait();
				
				//Confirm Order
				Mobile.Click(driver,OR.AutoPrac.ObjectRep.btnConfirmOrder,true,20);
				Wait();
				
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
					Library.sendEmail();
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
