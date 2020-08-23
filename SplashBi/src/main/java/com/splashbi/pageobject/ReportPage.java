package com.splashbi.pageobject;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.utility.Utility;

import static com.splashbi.pageelement.ReportPageElement.*;
import static com.splashbi.pageelement.DomainPageElement.DOMAIN_HEADER;
import static com.splashbi.pageelement.DynamicPageElement.*;
import static com.splashbi.pageelement.HomePageElement.*;
public class ReportPage extends BasePage {

	public ReportPage(ExtentTest test) {
		super(test);
	}
		
	public ReportPage(WebDriver driver) {
		super(driver);
		
	}
	
	 public void setTest(ExtentTest test) {
			this.setExtentTest(test);
			
	}
	 String reportName;
	@SuppressWarnings("unchecked")
	public String createReport(String domainName,String tablename,String filter) throws Exception {
		try {
			reportName=Utility.getRandomNumber("ATREP");
			clickButton(CREATE_DROPDOWN);
			clickButton(CREATE_REPORT);
			clickByActions(DOMAIN_NAME_IN_REPORT,domainName);
			waitForInvisibilityOfLoader();
			clickButton(EXPAND_DOMAIN_TABLE,tablename.toUpperCase());
			clickButton(SELECT_ALL_CHECKBOX,tablename.toUpperCase());
			clickButton(MOVE_TO_RIGHT);
			clickButton(SAVE_REPORT);
			inputText(ENTER_REPORT_NAME,reportName);
			clickButton(SAVE_REPORT_NAME);
			waitForInvisibilityOfLoader();
			
		}catch(Exception e) {
			logger.error("Failed to create Report",e);
			test.log(LogStatus.FAIL,"Report creation failed:"+test.addScreenCapture(addScreenshot()));
			throw e;
		}
		return reportName;
	}
		
	@SuppressWarnings("unchecked")
	public void AddFilterRunAndSubmitReport(String filter) throws Exception {	
		try {
			clickButton(REPORT_FILTER_CRITERIA);
			dragAndDrop(DRAG_COLUMN,filter,DROP_PLACE);
			clickButton(SAVE_REPORT_FILTER);
			waitForInvisibilityOfLoader();
			clickButton(RUN_REPORT);
			clickButton(OPTION_MORE);
			clickButton(SUBMIT_REPORT);
			waitForVisibilityOfElement(SUBMIT_SUCCESS);
			
		}catch(Exception e) {
			logger.error("Failed to create Report",e);
			test.log(LogStatus.FAIL,"Report creation failed:"+test.addScreenCapture(addScreenshot()));
			handleException(e);
			throw e;
		}
		
	}
	@SuppressWarnings("unchecked")
	public boolean verifySqlViewContent(String text1, String text2) throws Exception  {
		boolean present=false;
		try {
			clickButton(VIEW_SQL);
			waitForVisibilityOfElement(SQL_SELECT_CONTENT);
			waitForInvisibilityOfLoader();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			String content=getTextValue(SQL_SELECT_CONTENT);
			if(content.contains(text1) && content.contains(text2)) {
				present = true;
				test.log(LogStatus.PASS,"SQL content Texts validated successfully");
			}
			
		}catch(Exception e) {
			logger.error("SQL content verification failed");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			throw e;
		}
		return present;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isReportCreated(String reportName) throws Exception {
		boolean created = false;
		try {
			if(getTextValue(VERIFY_REPORT_CREATED).contains(reportName)) {
				created = true;
			}
				System.out.println("Report created successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				test.log(LogStatus.PASS,"Report created successfully");
			
		}catch(Exception e) {
			logger.error("Report not created",e);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			handleException(e);
			throw e;
		}
	
	return created;
}
	

}
