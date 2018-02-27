package com.selenium.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.utilities.ExtentManager;
import com.selenium.utilities.TestUtil;
import com.selenium.utilities.Xls_Reader;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static Xls_Reader excel = new Xls_Reader(System.getProperty(("user.dir"))+"\\src\\test\\java\\excel\\testData.xls");
	public static WebDriverWait wait;
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	
	@BeforeSuite
	public void setUp() throws IOException{
		
		if(driver == null){
			
			log.debug("Loading the config file..!!");
			 fis = new FileInputStream(System.getProperty(("user.dir"))+"\\src\\test\\java\\properties\\Config.properties");
			config.load(fis);
			
			log.debug("Loading the OR file..!!");
			fis = new FileInputStream(System.getProperty(("user.dir"))+"\\src\\test\\java\\properties\\OR.properties");
			OR.load(fis);
			
			if(config.getProperty("browser").equals("Firefox")){
				
				log.debug("Loading the Firefox Browser");
				driver = new FirefoxDriver();
				
			}else if(config.get("browser").equals("Chrome")){
				
				log.debug("Loading the Chrome Browser");
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\java\\executables\\chromedriver.exe"); 
				driver = new ChromeDriver();
				
			}else if(config.getProperty("browser").equals("IE")){
				
				log.debug("Loading the IE Browser");
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\test\\java\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				
			}
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){
				
				browser = System.getenv("browser");
				
				
			}else{
				
				browser = config.getProperty("browser");
				
			}
			
			config.getProperty("browser", browser);
			
			driver.get(config.getProperty("testSiteURL"));
			log.debug("Navigating to : "+config.getProperty("testSiteURL"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			wait = new WebDriverWait(driver,5);
		
			}
		
	}
	
	public void click(String locator){
		
		if(locator.endsWith("_CSS")){
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		
		}else if(locator.endsWith("_XPATH")){
			
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
				
		}else if(locator.endsWith("_ID")){
			
			driver.findElement(By.id(OR.getProperty(locator))).click();
			
		}
		
		test.log(LogStatus.INFO, "Clicking on : "+locator);
	}
	
	public void type(String locator, String value){
		
		if(locator.endsWith("_CSS")){
			
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		
		}else if(locator.endsWith("_XPATH")){
			
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
			
		}else if(locator.endsWith("_ID")){
			
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		
		test.log(LogStatus.INFO, "Typing in : "+locator+" entered Values as : "+value);
		
	}
	
	static WebElement dropdown;
	public void select(String locator, String value){
		
		if(locator.endsWith("_CSS")){
			
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		
		}else if(locator.endsWith("_XPATH")){
			
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		
		}else if(locator.endsWith("_ID")){
			
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, "Selecting from dropdown : "+locator+ " value as "+value);
	
	}
	
	
	
	public boolean isElementPresent(By by){
		
		try{
			
			driver.findElement(by);
			return true;
			
		}catch(NoSuchElementException e){
			
			return false;
		}
		
			
	}
	

	
	public static void verifyEquals(String expected, String actual) throws IOException{
		
		try{
			
			Assert.assertEquals(actual, expected);
			
		}catch(Throwable t){
			
			TestUtil.captureScreenshot();
			Reporter.log("<br>"+"Verification Failure : "+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotPath+"><img src="+TestUtil.screenshotPath+" height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			test.log(LogStatus.FAIL, " Varification Failed with Exception : "+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotPath));
			
			
			
		}
		
		
	}
	
	@AfterSuite
	public void tearDown(){
		
		if(driver!=null){
			
			driver.quit();
			log.debug("Browser successfully closed..!!");
			
		}
		
	}

}
