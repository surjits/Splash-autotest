package com.splashbi.sanitydata.admin;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.setup.TestSetup;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class setup extends TestSetup {
    public static Logger logger = Logger.getLogger(setup.class);
    @Test(dataProvider = "LoadData")
    public void createUserGroupAndAddUser(Hashtable<String, String> data){
        try{
            logger.info("In createUser and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Check Connection
            ExtentTest childTest1 = extent.startTest("Navigate to User Group Page");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToSetupPage();
            assertTrue(setup.isSetupPageOpen(),"Failed to navigate to Setup Page");
            setup.navigateToUserGroup();
            assertTrue(usergroup.isUserGroupPageOpen(),"Failed to navigate to Setup Page");

            ExtentTest childTest2= extent.startTest("Create User Group");
            setChildTest(childTest2);
            String usergroupname = usergroup.createUserGroup();

            ExtentTest childTest3= extent.startTest("Add User to User Group");
            setChildTest(childTest3);
            usergroup.addAnyUserToGroup(usergroupname );
            assertTrue(usergroup.isUserPresentInGroup(usergroupname),"User not present in user group");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createUserGroup Failed",e);
            Assert.fail();
        }
    }
    @Test(dataProvider = "LoadData")
    public void createFolder(Hashtable<String, String> data){
        try{
            if(businessAppName.isEmpty()){
                businessAppName = data.get("businessapp");
            }
            logger.info("In createFolder and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Check Connection
            ExtentTest childTest1 = extent.startTest("Navigate to Folder Page");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToSetupPage();
            assertTrue(setup.isSetupPageOpen(),"Failed to navigate to Setup Page");
            setup.navigateToFolder();
            assertTrue(folder.isFolderPageOpen(),"Failed to navigate to Folder Page");

            ExtentTest childTest2= extent.startTest("Create Folder");
            setChildTest(childTest2);
            String foldername = folder.createFolder(businessAppName);

            ExtentTest childTest3= extent.startTest("Verfy Created Folder");
            setChildTest(childTest3);
            assertTrue(folder.verifyFolder(foldername),"Folder not found");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createFolder Failed",e);
            Assert.fail();
        }
    }

    @Test(dataProvider = "LoadData")
    public void createBusinessApplication(Hashtable<String, String> data){
        try{
            logger.info("In createBusinessApplication and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Check Connection
            ExtentTest childTest1 = extent.startTest("Navigate to BusinessApp Page");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToSetupPage();
            assertTrue(setup.isSetupPageOpen(),"Failed to navigate to Setup Page");
            setup.navigateToBusinessApp();
            assertTrue(businessapp.isBusinessAppPageOpen(),"Failed to navigate to Business App Page");

            ExtentTest childTest2= extent.startTest("Create Business Application");
            setChildTest(childTest2);
            businessAppName = businessapp.createBusinessApplication();

            ExtentTest childTest3= extent.startTest("Verfy Created BusinessApp");
            setChildTest(childTest3);
            assertTrue(businessapp.verifyBusinessApp(businessAppName),"Business Application not found");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createBusinessApplication Failed",e);
            Assert.fail();
        }
    }
}
