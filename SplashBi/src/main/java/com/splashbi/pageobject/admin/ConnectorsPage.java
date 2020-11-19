package com.splashbi.pageobject.admin;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.pageobject.BasePage;
import com.splashbi.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Hashtable;

import static com.splashbi.pageelement.DynamicPageElement.*;
import static com.splashbi.pageelement.admin.ConnectorsPageElement.*;
public class ConnectorsPage extends BasePage {
    Logger logger = Logger.getLogger(ConnectorsPage.class);

    public ConnectorsPage(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    public ConnectorsPage(WebDriver driver) {
        super(driver);
    }
    public void setTest(ExtentTest test) {
        this.setExtentTest(test);

    }
    public boolean checkConnection(String connector) throws Exception{
        boolean connectionStatus = false;
        switch(connector){
            case "oracleEBS":
                clickButton(ORACLE_EBS_INFO);
                break;
        }
        try {
            waitAndClick(TEST_CONNECTOR);
            waitForVisibilityOfElement(CONNECTOR_VALIDATE);
        }catch(Exception e){
            logger.error("Connection status not validated",e);
            test.log(LogStatus.FAIL,"Connection can not be validated");
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
        }
        return verifyTestConnection();
    }
    public boolean verifyTestConnection() throws Exception{
        if(getTextValue(CONNECTOR_VALIDATE).contains("Valid Database Connector")){
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS,"Connection validated successfully");
            return true;
        }
        else{
            test.log(LogStatus.FAIL,"Connection is not get validated successfully");
            return false;
        }

    }
    public boolean isConnectorSavedSuccessfully() throws Exception{
        if(getTextValue(CONNECTOR_SAVE_SUCCESS_POPUP).contains("Database Connector saved successfully.")){
            // test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS,"Connector saved successfully");
            return true;
        }
        else{
            test.log(LogStatus.FAIL,"Connection is not saved");
            return false;
        }

    }
    // Navigate to Connector Page and verify its open
    public boolean isConnectorOpen(String connector_name) throws Exception {
        try {
            waitAndClick(CONNECTOR, connector_name);
            return isElementDisplayed(CONNECTOR_TAB, connector_name);
        }catch(Exception e){
            waitAndClick(CONNECTOR, connector_name);
            return isElementDisplayed(CONNECTOR_TAB, connector_name);
        }
    }

    /******* Create a New DB Connector *************/
    public String createDBConnector(Hashtable<String, String> data) throws Exception{
        String connector_name = Utility.getRandomNumber("AUTOCONNECT");
        clickButton(CREATE_CONNECTORS);
        if(isElementDisplayed(DB_CONNECTORS_HEADER)){
            clickButton(DB_CONNECTOR_TYPE,data.get("connector_type"));
            waitForVisibilityOfElement(VERIFY_CREATE_CONNECTOR);
            fillDBConnectorDetails(connector_name,data);
        }
        clickButton(CREATE_CONNECTOR_TEST);
        clickButton(CREATE_CONNECTOR_SAVE);
        waitForVisibilityOfElement(CONNECTOR_HOME);
        return connector_name;
    }
    public boolean isConnectorCreated(String connector_name) throws Exception{
        inputText(SEARCH_CONNECTOR,connector_name);
        if(isElementDisplayed(CONNECTOR,connector_name)){
            clickButton(INFO_CONNECTOR);
            waitForVisibilityOfElement(CREATED_CONNECTOR_IMAGE);
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS,"Created Connector",connector_name +" "+"created successfully");

        }else{
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.FAIL,"Connector creation failed",connector_name +" "+"not created");
        }
        return true;
    }
    public void fillDBConnectorDetails(String connector_name,Hashtable<String, String> data) throws Exception{
        inputText(CONNECTOR_NAME,connector_name);
        clickButton(SYSTEM_TYPE_LIST);
        clickButton(SYSTEM_TYPE,data.get("system_type"));
        clickButton(CONNECTION_TYPE_LIST);
        clickButton(CONNECTION_TYPE,data.get("connection_type"));
        inputText(HOST_NAME,data.get("hostname"));
        inputText(SID,data.get("sid"));
        inputText(CONNECTION_USER,data.get("connection_user"));
        inputText(CONNECTION_PASSWORD,data.get("connection_password"));
        inputText(PORT_NUMBER,data.get("port_number"));
        inputText(SCHEMA_NAME,data.get("schema_name"));
    }
    public void testConnectorAndSave() throws Exception{
        clickButton(CREATE_CONNECTOR_TEST);
        clickButton(CREATE_CONNECTOR_SAVE);
    }
    public void shareConnectionWithUser(String connector_name, String username) throws Exception{
        inputText(SEARCH_CONNECTOR,connector_name);
        hitEnterKey(SEARCH_CONNECTOR);
        clickButton(CONNECTION_ACTION_LIST);
        clickButton(SHARE_CONNECTOR_OPTION);
        waitForVisibilityOfElement(VERIFY_SHARE_CONNECTOR_PAGE);
        inputText(AVAILABLE_USER_SEARCH,username);
        hitEnterKey(AVAILABLE_USER_SEARCH);
        wait(1);
        waitForVisibilityOfElement(USER_IN_SHARE_LIST,username);
        waitAndClick(SELECT_AVAILABLE_USER,username);
        clickButton(SHIFT_RIGHT_BUTTON);
        wait(1);
        waitForVisibilityOfElement(USER_IN_SHARE_LIST,username);
        if(getListSize(SHARED_USER_LIST)>0){
            clickButton(SAVE_SHARE);
        }
        waitForVisibilityOfSuccessMessage();
        wait(1);
    }
    public boolean verifyConnectionShare(String connector_name,String username) throws Exception{
        inputText(SEARCH_CONNECTOR,connector_name);
        hitEnterKey(SEARCH_CONNECTOR);
        waitForElementToBePresent(CONNECTOR,connector_name);

        clickButton(INFO_CONNECTOR);
        waitForVisibilityOfElement(CREATED_CONNECTOR_IMAGE);
        clickButton(CONNECTOR_SHARE_TAB);
        clickButton(EXPAND_ALL);
        if(getListSize(VERIFY_USER_IN_SHARE_TAB,username)>0){
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS,"User found",connector_name +" "+"shared with"+""+ username);

        }else{
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.FAIL,"Connector share failed",connector_name +" "+"not shared");
        }
        return true;
    }


}
