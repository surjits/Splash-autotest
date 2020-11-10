package com.splashbi.pageobject;

import static com.splashbi.pageelement.LoginPageElement.*;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.splashbi.utility.Utility;
import com.splashbi.utility.Constant;
import com.splashbi.pageobject.BasePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage extends BasePage {
	
	Logger logger = Logger.getLogger(LoginPage.class);
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	public LoginPage(WebDriver driver, ExtentTest test) {
		
		super(driver, test);
		System.out.println("In PageOne");
		
	}
	public void setTest(ExtentTest test) {
		this.setExtentTest(test);
		
	}
	
	@SuppressWarnings("unchecked")
	public void signIn() throws InterruptedException, Exception {
		try {
			logger.info("Entering signin page");
			openUrl(Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"url"));
			String username = Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"user");
			String password = Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"password");
			System.out.println("going to login");
			inputText(USER_NAME,username);
			inputText(PASSWORD,password);
			clickButton(LOGIN);
			if(isElementDisplayed(WARNING)) {
				test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}else {
				test.log(LogStatus.PASS, "No Error warning in Login");
				
			}
			waitForInvisibilityOfLoader();
		}catch(Exception e) {
			//logger.error("Error in input text:" + e.fillInStackTrace());
			test.log(LogStatus.FAIL, "Login Failed" );
			throw e;
		}			
		
	}
		
}

