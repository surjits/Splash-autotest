package com.splashbi.smoketest;

import static org.testng.Assert.assertTrue;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.pageobject.DomainPage;
import com.splashbi.pageobject.HomePage;
import com.splashbi.pageobject.LandingPage;
import com.splashbi.pageobject.LoginPage;
import com.splashbi.setup.TestSetup;

public class DomainModule extends TestSetup {
	public static Logger logger = Logger.getLogger(DomainModule.class);
	String domainName;
	@Test(dataProvider = "LoadTestData")
	public void createMultipleFilter(Hashtable<String, String> data) throws InterruptedException {
		try {
			logger.info("In createMultipleFilter" );
	        //Login to the system
			ExtentTest childTest=extent.startTest("Login To the SplashBi");
			setChildTest(childTest);
			login.signIn();
			System.out.println("Title name is"+driver.getTitle());
			assertTrue(home.verifyHomePage(),"Failed to login to application");
			System.out.println("Title name is"+driver.getTitle());
			
			//Create Domain
		    ExtentTest childTest1 = extent.startTest("Create Domain");
		    setChildTest(childTest1);
		    home.navigateToDomainPage();
			domainName=domain.createDomainWithNewFolder(data.get("bussinessApp"),data.get("dbconnector"));
			assertTrue(domain.isDomainTabOpen(domainName),"Domain creation failed");
			
			//Create Domain Filter 
		    ExtentTest childTest2 = extent.startTest("Create domain Filter");
		    setChildTest(childTest2);
			domain.addTableToDomain(data.get("Tablename"));
			String [][]filterdata={{data.get("Filter1"),data.get("text1")},{data.get("Filter2"),data.get("text2")}};
			domain.createMultipleFiltersFortable(data.get("Tablename"), filterdata);
			
			//Create Report
			ExtentTest childTest3 = extent.startTest("Create Report");
		    setChildTest(childTest3);
			home.navigateToReportPage();
			String reportname=report.createReport(domainName,data.get("Tablename"));
			assertTrue(report.isReportCreated(reportname));
			
			//Add Filter to Report and Run
			ExtentTest childTest4 = extent.startTest("Add Filter and Run Report");
		    setChildTest(childTest4);
			report.AddFilterRunAndSubmitReport(data.get("Filter1"));
			
			ExtentTest childTest5 = extent.startTest("Verify SQL view");
		    setChildTest(childTest5);
			assertTrue(report.verifySqlViewContent(data.get("text1"),data.get("text2")));
			
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Test Failed");
			logger.error("Filter creation failed",e);
			Assert.fail();
			
		}
	}
	@Test(dataProvider = "LoadTestData")
	public void createDomainLOV(Hashtable<String, String> data) throws Exception{
		
		try {
			logger.info("In createDomainLOV" );
			ExtentTest childTest=extent.startTest("Login To the SplashBi");
			setChildTest(childTest);
			login.signIn();
			System.out.println("Title name is"+driver.getTitle());
			assertTrue(home.verifyHomePage(),"Failed to login to application");
			System.out.println("Title name is"+driver.getTitle());
		
			//Create Domain
		    ExtentTest childTest1 = extent.startTest("Create Domain");
		    setChildTest(childTest1);
		    home.navigateToDomainPage();
			domainName=domain.createDomainWithNewFolder(data.get("bussinessApp"),data.get("dbconnector"));
			assertTrue(domain.isDomainTabOpen(domainName),"Domain creation failed");
			
			ExtentTest childTest2 = extent.startTest("Create Domain LOV and Verify");
			setChildTest(childTest2);
			String lovName = domain.createDomainLOV(data.get("lovType"), data.get("lovQuery"));
			assertTrue(domain.verifyLOVcreated(lovName),"LOV creation failed");
			
		}catch(Exception e) {
			test.log(LogStatus.FAIL, "Test Failed");
			logger.error("Test case createDomainLOV Failed",e);
			Assert.fail();
		}
	}
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
			
			//Create Domain
		    ExtentTest childTest1 = extent.startTest("Create Domain");
		    setChildTest(childTest1);
		    home.navigateToDomainPage();
			domainName=domain.createDomainWithNewFolder(data.get("bussinessApp"),data.get("dbconnector"));
			assertTrue(domain.isDomainTabOpen(domainName),"Domain creation failed");
			
			//Add Tables
			
			 ExtentTest childTest2 = extent.startTest("Create domain Filter");
			 setChildTest(childTest2);
			 String tablenames = data.get("Tablename");
			 String[] tables = tablenames.split(",");
		     domain.addTablesToDomain(tables);
		     domain.verifyTableAdded(tables); 

        }catch(Exception e) {
        	test.log(LogStatus.FAIL, "Test Failed");
			logger.error("Test case createDomainWithMultiTable Failed",e);
			Assert.fail();
        }
}

}
