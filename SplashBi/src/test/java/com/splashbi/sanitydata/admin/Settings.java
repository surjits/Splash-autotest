package com.splashbi.sanitydata.admin;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.setup.TestSetup;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class Settings extends TestSetup {
    public static Logger logger = Logger.getLogger(Settings.class);

    @Test(dataProvider = "LoadData")
    public void setUserGroupCreationSiteInSettings(Hashtable<String, String> data){
        try{
            logger.info("In setUserGroupCreationSiteInSettings and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Check Connection
            ExtentTest childTest1 = extent.startTest("Navigate to Settings Page");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToSettingsPage();
            assertTrue(setting.isSettingsPageOpen(),"Failed to navigate to Settings Page");

            ExtentTest childTest2= extent.startTest("Set User Group Creation settings");
            setChildTest(childTest2);
            assertTrue(setting.isUserGroupCreationSiteSet(data.get("site_option")),"Setting not Set");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case setUserGroupCreationSiteinSettings Failed",e);
            Assert.fail();
        }
    }
    @Test(dataProvider = "LoadData")
    public void setDashboardListviewSiteInSettings(Hashtable<String, String> data){
        try{
            logger.info("In setDashboardListviewSiteInSettings and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Check Connection
            ExtentTest childTest1 = extent.startTest("Navigate to Settings Page");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToSettingsPage();
            assertTrue(setting.isSettingsPageOpen(),"Failed to navigate to Settings Page");

            ExtentTest childTest2= extent.startTest("Set Dashboard Listview settings");
            setChildTest(childTest2);
            assertTrue(setting.isDashBoardListViewSiteSet(data.get("site_option")),"Setting not Set");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case setDashboardListviewSiteInSettings Failed",e);
            Assert.fail();
        }
    }
}
