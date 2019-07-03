package com.bosch.validation.bh.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;

public class SelectModePage extends MasterPage {
	@Override
	public <P extends PageObject> P initPageObject(Class<P> pageObjectClass) {
		return null;
	}
	
	@Override
	public boolean isReady() {
		return true;
	}

	public SelectModePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy (how = How.CSS, using = "h1.headline")
	WebElement header_lblSelectedNumber;

	@FindBy (how = How.XPATH, using = "//button[contains(text(),'Create Group')] | //button[contains(text(),'Gruppe erstellen')]")
	WebElement header_btnCreateGroup;
	
	@FindBy (how = How.XPATH, using = "//button[contains(text(),'Delete')] | //button[contains(text(),'Löschen')]")
	WebElement header_btnDelete;
	
	@FindBy (how = How.CSS, using = ".group")
	List<WebElement> lstIconsSelectGroup;
	
	@FindBy (how = How.CSS, using = "svg.unselected.group")
	List<WebElement> lstIconsUnselectGroup;
	
	@FindBy (how = How.CSS, using = "svg.deactivated.group")
	List<WebElement> lstIconsDeactivatedGroup;
	
	@FindBy (how = How.CSS, using = ".TitleInput")
	WebElement tbxGroupTitle;
	
	@FindBy (how = How.XPATH, using = "//button[contains(text(),'Add')] | //button[contains(text(),'Anlegen')]")
	WebElement btnAddNewGroup;
	
	@FindBy (how = How.XPATH, using = "//button[text()='Yes'] | //button[text()='Ja']")
	WebElement popupDeleteConfirmation_btnYes;
	
	@FindBy (how = How.XPATH, using = "//button[text()='No'] | //button[text()='Nein']")
	WebElement popupDeleteConfirmation_btnNo;
	
	@FindBy (how = How.CSS, using = ".body>div")
	WebElement popupDeleteConfirmation_contentMessage;
	
	
	String formatXpath_titleQL_inGroup = "//div[@class='edit-container']//h3[text()='%s']/../../..//div[text()='%s']";
	String formatXpath_lstTitleQLs_inGroup = "//div[@class='edit-container']//h3[text()='%s']/../../..//div[text()='%s']";

	public Result selectQL_viaGroupAndTitle(String groupTitle, String qlTitle) {
		Result res = new Result();
		try {
			waitListElementsDisplay(lstIconsSelectGroup, 30);
			String actualXpath_titleQL = String.format(formatXpath_titleQL_inGroup, groupTitle, qlTitle);
			findElement(By.xpath(actualXpath_titleQL)).findElement(By.xpath("..")).click();
			waitListElementsDisplay(lstIconsDeactivatedGroup, 30);
			WebElement itemAfterSelected = findElement(By.xpath(actualXpath_titleQL));
			if(itemAfterSelected.findElement(By.xpath("../..")).getAttribute("class").contains("mws-draggable")) {
				res.setResult(true);
				res.setMessage("Select QuickLink '" + qlTitle + "' in group '" + groupTitle + "' successful");
			} else {
				res.setResult(false);
				res.setMessage("Select QuickLink '" + qlTitle + "' in group '" + groupTitle + "' unsuccessful");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}
	
	public Result isButtonEnabled(String btnName, boolean expectedStatus) {
		Result res = new Result();
		WebElement btnElement = null;
		switch (btnName.toLowerCase()) {
		case "create group":
			btnElement = header_btnCreateGroup;
			break;
		case "delete":
			btnElement = header_btnDelete;
			break;
		}

		try {
			if (expectedStatus) {
				if (waitForTrue(btnElement.isEnabled(), 5)) {
					res.setResult(true);
					res.setMessage(
							String.format("Method [%s] - [%s]", "Verify button '" + btnName + "' is enabled", "TRUE"));
				} else {
					res.setResult(false);
					res.setMessage(
							String.format("Method [%s] - [%s]", "Verify button '" + btnName + "' is enabled", "FALSE"));
				}
			} else {
				if (waitForTrue(!btnElement.isEnabled(), 5)) {
					res.setResult(true);
					res.setMessage(
							String.format("Method [%s] - [%s]", "Verify button '" + btnName + "' is disabled", "TRUE"));
				} else {
					res.setResult(false);
					res.setMessage(String.format("Method [%s] - [%s]", "Verify button '" + btnName + "' is disabled",
							"FALSE"));
				}
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}
	
	public Result verifyAllIconsSelectGroupDeactived() {
		Result res = new Result();
		try {
			if(waitListElementsDisappear(lstIconsUnselectGroup, 30)) {
				waitListElementsDisplay(lstIconsDeactivatedGroup, 30);
				int numberDeactivatedGroups = lstIconsDeactivatedGroup.size();
				if (numberDeactivatedGroups > 0) {
					res.setResult(true);
					res.setMessage("All icons select group are deactived");
				}
			} else {
				res.setResult(false);
				res.setMessage("All icons select group are still actived");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	} 
	
	public Result verifyNumberSelectedOnHeader(int expectedNumber) {
		Result res = new Result();
		try {
			String strHeader_lblSelectedNumber = header_lblSelectedNumber.getText();
			String actualNumber_header_lblSelectedNumber = strHeader_lblSelectedNumber
					.substring(strHeader_lblSelectedNumber.lastIndexOf('(') + 1).replace(")", "");

			if (Integer.parseInt(actualNumber_header_lblSelectedNumber) == expectedNumber) {
				res.setResult(true);
				res.setMessage("Verify number selected items equal '" + expectedNumber + "' -> successful");
			} else {
				res.setResult(false);
				res.setMessage("Verify number selected items -> unsuccessful: Actual["
						+ actualNumber_header_lblSelectedNumber + "] - Expected[" + expectedNumber + "]");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}
	
	public Result createGroupAfterSelectedQLs(String newGroupTitle) {
		Result res = new Result();

		try {
			waitAndClick(header_btnCreateGroup, 10);
			tbxGroupTitle.sendKeys(newGroupTitle);
			waitAndClick(btnAddNewGroup, 10);
			res.setResult(true);
			res.setMessage("Create group after selected quicklinks -> successful");
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}
		return res;
	}
	
	public Result deleteQLsAfterSelectedQLs() {
		Result res = new Result();
		String expectedContent_en = "All selected Quicklinks will be deleted. Are you sure?";
		String expectedContent_de = "Alle ausgewählten Quick Links werden gelöscht. Sind Sie sicher?";

		try {
			waitAndClick(header_btnDelete, 10);
			if (!waitForElementDisplay(popupDeleteConfirmation_btnYes, 5)
					|| !waitForElementDisplay(popupDeleteConfirmation_btnNo, 5)) {
				res.setResult(false);
				res.setMessage("Buttons 'Yes' and 'No' of popup delete confirmation are not displayed");
				return res;
			}
			if (waitElementTextNotEmpty(popupDeleteConfirmation_contentMessage, 10)) {
				String actualContent = popupDeleteConfirmation_contentMessage.getText();
				if (!actualContent.contains(expectedContent_en) && !actualContent.contains(expectedContent_de)) {
					res.setResult(false);
					res.setMessage("Content of popup delete confirmation is wrong");
					return res;
				}
			}

			waitAndClick(popupDeleteConfirmation_btnYes, 10);
			waitForElementDisappear(popupDeleteConfirmation_btnYes, 10);
			res.setResult(true);
			res.setMessage("Create group after selected quicklinks -> successful");
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}
		return res;
	}
}
