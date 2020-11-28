package com.splashbi.sanitydata.admin;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.pageobject.admin.AdminPage;
import com.splashbi.setup.TestSetup;
import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class Users extends TestSetup {
    public static Logger logger = Logger.getLogger(Users.class);

    @Test(dataProvider = "LoadData")
    public void createUser(Hashtable<String, String> data){

        try{
            logger.info("In createUser and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Check Connection
            ExtentTest childTest1 = extent.startTest("Navigate to Users Page");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToUsersPage();
            assertTrue(users.isUsersPageOpen(),"Failed to navigate to Users Page");
            ExtentTest childTest2= extent.startTest("Create User");
            setChildTest(childTest2);
            userName=users.createUser(data,firstName,lastName);
            assertTrue(users.isUserCreated(userName),"User not Created");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createUser Failed",e);
            Assert.fail();
        }

    }

}
