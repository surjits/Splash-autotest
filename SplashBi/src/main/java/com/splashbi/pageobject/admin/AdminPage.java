package com.splashbi.pageobject.admin;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.pageelement.InitPageElement;
import com.splashbi.pageobject.BasePage;
import org.apache.log4j.Logger;
import static com.splashbi.pageelement.admin.AdminPageElement.*;
import org.openqa.selenium.WebDriver;

import static com.splashbi.pageelement.HomePageElement.DOMAIN;

public class AdminPage extends BasePage {
    Logger logger = Logger.getLogger(AdminPage.class);

    public AdminPage(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }
    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void setTest(ExtentTest test) {
        this.setExtentTest(test);

    }

    public void navigateToConnectorsPage()  {
        try {
            logger.info("Entered navigateToConnectorsPage method");

            waitAndClick(CONNECTORS);
            waitForInvisibilityOfLoader();
            test.log(LogStatus.INFO, "Navigated to Connector Page: ");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Failed to Navigate to Connectors Page: ");

        }
    }
    public void navigateToUsersPage()  {
        try {
            logger.info("Entered navigateToUsersPage method");

            waitAndClick(USERS);
           // waitForInvisibilityOfLoader();
            test.log(LogStatus.PASS, "Navigated to Users Page: ");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Failed to Navigate to Users Page: ");
        }
    }
    public void navigateToSetupPage()  {
        try {
            logger.info("Entered navigateToUsersPage method");

            waitAndClick(SETUP);
            test.log(LogStatus.PASS, "Navigated to Setup Page: ");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Failed to Navigate to Setup Page: ");
        }
    }
    public void navigateToSettingsPage()  {
        try {
            logger.info("Entered navigateToUsersPage method");

            waitAndClick(SETTINGS);
            test.log(LogStatus.PASS, "Navigated to Settings Page: ");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Failed to Navigate to Settings Page: ");
        }
    }
    public void navigateToERPMappingPage()  {
        try {
            logger.info("Entered navigateToUsersPage method");

            waitAndClick(ERP_MAPPING);
            test.log(LogStatus.PASS, "Navigated to ERP Mapping Page: ");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Failed to Navigate to ERP Mapping Page: ");
            logger.error("Failed to navigate to ERP-Mapping Page",e);
        }
    }


}
