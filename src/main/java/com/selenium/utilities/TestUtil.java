package com.selenium.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.selenium.base.TestBase;

public class TestUtil extends TestBase{
	
	public static String screenshotPath;
	public static String screenshotName;
	
	
	public static void captureScreenshot() throws IOException{
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		
		Date d = new Date();
		//System.out.println(d);
		screenshotName = d.toString().replace(":", "_").replace(" ", "_");
		//System.out.println(screenshotName);
		
		screenshotPath = System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenshotName+".jpg";
		System.out.println(screenshotPath);
		FileUtils.copyFile(scrFile, new File(screenshotPath));
		
	}
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m){
		
		String sheetName = m.getName();
		
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[rows-1][1];
		
		Hashtable<String,String> table = null;
		
		for(int rowNum=2; rowNum<=rows;rowNum++){
			
			table = new Hashtable<String,String>();
			
			for(int colNum=0; colNum<cols;colNum++){
				
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				
				data[rowNum-2][0] = table;
			}
		}
		
		
		
		return data;
	}
	
	public static boolean isTestRunnable(String testName, Xls_Reader excel){
		
		String sheetName = "Test_Suite";
		int rows = excel.getRowCount(sheetName);
	
		for(int rowNum=2; rowNum<=rows; rows++){
			
			String testcase = excel.getCellData(sheetName, "TCID", rowNum);
				
				if(testcase.equalsIgnoreCase(testName)){
					
					String runmode = excel.getCellData(sheetName, "Runmode", rowNum);
						
						if(runmode.equalsIgnoreCase("Y"))
							
							return true;
					
							else
								
								return false;
						
				}
				
	
	         }
		
					return false;
	}
	
}	

