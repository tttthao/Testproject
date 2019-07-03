package com.bosch.validation.bh.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;
import com.bosch.validation.fw.setup.PageObjectSkeleton;

public class MyHome extends PageObjectSkeleton implements PageObject {

	@Override
	public <P extends PageObject> P initPageObject(Class<P> pageObjectClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@FindBy(how = How.XPATH, using = "//div[@class='global-bar']//button[@class='btn-ql']")
	WebElement ButtonQuicklink;
	

	
	public Result ClickQuickLink() {
		Result res = new Result();
		
		waitAndClick(ButtonQuicklink, 5);
		
		return res;
	}
	
	

}
