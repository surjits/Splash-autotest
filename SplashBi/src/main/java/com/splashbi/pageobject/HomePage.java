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
		waitAndClick(ADMINISTRATOR);
		waitForInvisibilityOfLoader();
		test.log(LogStatus.INFO, "Navigated to Admin Page: " );
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
    
    public void navigateToReportPage() throws Exception {
    	logger.info("Entered navigateToDomainPage method" );
    	try {
    		waitAndClick(REPORT);
	    	waitForInvisibilityOfLoader();
	    	test.log(LogStatus.INFO, "Navogated to Report Page: " );
    	}catch(Exception e) {
    		throw e;
    	}
    	
    }
    @SuppressWarnings("unchecked")
	public boolean verifyHomePage() throws Exception {
    	logger.info("Entering method verifyHomePage()");
    	boolean present = false;
    	try {
    		
	    	if(isElementDisplayed(HOME)) {
	    		present = true;
	    		test.log(LogStatus.PASS, "Login Successfull and Entered Home Page:"+test.addScreenCapture(addScreenshot()));
	    	}
    	}catch(Exception e) {
    		logger.error("Could not land in home page:"+e.getMessage());
    		test.log(LogStatus.FAIL,"Could not enter Home Page");
    		throw e;
    	}
    	return present;
    	
    }


}
