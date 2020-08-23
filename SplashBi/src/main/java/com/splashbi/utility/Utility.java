package com.splashbi.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class Utility {
public static String getValueFromPropertyFile(String path, String key) {
		
		Properties prop = new Properties();
		FileInputStream f = null;
		try {
			f = new FileInputStream(new File(path));
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
		     prop.load(f);
		}catch(IOException e) {
			System.out.println("Not able to load property file");
		}
				
		return prop.getProperty(key);
	}

public static String getRandomNumber(String prefix)
{
	Random random = new Random();
	String staticNum = prefix;
	int randomNum = random.nextInt(1000000) + 1000000;
	String randomValue = Integer.toString(randomNum);
	String randomNumber = staticNum+randomValue;
	return randomNumber;
}

public static String getExpressionForDynamiLocator(String filename,String key) {
	String value="";
	value = getValueFromPropertyFile(Constant.OR_PATH+"/"+filename,key);
	return value;
}
public static Object[][] getData(File jsonfilepath, String testCaseName){ 
	Gson gson = new GsonBuilder().create();
	 JsonObject job = null;
	try {
		job = gson.fromJson(new FileReader(jsonfilepath), JsonObject.class);
	} catch (JsonSyntaxException e) {
		e.printStackTrace();
	} catch (JsonIOException e) {
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}		 
	 JsonElement cate= job.getAsJsonArray(testCaseName);
	 int trows= job.getAsJsonArray(testCaseName).size();
	 System.out.println("Row 1: "+job.getAsJsonArray(testCaseName).get(0));
	 System.out.println("Total Rows: "+trows);		
	 
	 Hashtable<String, String> table=null;
	 int index=0;
	 Object data[][]=new Object[trows][1];
	 
	 System.out.println("============================================================================================================================");
	 table=new Hashtable<String, String>();
	 for(int rowNum=0;rowNum<trows;rowNum++) {
		 JsonElement rowdata= job.getAsJsonArray(testCaseName).get(rowNum);
		 String values=rowdata.toString().substring(1, rowdata.toString().length()-1);
		 String[] splitvalues= values.split("\",");
		 int tcols = splitvalues.length;
		 System.out.println("Total Columns: "+splitvalues.length);
		 table=new Hashtable<String, String>();
	 for(int c=0;c<splitvalues.length;c++) {
		 if(c<splitvalues.length-1) {
		 table.put(splitvalues[c].split("\":")[0].substring(1, splitvalues[c].split("\":")[0].length()), splitvalues[c].split("\":")[1].substring(1, splitvalues[c].split("\":")[1].length()));
		 //System.out.println(splitvalues[c].split(":")[0].substring(1, splitvalues[c].split(":")[0].length()-1)+"=="+splitvalues[c].split(":")[1].substring(1, splitvalues[c].split(":")[1].length()));
		 System.out.println(splitvalues[c].split("\":")[0].substring(1, splitvalues[c].split("\":")[0].length())+"=="+splitvalues[c].split("\":")[1].substring(1, splitvalues[c].split("\":")[1].length()));
		 }else {
			 table.put(splitvalues[c].split("\":")[0].substring(1, splitvalues[c].split("\":")[0].length()), splitvalues[c].split("\":")[1].substring(1, splitvalues[c].split("\":")[1].length()-1));
			 System.out.println(splitvalues[c].split("\":")[0].substring(1, splitvalues[c].split("\":")[0].length())+"=="+splitvalues[c].split("\":")[1].substring(1, splitvalues[c].split("\":")[1].length()-1));				 
		 }			 
	 }
	 //System.out.println("Row Data: "+table);
	 data[index][0]=table;	
	 index++;
	 System.out.println();
	 }
	 System.out.println("===========================================================================================================================");
	return data;		
}
public Object[][] testDataSet() {
	Hashtable<String, String> table=null;
	return getData(new File("F://Automation//SplashBi//Testdata//smoketest.json"), "TC_DOM_TABLE_FILTERS_014");
}

public static void main(String args[]) {
	
	
	
}
  

}
