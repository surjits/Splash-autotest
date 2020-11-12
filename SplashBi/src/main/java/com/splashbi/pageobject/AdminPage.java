package com.splashbi.pageobject;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.pageelement.InitPageElement;
import org.apache.log4j.Logger;
import static com.splashbi.pageelement.DynamicPageElement.*;
import static com.splashbi.pageelement.AdminPageElement.*;
import org.openqa.selenium.WebDriver;

import static com.splashbi.pageelement.HomePageElement.DOMAIN;

public class AdminPage extends BasePage{
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

    public void navigateToConnectorsPage() {
        try {
            logger.info("Entered navigateToConnectorsPage method");

            waitAndClick(CONNECTORS);
            waitForInvisibilityOfLoader();
            test.log(LogStatus.INFO, "Navigated to Admin Page: ");
        } catch (Exception e) {

        }
    }
}
