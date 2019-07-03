package com.bosch.validation.bh.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;
import com.bosch.validation.fw.setup.PageObjectFactory;

public class MyWorkspacePage extends MasterPage {
	private static final Logger log = LogManager.getLogger(MyWorkspacePage.class);
	
	//My Workspace Off Canvas Header
	@FindBy(how = How.XPATH, using = "//div [@class='header-top-content-container'] //span [@class='headline']")
	WebElement lablelMyWorkspace;
	
	@FindBy(how = How.XPATH, using = "//mws-off-canvas-header-top//span[@class = 'button mws-icon-options mws-icon-lg']")
	WebElement btnMyWorkspace;
	
	@FindBy(how = How.XPATH, using = "//div[@class='mws-icon mws-icon-settings']/../span")
	WebElement btnSettingMyWorkspace;
	
	@FindBy(how = How.XPATH, using = "//li [@_ngcontent-c5=''] [2]")
	WebElement btnGiveFeedback;
	
	@FindBy(how = How.CSS, using = ".mws-icon-close ")
	WebElement btnClose;
	
	//Quick Links & Enterprise Search Sticky Header
	@FindBy(how = How.XPATH, using = "//div [@class='header-sub-container'] //div [contains(@class,'quick-links')]")
	WebElement tabQuickLinks;
	
	@FindBy(how = How.XPATH, using = "//div [@class='header-sub-container'] //div [contains(@class,'enterprise-search')]")
	WebElement tabEnterpriseSearch;
	
	
	//Feedback Footer
	@FindBy(how = How.XPATH, using = "//div [@class='feedback-description']")
	WebElement txtFeedbackDescription;
	
	@FindBy(how = How.XPATH, using = "//div [@class='feedback-button']")
	WebElement btnFeedback;
	
	
	//Footer
	@FindBy(how = How.XPATH, using = "//div [@class='copyright-label']")
	WebElement labelCopyright;
	
	@FindBy(how = How.XPATH, using = "//div [@class='imprint-legal-notice-label']")
	WebElement labelImprint;
	
	@FindBy(how = How.XPATH, using = "//div [@class='footer-content-bottom'] //span[contains(text(),'/')]")
	WebElement labelSlash;
	
	@FindBy(how = How.XPATH, using = "//div [@class='legal-notice-label imprint-legal-notice-label']")
	WebElement labelLegalNotice;
	
	@FindBy(how = How.XPATH, using = "//div [@class='icon-up-container-large']")
	WebElement btnBacktoTop;

	@FindBy(how = How.XPATH, using = "//div [@class='footer-content'] //span[contains(text(),'E-Mail')]")
	WebElement labelEmail;
	
	@FindBy(how = How.XPATH, using = "//div [@class='footer-content'] //a[@href='mailto:CIHotline@bosch.com']")
	WebElement linkEmail;
	
	@FindBy(how = How.XPATH, using = "//div [@class='footer-content'] //span[contains(text(),'CI Hotline')]")
	WebElement labelCIHotline;
	
	@FindBy(how = How.XPATH, using = "//div [@class='footer-content'] //span[contains(text(),'3311')]")
	WebElement lablCIHotlineNumber;
	
	@FindBy(how = How.XPATH, using = "//div [@class='workspace-settings-label']")
	WebElement labelWorkspaceSettings;
	
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

	public MyWorkspacePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	
	/*
	 * @author: AIT1HC
	 */
	public Result selectSettingBesideMyWorkspace(ExtentTest testReport) {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnMyWorkspace, 20));
		sleep(500);
		btnMyWorkspace.click();
		if(res.isResult()) {
			res.setResult(waitForElementDisplay(btnSettingMyWorkspace, 20));
			sleep(500);
			if(res.isResult()) {
				btnSettingMyWorkspace.click();
				testReport.pass(String.format("WebElement [%s] - [%s]", "Settings", "Select successfully"));
			}
			else {
				testReport.fail(String.format("WebElement [%s] - [%s]", "Settings", "NOT FOUND"));
				return res;
			}
		}	
		else {
			testReport.fail(String.format("WebElement [%s] - [%s]", "...", "NOT FOUND"));
			return res;
		}
		
		return res;
	}
	
	
	/*
	 * @author: AIT1HC
	 */
	public Result closeMyWorkspace(ExtentTest testReport) throws InterruptedException {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnClose, 20));
		btnClose.click();
		if(res.isResult()) {
			Thread.sleep(3000);
			try {
				btnClose.isDisplayed();
				res.setResult(false);
				testReport.fail(String.format("WebElement [%s] - [%s]", "Close button", "Still display after close"));
				return res;
			}catch(Exception e) {	
				testReport.pass(String.format("WebElement [%s] - [%s]", "Close button", "Successfully"));
			}
		}	
		else {
			testReport.fail(String.format("WebElement [%s] - [%s]", "Close button", "NOT FOUND"));
			return res;
		}
		
		return res;
	}
	
	/*
	 * @author: AIT1HC
	 */
	public Result clickOnSettingLabelAtTheBottom(ExtentTest testReport) {
		Result res = new Result();
		res.setResult(waitForElementDisplay(labelWorkspaceSettings, 20));
		sleep(500);
		if(!res.isResult()) {
			testReport.fail(String.format("WebElement [%s] - [%s]", "Setting link at the bottom of page", "NOT FOUND"));
			return res;
		}
		clickByJS(driver, labelWorkspaceSettings);
		testReport.pass(String.format("WebElement [%s] - [%s]", "Setting link at the bottom of page", "Click Successfully"));
		return res;
	}
	
	
}