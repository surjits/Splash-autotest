package com.splashbi.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.splashbi.setup.TestSetup;
import com.splashbi.utility.Constant;
import com.splashbi.utility.ExcelUtility;

public class SetAnnotation implements IAnnotationTransformer {
	
	  public static ExcelUtility xl = new ExcelUtility(Constant.TEST_DATA);
	  public static String sheet = TestSetup.sheetName;
	 
	  public boolean getRunValueOfTest(String testname) {
		  boolean run = true;
		  if(xl.getDataForTestName(xl, sheet, testname, "Run").equals("No")) {
		  run = false;
		  }
		  return run;
	  }
	  
	   public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) 
	   {
		 if(!getRunValueOfTest(testMethod.getName()))
	      
	      {
	         annotation.setEnabled(false);
	         System.out.println("Disabled run value for test: "+testMethod.getName());
	      }
		
	   }

}
