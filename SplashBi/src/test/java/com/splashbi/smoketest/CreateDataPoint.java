package com.splashbi.smoketest;

import static org.testng.Assert.assertTrue;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.setup.TestSetup;

public class CreateDataPoint extends TestSetup {
public static Logger logger = Logger.getLogger(CreateDataPoint.class);
	
    String domainName;
    
	@Test(dataProvider = "LoadTestData")
	public void createDomainWithMultiTable(Hashtable<String, String> data) throws InterruptedException {
		try {
			logger.info("In createDomainWithMultiTable" );
			System.out.println("In createDomainWithMultiTable:"+data.get("bussinessApp"));
			ExtentTest childTest=extent.startTest("Login To the SplashBi");
			System.out.println("In createDomainWithMultiTable:"+data.get("bussinessApp"));
			setChildTest(childTest);
			login.signIn();
			System.out.println("Title name is"+driver.getTitle());
			assertTrue(home.verifyHomePage(),"Failed to login to application");
			/*
			//Create Domain
		    ExtentTest childTest1 = extent.startTest("Create Domain");
		    setChildTest(childTest1);
		    home.navigateToDomainPage();
			domainName=domain.createDomainWithNewFolder(data.get("bussinessApp"),data.get("dbconnector"));
			assertTrue(domain.isDomainCreated(),"Domain creation failed");
			
			//Add Tables
			
			 ExtentTest childTest2 = extent.startTest("Create domain Filter");
			 setChildTest(childTest2);
			 String tablenames = data.get("Tablename");
			 String[] tables = tablenames.split(",");
		     domain.addTablesToDomain(tables);
		     domain.verifyTableAdded(tables); */

        }catch(Exception e) {
        	test.log(LogStatus.FAIL, "Test Failed");
			logger.error("Test case createDomainWithMultiTable Failed",e);
			Assert.fail();
        }
}
	/*
	public void createJoinForDomainTable(Hashtable<String, String> data) throws InterruptedException {
		try {
			ExtentTest childTest=extent.startTest("Login To the SplashBi");
			setChildTest(childTest);
			login.signIn();
			System.out.println("Title name is"+driver.getTitle());
			assertTrue(home.verifyHomePage(),"Failed to login to application");
			
			//
		    ExtentTest childTest1 = extent.startTest("Create Domain");
		    setChildTest(childTest1);
		    home.navigateToDomainPage();
			
			
			
		}catch(Exception e){
			test.log(LogStatus.FAIL, "Test Failed");
			logger.error("Test case createJoinForDomainTable Failed",e);
			Assert.fail();
			
		}
}*/
}