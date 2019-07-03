package com.bosch.validation.bh.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;
import com.bosch.validation.fw.setup.PageObjectFactory;
import com.bosch.validation.fw.setup.PageObjectSkeleton;

public class MasterPage  extends PageObjectSkeleton implements PageObject{

	@Override
	public <P extends PageObject> P initPageObject(Class<P> pageObjectClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private WebDriver driver;
	
	public MasterPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public Result verifyLink(WebElement webelement, String elementName, String pageTitle, ExtentTest testReport){

		Result res = new Result();		
		
		if(waitAndClick(webelement, 60)){//if exist
			//PageObjectFactory.initPageObject(MainPage.class, driver);
			String title = driver.getTitle();
			if(title.equalsIgnoreCase(pageTitle)){
				res.setResult(true);
				res.setMessage("");
				testReport.pass(String.format("Page [%s] - <DISPLAYED>", pageTitle));
			}
			else{
				res.setMessage(String.format("Page [%s] - <NOT DISPLAYED>", pageTitle));
				testReport.fail(String.format("Page [%s] - <NOT DISPLAYED>", pageTitle));
			}
		}
		else{
			res.setMessage(String.format("Can't found the web element [%s]", elementName));
			testReport.fail(String.format("Can't found the web element [%s]", elementName));
		}		
		
		return res;
	}
	
	public Result verifyWebElementContent(WebElement webelement, String elementName, String webElementContent, ExtentTest testReport){

		Result res = new Result();		
		if(waitForElementDisplay(webelement, 60)){//if exist
			String actualContent = webelement.getText();
			if(actualContent.contains(webElementContent)){
				res.setResult(true);
				res.setMessage("");
				testReport.pass(String.format("WebElement [%s] - [TEXT: %s][CONTAIN: %s]", elementName, actualContent, webElementContent));
			}
			else{
				res.setMessage(String.format("WebElement [%s] - [Actual Text: %s][Expected Text: %s]", elementName, actualContent, webElementContent));
				testReport.fail(String.format("WebElement [%s] - [Actual Text: %s][Expected Text: %s]", elementName, actualContent, webElementContent));
			}
		}
		else{
			res.setMessage(String.format("Can't found the web element [%s]", elementName));
			testReport.fail(String.format("Can't found the web element [%s]", elementName));
		}		
		
		return res;
	}
	
	public Result verifyWebElementExist(WebElement webelement, String elementName) {
		Result res = new Result();
		if (waitForElementDisplay(webelement, 60)) {// if exist
			boolean bExist = webelement.isDisplayed();
			if (bExist) {
				res.setResult(true);
				res.setMessage(String.format("WebElement [%s] - <DISPLAYED>", elementName));

			} else {
				res.setMessage(String.format(
						"WebElement [%s] - <NOT DISPLAYED>", elementName));
			}
		} else {
			res.setMessage(String.format("%s is NOT displayed, which is unexpected.", elementName));
		}
		return res;
	}
	
	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}
	
	public WebElement findElement(By by) {
		try {		
			return driver.findElement(by);
		} catch (Exception e) {
			return null;
		}
	}
}
