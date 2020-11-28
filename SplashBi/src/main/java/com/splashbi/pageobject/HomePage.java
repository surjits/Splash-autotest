package com.splashbi.pageobject;

import static com.splashbi.pageelement.HomePageElement.*;

import com.splashbi.pageelement.HomePageElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class HomePage  extends BasePage {
	
	Logger logger = Logger.getLogger(HomePage.class);
	
    public HomePage(WebDriver driver, ExtentTest test) {
    	super(driver, test);
	}
    public HomePage(WebDriver driver) {

    	super(driver);
	}
    public void setTest(ExtentTest test) {
		this.setExtentTest(test);
		
	}
	public void navigateToPage(String pagename){
    	HomePageElement page=null;
    	switch(pagename){
			case "admin":
					page = ADMINISTRATOR;
					break;
			case "domain":
				page = DOMAIN;
				break;
			case "report":
				page = REPORT;
				break;
			case "dashboard":
				page = DASHBOARD;
				break;
		}
		try {
			clickButton(page);
			waitForInvisibilityOfLoader();
			while(isElementDisplayed(HOME)) {
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
    public void navigateToAdminPage() throws Exception {
		navigateToPage("admin");
	}
    public void navigateToDomainPage() {
		navigateToPage("domain");
    }
	public void navigateToDashboardPage() throws Exception {
		navigateToPage("dashboard");
	}
    
    public void navigateToReportPage() throws Exception {
		navigateToPage("report");
    }

	public boolean verifyHomePage() throws Exception {
    	logger.info("Entering method verifyHomePage()");
    	boolean present = false;
    	try {
    		waitForVisibilityOfElement(LOGOUT);
	    	if(isElementDisplayed(LOGOUT)) {
	    		present = true;
	    		//test.log(LogStatus.PASS, "Login Successfull and Entered Home Page:"+test.addScreenCapture(addScreenshot()));
	    	}
    	}catch(Exception e) {
    		logger.error("Could not land in home page:",e);
    		test.log(LogStatus.FAIL,"Could not enter Home Page");
    		throw e;
    	}
    	return present;
    	
    }
    public boolean isLoggedOut(){
    	boolean logout = false;
    	try{
    		clickButton(LOGOUT);
    		waitForVisibilityOfElement(VERIFY_LOGOUT);
    		if(isElementPresent(VERIFY_LOGOUT)){
				logout = true;
			}
		}catch(Exception e){
    		logout =false;
			test.log(LogStatus.FAIL,"Could not Logout");
		}
    	return logout;
	}


}
