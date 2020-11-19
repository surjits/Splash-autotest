package com.splashbi.sanitydata.report;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.sanitydata.domain.Domain;
import com.splashbi.setup.TestSetup;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class Reports extends TestSetup {

    public static Logger logger = Logger.getLogger(Reports.class);
    String domainName="";
    @Test(dataProvider = "LoadData")
    public void createReport(Hashtable<String, String> data){
        try{
            logger.info("In createReport and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Create Domain
            ExtentTest childTest1 = extent.startTest("Create Report");
            setChildTest(childTest1);
            home.navigateToReportPage();
           // assertTrue(report.isReportPageOpen(),"Failed to Navigate to Report Page");
            String reportname=report.createReport(data.get("domainName"),data.get("tablename"));
            assertTrue(report.isReportCreated(reportname));

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createReport Failed",e);
            Assert.fail();
        }
    }
    @Test(dataProvider = "LoadData")
    public void createReportSet(Hashtable<String, String> data){
        try{
            logger.info("In createReport and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");

            ExtentTest childTest1 = extent.startTest("Create Report Set");
            setChildTest(childTest1);
            home.navigateToReportPage();

            String reportsetname=report.createReportSet(data.get("businessapp_name"));
            assertTrue(report.isReportSetCreated(reportsetname));

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createReport Failed",e);
            Assert.fail();
        }
    }
}
