package com.splashbi.setup;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//import com.splashbi.pageobject.*;
//import com.splashbi.pageobject.admin.*;
import com.splashbi.pageobject.admin.setup.BusinessAppPage;
import com.splashbi.pageobject.admin.setup.FolderPage;
import com.splashbi.pageobject.admin.setup.UserGroupPage;
import com.splashbi.report.ExecutionReport;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
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

	public static ExtentReports extent;
	public ExtentTest test;
	public static Logger logger = Logger.getLogger(TestSetup.class);
	public static ExcelUtility xl;
	public static String sheetName = "TestData";
	ExecutionReport execRep = new ExecutionReport();
	ExtentReport extentreport = new ExtentReport();
	public static com.splashbi.pageobject.LoginPage login=null;
	public static com.splashbi.pageobject.HomePage home = null;
	public static com.splashbi.pageobject.DomainPage domain = null;
	public static com.splashbi.pageobject.ReportPage report = null;
	public static com.splashbi.pageobject.admin.AdminPage admin = null;
	public static com.splashbi.pageobject.admin.ConnectorsPage connector = null;
	public static com.splashbi.pageobject.admin.UsersPage users = null;
	public static com.splashbi.pageobject.admin.SetupPage setup = null;
	public static UserGroupPage usergroup = null;
	public static com.splashbi.pageobject.admin.SettingsPage setting = null;
	public static FolderPage folder = null;
	public static BusinessAppPage businessapp = null;
	public static com.splashbi.pageobject.admin.ERPMappingPage erpmap = null;
	public static com.splashbi.pageobject.DashboardPage dashboard = null;
	Date startDate = null;
	Date endDate = null;
	static int totalTestCount = 0;
	static int totalTestPassed = 0;
	static int totalTestFailed = 0;
	static int totalTestSkipped = 0;

	//Variables to be used
	public static String domainName = Utility.getRandomNumber(Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"domainname"));
	//public static String ebsDomainName =
	public static String ebsDomainName = Utility.getRandomNumber(Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"ebs_domainname"));
	public static String reportName = "";
	public static String chartName = "";
	public static String dashboardName = "";
	public static String user_password = Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"user_password");
	public static String new_password = Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"new_password");
	public static String reportSetName = Utility.getRandomNumber(Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"repsetname"));;
	public static String userName= "";
	public static String firstName = Utility.getRandomNumber(Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"firstname"));
	public static String lastName = Utility.getRandomNumber(Utility.getValueFromPropertyFile(Constant.CONFIG_PATH,"lastname"));
	public static String connectorName = "";
	public static String empName = firstName+" "+lastName;
	public static String businessAppName = "";
	public TestSetup(ExtentTest test){
		this.test=test;
	}
	public TestSetup(){

	}
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
			//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		}
		}catch(Exception e) {
			logger.error("Failed to initialize Chromedriver",e);
			System.out.println("Failed to initialize Chromedriver");
		}
		return driver;
	}
	
	public static void initializePageObjects() {
		login = new com.splashbi.pageobject.LoginPage(driver);
		home = new com.splashbi.pageobject.HomePage(driver);
		domain = new com.splashbi.pageobject.DomainPage(driver);
		report = new com.splashbi.pageobject.ReportPage(driver);
		admin = new com.splashbi.pageobject.admin.AdminPage(driver);
		users = new com.splashbi.pageobject.admin.UsersPage(driver);
		setup = new com.splashbi.pageobject.admin.SetupPage(driver);
		setting = new com.splashbi.pageobject.admin.SettingsPage(driver);
		folder = new FolderPage(driver);
		usergroup = new UserGroupPage(driver);
		connector = new com.splashbi.pageobject.admin.ConnectorsPage(driver);
		erpmap = new com.splashbi.pageobject.admin.ERPMappingPage(driver);
		businessapp = new BusinessAppPage(driver);
		dashboard = new com.splashbi.pageobject.DashboardPage(driver);
	}
	public void setDatapoints(){

	}
	public static WebDriver runInHeadLess() {
		ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1366,768");
        driver = new ChromeDriver(options);
        return driver;
	}
	
	@BeforeTest
	public void killActiveProcess(ITestContext test) throws Exception {
		String log4j = Constant.LOG4J_PATH;
		PropertyConfigurator.configure(log4j);
		startDate=new Date();
	//	xl = new ExcelUtility(Constant.TEST_DATA);
		System.out.println("Killing chromedriver.exe");
		Runtime.getRuntime().exec("TASKKILL /IM chromedriver.exe /F");
		//extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/STMExtentReport.html", true);
		extent = extentreport.getInstance("SanityData");
	    initDriver();
	}
	
		
	@BeforeMethod
	public void beforeMethod(Method method) {
		initializePageObjects();
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
		totalTestCount++;
		try {
			if(result.getStatus() == ITestResult.SUCCESS)
			{
				test.log(LogStatus.PASS, "passed");
				totalTestPassed++;
			}
			if(result.getStatus() == ITestResult.SKIP) {
				test.log(LogStatus.SKIP, "skipped");
				totalTestSkipped++;
			}
			if(result.getStatus() == ITestResult.FAILURE) {
				test.log(LogStatus.FAIL, "failed");
				totalTestFailed++;
			}

			extent.endTest(test);
		//	driver.quit();
			
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
		admin.setTest(childtest);
		domain.setTest(childtest);
		report.setTest(childtest);
		users.setTest(childtest);
		usergroup.setTest(childtest);
		setup.setTest(childtest);
		setting.setTest(childtest);
		folder.setTest(childtest);
		businessapp.setTest(childtest);
		erpmap.setTest(childtest);
		dashboard.setTest(childtest);
		test.appendChild(childtest);
	}
	public void endChildTest(ExtentTest childtest){
		extent.endTest(childtest);
	}
	
	@BeforeClass
	
	public void initializeClassValues() {

		System.out.println("Before class executed");
	}
	@AfterClass
	 public void afterClass() {
		System.out.println("In after class");
		//extent.endTest(test);
     logger.info("Class completed");
//	    driver.quit();
	}
	@AfterSuite
	public void generateReport(ITestContext test){
	//driver.quit();
	endDate=new Date();
	execRep.generateExecutionReport(totalTestCount, totalTestPassed, totalTestFailed, totalTestSkipped, startDate,endDate);
	}
	@AfterTest
	
	public void closetest() {
		extent.flush();
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
