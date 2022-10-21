package com.actiondriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging. Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.utility.Constants;
import com.utility.Library;

import io.restassured.RestAssured;
import io.restassured.response. Response;
import io.restassured.http.ContentType;
//import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;

public class RestAssuredAPI extends Library { 

	private static Logger logger =Logger.getLogger(RestAssuredAPI.class.getName());
	
	public static Response response=null;
	public static RequestSpecification request;
	
	
	public static Properties prop=new Properties();
	public static FileInputStream ip; 
	public static String baseURI="";
	
	//Load Properties
	public static void loadprop(Properties prop) throws IOException
	{
		//prop=new Properties();
		ip = new FileInputStream("./src/test/resources/Config.properties");
		prop. load (ip);
		baseURI=prop.getProperty("baseURI");
	}
	
	public static void setURI (String baseURI)
	{
		request=RestAssured.given().baseUri(baseURI);
	}
	

	public static Response getRequest (String resource, HashMap<String, String> headers)
	{
	try 
	{
		response =null;
		response = RestAssured.given(request).headers (headers). request (Method.GET, resource);
		logger.info("This method is used to perform GET Operation");
	}
	catch(Exception e)
	{
		logger.throwing("RestAssuredAPI","getRequest",e);
		Library.logResult("Uncaught Error","Step-"+String.join("\n",ExceptionUtils.getStackFrames(e)).split("at ")[1].trim(),
		new Exception().initCause(e).getCause().toString(),Constants.FAIL);
	}

	return response;
	}
	
	
	public static Response postRequest (HashMap<String,String> headers, String payLoad, String resource)
	{
	try 
	{
		response =null;
		response = RestAssured.given(request).headers(headers).body(payLoad).request(Method.POST,resource);
		logger.info("This method is used to perform POST Operation");
	}
	catch (Exception e)
	{
		logger.throwing("RestAssuredAPI","postRequest",e);
		Library.logResult("Uncaught Error","Step-"+String.join("\n",ExceptionUtils.getStackFrames(e)).split("at ")[1].trim(),
		new Exception().initCause(e).getCause().toString(),Constants.FAIL);
	}
	return response;
	}
	
	public static HashMap<String,String> defaultHeaders()
	{
	HashMap<String,String> headers = new HashMap<String, String>();
	return headers;
	}
	
	public static List<String> getResponseList(Response response,String responsekey)
	{
	return response.jsonPath().getList(responsekey);
	}
	
	public static void setContentType(String contentType)
	{
		try
		{
			System.out.println(RestAssured.given().contentType(ContentType.JSON));
		}
		catch(Exception ex)
		{
			logger.info("Getting Exception"+ex);
		}
	}

}
