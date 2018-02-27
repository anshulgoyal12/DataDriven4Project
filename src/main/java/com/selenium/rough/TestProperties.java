package com.selenium.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {
		
		Properties config = new Properties();
		Properties OR = new Properties();
		
		FileInputStream fis = new FileInputStream(System.getProperty(("user.dir"))+"\\src\\test\\java\\properties\\Config.properties");
		config.load(fis);
		
		System.out.println(config.getProperty("browser"));
		
		fis = new FileInputStream(System.getProperty(("user.dir"))+"\\src\\test\\java\\properties\\OR.properties");
		OR.load(fis);
		
		System.out.println(OR.getProperty("signInLink"));

	}

}