package com.splashbi.pageobject;

import static com.splashbi.pageelement.HomePageElement.*;

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
    public void navigateToAdminPage() throws Exception {
		try {
			clickButton(ADMINISTRATOR);
			waitForInvisibilityOfLoader();
			test.log(LogStatus.INFO, "Navigated to Admin Page: ");
		} catch (Exception e) {

		}
	}
    public void navigateToDomainPage() {
    	System.out.println("navigateToDomainPage");
    	logger.info("Entered navigateToDomainPage method" );
    	try {
    		waitAndClick(DOMAIN);
	    	waitForInvisibilityOfLoader();
	    	test.log(LogStatus.INFO, "Navigated to domain Page: " );
    	}catch(Exception e) {
    		
    	}
    	
    }
	public void navigateToDashboardPage() throws Exception {
		logger.info("Entered navigateToDomainPage method" );
		try {
			waitAndClick(DASHBOARD);
			waitForInvisibilityOfLoader();
			wait(1);
			test.log(LogStatus.PASS, "Navigated to Report Page: " );
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
    
    public void navigateToReportPage() throws Exception {
    	logger.info("Entered navigateToDomainPage method" );
    	try {
    		waitAndClick(REPORT);
	    	waitForInvisibilityOfLoader();
	    	wait(1);
	    	test.log(LogStatus.PASS, "Navigated to Report Page: " );
    	}catch(Exception e) {
    		throw e;
    	}
    	
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
