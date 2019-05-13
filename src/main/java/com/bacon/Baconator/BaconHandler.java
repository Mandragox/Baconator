package com.bacon.Baconator;

import java.sql.Timestamp;
import java.util.Date;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**{@code BaconHandler} is a static class for getting JSON data from baconipsum and converting it into HTML code
 *@author Adam Priesnitz */
public class BaconHandler
{
	/**{@code tab} produces HTML tabs, used for better code readibility
	 *@param number number of tabs 
	 *@return string made from HTML tabs*/
	private static String tab(int number)
	{
		String output = "";
		for(int i = 0; i < number; i++)
		{
			output += "&emsp;";
		}
		return output;
	}
	
	/**{@code streamToString} converts InputStream into String
	 * @param inputStream InputStream which we want to convert
	 * @return IntputStream converted into String*/
	private static String streamToString(InputStream inputStream) 
	{
		String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
		return text;
	}
	
	/**{@code ProduceBacon} produces HTML output of dates and data from baconipsum
	 *@throws IOException when something goes wrong with reading from baconipsum
	 *@return HTML code with data from baconipsum and time when it started and ended 
	 */
	public static String ProduceBacon() throws IOException
	{
		Date start =  new Date();
		
		//Getting JSON string from baconipsum
		String json = null;
	    try 
	    {
	      URL url = new URL("https://baconipsum.com/api/?type=all-meat&paras=1");
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      connection.setDoOutput(true);
	      connection.setInstanceFollowRedirects(false);
	      connection.setRequestMethod("GET");
	      connection.setRequestProperty("Content-Type", "application/json");
	      connection.setRequestProperty("charset", "utf-8");
	      connection.connect();
	      InputStream inStream = connection.getInputStream();
	      json = streamToString(inStream);	      
	    } 
	    catch (IOException ex) 
	    {
	      ex.printStackTrace();
	      throw new IOException();
	    }
		
		Date end =  new Date();		
		//Making output
		String output = tab(3) + "{<br>";
		output += tab(4) + "\"start\": " + Timestamp.from(start.toInstant()) + ",<br>";
		output += tab(4) + "\"end\": " + Timestamp.from(end.toInstant()) + ",<br>";
		output += tab(4) + "\"data\": " + json + ",<br>";
		output += tab(3) + "}";
		
		return output;
	}
	
	
}
