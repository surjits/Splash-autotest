package com.splashbi.sanitydata.admin;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.setup.TestSetup;
import com.splashbi.utility.Utility;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class Connectors extends TestSetup {

    public static Logger logger = Logger.getLogger(Connectors.class);

    @Test(dataProvider = "LoadData")
    public void checkConnectionStatus(Hashtable<String, String> data){
        try{
            logger.info("In checkConnectionStatus and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Check Connection
            ExtentTest childTest1 = extent.startTest("Check Connection status");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToConnectorsPage();
            assertTrue(connector.checkConnection(data.get("connector")),"Connection not valid");

        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case checkConnectionStatus Failed",e);
            Assert.fail();
        }
    }

    @Test(dataProvider = "LoadData")
    public void createOracleDBConnector(Hashtable<String, String> data){
        try{
            logger.info("In createOracleDBConnector and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Create Connection
            ExtentTest childTest1 = extent.startTest("Create DB Connection");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToConnectorsPage();
            connectorName = connector.createDBConnector(data);
            Utility.setValueInPropertyFile("connector",connectorName);

            ExtentTest childTest2 = extent.startTest("Verify DB Connection");
            setChildTest(childTest2);
            assertTrue(connector.isConnectorCreated(connectorName));
            logger.info("Testcase createDBConnector Passed");

        }catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Testcase createDBConnector Failed",e);
            Assert.fail();
        }

    }
    @Test(dataProvider = "LoadData")
    public void createSplashDBConnector(Hashtable<String, String> data){
        try{
            logger.info("In createSplashDBConnector and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Create Connection
            ExtentTest childTest1 = extent.startTest("Create DB Connection");
            setChildTest(childTest1);
            home.navigateToAdminPage();
            admin.navigateToConnectorsPage();
            connectorName = connector.createDBConnector(data);
            Utility.setValueInPropertyFile("connector",connectorName);
            assertTrue(connector.isConnectorCreated(connectorName));
            logger.info("Testcase createSplashDBConnector Passed");

        }catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Testcase createDBConnector Failed",e);
            Assert.fail();
        }

    }
    @Test(dataProvider = "LoadData")
    public void shareConnectorWithUser(Hashtable<String, String> data){
        try{
            if(connectorName.isEmpty()){
                connectorName = data.get("connector");
            }

            if(userName.isEmpty()){
                userName=data.get("user_name");
            }
            logger.info("In checkConnectionStatus and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Share Connection
            ExtentTest childTest1 = extent.startTest("Share Connection with User");
            setChildTest(childTest1);
           // home.navigateToAdminPage();
            home.navigateToPage("admin");
            admin.navigateToConnectorsPage();
            connector.shareConnectionWithUser(connectorName,userName);

            ExtentTest childTest2 = extent.startTest("Verify Connection shared with User");
            setChildTest(childTest2);
            assertTrue(connector.verifyConnectionShare(connectorName,userName));
            logger.info("Testcase shareConnectorWithUser Passed");

        }catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Testcase shareConnectorWithUser Failed",e);
            Assert.fail();
        }

    }
}
