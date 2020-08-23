package com.splashbi.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static com.splashbi.pageelement.LandingPageElement.HOME;
import static com.splashbi.pageelement.DynamicPageElement.*;
import static com.splashbi.pageelement.DomainPageElement.*;

import java.io.IOException;
import java.util.Hashtable;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.utility.Utility;

public class DomainPage extends BasePage {
	String domainName;
	Logger logger = Logger.getLogger(DomainPage.class);
	 public DomainPage(WebDriver driver, ExtentTest test) {
	    	super(driver, test);
	}
	 public DomainPage(WebDriver driver) {
	    	super(driver);
	}
	 
	 public void setTest(ExtentTest test) {
			this.setExtentTest(test);
			
		}
	@SuppressWarnings("unchecked")
	public String createDomainWithNewFolder(String businessapp, String dbconnector) throws Exception {
		try {
			System.out.println("business app= "+businessapp);
			logger.info("Entering createDomainWithNewFolder method");
			waitAndClick(CREATE_DOMAIN);
			domainName=Utility.getRandomNumber("ATDOM");
			inputText(DOMAIN_NAME,domainName);
			clickButton(BUSINESS_APP_LIST);
			waitAndClick(BUSINESS_APP_NAME,businessapp);
			clickButton(CREATE_FOLDER_CHECKBOX);
			clickButton(CREATE_FOLDER);
			String folderName=Utility.getRandomNumber("AT");
			logger.info("Folder name is: "+folderName);
			inputText(FOLDER_NAME,folderName);
			clickButton(DB_CONNECTOR_LIST);
			selectItemFromUlist(DB_CONNECTOR,dbconnector);
			clickButton(SAVE_BUTTON);
			
		}catch(Exception e) {
		 e.printStackTrace();
		 test.log(LogStatus.FAIL, "Domain creation failed");
		 logger.error(e.toString());
		 throw e;
		}
		return domainName;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isDomainCreated() throws Exception {
		//waitForVisibilityOfElement(DOMAIN_HEADER,"");
		boolean created = false;
			if(isElementDisplayed(DOMAIN_HEADER)) {
				created = true;
				System.out.println("Domain created successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				test.log(LogStatus.PASS,"Domain created successfully");
			}
		
		return created;
	}
	
	@SuppressWarnings("unchecked")
	public void addTableToDomain(String tablename) throws Exception {
		System.out.println(" in addTableToDomain");
		try {
		     inputText(TABLE_SEARCH_BOX,tablename);
		     dragAndDrop(TABLE_TO_DRAG,tablename.toUpperCase(),TABLE_DROP_LOC);
		     waitForVisibilityOfElement(DOMAIN_TABLE,tablename.toUpperCase());
		     
		}catch(Exception e) {
			logger.error(e.toString());
			test.log(LogStatus.FAIL, "Table add failed");
			throw e;
		}
	}
	
	public boolean verifyTableAdded(String[] table) throws Exception {
		boolean display = false;
		try {
			for(int i =0; i<table.length; i++) {
				if(getTextValue(DOMAIN_TABLE,table[i].toUpperCase()).equals(table[i].replaceAll("_",""))) {
					display = true;
				}
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.PASS,"Tables added successfully");
		}catch(Exception e) {
			logger.error(e.toString());
			test.log(LogStatus.FAIL, "Table add failed");
			throw e;
			
		}
		
		return display;
	}
	
	@SuppressWarnings("unchecked")
	public void addTablesToDomain(String []tables) throws Exception {
		System.out.println(" in addTableToDomain");
		for(int i = 0; i< tables.length;i++)
		try {
		     inputText(TABLE_SEARCH_BOX,tables[i]);
		     dragAndDrop(TABLE_TO_DRAG,tables[i].toUpperCase(),TABLE_DROP_LOC);
		     waitForVisibilityOfElement(DOMAIN_TABLE,tables[i].toUpperCase());
		     
		}catch(Exception e) {
			logger.error(e.toString());
			test.log(LogStatus.FAIL, "Table add failed");
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public void createFiltersFortable(String table,String filter,String text) throws Exception {
		try {
			clickButton(DOMAIN_TABLE,table.toUpperCase());
			waitAndClick(FILTER_TAB,domainName);
			clickButton(ADD_FILTER,domainName);
			dragAndDrop(FILTER_NAME_TO_DRAG,filter,FILTER_DROP_LOC);
			clickButton(FILTER_NAME,filter);
			clickButton(FREE_TEXT_FOR_FILTER,"1");
			inputText(INPUT_FREE_TEXT_AREA,"1",text);
			clickButton(SAVE_FILTER,"");
			test.log(LogStatus.INFO, test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.PASS,"Filters added successfully");
		}catch(Exception e) {
			logger.error(e.toString());
			test.log(LogStatus.FAIL, "Filter Creation Failed");
			throw e;
			
		}
	}
		@SuppressWarnings("unchecked")
		public void createMultipleFiltersFortable(String table,String [][] filterdata)  throws Exception {
			try {
				clickButton(DOMAIN_TABLE,table.toUpperCase());
				for(int i=0; i<filterdata.length;i++) {
				
				waitAndClick(FILTER_TAB,domainName);
				clickButton(ADD_FILTER,domainName);
				dragAndDrop(FILTER_NAME_TO_DRAG,filterdata[i][0],FILTER_DROP_LOC);
				clickButton(FILTER_NAME,filterdata[i][0]);
				clickButton(FREE_TEXT_FOR_FILTER,String.valueOf(i+1));
				inputText(INPUT_FREE_TEXT_AREA,String.valueOf(i+1),filterdata[i][1]);
				clickButton(SAVE_FILTER);
			}
				test.log(LogStatus.INFO, test.addScreenCapture(addScreenshot()));
				test.log(LogStatus.PASS,"Filters added successfully");
			}catch(Exception e) {
				logger.error(e.toString());
				test.log(LogStatus.FAIL, "Filter Creation Failed"+test.addScreenCapture(addScreenshot()));
				throw e;
				
			}
	}
		@SuppressWarnings("unchecked")
		public String createDomainLOV(String lovType,String query) throws Exception {
			String lovName = Utility.getRandomNumber("ATDOMLOV");
			try {
				clickButton(DOMAIN_LOV_BUTTON);
				clickButton(CREATE_LOV);
				inputText(LOV_NAME_FIELD,lovName);
				clickButton(LOV_TYPE_DROPDOWN);
				clickButton(LOV_TYPE_VALUE,lovType);
				inputText(LOV_QUERY_BOX,query);
				clickButton(LOV_VALIDATE_SQL);
				clickButton(SAVE_AND_NEXT);
				waitAndClick(SAVE_LOV_BUTTON);
				
			}catch(Exception e) {
				logger.error(e.toString());
				test.log(LogStatus.FAIL, "LOV Creation Failed");
				throw e;
			}
			return lovName;
		}
		
		@SuppressWarnings("unchecked")
		public boolean verifyLOVcreated(String lovName) throws Exception {
			boolean val =false;
			try {
				waitForVisibilityOfElement(VERIFY_LOV,lovName);
				if(getTextValue(VERIFY_LOV,lovName).contentEquals(lovName)) {
					val = true;
				}
				test.log(LogStatus.INFO, test.addScreenCapture(addScreenshot()));
				test.log(LogStatus.PASS,"Domain LOV created successfully");
				
			}catch(Exception e) {
				logger.error(e.toString());
				test.log(LogStatus.FAIL, "LOV Creation Failed"+test.addScreenCapture(addScreenshot()));
				throw e;
			}
			return val;
		}
		
		@SuppressWarnings("unchecked")
		public void createJoinForTable(String domainName,String table) throws Exception{
			inputText(SEARCH_DOMAIN,domainName);
			waitForVisibilityOfElement(DOMAIN_NAME_BUTTON,domainName);
			clickButton(EDIT_DOMAIN);
			waitForInvisibilityOfLoader();
			clickButton(EDIT_DOMAIN_TABLE,table);
			waitAndClick(JOIN_TAB,domainName);
			clickButton(SGGESTED_JOINS,domainName);
			test.log(LogStatus.INFO, test.addScreenCapture(addScreenshot()));
			clickButton(LOAD_JOINS);
			clickButton(REVERSE_JOIN);
			clickButton(TABLE_JOIN_SAVE);
			//String alert=waitForAlertToDisplay();
			
		}
		

}
