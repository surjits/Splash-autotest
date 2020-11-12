package com.splashbi.sanitydata.report;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.sanitydata.OracleEBS;
import com.splashbi.setup.TestSetup;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class Report extends TestSetup {

    public static Logger logger = Logger.getLogger(Report.class);

    @Test(dataProvider = "LoadData")
    public void createReport(Hashtable<String, String> data){
        try{
            logger.info("In createReport and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");

/*            ExtentTest childTest1 = extent.startTest("Create Report");
            setChildTest(childTest1);
            home.navigateToReportPage();
            report.createReport("test","test_report","");*/
        }
        catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createReport Failed",e);
        }
    }
}
