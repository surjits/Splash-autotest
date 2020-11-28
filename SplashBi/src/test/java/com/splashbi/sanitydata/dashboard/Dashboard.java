package com.splashbi.sanitydata.dashboard;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.pageobject.DashboardPage;
import com.splashbi.sanitydata.domain.Domain;
import com.splashbi.setup.TestSetup;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class Dashboard extends TestSetup {

    public static Logger logger = Logger.getLogger(Dashboard.class);

    @Test(dataProvider = "LoadData")
    public void createNonEbsChart(Hashtable<String, String> data){
        if(domainName.isEmpty()){
            domainName = data.get("domainName");
        }
        try{
            logger.info("In createNonEbsChart and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");

            ExtentTest childTest1 = extent.startTest("Create Chart");
            setChildTest(childTest1);
            //Navigate to dashboard and create chart
            home.navigateToDashboardPage();
            assertTrue(dashboard.isDashBoardPageOpen(),"Failed to navigate to Dashboard page");
            chartName=dashboard.createChart(domainName,data.get("tablename"),data.get("col_dimension"),data.get("col_measures"));
            dashboard.isChartCreated(chartName,data.get("col_dimension"),data.get("col_measures"));

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createNonEbsChart Failed",e);
            Assert.fail();
        }
    }
    @Test(dataProvider = "LoadData")
    public void createDashboard(Hashtable<String, String> data){
        if(chartName.isEmpty()){
            chartName = data.get("chartName");
        }

        try{
            logger.info("In createDashboard and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");

            ExtentTest childTest1 = extent.startTest("Create Dashboard");
            setChildTest(childTest1);
            //Navigate to dashboard and create Dashboard
            home.navigateToDashboardPage();
            assertTrue(dashboard.isDashBoardPageOpen(),"Failed to navigate to Dashboard page");
            dashboardName=dashboard.createDashBoard(chartName,data.get("businessapp"));
            assertTrue(dashboard.isDsahboardCreated(dashboardName));

       } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createNonEbsChart Failed",e);
            Assert.fail();
        }
    }
    @Test(dataProvider = "LoadData")
    public void shareDashboardWithUser(Hashtable<String, String> data) throws Exception {
        if(chartName.isEmpty()){
            chartName = data.get("chartName");
        }
        if(userName.isEmpty()){
            userName = data.get("user_name");
        }
        try{
        logger.info("In shareDashboardWithUser and run value is :"+data.get("Run") );
        ExtentTest childTest=extent.startTest("Login To the SplashBi");
        setChildTest(childTest);
        login.signIn();
        System.out.println("Title name is"+driver.getTitle());
        assertTrue(home.verifyHomePage(),"Failed to login to application");

        ExtentTest childTest1 = extent.startTest("Search For Dashboard Object");
        setChildTest(childTest1);
        home.navigateToDashboardPage();
        //assertTrue(domain.isDomainPresent(data.get("domainName")),"Domain not present");
        assertTrue(dashboard.isDashboardPresent(chartName),"Dashboard not present");

        ExtentTest childTest2 = extent.startTest("Share Dashboard with User");
        setChildTest(childTest2);
        dashboard.shareDashboardObjectWithUser(chartName,userName);
        assertTrue(dashboard.isDashboardSharedWithUser(chartName,userName));

        ExtentTest childTest3 = extent.startTest("Login with other User and verify dashboard present");
        setChildTest(childTest3);
        assertTrue(home.isLoggedOut(),"Could not logout");
        login.signInWithOtherUser(userName,user_password, new_password);
        assertTrue(home.verifyHomePage(),"Failed to login to application");
        home.navigateToDashboardPage();
        assertTrue(dashboard.isDashboardPresent(chartName),"Dashboard not present");
        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case shareDashboardWithUser Failed",e);
            Assert.fail();
        }
    }
}
