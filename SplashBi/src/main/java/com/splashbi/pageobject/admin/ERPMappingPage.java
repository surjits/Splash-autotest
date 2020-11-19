package com.splashbi.pageobject.admin;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.pageobject.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.splashbi.pageelement.DynamicPageElement.*;
import static com.splashbi.pageelement.admin.ERPMappingPageElement.*;

public class ERPMappingPage extends BasePage {
    Logger logger = Logger.getLogger(ERPMappingPage.class);

    public ERPMappingPage(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    public ERPMappingPage(WebDriver driver) {
        super(driver);
    }
    public ERPMappingPage(){

    }
    public void setTest(ExtentTest test) {
        this.setExtentTest(test);

    }
    public boolean isERPMappingPageOpen(){
        boolean isnavigated = false;
        try {
            if (isElementDisplayed(ERP_MAPPING_HOME)) {
                isnavigated = true;
            }
        }catch(Exception e){
            test.log(LogStatus.FAIL, "ERP Mapping Page not displayed" + test.addScreenCapture(addScreenshot()));

        }
        return isnavigated;
    }
    public void createERPMapping(String splashBi_empname, String ebs_connection,String authmethod) throws Exception{
        clickButton(CREATE_ERP_MAPPING);
        waitForVisibilityOfElement(CREATE_ERP_MAPPING_HOME);
        clickButton(SPLASHBI_EMP_NAME_LIST);
        clickButton(SPLASHBI_EMP_NAME,splashBi_empname);
        clickButton(EBS_CONNECTION_LIST);
        clickButton(EBS_CONNECTION_NAME,ebs_connection);
        clickButton(AUTHENTICATION_METHOD_LIST);
        clickButton(USER_AUTHENTICATION_METHOD,authmethod);
        clickButton(SELECT_USER_MAPPING);
        waitAndClick(SEARCH_EBS_USER);
        wait(2);
        waitForInvisibilityOfLoader();
        waitForVisibilityOfElement(SEARCH_AND_SELECT_EBS_USER_WINDOW);
        selectFirstItemFromList(EBS_USER_LIST);
        clickButton(SAVE_ERP_MAPPING);
    }
    public void navigateToOracleEBusinessSuite(){
        try {
            waitAndClick(ORACLE_EBUSINESS_SUIT);
            waitForInvisibilityOfLoader();
            waitForVisibilityOfElement(ORACLE_EBUSINESS_SUIT_HOME);
            wait(3);
        }catch(Exception e){
            test.log(LogStatus.FAIL,"Failed to navigate to Oracle E Business suite");
            logger.error("Failed to navigate to Oracle E Business suite",e);
        }
    }
    public boolean isUserMappingDeleted(String user){
        boolean isDeleted = false;
        try {
            clickButton(SEARCH_USER_MAPPING);
            inputText(SEARCH_USER_MAPPING, user);
            hitEnterKey(SEARCH_USER_MAPPING);
            //waitForVisibilityOfElement(USER_MAPPING_SEARCHED);
            if(isElementDisplayed(USER_SEARCHED_IN_USER_MAPPING,user)){
                clickButton(DELETE_USER_MAPPING);
                clickOkIfWarningPresent();
                waitForVisibilityOfElement(SEARCH_USER_MAPPING);

            }
            isDeleted = true;
            test.log(LogStatus.PASS,"User mapping deleted successfully");

        }catch(Exception e){
            isDeleted = true;
        }
        return isDeleted;
    }
    public boolean verifyImportEBS(String connection) throws Exception{
        boolean isPresent = false;
        try {
            if (isElementDisplayed(ORACLE_EBUSINESS_SUIT_HOME)) {
                clickButton(ROLES_RESPONSIBILITY_TAB);
            }
            inputText(SEARCH_USER_IN_ROLE_RESPONSIBILITY_TAB, connection);
            hitEnterKey(SEARCH_USER_IN_ROLE_RESPONSIBILITY_TAB);

            if (isElementDisplayed(USER_SEARCHED_IN_USER_MAPPING, connection)) {
                isPresent = true;
                test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
                test.log(LogStatus.PASS, "Imported", connection + " " + "imported successfully");

            } else {
                test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
                test.log(LogStatus.FAIL, "Import failed", connection + " " + "not imported");
            }
        }catch(Exception e){
            throw e;
        }
        return isPresent;
    }
    public boolean verifyMapping(String splashBi_empname ) throws Exception{
        boolean isPresent = false;
        waitForVisibilityOfElement(ORACLE_EBUSINESS_SUIT_HOME);
        wait(1);
        clickButton(SEARCH_USER_MAPPING);
        inputText(SEARCH_USER_MAPPING, splashBi_empname);
        hitEnterKey(SEARCH_USER_MAPPING);
        if (isElementDisplayed(USER_SEARCHED_IN_USER_MAPPING,splashBi_empname)) {
            isPresent = true;
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS, "Created Mapping", splashBi_empname + " " + "mapped successfully");

        } else {
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.FAIL, "Mapping creation failed", splashBi_empname + " " + "not mapped");
        }
        return isPresent;
    }
    public void importEBSResponsibility(String ebsConncetion,String responsibility) {
        try {
            if (isElementDisplayed(ORACLE_EBUSINESS_SUIT_HOME)) {
                clickButton(ROLES_RESPONSIBILITY_TAB);
                clickButton(IMPORT_EBS_RESPONSIBILITIES);
                waitForVisibilityOfElement(IMPORT_RESPONSIBILITIES_WINDOW);
                clickButton(IMPORT_EBS_CONNECTION_LIST);
                clickButton(EBS_CONNECTION_NAME, ebsConncetion);
                waitForInvisibilityOfLoader();
                clickButton(ADD_USER_TO_RESONSIBILITY_CHECKBOX);
                inputText(SEARCH_RESPONSIBILITY, responsibility);
                hitEnterKey(SEARCH_RESPONSIBILITY);
                clickButton(FIRST_AVAILABLE_ROLE_RESPONSIBILITY_TO_IMPORT);
                clickButton(MOVE_TO_RIGHT_ARROW);
                waitForInvisibilityOfLoader();
                waitForVisibilityOfElement(VERIFY_FIRSTROW_IN_SELECTED_ROLE);
                clickButton(SAVE_IMPORT_RESPONSIBILITY);
                waitForInvisibilityOfLoader();
            }
        } catch (Exception e) {
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.FAIL, "Import failed", "Could not import" + " " + "responsibility");
        }
    }
    public void addResponsibilityToUser(String splashBiEmpName){
        try{
            if(isElementDisplayed(ORACLE_EBUSINESS_SUIT_HOME)) {
                clickButton(SEARCH_USER_MAPPING);
            }
            inputText(SEARCH_USER_MAPPING,splashBiEmpName);
            hitEnterKey(SEARCH_USER_MAPPING);
            if(isElementDisplayed(USER_SEARCHED_IN_USER_MAPPING,splashBiEmpName)){
                clickButton(ADD_RESPONSIBILITY_ACTION);
                waitForInvisibilityOfLoader();
                waitForVisibilityOfElement(ROLES_RESPONSIBILITY_WINDOW);
                test.log(LogStatus.INFO, "Snapshot Before adding responsibility: " + test.addScreenCapture(addScreenshot()));


            }
            clickButton(FIRST_AVAILABLE_ROLE_RESPONSIBILITY_TO_ADD);
            clickButton(RIGHT_ARROW_TO_MOVE);
            if(isElementDisplayed(VERIFY_FIRSTROW_IN_SELECTED_ROLE_To_ADD)){
                clickButton(SAVE_USER_RESPONSIBILITY);
            }
        }catch(Exception e){
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.FAIL, "Add responsibility failed", "Could not add" + " " + "responsibility");
        }

    }
    public boolean isResponsibilityAdded(String splashBi_empname ) throws Exception{
        boolean isAdded = false;
        waitForVisibilityOfElement(ORACLE_EBUSINESS_SUIT_HOME);
        wait(1);
        clickButton(SEARCH_USER_MAPPING);
        inputText(SEARCH_USER_MAPPING, splashBi_empname);
        hitEnterKey(SEARCH_USER_MAPPING);
        if (isElementDisplayed(USER_SEARCHED_IN_USER_MAPPING,splashBi_empname)) {
            clickButton(ADD_RESPONSIBILITY_ACTION);
            if(isElementDisplayed(VERIFY_FIRSTROW_IN_SELECTED_ROLE_To_ADD)){
                isAdded = true;
            }

            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS, "Added responsibility ", splashBi_empname + " " + "added responsibility successfully");

        } else {
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.FAIL, "Mapping creation failed", splashBi_empname + " " + "not mapped");
        }
        return isAdded;
    }


    public static void main(String args[]){
        WebDriver driver=null;
        ERPMappingPage erp = new ERPMappingPage();
        erp.test();
    }
}
