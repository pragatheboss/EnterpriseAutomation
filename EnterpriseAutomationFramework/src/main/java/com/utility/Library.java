package com.utility;

import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.openqa.selenium.WebDriver;
import org.xml.sax.helpers.DefaultHandler;

import com.actiondriver.Web;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
//import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;



public class Library extends DefaultHandler
{

	private static Logger logger = Logger.getLogger(Library.class.getName());
	
	public static ConcurrentHashMap<String, HashMap<String,String>> screenshotListByTest = new ConcurrentHashMap<String, HashMap<String,String>>();
	public static LinkedHashMap<String,List<String>> resultsListByTestLHM = new LinkedHashMap<String,List<String>>();
	
	public static ConcurrentHashMap<String, Integer> stepsCountByTest = new ConcurrentHashMap<String,Integer>();
	public static ConcurrentHashMap<String, Integer> failedStepsCountbyTest = new ConcurrentHashMap<String,Integer>();
	public static ConcurrentHashMap<String,List<String>> testandvalidationresultsMapforEachformat = new ConcurrentHashMap<String,List<String>>();
	public static ConcurrentHashMap<String,List<String>> testcaseStatusMap = new ConcurrentHashMap<String,List<String>>();
	public static ConcurrentHashMap<String,List<String>> summaryReportGenerationMap = new ConcurrentHashMap<String,List<String>>();
	public static ConcurrentHashMap<String,List<String>> overallTestStatusMap = new ConcurrentHashMap<String,List<String>>();
	
	
	public static ConcurrentHashMap<String, String > tcStartTime = new ConcurrentHashMap<String,String>();
	public static ConcurrentHashMap<String, String > tcEndTime = new ConcurrentHashMap<String,String>();
	
	public static InheritableThreadLocal<String> testName = new InheritableThreadLocal<String>();
	
	
	public static ThreadLocal<String> browser = new ThreadLocal<String>();
	
	
	public static String resourceLocation = System.getProperty("user.dir").toString().replace("/","//")+"src/test/resources";
	public static String testEnvironment;
	public static String testReportsLocation;
	public static String screenshotsLocation="Screenshots\\"; 
	public static String outputFilesLocation;
	public static String application;
	

	
	public static boolean enableHighlighting = true;
	public static boolean loglibraryActionWithScreenshots = true;
	public static int defaultimpiclit = 5;
	
	public static String OS = System.getProperty("os.name").toUpperCase();
	public static Map<String,List<String>> resultsListByTest = Collections.synchronizedMap(resultsListByTestLHM);
	
	
	//public static String testExecutionChannel;
	
	public static boolean isInvalid(String input) throws Exception
	{
		boolean isInvalid = true;
		if(input!= null && input.trim().length()>0)
			isInvalid = false;
		return isInvalid;
		
	}
	
	public static void logResult(String descriptionOfStep, String expectedResult, String actualResult, String stepStatus)
	{
		int failedSteps =0;
		int stepCount =0;
		String screenShotName ="";
		String classAndMethodWhichCalledThis ="";
		
		try 
		{
			//classAndMethodWhichCalledThis=testName.get();
			if(stepStatus.equalsIgnoreCase("Fail"))
			{
				if(failedStepsCountbyTest.containsKey(classAndMethodWhichCalledThis))
				{
					failedSteps= (Integer)failedStepsCountbyTest.get(classAndMethodWhichCalledThis);
					failedSteps=failedSteps+1;
				}
				else
				{
					failedStepsCountbyTest.put(classAndMethodWhichCalledThis, 0);
					failedSteps= (Integer)failedStepsCountbyTest.get(classAndMethodWhichCalledThis);
					failedSteps=failedSteps+1;
				}
				failedStepsCountbyTest.put(classAndMethodWhichCalledThis,failedSteps);
			}
			
			if(stepsCountByTest.containsKey(classAndMethodWhichCalledThis))
			{
				stepCount= (Integer)stepsCountByTest.get(classAndMethodWhichCalledThis)+1;
			}
			else
			{
				stepsCountByTest.put(classAndMethodWhichCalledThis, 0);
				stepCount= (Integer)stepsCountByTest.get(classAndMethodWhichCalledThis)+1;
			}
			if(screenshotListByTest.get(classAndMethodWhichCalledThis)!=null)
			{
				screenshotListByTest.get(classAndMethodWhichCalledThis).put(Integer.toString(stepCount),screenShotName);
			}
			else
			{
				screenshotListByTest.put(classAndMethodWhichCalledThis, new HashMap<String,String>());
				screenshotListByTest.get(classAndMethodWhichCalledThis).put(Integer.toString(stepCount),screenShotName);
			}
			
			switch(stepStatus)
			{
			case "Pass":
				logger.info(stepCount	+" "+descriptionOfStep	+" "+ " "+	expectedResult+ " "+	actualResult+ " "+	stepStatus+ " "+ 	screenShotName+ " "+
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			break;
			
			case "Fail":
				logger.info(stepCount+" "+descriptionOfStep+" "+ " "+expectedResult+ " "+actualResult+ " "+stepStatus+ " "+ screenShotName+ " "+
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			break;
			
			case "Info":
				logger.info(stepCount+" "+descriptionOfStep+" "+ " "+expectedResult+ " "+actualResult+ " "+stepStatus+ " "+ screenShotName+ " "+
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			break;
			}
			
			if(!resultsListByTest.containsKey(classAndMethodWhichCalledThis))
			{
				List<String> currentTestResults = new ArrayList<String>();
				currentTestResults.add(classAndMethodWhichCalledThis
						+"\t"
						+stepCount
						+"\t"
						+descriptionOfStep
						+"\t"
						+expectedResult
						+"\t"
						+actualResult
						+"\t"
						+stepStatus
						+"\t"
						+screenShotName
						+"\t"
						+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
						
				resultsListByTest.put(classAndMethodWhichCalledThis,currentTestResults);
			}
			else
			{
				resultsListByTest.get(classAndMethodWhichCalledThis).add(
						classAndMethodWhichCalledThis
						+"\t"
						+stepCount
						+"\t"
						+descriptionOfStep
						+"\t"
						+expectedResult
						+"\t"
						+actualResult
						+"\t"
						+stepStatus
						+"\t"
						+screenShotName
						+"\t"
						+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			}
			stepsCountByTest.put(classAndMethodWhichCalledThis,stepCount);
		}
		catch(Exception ex)
		{
			System.out.println("Exception message from Log Result"+classAndMethodWhichCalledThis+"-"+ex.getMessage());
		}
				
	}
		
	public static void logResult(String descriptionOfStep, String expectedResult, String actualResult, String stepStatus, WebDriver driver)
	{
		int failedSteps =0;
		int stepCount =0;
		String screenShotName ="";			
		String classAndMethodWhichCalledThis = "";
		
		try 
		{
			//classAndMethodWhichCalledThis=testName.get();
			if(stepStatus.equalsIgnoreCase("Fail"))
			{
				if(failedStepsCountbyTest.containsKey(classAndMethodWhichCalledThis))
				{
					failedSteps= (Integer)failedStepsCountbyTest.get(classAndMethodWhichCalledThis);
					failedSteps=failedSteps+1;
				}
				else
				{
					failedStepsCountbyTest.put(classAndMethodWhichCalledThis, 0);
					failedSteps= (Integer)failedStepsCountbyTest.get(classAndMethodWhichCalledThis);
					failedSteps=failedSteps+1;
				}
				failedStepsCountbyTest.put(classAndMethodWhichCalledThis,failedSteps);
			}
			
			if(stepsCountByTest.containsKey(classAndMethodWhichCalledThis))
			{
				stepCount= (Integer)stepsCountByTest.get(classAndMethodWhichCalledThis)+1;
			}
			else
			{
				stepsCountByTest.put(classAndMethodWhichCalledThis, 0);
				stepCount= (Integer)stepsCountByTest.get(classAndMethodWhichCalledThis)+1;
			}
			screenShotName= "=HYPERLINK(\""+Web.takeScreenshot(driver, classAndMethodWhichCalledThis+"."+stepCount+".png")+"\",\"Click Here\")";
			if(screenshotListByTest.get(classAndMethodWhichCalledThis)!=null)
			{
				screenshotListByTest.get(classAndMethodWhichCalledThis).put(Integer.toString(stepCount),screenShotName);
			}
			else
			{
				screenshotListByTest.put(classAndMethodWhichCalledThis, new HashMap<String,String>());
				screenshotListByTest.get(classAndMethodWhichCalledThis).put(Integer.toString(stepCount),screenShotName);
			}
			
			switch(stepStatus)
			{
			case "Pass":
				logger.info(stepCount	+" "+descriptionOfStep	+" "+ " "+	expectedResult+ " "+	actualResult+ " "+	stepStatus+ " "+ 	screenShotName+ " "+
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			break;
			
			case "Fail":
				logger.info(stepCount+" "+descriptionOfStep+" "+ " "+expectedResult+ " "+actualResult+ " "+stepStatus+ " "+ screenShotName+ " "+
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			break;
			
			case "Info":
				logger.info(stepCount+" "+descriptionOfStep+" "+ " "+expectedResult+ " "+actualResult+ " "+stepStatus+ " "+ screenShotName+ " "+
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			break;
			}
			
			if(!resultsListByTest.containsKey(classAndMethodWhichCalledThis))
			{
				List<String> currentTestResults = new ArrayList<String>();
				currentTestResults.add(classAndMethodWhichCalledThis
						+"\t"
						+stepCount
						+"\t"
						+descriptionOfStep
						+"\t"
						+expectedResult
						+"\t"
						+actualResult
						+"\t"
						+stepStatus
						+"\t"
						+screenShotName
						+"\t"
						+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
						
				resultsListByTest.put(classAndMethodWhichCalledThis,currentTestResults);
			}
			else
			{
				resultsListByTest.get(classAndMethodWhichCalledThis).add(
						classAndMethodWhichCalledThis
						+"\t"
						+stepCount
						+"\t"
						+descriptionOfStep
						+"\t"
						+expectedResult
						+"\t"
						+actualResult
						+"\t"
						+stepStatus
						+"\t"
						+screenShotName
						+"\t"
						+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			}
			stepsCountByTest.put(classAndMethodWhichCalledThis,stepCount);
		}
		catch(Exception ex)
		{
			System.out.println("Exception message from Log Result"+classAndMethodWhichCalledThis+"-"+ex.getMessage());
		}
				
	}
		
	public static void logResult(String descriptionOfStep, String expectedResult, String actualResult, String stepStatus, String screenShotName)
	{
		int failedSteps =0;
		int stepCount =0;
		//String screenShotName ="";
		String classAndMethodWhichCalledThis ="";
		
		try 
		{
			//classAndMethodWhichCalledThis=testName.get();
			if(stepStatus.equalsIgnoreCase("Fail"))
			{
				if(failedStepsCountbyTest.containsKey(classAndMethodWhichCalledThis))
				{
					failedSteps= (Integer)failedStepsCountbyTest.get(classAndMethodWhichCalledThis);
					failedSteps=failedSteps+1;
				}
				else
				{
					failedStepsCountbyTest.put(classAndMethodWhichCalledThis, 0);
					failedSteps= (Integer)failedStepsCountbyTest.get(classAndMethodWhichCalledThis);
					failedSteps=failedSteps+1;
				}
				failedStepsCountbyTest.put(classAndMethodWhichCalledThis,failedSteps);
			}
			
			if(stepsCountByTest.containsKey(classAndMethodWhichCalledThis))
			{
				stepCount= (Integer)stepsCountByTest.get(classAndMethodWhichCalledThis)+1;
			}
			else
			{
				stepsCountByTest.put(classAndMethodWhichCalledThis, 0);
				stepCount= (Integer)stepsCountByTest.get(classAndMethodWhichCalledThis)+1;
			}
			//screenShotName= "=HYPERLINK(\""+Web.takeScreenshot(driver, classAndMethodWhichCalledThis+"."+stepCount+".png")+"\",\"Click Here\")";
			if(screenshotListByTest.get(classAndMethodWhichCalledThis)!=null)
			{
				screenshotListByTest.get(classAndMethodWhichCalledThis).put(Integer.toString(stepCount),screenShotName);
			}
			else
			{
				screenshotListByTest.put(classAndMethodWhichCalledThis, new HashMap<String,String>());
				screenshotListByTest.get(classAndMethodWhichCalledThis).put(Integer.toString(stepCount),screenShotName);
			}
			
			switch(stepStatus)
			{
			case "Pass":
				logger.info(stepCount	+" "+descriptionOfStep	+" "+ " "+	expectedResult+ " "+	actualResult+ " "+	stepStatus+ " "+ 	screenShotName+ " "+
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			break;
			
			case "Fail":
				logger.info(stepCount+" "+descriptionOfStep+" "+ " "+expectedResult+ " "+actualResult+ " "+stepStatus+ " "+ screenShotName+ " "+
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			break;
			
			case "Info":
				logger.info(stepCount+" "+descriptionOfStep+" "+ " "+expectedResult+ " "+actualResult+ " "+stepStatus+ " "+ screenShotName+ " "+
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			break;
			}
			
			if(!resultsListByTest.containsKey(classAndMethodWhichCalledThis))
			{
				List<String> currentTestResults = new ArrayList<String>();
				currentTestResults.add(classAndMethodWhichCalledThis
						+"\t"
						+stepCount
						+"\t"
						+descriptionOfStep
						+"\t"
						+expectedResult
						+"\t"
						+actualResult
						+"\t"
						+stepStatus
						+"\t"
						+screenShotName
						+"\t"
						+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
						
				resultsListByTest.put(classAndMethodWhichCalledThis,currentTestResults);
			}
			else
			{
				resultsListByTest.get(classAndMethodWhichCalledThis).add(
						classAndMethodWhichCalledThis
						+"\t"
						+stepCount
						+"\t"
						+descriptionOfStep
						+"\t"
						+expectedResult
						+"\t"
						+actualResult
						+"\t"
						+stepStatus
						+"\t"
						+screenShotName
						+"\t"
						+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
			}
			stepsCountByTest.put(classAndMethodWhichCalledThis,stepCount);
		}
		catch(Exception ex)
		{
			System.out.println("Exception message from Log Result"+classAndMethodWhichCalledThis+"-"+ex.getMessage());
		}
				
	}
	
	public static String getCurrentTimeStamp()
	{
		SimpleDateFormat sfdate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		String currentTimeStamp =sfdate.format(cal.getTime());
		return currentTimeStamp;
	}

	public static void sendEmail()
	{
		final String to = "pragatheboss@gmail.com";
		final String from = "pragatheboss@ymail.com";

		 String host = "smtp.mail.yahoo.com";
		 Properties properties = System.getProperties();

		 properties.put("mail.smtp.host", host);
		 properties.put("mail.smtp.port", "587");
		 properties.put("mail.smtp.starttls.enable", "true");
		 properties.put("mail.smtp.auth", "true");
		 
	     Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	             return new PasswordAuthentication("pragatheboss@ymail.com", "lndmgazfyctqehvo");
	         }
	     });
	     
	     session.setDebug(true);
	     try {
	         MimeMessage message = new MimeMessage(session);

	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         message.setSubject("Create Order Test NG");
	         message.setText("Order Created");

	         System.out.println("sending...");
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	     } catch (MessagingException mex) {
	         mex.printStackTrace();
	     }
	     
	}
	
}