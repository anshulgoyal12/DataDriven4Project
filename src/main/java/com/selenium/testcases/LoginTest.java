package com.selenium.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.selenium.base.TestBase;
import com.selenium.utilities.TestUtil;

public class LoginTest extends TestBase{
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void loginTest(Hashtable<String, String> data) throws InterruptedException, IOException{
		
		if(!TestUtil.isTestRunnable("LoginTest", excel)){
			
			throw new SkipException("Skipping the test case "+"loginTest"+" as run mode is NO");
		}
		
		if(!data.get("Runmode").equals("Y")){
			
			throw new SkipException("Skipping the test case as the run mode for data is set to No");
		}
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		
		log.debug("Try to login as a Buyer");
		
	
			
		verifyEquals("abc", "def");
		Thread.sleep(2000);
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("signInLink_CSS"))),"Login not successfull");
		
		click("signInLink_CSS");
		click("signInLinkAsBuyer_XPATH");
		type("Email_XPATH", data.get("Email"));
		type("Password_XPATH", data.get("Password"));
		click("LoginButton_XPATH");
		Thread.sleep(3000);
		
		/*Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(alertText));
		alert.accept();*/
		
		
		log.debug("Login is successfully executed!!");
		
		//Assert.fail();
		
	}

}
