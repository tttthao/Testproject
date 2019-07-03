package com.bosch.validation.bh.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;
import com.bosch.validation.fw.setup.PageObjectFactory;

public class MainPage extends MasterPage {
	private static final Logger log = LogManager.getLogger(MainPage.class);

	//My Workspace Main Page
	@FindBy(how = How.XPATH, using = "//button[@class='btn-ql']")
	WebElement btnAllQuickLink;
	
	@FindBy(how = How.XPATH, using = "//button[@class='btn-es']")
	WebElement btnEnterpriseSearch;
	
	@FindBy(how = How.CSS, using = "#meta_nav .M-LanguageSelector__languageSelect")
	WebElement cbxSelectLanguage;
	
	@Override
	public <P extends PageObject> P initPageObject(Class<P> pageObjectClass) {
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

	public MainPage(WebDriver driver) {
		super(driver);
		this.driver = driver; 
	}
	
	/*
	 * @author: AIT1HC
	 */
	public Result clickAllQuickLinks() {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnAllQuickLink, 20));
		btnAllQuickLink.click();
		return res;
	}
	
	/*
	 * @author: AIT1HC
	 */
	public Result clickEnterpriseSearch() {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnEnterpriseSearch, 20));
		sleep(1000);
		btnEnterpriseSearch.click();
		return res;
	}
	
	public Result changeLanguage(String shortLanguage) {
		Result res = new Result();
		try {
			waitForElementDisplay(btnAllQuickLink, 60);
			Select selectLanguage = new Select(cbxSelectLanguage);
			String currentLanguage = selectLanguage.getFirstSelectedOption().getText();
			String expectedLanguage = "";
			switch (shortLanguage.toLowerCase()) {
			case "en":
				expectedLanguage = "English";
				break;
			case "de":
				expectedLanguage = "Deutsch";
				break;
			}
			if (expectedLanguage.equals(currentLanguage)) {
				waitForElementDisplay(btnAllQuickLink, 60);
				res.setResult(true);
				res.setMessage("Change to language '" + expectedLanguage + "' successfull");
			} else {
				selectLanguage.selectByVisibleText(expectedLanguage);
				waitForElementDisplay(btnAllQuickLink, 60);
				res.setResult(true);
				res.setMessage("Change to language '" + expectedLanguage + "' successfull");
			}

		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}
		return res;
	}
}