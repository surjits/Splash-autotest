package com.splashbi.pageobject.admin;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
//import com.splashbi.pageelement.HomePageElement;
import com.splashbi.pageelement.InitPageElement;
import com.splashbi.pageelement.admin.AdminPageElement;
import com.splashbi.pageobject.BasePage;
import org.apache.log4j.Logger;

import static com.splashbi.pageelement.HomePageElement.*;
import static com.splashbi.pageelement.admin.AdminPageElement.*;
import org.openqa.selenium.WebDriver;

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
    public void navigateToPageInAdmin(String pagename){
        AdminPageElement page=null;
        switch(pagename){
            case "connector":
                page = CONNECTORS;
                break;
            case "users":
                page = USERS;
                break;
            case "settings":
                page = SETTINGS;
                break;
            case "erp":
                page = ERP_MAPPING;
                break;
            case "setup":
                page = SETUP;
                break;
        }
        try {
            clickButton(page);
            waitForInvisibilityOfLoader();
            wait(1);
            while(isElementDisplayed(ADMIN_HOME)) {
                clickButton(page);
                waitForInvisibilityOfLoader();
            }
            test.log(LogStatus.PASS,"Navigated to page"+" "+pagename);
            test.log(LogStatus.INFO, "Navigated to Admin Page: ");
        } catch (Exception e) {
            test.log(LogStatus.ERROR, printError(e,2));
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.FAIL,"Failed to navigate to "+" "+pagename);
        }

    }

    public void navigateToConnectorsPage()  {
        navigateToPageInAdmin("connector");
    }
    public void navigateToUsersPage()  {
        navigateToPageInAdmin("users");
    }
    public void navigateToSetupPage()  {
        navigateToPageInAdmin("setup");
    }
    public void navigateToSettingsPage()  {
        navigateToPageInAdmin("settings");
    }
    public void navigateToERPMappingPage()  {
        navigateToPageInAdmin("erp");
    }


}
