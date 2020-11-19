package com.splashbi.pageobject.admin;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.pageobject.BasePage;
import com.splashbi.pageobject.LoginPage;
import com.splashbi.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static com.splashbi.pageelement.DynamicPageElement.*;
import static com.splashbi.pageelement.admin.ConnectorsPageElement.CREATED_CONNECTOR_IMAGE;
import static com.splashbi.pageelement.admin.ConnectorsPageElement.INFO_CONNECTOR;
import static com.splashbi.pageelement.admin.UserPageElement.*;

import java.util.Hashtable;

public class UsersPage extends BasePage {
    String username="";
    Logger logger = Logger.getLogger(UsersPage.class);

    public UsersPage(WebDriver driver) {
        super(driver);
    }
    public UsersPage(WebDriver driver, ExtentTest test) {

        super(driver, test);

    }
    public void setTest(ExtentTest test) {
        this.setExtentTest(test);

    }
    public boolean isUsersPageOpen(){
        boolean userPage = false;
        if(isElementDisplayed(USER_HOME_PAGE)){
            userPage = true;
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS,"Navigated to users Page","In Users Page");

        }else{
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.FAIL,"Failed top navigate to user page","Not in userpage");
        }
        return userPage;

    }
    public boolean isUserCreated() throws Exception{
        boolean userCreated = false;
        waitForVisibilityOfElement(USER_HOME_PAGE);
        clickButton(SEARCH_USER);
        inputText(SEARCH_USER,username);
        hitEnterKey(SEARCH_USER);
        if(isElementDisplayed(USER_SEARCHED,username)){
            userCreated = true;
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS,"Created User",username +" "+"created successfully");

        }else{
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.FAIL,"User creation failed",username +" "+"not created");
        }
        return userCreated;
    }

    public void createUser(Hashtable<String, String> input) throws Exception {
        clickButton(CREATE_USER);
        waitForVisibilityOfElement(CREATE_USER_PAGE);
        fillUserBasicDetails(input);
        clickButton(SAVE_NEXT);
        waitForVisibilityOfElement(SUCCESS_MESSAGE);
        fillUserSettings(input);
        clickButton(SAVE_NEXT_USER_EDIT);
        waitForVisibilityOfElement(SUCCESS_MESSAGE);
        waitAndClick(SAVE_USER_GROUP);
        waitForInvisibilityOfLoader();
        waitForVisibilityOfElement(SUCCESS_MESSAGE);

    }
    public void fillUserBasicDetails(Hashtable<String, String> input) throws Exception{
        username = Utility.getRandomNumber("DPUSER");
        inputText(USER_NAME,username);
        clickButton(USER_AUTHENTICATION_LIST);
        waitAndClick(USER_AUTHENTICATION_METHOD,input.get("authentication_method"));
        clickButton(LETME_CREATE_PASSWORD);
        clickButton(SEND_EMAIL);
        inputText(ENTER_PASSWORD,input.get("password"));
        inputText(FIRST_NAME,input.get("first_name"));
        inputText(LAST_NAME,input.get("last_name"));
        inputText(EMAIL_ADDRESS,input.get("email"));

    }
    public void fillUserSettings(Hashtable<String, String> input) throws Exception{
        if(input.get("create_privilege").equalsIgnoreCase("yes"))
        {
            selectCreatePrivilege(input);
        }
        if(input.get("copy_privilege").equalsIgnoreCase("yes"))
        {
            selectCopyPrivilege(input);
        }
        if(input.get("share_privilege").equalsIgnoreCase("yes"))
        {
            selectSharePrivilege(input);
        }
        if(input.get("distribution_privilege").equalsIgnoreCase("yes"))
        {
            selectDistributionPrivilege(input);
        }
    }
    public void selectCreatePrivilege(Hashtable<String, String> input) throws Exception{
        if(input.get("create_Domain").equalsIgnoreCase("yes")){
            clickButton(CREATE_DOMAIN_PRIVILEGE);
        }
        if(input.get("create_Report").equalsIgnoreCase("yes")){
            clickButton(CREATE_REPORT_PRIVILEGE);
        }
        if(input.get("create_Dashboard").equalsIgnoreCase("yes")){
            clickButton(CREATE_DASHBOARD_PRIVILEGE);
        }
        if(input.get("create_Chart").equalsIgnoreCase("yes")){
            clickButton(CREATE_CHART_PRIVILEGE);
        }
        if(input.get("copy_Report_set").equalsIgnoreCase("yes")){
            clickButton(CREATE_REPORTSET_PRIVILEGE);
        }

    }
    public void selectCopyPrivilege(Hashtable<String, String> input) throws Exception{
        if(input.get("copy_Domain").equalsIgnoreCase("yes")){
            clickButton(COPY_DOMAIN_PRIVILEGE);
        }
        if(input.get("copy_Report").equalsIgnoreCase("yes")){
            clickButton(COPY_REPORT_PRIVILEGE);
        }
        if(input.get("copy_Dashboard").equalsIgnoreCase("yes")){
            clickButton(COPY_DASHBOARD_PRIVILEGE);
        }
        if(input.get("copy_Chart").equalsIgnoreCase("yes")){
            clickButton(COPY_CHART_PRIVILEGE);
        }
        if(input.get("copy_Report_set").equalsIgnoreCase("yes")){
            clickButton(COPY_REPORTSET_PRIVILEGE);
        }

    }
    public void selectSharePrivilege(Hashtable<String, String> input) throws Exception{
        if(input.get("share_Domain").equalsIgnoreCase("yes")){
            clickButton(SHARE_DOMAIN_PRIVILEGE);
        }
        if(input.get("share_Report").equalsIgnoreCase("yes")){
            clickButton(SHARE_REPORT_PRIVILEGE);
        }
        if(input.get("share_Dashboard").equalsIgnoreCase("yes")){
            clickButton(SHARE_DASHBOARD_PRIVILEGE);
        }
        if(input.get("share_Chart").equalsIgnoreCase("yes")){
            clickButton(SHARE_CHART_PRIVILEGE);
        }
        if(input.get("share_Report_set").equalsIgnoreCase("yes")){
            clickButton(SHARE_REPORTSET_PRIVILEGE);
        }
        if(input.get("share_Report_set_process").equalsIgnoreCase("yes")){
            clickButton(SHARE_REPORTSET_PROCESS_PRIVILEGE);
        }

    }
    public void selectDistributionPrivilege(Hashtable<String, String> input) throws Exception{
        if(input.get("distribution_email").equalsIgnoreCase("yes")){
            clickButton(DISTRIBUTION_EMAIL_PRIVILEGE);
        }
        if(input.get("distribution_ftp").equalsIgnoreCase("yes")){
            clickButton(DISTRIBUTION_FTP_PRIVILEGE);
        }
        if(input.get("distribution_filesystem").equalsIgnoreCase("yes")){
            clickButton(DISTRIBUTION_FILESYSTEM_PRIVILEGE);
        }
        if(input.get("distribution_bursting").equalsIgnoreCase("yes")){
            clickButton(DISTRIBUTION_BURSTING_PRIVILEGE);
        }
        if(input.get("distribution_publishing").equalsIgnoreCase("yes")){
            clickButton(DISTRIBUTION_REPORTSET_PRIVILEGE);
        }

    }
}
