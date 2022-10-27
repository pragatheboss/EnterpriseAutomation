package com.testcases.API;

import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

//import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import org.json.simple.parser.ParseException;

import com.actiondriver.RestAssuredAPI;
import com.utility.Constants;
import com.utility.Library;

import io.restassured.response.Response;

public class API extends RestAssuredAPI{

	public static ThreadLocal<String> testClassMethod = new ThreadLocal<String>();
	private static Logger logger =Logger.getLogger (API.class);
	
	@BeforeTest
	public void loadProp() throws Exception
	{
		System.out.println("Before API Test Method");
		RestAssuredAPI.loadprop(prop);
	}
	@Test(enabled=false)
	public void getData() throws Exception 
	{
		try 
		{
			RestAssuredAPI. setURI(baseURI);
			HashMap<String,String> headers = RestAssuredAPI.defaultHeaders();
			Response rs= RestAssuredAPI.getRequest("/api/users",headers);
			System.out.println("HTTP Status Code  of GET Request is =›\n"+rs.getStatusCode ());
			System.out.println("Response Body of GET Request is =› \n"+rs.getBody().asString());
			//System.out.println("Response Body GET Request is =› "+rs.asString());
			List<String> type = RestAssuredAPI.getResponseList(rs,"data.id");
			System.out.println("Num of Value in GET Request is =›\n"+type.toString());
		}
		catch(Exception ex)
		{
			logger. info("Exception message from test"+ex. getMessage());
			Library.logResult("Verify exceptions","No exceptions" , ex.getMessage (), Constants.FAIL);
		}
		finally
		{
			logger.info("\nCompleted GET execution quitting");
			System.out.println("\n Completed Execution of Test"+this.getClass().getName());
		}
	}
	
	@Test(enabled=true)
	public void postData() throws Exception 
	{
		try 
		{
			String json="{\n"
					+ "    \"email\": \"eve.holt@reqres.in\",\n"
					+ "    \"password\": \"pistol\"\n"
					+ "}";
			
			RestAssuredAPI. setURI(baseURI);
			HashMap<String,String> headers = RestAssuredAPI.defaultHeaders();
			headers.put("Content-type", "application/json");
			RestAssuredAPI.setContentType("application/json");
			Response rs= RestAssuredAPI.postRequest(headers,json,"/api/register");
			System.out.println("HTTP Status Code of POST Request is =›\n"+rs.getStatusCode ());
			System.out.println("Response Body of POST Request is =› \n"+rs.getBody().asString());
			//System.out.println("Response Body GET Request is =› "+rs.asString());
			List<String> type = RestAssuredAPI.getResponseList(rs,"data.id");
			System.out.println("Num of Value in POST Request is =›\n"+type.toString());
		}
		catch(Exception ex)
		{
			logger. info("Exception message from test"+ex. getMessage());
			Library.logResult("Verify exceptions","No exceptions" , ex.getMessage (), Constants.FAIL);
		}
		finally
		{
			logger.info("\nCompleted GET execution quitting");
			System.out.println("\n Completed Execution of Test"+this.getClass().getName());
		}
	}
	
	
	
}
