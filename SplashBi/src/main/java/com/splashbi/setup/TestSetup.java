package com.splashbi.setup;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import com.splashbi.pageobject.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.splashbi.report.ExtentReport;
import com.splashbi.utility.Constant;
import com.splashbi.utility.ExcelUtility;
import com.splashbi.utility.Utility;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestSetup {
public static WebDriver driver;
	
	public ExtentReports extent;
	public ExtentTest test;
	public static Logger logger = Logger.getLogger(TestSetup.class);
	public static ExcelUtility xl;
	public static String sheetName = "TestData";
	ExtentReport extentreport = new ExtentReport();
	public static LoginPage login=null;
	public static HomePage home = null;
	public static DomainPage domain = null;
	public static ReportPage report = null;
	public static AdminPage admin = null;
	public static ConnectorsPage connector = null;
	
	public static WebDriver initDriver() {
		WebDriverManager.chromedriver().setup();
		try {
		//System.setProperty("webdriver.chrome.driver",Utility.getValueFromPropertyFile(Constant.CONFIG_PATH, "chromedriver_path"));
		if(Utility.getValueFromPropertyFile(Constant.CONFIG_PATH, "run_mode").equalsIgnoreCase("headless"))
		{
			driver = runInHeadLess();
		}
		else {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		}catch(Exception e) {
			logger.error("Failed to initialize Chromedriver",e);
			System.out.println("Failed to initialize Chromedriver");
		}
		return driver;
	}
	
	public static void initializePageObjects() {
		login = new LoginPage(driver);
		home = new HomePage(driver);
		domain = new DomainPage(driver);
		report = new ReportPage(driver);
		admin = new AdminPage(driver);
		connector = new ConnectorsPage(driver);
	}
	public static WebDriver runInHeadLess() {
		ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1366,768");
        driver = new ChromeDriver(options);
        return driver;
	}
	
	@BeforeTest
	public void killActiveProcess() throws Exception {
		xl = new ExcelUtility(Constant.TEST_DATA);
		System.out.println("Kiiling chromedriver.exe");
		Runtime.getRuntime().exec("TASKKILL /IM chromedriver.exe /F");
		//extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/STMExtentReport.html", true);
		extent = extentreport.getInstance("SanityData");
		String log4j = Constant.LOG4J_PATH;
		PropertyConfigurator.configure(log4j);
		initDriver();
	}
	
		
	@BeforeMethod
	public void beforeMethod(Method method) {
		try {
			System.out.println("Entering method:"+method.getName());
			test = extent.startTest(method.getName());
			System.out.println("Test is:"+test);
			System.out.println("Entering method:"+method.getName());
		}catch(Exception e) {
			logger.error("before method failed",e);
			e.printStackTrace();
		}
	}
	
	@AfterMethod
	
	public void afterMethod(ITestResult result) {
		try {
			if(result.getStatus() == ITestResult.SUCCESS)
			{
				test.log(LogStatus.PASS, "passed");
			}
			if(result.getStatus() == ITestResult.SKIP) {
				test.log(LogStatus.SKIP, "skipped");
			}
			if(result.getStatus() == ITestResult.FAILURE) {
				test.log(LogStatus.FAIL, "failed");
			}
			extent.endTest(test);
			
		} catch (Exception e) {
			e.printStackTrace();
	}
		finally{
		System.out.println("test completed");
		
		}
	}
	public void setChildTest(ExtentTest childtest ) {
		System.out.println("in setChildTest");
		login.setTest(childtest);
		connector.setTest(childtest);
		connector.setTest(childtest);
		home.setTest(childtest);
		domain.setTest(childtest);
		report.setTest(childtest);
		test.appendChild(childtest);
	}
	
	@BeforeClass
	
	public void initializeClassValues() {
		initializePageObjects();
		System.out.println("Before class executed");
	}
	@AfterClass
	 public void afterClass() {
		extent.endTest(test);
		extent.flush();
		driver.quit();
	}
	@AfterTest
	
	public void closetest() {
	//	extent.flush();
	//	extent.close();
	}
	
	
	@DataProvider(name="LoadTestData")
	public static Object[][] getTestData(Method method) {
        System.out.println("In Dataprovider");
		return xl.getData(xl, sheetName, method.getName());
	}
	
	
	@DataProvider(name="LoadData")
	public static Object[][] tableFilters(Method method) {
		return Utility.getData(new File(Constant.TEST_DATA_JSON), method.getName());
	}
}
