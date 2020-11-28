package com.splashbi.pageobject;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
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

import static com.splashbi.pageelement.DynamicPageElement.BUSINESS_APP_NAME;

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
		wait = new WebDriverWait(driver,60);
		this.test = test;
		actions = new Actions(driver);
	}


	JavascriptExecutor javascript = ((JavascriptExecutor) driver);

	static Exception handleException(Exception e) {
		System.err.println("Handling Exception: " + e);
		return new Exception(e);
	}
	public static String printError(Exception e, int maxLines) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		String[] lines = writer.toString().split("\n");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Math.min(lines.length, maxLines); i++) {
			sb.append(lines[i]).append("\n");
		}
		return sb.toString();
	}
	public boolean isElementDisplayed(T element,String val)  {
		boolean isDisplayed = false;
		try {
			//waitForVisibilityOfElement(element,val);
			if(getWebElement(element,val).isDisplayed()){
				isDisplayed = true;
			}
		}catch(Exception e) {
			logger.error("Element not displayed",e);
			throw e;
		}
		return isDisplayed;
	}
	public void openUrl(String url) throws Exception {
		System.out.println("opening url:"+url);
		try {
			driver.get(url);
			wait(2);
			//test.log(LogStatus.PASS, "URL opened" + test.addScreenCapture(addScreenshot()));
		}
		catch(Exception e) {
			throw e;
		}

	}

	public void wait(int timeout)  {
		try {
			long milliSeconds = timeout * 1000;
			Thread.sleep(milliSeconds);
		}catch(InterruptedException e) {
			logger.debug(e.getMessage());
		}
	}
	/***********To get Dynamic element********************/
	public WebElement getWebElement(T element,String val) {
		try{
			return driver.findElement(element.getDynamicBy(val));
		}catch(NoSuchElementException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}
	}
	/***********To get element********************/
	public WebElement getWebElement(T element) {
		try {
			return driver.findElement(element.getBy());
		}catch(NoSuchElementException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}
	}
	public List<WebElement> getWebElementList(T element) {
		List<WebElement> wl = null;
		try {
			wl = driver.findElements(element.getBy());
		}catch(Exception e){
			logger.error("List Element not found",e);
		}
		return wl;
	}

	public List<WebElement> getWebElementList(T element,String val) {
		List<WebElement> wl = null;
		try {
			wl = driver.findElements(element.getDynamicBy(val));
		}catch(Exception e){
			logger.error("List Element not found",e);
		}
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

	public int getListSize(T elementlist){
		List<WebElement> items = getWebElementList(elementlist);
		return items.size();
	}
	public int getListSize(T elementlist,String val){
		List<WebElement> items = getWebElementList(elementlist,val);
		return items.size();
	}

	public void selectFirstNItemFromList(T elementList,int count){
		try {
			List<WebElement> items = getWebElementList(elementList);
			for(int i =0; i< count;i++) {
				items.get(i).click();

			}
		}catch(Exception e){
			test.log(LogStatus.FAIL, "Could not select first n itemsfrom list" + test.addScreenCapture(addScreenshot()));
		}
	}
	public boolean isElementSelected(T element){
		boolean selected = false;
		try{
			if(getWebElement(element).isSelected()) {

				selected = true;
			}
		}catch(Exception e){
			selected = false;
		}
		return selected;
	}
	public void selectAnItemFromList(T element, String val){
		logger.info("Inside selectAnItemFromList ");
		wait(1);
		try {
			if(!isElementDisplayedInList(element,val)){
				System.out.println("Element"+" "+val+" not displayed");
				logger.debug("Element"+" "+val+" not displayed");
				scrollDownToElement(element,val);
				if(isElementDisplayedInList(element,val)){
					System.out.println("Element"+" "+val+" displayed");
					logger.debug("Element"+" "+val+" displayed");
					clickButton(element,val);
				}
			}
		}catch(Exception e){
			test.log(LogStatus.ERROR, printError(e,2));
			test.log(LogStatus.FAIL, "Could not select the given item from list" );
		}
	}

	public void selectFirstItemFromList(T elementList){
		try {
			List<WebElement> items = getWebElementList(elementList);
			items.get(0).click();

		}catch(Exception e){
			test.log(LogStatus.FAIL, "Could not select first from list" + test.addScreenCapture(addScreenshot()));
		}
	}
	public void selectAnItemFromList(T list, T element, String val) throws Exception{
		clickButton(list);
		wait(1);
		if(isElementDisplayed(element, val)){
			logger.info("Item visible");
			System.out.println("Item visible");
			clickButton(element, val);
		}
	}
	public void selectItemFromAlist(T element, String itemname) {
		try {
			logger.info("Inside selectItemFromAlist ");
			List<WebElement>itemlist = getWebElementList(element);
		//	wait.until(ExpectedConditions.visibilityOf(itemlist.get(0)));
			for(WebElement li:itemlist){
				logger.info("Text of li is:"+li.getText());
				if(li.getText().equalsIgnoreCase(itemname)){
					wait.until(ExpectedConditions.elementToBeClickable(li));
					li.click();
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Failed to select:",e);
			test.log(LogStatus.FAIL, "Could not select item from list" + test.addScreenCapture(addScreenshot()));
			//throw e;
			logger.error("Not able to select the desired element from List:",e);
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
	public boolean isElementPresent(T element) {
		boolean isDisplayed = false;
		try {
			if(getWebElement(element).isDisplayed()){
				isDisplayed = true;
			}
		}catch(Exception e) {
			logger.error("Element not displayed",e);
			isDisplayed = false;
		}
		return isDisplayed;
	}
	public void hitEnterKey(T element){
		try {
			getWebElement(element).sendKeys(Keys.RETURN);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Hit Enter was not success");
		}
	}
	public void hitDownKey(T element){
		getWebElement(element).sendKeys(Keys.DOWN);

	}
	public void hitUpKey(T element){
		try {
			getWebElement(element).sendKeys(Keys.UP);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Hit Up was not success");
		}
	}
	public String getTextValue(T element,String dynamicVal)  {
		WebElement we = null;
		waitForVisibilityOfElement(element, dynamicVal);
		we=getWebElement(element, dynamicVal);
		return we.getText();
	}
	public String getTextValue(T element)  {
		WebElement we = null;
		waitForVisibilityOfElement(element);
		we=getWebElement(element);
		return we.getText();
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
		}catch(Exception e) {
			//logger.error("Error in input text:" + e.toString());
			test.log(LogStatus.FAIL, "Could not enter text" + test.addScreenCapture(addScreenshot()));
			throw e;
		}
	}

	public void inputText(T element,String value)  {
		try {
			logger.info("Inside inputText to enter "+value);
			we = getWebElement(element);
			wait.until(ExpectedConditions.visibilityOf(we));
			we.click();
			we.clear();
			we.sendKeys(value);
			//wait.until(ExpectedConditions.textToBePresentInElement(we,value));
			test.log(LogStatus.PASS, " Enter Text", "Entered value  " + value + " in " + element+" field");
		}catch(NoSuchElementException e) {
			//logger.error("Error in input text:" + e.toString());
			test.log(LogStatus.FAIL, "Could not enter text" + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.ERROR, printError(e,3));
			throw e;
		}catch(Exception e) {
			//logger.error("Error in input text:" + e.toString());
			test.log(LogStatus.FAIL, "Could not enter text" + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.ERROR, printError(e,3));
			throw e;
		}
	}

	public void clickWithJavaScript(T element,String val) throws Exception {
		try {
			we = getWebElement(element,val);
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", val);
		}catch(Exception e) {
			logger.error("Failed to click by Actions");
			throw e;
		}
	}
	public void clickWithJavaScript(T element) throws Exception {
		try {
			we = getWebElement(element);
		}catch(Exception e) {
			waitForVisibilityOfElement(element);
		}
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	public void clickByActions(T element,String val) throws Exception {
		wait(1);
		try {
			we = getWebElement(element,val);
			actions.moveToElement(we).click();
			actions.perform();
			test.log(LogStatus.PASS, "Click element", "Clicked  " + val);

		}catch(Exception e) {
			logger.error("Failed to click by Actions");
			test.log(LogStatus.ERROR, printError(e,3));
			test.log(LogStatus.FAIL, "Could not click element" +val+" ");
			throw e;
		}
	}

	public void clickButton(T element,String val) throws Exception {

		try {
			we = getWebElement(element,val);
			we.click();
			test.log(LogStatus.PASS, "Click element", "Selected  " + val);
		}catch(NoSuchElementException e){
			waitForVisibilityOfElement(element,val);
			we.click();
		}catch(ElementNotInteractableException e) {
			wait.until(ExpectedConditions.visibilityOf(we));
			waitForVisibilityOfElement(element,val);
			//	JavascriptExecutor js= (JavascriptExecutor) driver;
			javascript.executeScript("arguments[0].click();", we);
		}
		catch(Exception e) {
			logger.error("Error in click:",e);
			test.log(LogStatus.FAIL, "Could not click element" + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.ERROR, printError(e,3));
			throw e;
		}
	}
	public void
	clickButton(T element) throws Exception {
		//waitForVisibilityOfElement(element);

		try {
			we = getWebElement(element);
			System.out.println("Element foud");
			we.click();
			test.log(LogStatus.PASS, "Click element", "Clicked  " + element);
		}
		catch(ElementClickInterceptedException e) {
			//	JavascriptExecutor js= (JavascriptExecutor) driver;
			actions.moveToElement(we).click().perform();
			//we.click();
		}
		catch(ElementNotInteractableException e) {
			//	JavascriptExecutor js= (JavascriptExecutor) driver;
			wait.until(ExpectedConditions.visibilityOf(we));
			we.click();
		}
		catch(Exception e) {
			logger.error("Error in click:" ,e);
			test.log(LogStatus.FAIL, "Could not click element" + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.ERROR, printError(e,3));
			throw e;
		}
	}
	public void navigateToHomePage() {
		driver.findElement(By.xpath("//li[@id='homeLink']"));
	}

	public boolean isErrorPresent(){
		boolean isPresent = false;
		try{
			WebElement we = driver.findElement(By.xpath("//span[contains(@class,'warning-text') and text()='Error']"));
			if(we.isDisplayed()){
				isPresent = true;
			}

		}catch(Exception e){
         logger.debug("Error warning not found");
		}
		return isPresent;
	}
	public void waitForInvisibilityOfLoader() {

		try {
			By byelement = By.xpath("//img[@src='../../images/assets/loader3.gif']");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(byelement));
		} catch (Exception e) {
			test.log(LogStatus.ERROR, printError(e,3));
			logger.error("Loader image still visible within wait time",e);
			throw e;
		}
	}
	public void waitForInvisibilityOfSuccessPopup() {
		try {
			By byelement = By.xpath("//div[@class='sbi2-success-popup-msg']");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(byelement));
		} catch (Exception e) {
			test.log(LogStatus.FAIL, printError(e,3));
			logger.error("Success popup still visible after wait time",e);
			throw e;
		}
	}
	public void waitForInvisibilityOfElement() {

		try {
			By byelement = By.xpath("//div[@class='sbi2-success-popup-msg']");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(byelement));
		} catch (Exception e) {
			test.log(LogStatus.FAIL, printError(e,3));
			logger.error("Success popup still visible after wait time", e);
			throw e;
		}
	}
	public void waitForVisibilityOfSuccessMessage() {

		try {
			By byelement = By.xpath("//div[@class='sbi2-success-popup-msg']");
			wait.until(ExpectedConditions.visibilityOfElementLocated(byelement));
		} catch (Exception e) {
			test.log(LogStatus.FAIL, printError(e,3));
			logger.error("Success Message not yet visible within wait time",e);
			throw e;
		}
	}

	public void waitForElementToBePresent(T element)  {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(element.getBy()));
		}catch(Exception e) {
			test.log(LogStatus.FAIL, printError(e,3));
			logger.error("Element not visible within given wait time"+e);
			throw e;
		}
	}
	public void waitForElementToBePresent(T element,String val)  {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(element.getDynamicBy(val)));
		}catch(Exception e) {
			test.log(LogStatus.FAIL, printError(e,3));
			logger.error("Element not visible within given wait time"+e);
			throw e;
		}
	}
    public void waitForTheVisibility(WebElement we){
		wait.until(ExpectedConditions.visibilityOf(we));
	}
	public void waitForVisibilityOfElement(T element)  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element.getBy()));
		}catch(TimeoutException e) {
			wait.until(ExpectedConditions.presenceOfElementLocated(element.getBy()));
		}catch(Exception e){
			logger.error("Element not visible within given wait time",e);
			test.log(LogStatus.ERROR, printError(e,3));
			test.log(LogStatus.FAIL, "Element"+" "+element+" "+" not visible");
			e.printStackTrace();
			throw e;
		}
	}

	public void waitForVisibilityOfElement(T element,String val)  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element.getDynamicBy(val)));

		}catch(Exception e) {
			test.log(LogStatus.FAIL, "Element"+" "+val+" "+ "not visible" + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.ERROR,printError(e,3));
			logger.error("Element not visible within given wait time"+e);
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
	public void acceptAlert(){
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	public boolean isAlertPresent() throws Exception {
		boolean alert = false;
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			alert= true;
		} catch (TimeoutException eTO) {
			alert = false;
		}
		return alert;
	}
	public void clickOkIfWarningPresent(){
		By warn = By.id("warning_Box");
		By ok = By.xpath("//button[@title='OK']");
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(warn));
			if(driver.findElement(warn).isDisplayed()){
				driver.findElement(ok).click();
			}

		} catch (Exception e) {

		}

	}
	public void waitAndClick(T element,String dynamicVal) throws Exception {
		waitForVisibilityOfElement(element,dynamicVal);
		try {
			we = getWebElement(element,dynamicVal);
			we.click();
			test.log(LogStatus.PASS, "Click element", "Clicked  " + element);
		}
		catch(Exception e) {
			logger.error("Error in click:" + e.getMessage());
			test.log(LogStatus.FAIL, "Could not click element" +" "+dynamicVal+ test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.ERROR, printError(e,3));
		}
	}
	public void test(){
		System.out.println("Test");
	}
	public void waitAndClick(T element) throws Exception {

		try {
			waitForVisibilityOfElement(element);
			we = getWebElement(element);
			we.click();
			test.log(LogStatus.PASS, "Click element", "Clicked  " + element);
		}
		catch(Exception e) {
			logger.error("Error in click:" + e.getMessage());
			test.log(LogStatus.FAIL, "Could not click element" + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.ERROR, printError(e,3));
		}
	}
	public boolean isElementDisplayed(T element){
		boolean displayed = false;
		try{
			if(getWebElement(element).isDisplayed()){
				displayed = true;
			}
		}catch(Exception e){
			displayed = false;
		}
		return displayed;
	}
	public boolean isElementDisplayedInList(T element, String val){
		boolean displayed = false;
		try{
			if(getWebElement(element,val).isDisplayed()){
				displayed = true;
			}
		}catch(Exception e){
			displayed = false;
		}
		return displayed;
	}

	public boolean isListElementDisplayed(T element){
		boolean displayed = false;
		try{
			waitForVisibilityOfElement(element);
			if(getWebElement(element).isDisplayed()){
				displayed = true;
			}
		}catch(Exception e){
			displayed = false;
		}
		return displayed;
	}

	public void dragAndDrop(T element,String val,T destination) throws Exception {
		try {
			waitForVisibilityOfElement(element, val);
			actions.dragAndDrop(getWebElement(element, val), getWebElement(destination)).build().perform();
		}catch(Exception e){
			test.log(LogStatus.ERROR, printError(e,3));
			test.log(LogStatus.FAIL, "Could not drag the element" );

		}
	}

	public void dragAndDropFromList(T element,T destination) throws Exception {
		try {
			List<WebElement> tablelist = driver.findElements(element.getBy());
			System.out.println("The table name is "+tablelist.get(0));
			actions.dragAndDrop(tablelist.get(0), getWebElement(destination,"")).build().perform();
		}catch(Exception e) {
			logger.error("Error in dragAndDrop:" + e.getMessage());
			test.log(LogStatus.ERROR, printError(e,3));
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

	public void scrollDown300() throws Exception {
		javascript.executeScript("window.scrollBy(0,300)", "");
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

	public void scrollDownAndClick(T element,String val) throws Exception {
		actions.moveToElement(getWebElement(element,val));
		actions.click();
		actions= (Actions) actions.build();
		actions.perform();
	}

	public void scrollDownToElement(T element) throws Exception {
		actions.moveToElement(getWebElement(element)).perform();

	}
	public void scrollDownToElement(T element, String val)  {
		try {
			actions.moveToElement(getWebElement(element, val)).perform();
			logger.info("Moved to target element");
			System.out.println("Moved to target element");
			wait(1);
		}catch(Exception e){
			logger.debug("Action for move to element was nopt successfull",e);
		}

	}
	public void logInfo(ExtentTest test, String message){
		test.log(LogStatus.INFO, message);//For extentTest HTML report
		logger.info("Info: " + message);

	}
	public void logFailure(ExtentTest test, String message){
		logger.info("Info: " + message);
		test.log(LogStatus.FAIL, "message" );

	}

	public void refreshPage() throws Exception {
		//wait(2);
		driver.navigate().refresh();

	}


}
