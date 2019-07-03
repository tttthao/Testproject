package com.bosch.validation.bh.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;

public class QuickLinksPage extends MasterPage {
	@FindBy(how = How.XPATH, using = "//span[@class='headline']")
	WebElement lblHeadline;	
	
	@FindBy(how = How.CSS, using = ".mws-icon-close")
	WebElement btnClose;	
	
	@FindBy(how = How.XPATH, using = "//span[text()='Add Quick Link']/.. | //span[text()='Quick Link anlegen']/..")
	WebElement btnAddQuickLink;	
	
	@FindBy(how = How.XPATH, using = "//span[text()='Add Group']/.. | //span[text()='Gruppe anlegen']/..")
	WebElement btnAddGroup;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Select Mode']/.. | //span[text()='Auswahl']/..")
	WebElement btnSelectMode;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ng-trigger ng-trigger-moveToPosition favorite')]/div[contains(@class, 'title')]")
	List<WebElement> listFavoriteQLTitles;
	
	@FindBy(how = How.XPATH, using = "//h3[@class='group-headline' and text()='Common Applications']/../../..//div[contains(@class,'ng-trigger ng-trigger-moveToPosition')]/div[contains(@class,'title')]")
	List<WebElement> listCommonAppQLTitles;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'favorite-links-area') and contains(@class,'ng-trigger ng-trigger-moveToPosition')]//div[contains(@class,'ng-trigger ng-trigger-moveToPosition')]/div[contains(@class,'title')]")
	List<WebElement> listNoGroupQLTitles;
	
	@FindBy(how = How.CSS, using = ".title")
	List<WebElement> listAllQLsTitle;
	
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'delete')]/.. | //span[contains(@class,'Löschen')]/..")
	WebElement btnDelete;
	
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'edit')]/.. | //span[contains(@class,'Bearbeitent')]/..")
	WebElement btnEdit;
	
	@FindBy (how = How.CSS, using = ".mws-icon-delete")
	WebElement iconDelete;
	
	@FindBy (how = How.XPATH, using = "//button[text()='Yes'] | //button[text()='Ja']")
	WebElement btnYes;
	
	String formatXpath_lstTitleQLs_inGroup = "//h3[text()='%s']/../../..//div[contains(@class,'title')]";
	
	
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

	public QuickLinksPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public Result IsMyWorkspaceDisplayed() {
		Result res = new Result();
		waitForElementDisplay(lblHeadline, 60);
		if(lblHeadline.getText().equals("My Workspace"))
		{
			verifyWebElementExist(btnAddQuickLink, "Add Quick Link") ;
			verifyWebElementExist(btnAddQuickLink, "Add Group") ;
			verifyWebElementExist(btnAddQuickLink, "Select Mode") ;
			res.setResult(true);
			res.setMessage(String.format("My Workspace is displayed"));
		}
		else
		{			
			res.setResult(false);
			res.setMessage(String.format("My Workspace is not displayed"));			
		}
		return res;
	}
	
	public Result ClickAddGroup() {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnAddGroup, 20));
		sleep(1000);
		btnAddGroup.click();	
		return res;
	}

	public Result clickOnAddQuickLink() {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnAddQuickLink, 20));
		sleep(1000);
		btnAddQuickLink.click();
		return res;
	}
	
	public Result changeToSelectMode() {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnSelectMode, 20));
		sleep(1000);
		btnSelectMode.click();
		return res;
	}
		
	public Result isExistInFavoriteQuickLinks(String quickLinkTitle, ExtentTest testReport) {
		testReport.info("Running: isExistInFavoriteQuickLinks");
		Result res = new Result();
		for(int i = 0; i < listFavoriteQLTitles.size(); i++) {
			if(listFavoriteQLTitles.get(i).getText().equals(quickLinkTitle)){
				res.setResult(true);
				testReport.info("Quick link title ["+ quickLinkTitle +"] is existing in Favorite Quick Links");
				return res;
			}
		}
		testReport.info("Quick link title ["+ quickLinkTitle +"] is NOT existing in Favorite Quick Links");
		return res;
	}

	public Result isExistAndNoGroup(String quickLinkTitle, ExtentTest testReport) {
		testReport.info("Running: isExistAndNoGroup");
		Result res = new Result();
		for(int i = 0; i < listNoGroupQLTitles.size(); i++) {
			if(listNoGroupQLTitles.get(i).getText().equals(quickLinkTitle)){
				res.setResult(true);
				testReport.info("Quick link title ["+ quickLinkTitle +"] is existing and No Group");
				return res;
			}
		}
		testReport.info("Quick link title ["+ quickLinkTitle +"] is NOT existing");
		return res;
	}
	
	public Result isExistInAGroup(String quickLinkTitle, String groupName, WebDriver driver, ExtentTest testReport) {
		testReport.info("Running: isExistInAGroup");
		Result res = new Result();
		String xpathURL = "//div[contains(@class,'group-headline-container')]/h3[@class='group-headline' and text()='"+ groupName +"']/../../../div//div[contains(@class, 'ng-trigger ng-trigger-moveToPosition')]//div[contains(@class, 'title')]";
		List<WebElement> listQuickLinkTitles = driver.findElements(By.xpath(xpathURL));
		for(int i = 0; i < listQuickLinkTitles.size(); i++) {
			if(listQuickLinkTitles.get(i).getText().equals(quickLinkTitle)){
				res.setResult(true);
				testReport.info("Quick link title ["+ quickLinkTitle +"] is existing in group ["+ groupName+"]");
				return res;
			}
		}
		testReport.info("Quick link title ["+ quickLinkTitle +"] is NOT existing in group ["+groupName+"]");
		return res;
	}

	public Result verifyAllQLsInGroup(String groupTitle, List<String> lstExpectedTitle) {
		Result res = new Result();
		boolean isTrue = true;

		String actualXpath_lstTitleQLs_inGroup = String.format(formatXpath_lstTitleQLs_inGroup, groupTitle);
		waitListElementsDisplay(findElements(By.xpath(actualXpath_lstTitleQLs_inGroup)), 10);
		waitElementTextNotEmpty(findElements(By.xpath(actualXpath_lstTitleQLs_inGroup)).get(0), 10);
		sleep(1000);
		List<WebElement> lstTitles = findElements(By.xpath(actualXpath_lstTitleQLs_inGroup));
		for (WebElement title : lstTitles) {
			if (!lstExpectedTitle.contains(title.getText())) {
				isTrue = false;
				break;
			}
		}
		if (isTrue) {
			res.setResult(true);
			res.setMessage("All selected quicklinks are in new group '" + groupTitle + "'");
		} else {
			res.setResult(false);
			res.setMessage("All selected quicklinks are not in new group '" + groupTitle + "'");
		}

		return res;
	}
	
	public Result verifyListDeletedQLs_notDisplay(List<String> lstDeletedQLsTitle) {
		Result res = new Result();
		boolean isTrue = true;
		String wrongQLTitle = "";
		waitListElementsDisplay(listAllQLsTitle, 10);
		waitElementTextNotEmpty(listAllQLsTitle.get(0), 10);
		sleep(1000);
		for (WebElement qlTitle : listAllQLsTitle) {
			if (lstDeletedQLsTitle.contains(qlTitle.getText())) {
				isTrue = false;
				wrongQLTitle = qlTitle.getText();
				break;
			}
		}
		if (isTrue) {
			res.setResult(true);
			res.setMessage("All deleted quicklinks are not displayed");
		} else {
			res.setResult(false);
			res.setMessage("One of deleted quicklinks '" + wrongQLTitle + "' are still displayed");
		}

		return res;
	}
	
	String xpathFormat_iconMenu_viaGroupTitle = "//h3[text()='%s']/../../..//mws-context-menu//span";
	public Result deleteGroup_viaGroupTitle(String groupTitle) {
		Result res = new Result();

		try {
			String actualFormat_iconMenu_viaGroupHeader = String.format(xpathFormat_iconMenu_viaGroupTitle,
					groupTitle);
			WebElement iconMenu_viaGroupTitle = findElement(By.xpath(actualFormat_iconMenu_viaGroupHeader));
			if (iconMenu_viaGroupTitle != null) {
				clickByJS(driver, iconMenu_viaGroupTitle);
				waitForElementDisplay(iconDelete, 5);
				clickByJS(driver, iconDelete);
				waitForElementDisplay(btnYes, 5);
				sleep(500);
				btnYes.click();
				waitForElementDisappear(btnYes, 5);
				waitForElementDisappear(iconMenu_viaGroupTitle, 10);
				res.setResult(true);
				res.setMessage("Delete group '" + groupTitle + "' successful");
			} else {
				res.setResult(false);
				res.setMessage("Group '" + groupTitle + "' is not displayed");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}
	
	public Result closeQuickLinks() {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnClose, 20));
		sleep(1000);
		btnClose.click();	
		return res;
	} 
}