package com.splashbi.sanitydata.admin;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.setup.TestSetup;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class ERPMapping extends TestSetup {

    public static Logger logger = Logger.getLogger(ERPMapping.class);

    @Test(dataProvider = "LoadData")
    public void createUserMapping(Hashtable<String, String> data){
        try{
            logger.info("In createUserMapping and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");

            ExtentTest childTest1 = extent.startTest("Navigate to ERP-Mapping Page");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToERPMappingPage();
            assertTrue(erpmap.isERPMappingPageOpen(),"Failed to navigate to ERP-MAPPING Page");

            ExtentTest childTest2 = extent.startTest("Create ERP Mapping");
            setChildTest(childTest2);
            erpmap.navigateToOracleEBusinessSuite();
            erpmap.createERPMapping(data.get("splashBi_empname"),data.get("ebs_connection"),data.get("authentication_method"));
            assertTrue(erpmap.verifyMapping(data.get("splashBi_empname")));
            logger.info("Testcase createDBConnector completed");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createUserMapping Failed",e);
            Assert.fail();
        }
    }
    @Test(dataProvider = "LoadData")
    public void importOracleEBSResponsibilities(Hashtable<String, String> data){
        try{
            logger.info("In importOracleEBSResponsibilities and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");

            ExtentTest childTest1 = extent.startTest("Navigate to ERP-Mapping Page");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToERPMappingPage();
            assertTrue(erpmap.isERPMappingPageOpen(),"Failed to navigate to ERP-MAPPING Page");

            ExtentTest childTest2 = extent.startTest("Import EBS Roles-Responsibility");
            setChildTest(childTest2);
            erpmap.navigateToOracleEBusinessSuite();
            erpmap.importEBSResponsibility(data.get("ebs_connection"),data.get("responsibility"));
            assertTrue(erpmap.verifyImportEBS(data.get("responsibility")));
            logger.info("Testcase importOracleEBSResponsibilities completed");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case importOracleEBSResponsibilities Failed",e);
            Assert.fail();
        }
    }
    @Test(dataProvider = "LoadData")
    public void addSResponsibilitiesToUser(Hashtable<String, String> data){
        try{
            logger.info("In addSResponsibilitiesToUser and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");

            ExtentTest childTest1 = extent.startTest("Navigate to ERP-Mapping Page");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToERPMappingPage();
            assertTrue(erpmap.isERPMappingPageOpen(),"Failed to navigate to ERP-MAPPING Page");

            ExtentTest childTest2 = extent.startTest("Add Roles-Responsibility to User");
            setChildTest(childTest2);
            erpmap.navigateToOracleEBusinessSuite();
            erpmap.addResponsibilityToUser(data.get("splashBi_empname"));
            assertTrue(erpmap.isResponsibilityAdded(data.get("splashBi_empname")));
            logger.info("Testcase addSResponsibilitiesToUser completed");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case addSResponsibilitiesToUser Failed",e);
            Assert.fail();
        }
    }


}
