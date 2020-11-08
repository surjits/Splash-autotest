package com.splashbi.pageobject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.pageelement.InitPageElement;
import com.splashbi.utility.Constant;

public class BasePage <T extends InitPageElement> {
	public WebDriver driver = null;
	public ExtentTest test;
	public WebElement we;
	Actions actions =  null;
	public WebDriverWait wait= null;
	public static Logger logger = Logger.getLogger(BasePage.class);
	String log4jprop;
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver,60);
		actions = new Actions(driver);
		this.log4jprop = Constant.LOG4J_PATH;
		PropertyConfigurator.configure(log4jprop);
	}
	
	public BasePage() {
		
	}
	public BasePage(ExtentTest test) {
		this.test = test;
	}
	
	public void setExtentTest(ExtentTest test) {
		this.test=test;
	}
	public BasePage(WebDriver driver, ExtentTest test) {
		this.log4jprop = Constant.LOG4J_PATH;
		PropertyConfigurator.configure(log4jprop);
		this.driver = driver;
		wait = new WebDriverWait(driver,50);
		this.test = test;
		actions = new Actions(driver);
	}
	
	JavascriptExecutor javascript = ((JavascriptExecutor) driver);
	
	static Exception handleException(Exception e) {
        System.err.println("Handling Exception: " + e);
        return new Exception(e);
    }
	public boolean isElementDisplayed(T element,String ...val) throws Exception {
		try {
			if(val.length >0) {
			   waitForVisibilityOfElement(element,val[0]);
			   return verifyElementPresent(element,val[0]);
			}
			else {
			   waitForVisibilityOfElement(element);
			   return verifyElementPresent(element);
			}
			
		}catch(Exception e) {
			logger.error("Element not displayed");
			throw e;
		
		}
	}
	public void openUrl(String url) throws Exception {
		System.out.println("opening url:"+url);
		try {
			driver.get(url);
			wait(2);
			test.log(LogStatus.PASS, "URL opened" + test.addScreenCapture(addScreenshot()));
		}
		catch(Exception e) {
			throw e;
		}
	  
	}
	//To verify dynamic object
	
	public void wait(int timeout) throws Exception {
		try {
			long milliSeconds = timeout * 1000;
			Thread.sleep(milliSeconds);
		}catch(InterruptedException e) {
		logger.debug(e.getMessage());
	}
	}
	
	public WebElement getDynamicWebElement(T element,String val) {
		return driver.findElement(element.getDynamicBy(val));
	}
	
	public WebElement getWebElement(T element) {
		try {
		   we = driver.findElement(element.getBy());
		}catch(NoSuchElementException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}
		return we;
	}
	
	public WebElement locateElement(T element,String[] val) {
		
		return we;
	}
	public WebElement getWebElement(T element,String val)  {
		try {
			if(val.isEmpty()) {
				we = driver.findElement(element.getBy());
			}else {
				we = driver.findElement(element.getDynamicBy(val));
			}
		}catch(NoSuchElementException e) {
			throw e;
		}catch(TimeoutException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}
		return we;
	}
	
	public List<WebElement> getWebElementList(T element,String val) {	
	    List<WebElement> wl = null;
	    if(val.isEmpty())
	    	wl = driver.findElements(element.getBy());
	    else 
		    wl = driver.findElements(element.getBy());
		return wl;
	}
	
	public void waitForPageLoad() {
		
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        
        wait.until(pageLoadCondition);
    }
	public void selectItemFromUlist(T element, String listitem) {
		try {	
			WebElement target=getWebElement(element,listitem);
			target.click();
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Not able to select the desired element from List:"+"  "+e.toString());
		}
	}
	
	public boolean isElementPresent(T element,String val) {
		if(getWebElementList(element,val).size()!=0) {
			System.out.println("Warning present");
		   return true;
		}
		else {
			return false;
		}
	}
	
	public boolean verifyElementPresent(T element,String ...val) {
		try {
			if(val.length > 0)
		       getWebElement(element,val[0]);
			else
			   getWebElement(element,"");
		    return true;
		}catch(Exception e) {
		
			return false;
		}
	}


	
	public String getTextValue(T element,String ...val) throws Exception {
		if(val.length > 0) {
		   waitForVisibilityOfElement(element,val[0]);
		   return getDynamicWebElement(element, val[0]).getText();
		}
		else {
			waitForVisibilityOfElement(element);
			return getWebElement(element).getText();
			
		}
	}
	public void inputText(T element, String dynamicVal,String value) throws Exception {
		try {
			we = getWebElement(element,dynamicVal);
			wait.until(ExpectedConditions.visibilityOf(we));
			we.clear();
			we.sendKeys(value);
			test.log(LogStatus.PASS, " Enter Text", "Entered value  " + value + " in " + element+" field");
		}
		catch(NoSuchElementException e) {
			//logger.error("Error in input text:" + e.toString());
			test.log(LogStatus.FAIL, "Could not enter text" + test.addScreenCapture(addScreenshot()));
			//throw e;
		}
		
	}
	
	public void inputText(T element,String value) throws Exception {
		try {
			we = getWebElement(element);
			wait.until(ExpectedConditions.visibilityOf(we));
			we.clear();
			we.sendKeys(value);
			test.log(LogStatus.PASS, " Enter Text", "Entered value  " + value + " in " + element+" field");
		}
		catch(NoSuchElementException e) {
			//logger.error("Error in input text:" + e.toString());
			test.log(LogStatus.FAIL, "Could not enter text" + test.addScreenCapture(addScreenshot()));
			throw e;
		}
		
	}
	
	public void clickWithJavaScript(T element,String val) throws Exception {
		try {
			we = getWebElement(element,val);
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element); 
		}catch(Exception e) {
			logger.error("Failed to click by Actions");
			throw e;
		}
	}
	public void clickByActions(T element,String val) throws Exception {
		wait(1);
		try {
			we = getWebElement(element,val);
			actions.moveToElement(we).click().perform();
			test.log(LogStatus.PASS, "Click element", "Clicked  " + element);
			
		}catch(Exception e) {
			logger.error("Failed to click by Actions");
			throw e;
		}
	}
	
	public void clickButton(T element,String val) throws Exception {
		waitForVisibilityOfElement(element,val);
	    we = getWebElement(element,val);
		try {
		    we.click();
		    test.log(LogStatus.PASS, "Click element", "Clicked  " + element);
		}
		catch(ElementClickInterceptedException e) {
			//	JavascriptExecutor js= (JavascriptExecutor) driver;
				javascript.executeScript("arguments[0].click();", we); 
		}
		catch(Exception e) {
			logger.error("Error in click:" + e.getMessage());
			test.log(LogStatus.FAIL, "Could not click element" + test.addScreenCapture(addScreenshot()));
			throw e;
		}
	}
	public void clickButton(T element) throws Exception {
		waitForVisibilityOfElement(element);
	    we = getWebElement(element);
		try {
		    we.click();
		    test.log(LogStatus.PASS, "Click element", "Clicked  " + element);
		}
		catch(ElementClickInterceptedException e) {
		//	JavascriptExecutor js= (JavascriptExecutor) driver;
			javascript.executeScript("arguments[0].click();", we); 
		}
		catch(Exception e) {
			logger.error("Error in click:" ,e);
			test.log(LogStatus.FAIL, "Could not click element" + test.addScreenCapture(addScreenshot()));
			throw e;
		}
	}
	public void navigateToHomePage() {
		driver.findElement(By.xpath("//li[@id='homeLink']"));
	}

	public void waitForInvisibilityOfLoader() {
		 By byelement = By.xpath("//img[@src='../../images/assets/loader3.gif']");
		  try {
		    wait.until(ExpectedConditions.invisibilityOfElementLocated(byelement));
		  } catch (Exception e) {
		    logger.error("Loader image still visible within wait time",e);
		    throw e;
		  }
		}
	
	public void waitForVisibilityOfElement(T element,String ...val) throws Exception {
		try {
			if(val.length>0) 
			    wait.until(ExpectedConditions.elementToBeClickable(element.getDynamicBy(val[0])));
			else 
				wait.until(ExpectedConditions.elementToBeClickable(element.getBy()));
		}catch(Exception e) {
			logger.error("Element not visible within wait time"+e);
		    throw e;
		}
		}
	
	public int rowCountinTable(T element,String val) {
		return getWebElementList(element,val).size();
	}
	
	public String waitForAlertToDisplay() throws Exception {
		String alert;
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			alert = driver.switchTo().alert().getText();
			test.log(LogStatus.INFO, "Alert:"+alert +":displayed" + test.addScreenCapture(addScreenshot()));
		}catch(Exception e) {
			logger.error("Alert not visible within wait time"+e);
		    throw e;
		}
		return alert;
	}
	public void waitAndClick(T element,String ... val) throws Exception {
		
		waitForVisibilityOfElement(element,val);
		try {
			if(val.length > 0)
			    we = getDynamicWebElement(element,val[0]);
			else
				we = getWebElement(element);
		    we.click();
		    test.log(LogStatus.PASS, "Click element", "Clicked  " + element);
		}
		catch(Exception e) {
			logger.error("Error in click:" + e.getMessage());
			test.log(LogStatus.FAIL, "Could not click element" + test.addScreenCapture(addScreenshot()));
		}
		
	}
	public boolean isElementDisplayed(T element){
		boolean displayed = false;
		try{
			if (getWebElement(element).isDisplayed()){
				displayed = true;
			}
		}catch(Exception e){
			logger.error("Error in click:" + e.getMessage());
		}
		return displayed;
	}
	
	public boolean isAlertPresent() {
		
		return true;
	}
	public void dragAndDrop(T element,String val,T destination) throws Exception {
		waitForVisibilityOfElement(element,val);
		actions.dragAndDrop(getWebElement(element,val),getWebElement(destination,"")).build().perform();
	}
	
    public void dragAndDropFromList(T element,T destination) throws Exception {
    	try {
	        List<WebElement> tablelist = driver.findElements(element.getBy());
	        System.out.println("The table name is "+tablelist.get(0));
	    	actions.dragAndDrop(tablelist.get(0), getWebElement(destination,"")).build().perform();
    	}catch(Exception e) {
    		logger.error("Error in dragAndDrop:" + e.getMessage());
    		test.log(LogStatus.FAIL, "Could not drag the element" );
    		throw e;
    	}
    }
	public void takeScreenShot() {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		File directory = new File(String.valueOf(Constant.SCREENSHOT_PATH));

		if (!directory.exists()) {
			directory.mkdir();
		}
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(directory + "/" + screenshotFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String addScreenshot() {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		return convertImageToString(scrFile);
	}
	
	public String convertImageToString(File file) {
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.getEncoder().encodeToString(bytes));
			// encodedBase64 =new String(Base64.encodeBase64(bytes), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "data:image/png;base64," + encodedBase64;
	}
	
	public void switchWindow() {
		String mainWindowHnd = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();
		for (String handle : set) {
			if (!(handle == mainWindowHnd)) {
				driver.switchTo().window(handle);
			}
		}
	}

	 public String captureElementScreenshot(T element) throws Exception{
	  
	  File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  int ImageWidth = getWebElement(element,"").getSize().getWidth();
	  int ImageHeight = getWebElement(element,"").getSize().getHeight();  
	  Point point = getWebElement(element,"").getLocation();
	  int xcord = point.getX();
	  int ycord = point.getY();
	    
	  BufferedImage img = ImageIO.read(screen);
	  BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
	  ImageIO.write(dest, "png", screen);
	  FileUtils.copyFile(screen, new File(Constant.SCREENSHOT_PATH+"\\screenshot.png"));
	  return convertImageToString(screen);
	 }
	
	
	public void scrollUp() throws Exception {
		javascript.executeScript("window.scrollBy(0,-5000)", "");
		wait(1);
	}

	public void scrollUp600() throws Exception {
		javascript.executeScript("window.scrollBy(0,-600)", "");
		wait(1);
	}

	public void scrollDown600() throws Exception {
		javascript.executeScript("window.scrollBy(0,600)", "");
		wait(2);
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
	}
	
	public void scrollToTop() {
		javascript.executeScript("window.scrollTo(0,0)", "");
	}

	public void scrollToLeft() {
		javascript.executeScript("window.scrollBy(-100,0)", "");
	}

	public void scrollDown() {
		javascript.executeScript("window.scrollBy(0,5000)", "");
		//test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
	}

	public void scrollDownAndClick(T element) throws Exception {
		actions.moveToElement(getWebElement(element,""));
		actions.click();
		actions.perform();
	}

	public void scrollDownToElement(T element) throws Exception {
		actions.moveToElement(getWebElement(element,""));
		actions.perform();
	}

	public void refreshPage() throws Exception {
		wait(2);
		driver.navigate().refresh();
	}
	
	
}
