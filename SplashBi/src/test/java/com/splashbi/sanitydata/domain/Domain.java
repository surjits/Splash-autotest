package com.splashbi.sanitydata.domain;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.sanitydata.admin.Connectors;
import com.splashbi.setup.TestSetup;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class Domain extends TestSetup {

    public static Logger logger = Logger.getLogger(Domain.class);
    String domainName="";
    @Test(dataProvider = "LoadData")
    public void createDomainWithMultiTable(Hashtable<String, String> data){
        try{
            logger.info("In createDomainAndAddTable and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To SplashBi");
            setChildTest(childTest);
            login.signIn();
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            //Create Domain
            ExtentTest childTest1 = extent.startTest("Create Domain");
            setChildTest(childTest1);
            home.navigateToDomainPage();
            domainName=domain.createDomainWithNewFolder(data.get("bussinessApp"),data.get("dbconnector"));
            assertTrue(domain.isDomainTabOpen(domainName),"Domain creation failed");

            ExtentTest childTest2 = extent.startTest("Add Tables to Domain");
            setChildTest(childTest2);
            String[] tables = data.get("tablenames").split(",");
            domain.addTablesToDomain(tables);
            domain.verifyTableAdded(tables);


        } catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createDomainWithMultiTable Failed",e);
            Assert.fail();
        }
    }

    @Test(dataProvider = "LoadData")
    public void createJoinForDomainTable(Hashtable<String, String> data) throws InterruptedException {
        try {
            logger.info("In createJoinForDomainTable and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To the SplashBi");
            setChildTest(childTest);
            login.signIn();
            System.out.println("Title name is"+driver.getTitle());
            assertTrue(home.verifyHomePage(),"Failed to login to application");

            ExtentTest childTest1 = extent.startTest("Search For Domain");
            setChildTest(childTest1);
            home.navigateToDomainPage();
            assertTrue(domain.isDomainPresent(data.get("domainName")),"Domain not present");

            ExtentTest childTest2 = extent.startTest("Create Join For Table");
            setChildTest(childTest2);
            domain.createJoinForTable(data.get("domainName"),data.get("tablename"));
            assertTrue(domain.isTablePresentInJoin(data.get("tablename")));


        }catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case createJoinForDomainTable Failed",e);
            Assert.fail();

        }
    }
    @Test(dataProvider = "LoadData")
    public void shareDomainWithAnUser(Hashtable<String, String> data) throws InterruptedException {
        try {
            logger.info("In shareDomainWithAnUser and run value is :"+data.get("Run") );
            ExtentTest childTest=extent.startTest("Login To the SplashBi");
            setChildTest(childTest);
            login.signIn();
            System.out.println("Title name is"+driver.getTitle());
            assertTrue(home.verifyHomePage(),"Failed to login to application");

            ExtentTest childTest1 = extent.startTest("Search For Domain");
            setChildTest(childTest1);
            home.navigateToDomainPage();
            assertTrue(domain.isDomainPresent(data.get("domainName")),"Domain not present");

            ExtentTest childTest2 = extent.startTest("Share Domain with User");
            setChildTest(childTest2);
            domain.shareDomainWithUser(data.get("domainName"),data.get("user_name"));

            ExtentTest childTest3 = extent.startTest("Login with other User and verify domain present");
            setChildTest(childTest3);
            assertTrue(home.isLoggedOut(),"Could not logout");
            login.signInWithOtherUser(data.get("user_name"),data.get("password"), data.get("new_password"));
            assertTrue(home.verifyHomePage(),"Failed to login to application");
            home.navigateToDomainPage();
            assertTrue(domain.isDomainPresent(data.get("domainName")),"Domain not present");
        }catch(Exception e){
            test.log(LogStatus.FAIL, "Test Failed");
            logger.error("Test case shareDomainWithUser Failed",e);
            Assert.fail();

        }
    }

}
