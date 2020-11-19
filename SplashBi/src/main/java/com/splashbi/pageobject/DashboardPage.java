package com.splashbi.pageobject;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import static com.splashbi.pageelement.DynamicPageElement.*;
import static com.splashbi.pageelement.DashboardPageElement.*;

public class DashboardPage extends BasePage {
    String chartName;
    Logger logger = Logger.getLogger(DashboardPage.class);

    public DashboardPage(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void setTest(ExtentTest test) {
        this.setExtentTest(test);

    }

    public boolean isDashBoardPageOpen() {
        waitForVisibilityOfElement(VERIFY_DASHBOARD_HOME);
        if (isElementPresent(VERIFY_DASHBOARD_HOME)) {
            return true;
        } else {
            return false;
        }
    }

    public String createChart(String domainName, String table, String col1, String col2) {
        chartName = Utility.getRandomNumber("ATCHART");
        try {
            clickButton(CREATE_DROPDOWN);
            clickButton(CREATE_CHART);
            waitForInvisibilityOfLoader();
            waitForElementToBePresent(VERIFY_CREATE_VIEW);
            inputText(DOMAIN_SEARCH, domainName);
            clickButton(DOMAIN_IN_DASHBOARD, domainName);
            waitForInvisibilityOfLoader();
            waitForVisibilityOfElement(VERIFY_NEWCHART_TAB);
            clickButton(TABLE_EXPAND_ICON, table);
            dragAndDrop(COLUMN_TO_DRAG, col1, TARGET_DROP_LOC_IN_DIMENSION);
            dragAndDrop(COLUMN_TO_DRAG, col2, TARGET_DROP_LOC_IN_MEASURES);
            clickButton(SAVE_UNTITLE_CHART);
            waitForVisibilityOfElement(SAVE_TITLED_CHART);
            inputText(ENTER_CHART_NAME, chartName);
            clickButton(SAVE_TITLED_CHART);
            waitForInvisibilityOfLoader();

        } catch (Exception e) {
            e.printStackTrace();
            test.log(LogStatus.FAIL, "Chart creation failed");
        }
        return chartName;
    }

    public boolean isChartCreated(String chartName, String col1, String col2) {
        boolean created = false;
        try {
            waitForVisibilityOfElement(VERIFY_CHARTNAME_TAB, chartName);
            if (isElementPresent(VERIFY_COL_IN_DIMENSIONS, col1) && isElementPresent(VERIFY_COL_IN_MEASURES, col2)) {
                created = true;
            }
            test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
            test.log(LogStatus.PASS, "Chart Created and columns verified ");
        }catch(Exception e){
            e.printStackTrace();
            test.log(LogStatus.FAIL, "Chart creation failed");
        }
        return created;
    }

}
