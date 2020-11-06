package com.splashbi.pageobject;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static com.splashbi.pageelement.ConnectorsPageElement.*;
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
        waitAndClick(TEST_CONNECTOR);
        waitForVisibilityOfElement(CONNECTOR_VALIDATE);
        if(getTextValue(CONNECTOR_VALIDATE).contains("Valid Database Connector")){
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS,"Connection validated successfully");
            connectionStatus = true;
        }
        return connectionStatus;
    }
}
