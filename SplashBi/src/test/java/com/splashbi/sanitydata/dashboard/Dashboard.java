package com.splashbi.sanitydata.dashboard;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.sanitydata.domain.Domain;
import com.splashbi.setup.TestSetup;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class Dashboard extends TestSetup {

    public static Logger logger = Logger.getLogger(Dashboard.class);
    String chartname="";
    @Test(dataProvider = "LoadData")
    public void createNonEbsChart(Hashtable<String, String> data){
        try{
            logger.info("In createNonEbsChart and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Create Domain
            ExtentTest childTest1 = extent.startTest("Create Chart");
            setChildTest(childTest1);
            home.navigateToDashboardPage();
            assertTrue(dashboard.isDashBoardPageOpen(),"Failed to navigate to Dashboard page");
            chartname=dashboard.createChart(data.get("domainName"),data.get("tablename"),data.get("col_dimension"),data.get("col_measures"));

            ExtentTest childTest2 = extent.startTest("Verify tables in created chart");
            setChildTest(childTest2);
            dashboard.isChartCreated(chartname,data.get("col_dimension"),data.get("col_measures"));


        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createNonEbsChart Failed",e);
            Assert.fail();
        }
    }
}
