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

public class SettingsPage extends MasterPage {
	private static final Logger log = LogManager.getLogger(SettingsPage.class);

	@FindBy(how = How.XPATH, using = "//div[@class='delete-latest-searches']/a")
	WebElement linkDeleteLatestSearches;
	
	@FindBy(how = How.XPATH, using = "//div[@class = 'reset-quicklinks']/a")
	WebElement linkResetQuickLinks;
	
	@FindBy(how = How.XPATH, using = "//div[@class='reset-searches']/a")
	WebElement linkResetSearches;
	
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'ng-tns-c') and text()='Yes'] | //button[contains(@class,'ng-tns-c') and text()='Ja']")
	WebElement btnConfirmYes;
	
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'ng-tns-c') and text()='No'] | //button[contains(@class,'ng-tns-c') and text()='Nein']")
	WebElement btnConfirmNo;
	
	@FindBy(how = How.XPATH, using = "//div[@class='sn-title']")
	WebElement resultTitle;
	
	@FindBy(how = How.XPATH, using = "//span[@class='mws-icon-lg mws-icon-back-left']")
	WebElement btnNavigateBack;
	


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

	public SettingsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	/*
	 * @author: AIT1HC
	 */
	public Result selectDeleteLatestSearches(String strYesNo, ExtentTest testReport) {
		return this.selectSettingLinks(linkDeleteLatestSearches, "Delete Latest Searches", strYesNo, testReport);
	}
	
	/*
	 * @author: AIT1HC
	 */
	public Result selectResetSearchs(String strYesNo, ExtentTest testReport) {
		return this.selectSettingLinks(linkResetSearches, "Reset Searches", strYesNo, testReport);
	}
	
	/*
	 * @author: AIT1HC
	 */
	public Result selectResetQuickLinks(String strYesNo, ExtentTest testReport) {
		return this.selectSettingLinks(linkResetQuickLinks, "Reset Quick Links", strYesNo, testReport);
	}
	
	/*
	 * @author: AIT1HC
	 */
	public Result verifyResultMessage(List<String> lstExpectedResult, ExtentTest testReport) {
		Result res = new Result();
		String strActualResult;
		res.setResult(waitForElementDisplay(resultTitle, 30));
		strActualResult = resultTitle.getText();
		
		if(res.isResult()) {

			for(int i = 0; i < lstExpectedResult.size(); i++) {
				if (strActualResult.contains(lstExpectedResult.get(i))) {
					res.setResult(true);
					res.setMessage(String.format("WebElement [%s] - Expected result [%s] Actual Result [%s]", "Result Message", lstExpectedResult.get(i), strActualResult));
					break;
				} else {
					if (i == lstExpectedResult.size() - 1) {
						res.setResult(false);
						res.setMessage("Failed: Expected result ["+ lstExpectedResult.get(i)+"], Actual result [" + strActualResult + "]");
						testReport.fail(String.format("WebElement [%s] - Expected result [%s] Actual Result [%s]", "Result Message", lstExpectedResult.get(i), strActualResult));
					}
				}
			}
		}else
			testReport.fail(String.format("WebElement [%s] - [%s]", "Result Message", "NOT FOUND!!!"));
		return res;
	}
	
	/*
	 * @author: AIT1HC
	 */
	private Result selectSettingLinks(WebElement linkToSelect, String linkName, String strYesNo, ExtentTest testReport) {
		Result res = new Result();
		res.setResult(waitForElementDisplay(linkToSelect, 20));
		linkToSelect.click();
		waitForElementDisplay(btnConfirmYes, 10);
		sleep(500);
		
		if(res.isResult() && strYesNo.equalsIgnoreCase("yes")) {
				res.setResult(waitForElementDisplay(btnConfirmYes, 20));
				if(res.isResult())
					btnConfirmYes.click();
				else {
					testReport.fail(String.format("WebElement [%s] - [%s]", "Confirm Yes No", "Not Found"));
					return res;
				}
		}else if(res.isResult() && strYesNo.equalsIgnoreCase("no")) {
				res.setResult(waitForElementDisplay(btnConfirmNo, 20));
				if(res.isResult())
					btnConfirmNo.click();
				else {
					testReport.fail(String.format("WebElement [%s] - [%s]", "Confirm Yes No", "Not Found"));
					return res;
				}
		}else {
			testReport.fail(String.format("WebElement [%s] - [%s]", linkName, "Not Found"));
			return res;
		}
		testReport.pass(String.format("WebElement [%s] - [%s]", linkName, "Select successfully!!!"));
		return res;
	}
	
	
	/*
	 * @author: AIT1HC
	 */
	public Result NavigateBack(ExtentTest testReport) throws InterruptedException {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnNavigateBack, 20));
		btnNavigateBack.click();
		sleep(2000);
		if(!res.isResult()) {
			testReport.fail(String.format("WebElement [%s] - [%s]", "Back button", "NOT FOUND"));
			return res;
		}
		testReport.pass(String.format("WebElement [%s] - [%s]", "Back button", "Back successfully!!!"));
		return res;
	}
	
	
	/*
	 * @author: AIT1HC
	 */
	public Result VerifySettingPage(ExtentTest testReport) {
		//To verify there are 3 links in pages
		Result res = new Result();
		res.setResult(waitForElementDisplay(linkDeleteLatestSearches, 20));
		if(!res.isResult()) {
			testReport.fail(String.format("WebElement [%s] - [%s]", "Delete Latest Searches", "NOT FOUND"));
			return res;
		}
		
		res.setResult(waitForElementDisplay(linkResetSearches, 20));
		if(!res.isResult()) {
			testReport.fail(String.format("WebElement [%s] - [%s]", "Delete Latest Searches", "NOT FOUND"));
			return res;
		}
		
		res.setResult(waitForElementDisplay(linkResetQuickLinks, 20));
		if(!res.isResult()) {
			testReport.fail(String.format("WebElement [%s] - [%s]", "Delete Latest Searches", "NOT FOUND"));
			return res;
		}

		testReport.pass(String.format("WebElement [%s] - [%s]", "3 links in Setting page", "FOUND!!!"));
		return res;
	}
	
}