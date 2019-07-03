package com.bosch.validation.bh.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;

public class EditQuickLinkPage extends MasterPage {
	@Override
	public <P extends PageObject> P initPageObject(Class<P> pageObjectClass) {
		return null;
	}
	
	@Override
	public boolean isReady() {
		return true;
	}

	public EditQuickLinkPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy (how = How.CSS, using = ".UrlInput")
	WebElement tbxUrl;	
	
	@FindBy (how = How.XPATH, using = "//input[contains(@class,'UrlInput')]/..//span")
	WebElement tbxUrlValidation;
	
	@FindBy (how = How.CSS, using = ".TitleInput")
	WebElement tbxTitleQL;
	
	@FindBy (how = How.XPATH, using = "//input[contains(@class,'TitleInput')]/..//span")
	WebElement tbxTitleQLValidation;
	
	@FindBy(how = How.CSS, using = ".colors>div>div")
	List<WebElement> listColorQL;
		
	@FindBy (how = How.XPATH, using = "//div[text()='Existing Group']/.. | //div[text()='Bestehende Gruppe']/..")
	WebElement btnExistgroup;
	
	@FindBy (how = How.XPATH, using = "//div[text()='New Group']/.. | //div[text()='Neue Gruppe']/..")
	WebElement btnNewGroup;
	
	@FindBy (how= How.CSS, using = ".newGroupContainer input")
	WebElement tbxTitleNewGroup;
	
	@FindBy(how = How.XPATH, using = "//div[@class='newGroupContainer marginTop']//div[contains(@class,'inner-circle bg')]")
	List<WebElement> listColorNewGroup;		
	
	@FindBy (how = How.CSS, using = ".DropdownItemList")
	WebElement dropdownSelectGroup;

	@FindBy (how = How.CSS, using = ".DropdownItem")
	List <WebElement> listExistingGroup;
	
	@FindBy (how = How.XPATH, using = "//button[@class='ng-tns-c11-3']")
	WebElement btnCancel;
	
	@FindBy (how = How.XPATH, using = "//div[not(contains(@class,'invisible'))]//button[contains(text(),'Add')] | //div[not(contains(@class,'invisible'))]//button[contains(text(),'Anlegen')]")
	WebElement btnAddQuickLink;
	
	@FindBy (how = How.XPATH, using = "//div[not(contains(@class,'invisible'))]//button[contains(text(),'Cancel')] | //div[not(contains(@class,'invisible'))]//button[contains(text(),'Abbrechen')]")
	WebElement btnCancelQuickLink;
	
	@FindBy (how = How.XPATH, using = "//div[not(contains(@class,'invisible'))]//button[contains(text(),'Save')] | //div[not(contains(@class,'invisible'))]//button[contains(text(),'Speichern')]")
	WebElement btnSaveQuickLink;

	@FindBy (how = How.CSS, using = ".quick-link-root>.favorite-links-area>mws-quick-link .title")
	List<WebElement> lstTitleQuickLinks_ungroup;
	
	@FindBy (how = How.CSS, using = ".quick-link-root>.favorite-links-area>mws-quick-link mws-context-menu")
	List<WebElement> lstIconMenuQuickLinks_ungroup;
	
	@FindBy (how = How.CSS, using = ".newGroupContainer span")
	WebElement lblErrorMessage_tbxNewGroupTitle;
	
	@FindBy (how = How.CSS, using = ".mws-icon-edit")
	WebElement iconEdit;
	
	@FindBy (how = How.CSS, using = ".mws-icon-delete")
	WebElement iconDelete;
	
	@FindBy (how = How.XPATH, using = "//button[text()='Yes'] | //button[text()='Ja']")
	WebElement btnYes;
	
	public Result verifyQuickLinkDefault() {

		Result res = new Result();
		if (tbxUrl.getText().equals(""))
			res.setResult(true);
		else
			res.setResult(false);

		verifyWebElementExist(tbxUrl, " ");
		verifyWebElementExist(tbxTitleQL, " ");
		verifyWebElementExist(btnExistgroup, " Existgroup");
		verifyWebElementExist(btnNewGroup, "NewGroup");
		verifyWebElementExist(dropdownSelectGroup, "SelectGroup");
		return res;
	}

	public Result AddQuickLink (String strURL, String strTitle, String strColorQL, String GroupType, String strGroupToSelect, String strNewGroupTitle, String strNewGroupColor, String strCancelOrAdd)
		{
			Result res = new Result ();
			Boolean flag;
			try {
				//EnterURL
				res.setResult(waitForElementDisplay(tbxUrl, 10));
				if(!res.isResult()) {
					res.setResult(false);
					res.setMessage(String.format("WebElement [%s] - [%s]", "URL", "NOT FOUND"));
					return res;
				}
				tbxUrl.sendKeys(strURL);
				
	
				//Enter Title
				tbxTitleQL.sendKeys(strTitle);
				
				//Enter color [green,dark-green, cyan,blue,dark-blue,purple, magenta, red]
				if(!strColorQL.isEmpty() && !strColorQL.equals("")) {
					for (WebElement lsCol : listColorQL) {
						String t = lsCol.getAttribute("class");
						if(t.equalsIgnoreCase("inner-circle bg-" + strColorQL)){
							lsCol.click();
							break;
						}
					}
				}
	
				
				//Existing Group: Select Group
				if(GroupType.equalsIgnoreCase("Existing Group"))
				{		
					Thread.sleep(1500);
					dropdownSelectGroup.click();
					waitForTrue(listExistingGroup.size() > 2, 5);
					Thread.sleep(1500);
					System.out.println("sleep 5 seconds: " + listExistingGroup.size());
					flag = false;
					for(WebElement lsGroupitem : listExistingGroup)
					{
						String t = lsGroupitem.getText();
						System.out.println(t);
						if(t.equalsIgnoreCase(strGroupToSelect)) {
							flag = true;
							lsGroupitem.click();
							Thread.sleep(1000);
							break;
						}
					}
					if(!flag) {
						res.setResult(false);
						res.setMessage("ERR: " + "This group ["+ strGroupToSelect +"] does NOT exist in list. Please check!!!");
						return res;
					}
				}else if (GroupType.equalsIgnoreCase("New Group")) {
					btnNewGroup.click();
					tbxTitleNewGroup.sendKeys(strNewGroupTitle);
					if(!strNewGroupColor.isEmpty() &&  !strNewGroupColor.equals("")) {
						for (WebElement lsColNewGroup : listColorNewGroup)
						{
							String t = lsColNewGroup.getAttribute("class");
							if(t.equalsIgnoreCase("inner-circle bg-" + strNewGroupColor)){
								lsColNewGroup.click();
								break;
							}
						}
					}
				}
				
				//Cancel or Add
				Thread.sleep(1000);
				if(strCancelOrAdd.equalsIgnoreCase("Add")) {
					waitForTrue(btnAddQuickLink.isEnabled(), 5);
					if(btnAddQuickLink.isEnabled())
						btnAddQuickLink.click();
					else {
						res.setResult(false);
						res.setMessage("ERR: " + "ADD button is disbled, so cannot click on this");
						return res;
					}
				}else {
					btnCancelQuickLink.click();
				}
				Thread.sleep(2000);
				
				res.setResult(true);
				res.setMessage(String.format("Method [%s] - [%s]", "Add Quick link", "Successfully"));
			}catch(Exception e) {
				res.setResult(false);
				res.setMessage("ERR [Try-Catch]: " + e.getMessage());
				return res;
			}
			
			return res;		
	}
	
	public Result InputURL(String strURL) {
		Result res = new Result();
		try {
			res.setResult(waitForElementDisplay(tbxUrl, 60));
			if (!res.isResult()) {
				res.setResult(false);
				res.setMessage(String.format("WebElement [%s] - [%s]", "URL", "NOT FOUND"));
				return res;
			}
			tbxUrl.clear();
			tbxUrl.sendKeys(strURL);
			res.setResult(true);
			res.setMessage(String.format("Method [%s] - [%s]", "Input URL", "Successfully"));
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
			return res;
		}

		return res;
	}

	public Result sendkeyURL(Keys keyboard) {
		Result res = new Result();
		try {
			res.setResult(waitForElementDisplay(tbxUrl, 60));
			if (!res.isResult()) {
				res.setResult(false);
				res.setMessage(String.format("WebElement [%s] - [%s]", "URL", "NOT FOUND"));
				return res;
			}
			tbxUrl.sendKeys(keyboard);
			res.setResult(true);
			res.setMessage(String.format("Method [%s] - [%s]", "Input URL", "Successfully"));
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
			return res;
		}

		return res;
	}
	
	public Result verifyColorOfMessage(String strElementCheck, String expectedColorCode) {
		Result res = new Result();
		WebElement checkingElement = null;
		if (strElementCheck.equals("url"))
			checkingElement = tbxUrlValidation;
		else if (strElementCheck.equals("title"))
			checkingElement = tbxTitleQLValidation;
					
		try {
			String actualColor = checkingElement.getCssValue("color");
			int r = Integer.parseInt(expectedColorCode.replace("#", "").substring(0, 2), 16);
			int g = Integer.parseInt(expectedColorCode.replace("#", "").substring(2, 4), 16);
			int b = Integer.parseInt(expectedColorCode.replace("#", "").substring(4, 6), 16);
			String expectedColor = "rgba(" + r + ", " + g + ", " + b;

			if (actualColor.startsWith(expectedColor)) {
				res.setResult(true);
				res.setMessage(String.format("Verify color of message: it is same with expected color"));
			} else {
				res.setResult(false);
				res.setMessage("Verify color of message: Expected[should start with '" + expectedColor
						+ "'] - Actual[it is '" + actualColor + "']");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}
	
	public Result verifyContentOfMessage(String strElementCheck, List<String> expectedContentMessage) {
		Result res = new Result();
		WebElement checkingElement = null;
		if (strElementCheck.equals("url"))
			checkingElement = tbxUrlValidation;
		else if (strElementCheck.equals("title"))
			checkingElement = tbxTitleQLValidation;

		try {
			if (!waitForElementDisplay(checkingElement, 10)) {
				res.setResult(false);
				res.setMessage(String.format("WebElement [%s] - [%s]", "Verify message when input invalid url",
						"MESSAGE DOESN't DISPLAY"));
				return res;
			} else {
				for(int i = 0; i < expectedContentMessage.size(); i++) {
					if (checkingElement.getText().equals(expectedContentMessage.get(i))) {
						res.setResult(true);
						res.setMessage(String.format("Method [%s] - [%s]", "Verify URL Input Validation", "Successfully"));
						break;
					} else {
						if (i == expectedContentMessage.size() - 1) {
							res.setResult(false);
							res.setMessage("Verify message when input invalid url: NOT MATCHING. Actual ["
									+ checkingElement.getText() + "] - expected [" + expectedContentMessage.get(i) + "]");
						}
					}
				}
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
		case "add":
			btnElement = btnAddQuickLink;
			break;
		case "cancel":
			btnElement = btnCancelQuickLink;
			break;
		case "save":
			btnElement = btnSaveQuickLink;
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
	
	public Result isTitleMessageNotDisplayed() {
		Result res = new Result();

		try {
			if (waitForElementDisappear(tbxTitleQLValidation, 3)) {
				res.setResult(true);
				res.setMessage(String.format("Method [%s] - [%s]", "Verify message of title is not displayed", "TRUE"));
			} else {
				res.setResult(false);
				res.setMessage(
						String.format("Method [%s] - [%s]", "Verify message of title is not displayed", "FALSE"));
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}
	
	public Result isTitleNewGroupMessageNotDisplayed() {
		Result res = new Result();
		sleep(1000);
		try {
			if (waitForElementDisappear(lblErrorMessage_tbxNewGroupTitle, 3)) {
				res.setResult(true);
				res.setMessage(String.format("Method [%s] - [%s]", "Verify message of title is not displayed", "TRUE"));
			} else {
				res.setResult(false);
				res.setMessage(
						String.format("Method [%s] - [%s]", "Verify message of title is not displayed", "FALSE"));
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}

	public Result InputTitle (String strTitle)
		{
			Result res = new Result ();
			try {
				//EnterURL
				res.setResult(waitForElementDisplay(tbxTitleQL, 60));
				if(!res.isResult()) {
					res.setResult(false);
					res.setMessage(String.format("WebElement [%s] - [%s]", "TITLE", "NOT FOUND"));
					return res;
				}
				tbxTitleQL.clear();
				if(strTitle.equals("")) {
					tbxTitleQL.sendKeys(" ");
					tbxTitleQL.sendKeys(Keys.BACK_SPACE);
				}else
					tbxTitleQL.sendKeys(strTitle);
				res.setMessage(String.format("Method [%s] - [%s]", "Input TITLE", "Successfully"));
			}catch(Exception e) {
				res.setResult(false);
				res.setMessage("ERR [Try-Catch]: " + e.getMessage());
				return res;
			}
			
			return res;	
	}

	public Result clickAddNewQuickLink_ungroup() {
		Result res = new Result();
		
		try {
			String currentTitle = tbxTitleQL.getAttribute("value");
			waitForTrue(btnAddQuickLink.isEnabled(), 10);
			btnAddQuickLink.click();
			waitListElementsDisplay(lstTitleQuickLinks_ungroup, 10);			
			int numberAddedQuickLinks_ungroup_afterAdd = lstTitleQuickLinks_ungroup.size();
			String titleLastQuickLink_ungroup = "";
			if(waitElementTextNotEmpty(lstTitleQuickLinks_ungroup.get(numberAddedQuickLinks_ungroup_afterAdd - 1), 10)) {
				titleLastQuickLink_ungroup = lstTitleQuickLinks_ungroup.get(numberAddedQuickLinks_ungroup_afterAdd - 1).getText();
				if (titleLastQuickLink_ungroup.equals(currentTitle)) {
					res.setResult(true);
					res.setMessage("Verify new quick link is displayed as the last element of the grid or list view -> TRUE");
				} else {
					res.setResult(false);
					res.setMessage("Verify new quick link is displayed as the last element of the grid or list view -> FALSE: Actual["+titleLastQuickLink_ungroup+"] - Expected["+currentTitle+"]");
				}
			} else {
				res.setResult(false);
				res.setMessage("New quick link is not displayed");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}
		sleep(1000);
		
		return res;
	}
	
	String xpathFormat_lstTitleQuickLinks_viaGroupHeader = "//h3[text()='%s']/../../..//div[contains(@class,'title')]";
	public Result clickAddNewQuickLink_selectExistedGroup(String groupHeader) {
		Result res = new Result();
		String actualXpath_lstTitleQuickLinks_viaGroupHeader = String.format(xpathFormat_lstTitleQuickLinks_viaGroupHeader, groupHeader);
		try {
			selectExistingQLGroup(groupHeader);
			sleep(500);
			String currentTitle = tbxTitleQL.getAttribute("value");
			waitForTrue(btnAddQuickLink.isEnabled(), 10);
			btnAddQuickLink.click();		
			waitListElementsDisplay(findElements(By.xpath(actualXpath_lstTitleQuickLinks_viaGroupHeader)), 10);	
			List<WebElement> lstTitleQuickLinks_viaGroupHeader = findElements(By.xpath(actualXpath_lstTitleQuickLinks_viaGroupHeader));
			int numberAddedQuickLinks_viaGroupHeader_afterAdd = lstTitleQuickLinks_viaGroupHeader.size();
			String titleLastQuickLink_viaGroupHeader = "";
			if(waitElementTextNotEmpty(lstTitleQuickLinks_viaGroupHeader.get(numberAddedQuickLinks_viaGroupHeader_afterAdd - 1), 10)) {
				titleLastQuickLink_viaGroupHeader = lstTitleQuickLinks_viaGroupHeader.get(numberAddedQuickLinks_viaGroupHeader_afterAdd - 1).getText();
			if (titleLastQuickLink_viaGroupHeader.equals(currentTitle)) {
				res.setResult(true);
				res.setMessage(
						"Verify new quick link is displayed as the last element of the grid or list view of the selected group '"
								+ groupHeader + "' -> TRUE");
			} else {
				res.setResult(false);
				res.setMessage(
						"Verify new quick link is displayed as the last element of the grid or list view of the selected group '"
								+ groupHeader + "' -> FALSE");
			}
			} else {
				res.setResult(false);
				res.setMessage("New quick link is not displayed");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}
		sleep(1000);
		
		return res;
	}
	
	public Result clickAddNewQuickLink_createNewGroup() {
		Result res = new Result();
		
		try {
			String currentTitle = tbxTitleQL.getAttribute("value");
			String newGroupTitle = tbxTitleNewGroup.getAttribute("value");
			waitForTrue(btnAddQuickLink.isEnabled(), 10);
			btnAddQuickLink.click();		
			String actualXpath_lstTitleQuickLinks_viaGroupHeader = String.format(xpathFormat_lstTitleQuickLinks_viaGroupHeader, newGroupTitle);
			waitListElementsDisplay(findElements(By.xpath(actualXpath_lstTitleQuickLinks_viaGroupHeader)), 10);	
			List<WebElement> lstTitleQuickLinks_viaGroupHeader = findElements(By.xpath(actualXpath_lstTitleQuickLinks_viaGroupHeader));
			String titleLastQuickLink_viaGroupHeader = "";
			if(waitElementTextNotEmpty(lstTitleQuickLinks_viaGroupHeader.get(0), 10)) {
				titleLastQuickLink_viaGroupHeader = lstTitleQuickLinks_viaGroupHeader.get(0).getText();
			if (titleLastQuickLink_viaGroupHeader.equals(currentTitle)) {
				res.setResult(true);
				res.setMessage("Verify new quick link is displayed as the last element of the grid or list view of new group '" + newGroupTitle + "' -> TRUE");
			} else {
				res.setResult(false);
				res.setMessage("Verify new quick link is displayed as the last element of the grid or list view of new group '" + newGroupTitle + "' -> FALSE");
			}
			} else {
				res.setResult(false);
				res.setMessage("New quick link is not displayed");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}
		sleep(1000);
		
		return res;
	}
	
	public Result clickSaveEditQuickLink_ungroup(String ...colorSelected) {
		Result res = new Result();
		
		try {
			String currentTitle = tbxTitleQL.getAttribute("value");
			waitForTrue(btnSaveQuickLink.isEnabled(), 10);
			btnSaveQuickLink.click();			
			waitListElementsDisplay(lstTitleQuickLinks_ungroup, 10);			
			int numberAddedQuickLinks_ungroup_afterAdd = lstTitleQuickLinks_ungroup.size();
			String titleLastQuickLink_ungroup = "";
			if(waitElementTextNotEmpty(lstTitleQuickLinks_ungroup.get(numberAddedQuickLinks_ungroup_afterAdd - 1), 10)) {
				WebElement lastQuickLink_ungroup = lstTitleQuickLinks_ungroup.get(numberAddedQuickLinks_ungroup_afterAdd - 1);
				titleLastQuickLink_ungroup = lastQuickLink_ungroup.getText();		
			String parentClass = lastQuickLink_ungroup.findElement(By.xpath("..")).getAttribute("class");
			if (titleLastQuickLink_ungroup.equals(currentTitle)) {
				res.setResult(true);
				res.setMessage("Verify edited quick link is displayed as the last element of the grid or list view -> TRUE");
			} else {
				res.setResult(false);
				res.setMessage("Verify edited quick link is displayed as the last element of the grid or list view -> FALSE");
			}
			
			if (colorSelected.length > 0) {
				if (parentClass.contains("border-" + colorSelected[0])) {
					res.setResult(true);
					res.setMessage("Verify color of edited quick link is same as color selected -> TRUE");
				} else {
					res.setResult(false);
					res.setMessage("Verify color of edited quick link is same as color selected -> FALSE");
				}
			}
			} else {
				res.setResult(false);
				res.setMessage("New quick link is not displayed");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}
		sleep(1000);
		
		return res;
	}
	
	public Result clickSaveEditQuickLink_selectExistedGroup(String groupHeader) {
		Result res = new Result();
		String actualXpath_lstTitleQuickLinks_viaGroupHeader = String.format(xpathFormat_lstTitleQuickLinks_viaGroupHeader, groupHeader);
		try {
			selectExistingQLGroup(groupHeader);
			sleep(500);
			String currentTitle = tbxTitleQL.getAttribute("value");
			waitForTrue(btnSaveQuickLink.isEnabled(), 10);
			btnSaveQuickLink.click();	
			waitListElementsDisplay(findElements(By.xpath(actualXpath_lstTitleQuickLinks_viaGroupHeader)), 10);	
			List<WebElement> lstTitleQuickLinks_viaGroupHeader = findElements(By.xpath(actualXpath_lstTitleQuickLinks_viaGroupHeader));
			int numberAddedQuickLinks_viaGroupHeader_afterAdd = lstTitleQuickLinks_viaGroupHeader.size();
			String titleLastQuickLink_viaGroupHeader = "";
			if(waitElementTextNotEmpty(lstTitleQuickLinks_viaGroupHeader.get(numberAddedQuickLinks_viaGroupHeader_afterAdd - 1), 10)) {
				titleLastQuickLink_viaGroupHeader = lstTitleQuickLinks_viaGroupHeader.get(numberAddedQuickLinks_viaGroupHeader_afterAdd - 1).getText();
			if (titleLastQuickLink_viaGroupHeader.equals(currentTitle)) {
				res.setResult(true);
				res.setMessage(
						"Verify new quick link is displayed as the last element of the grid or list view of the selected group '"
								+ groupHeader + "' -> TRUE");
			} else {
				res.setResult(false);
				res.setMessage(
						"Verify new quick link is displayed as the last element of the grid or list view of the selected group '"
								+ groupHeader + "' -> FALSE");
			}}
			else {
				res.setResult(false);
				res.setMessage("New quick link is not displayed");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}
		sleep(1000);
		
		return res;
	}
	
	public Result clickSaveEditQuickLink_createNewGroup() {
		Result res = new Result();
		
		try {
			String currentTitle = tbxTitleQL.getAttribute("value");
			String newGroupTitle = tbxTitleNewGroup.getAttribute("value");
			waitForTrue(btnSaveQuickLink.isEnabled(), 10);
			btnSaveQuickLink.click();			
			String actualXpath_lstTitleQuickLinks_viaGroupHeader = String.format(xpathFormat_lstTitleQuickLinks_viaGroupHeader, newGroupTitle);
			waitListElementsDisplay(findElements(By.xpath(actualXpath_lstTitleQuickLinks_viaGroupHeader)), 10);	
			List<WebElement> lstTitleQuickLinks_viaGroupHeader = findElements(By.xpath(actualXpath_lstTitleQuickLinks_viaGroupHeader));
			String titleLastQuickLink_viaGroupHeader = "";
			if(waitElementTextNotEmpty(lstTitleQuickLinks_viaGroupHeader.get(0), 10)) {
				titleLastQuickLink_viaGroupHeader = lstTitleQuickLinks_viaGroupHeader.get(0).getText();
			if (titleLastQuickLink_viaGroupHeader.equals(currentTitle)) {
				res.setResult(true);
				res.setMessage("Verify new quick link is displayed as the last element of the grid or list view of new group '" + newGroupTitle + "' -> TRUE");
			} else {
				res.setResult(false);
				res.setMessage("Verify new quick link is displayed as the last element of the grid or list view of new group '" + newGroupTitle + "' -> FALSE");
			}
			} else {
				res.setResult(false);
				res.setMessage("New quick link is not displayed");
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}
		sleep(1000);
		
		return res;
	}
	
	public Result InputTitleNewGroup(String strTitleNewGroup) {
		Result res = new Result();
		try {
			if (!waitForElementDisplay(tbxTitleNewGroup, 10)) {
				res.setResult(false);
				res.setMessage(String.format("WebElement [%s] - [%s]", "TITLE OF NEW GROUP", "NOT FOUND"));
				return res;
			} else {
				tbxTitleNewGroup.clear();
				tbxTitleNewGroup.sendKeys(strTitleNewGroup);
				res.setResult(true);
				res.setMessage(String.format("Method [%s] - [%s]", "Input TITLE OF NEW GROUP", "Successfully"));
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}
	
	public Result selectExistingQLGroup(String groupHeader) {
		Result res = new Result();

		try {
			dropdownSelectGroup.click();
			waitListElementsDisplay(listExistingGroup, 5);
			sleep(1000);
			for (int i = 0; i < listExistingGroup.size(); i++) {
				if (listExistingGroup.get(i).getText().toLowerCase().equals(groupHeader.toLowerCase())) {
					listExistingGroup.get(i).click();
					res.setResult(true);
					res.setMessage("Select existed group '" + groupHeader + "' successful");
					break;
				}
				
				if (i == listExistingGroup.size() - 1) {
					res.setResult(false);
					res.setMessage("Select existed group '" + groupHeader + "' unsuccessful");
				}
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}
	
	public Result switchToNewGroup() {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnNewGroup, 20));
		btnNewGroup.click();
		return res;
	}

	public Result chooseIconMenu_firstQuickLink_ungroup() {
		Result res = new Result();

		if (waitListElementsDisplay(lstIconMenuQuickLinks_ungroup, 30)) {
			lstIconMenuQuickLinks_ungroup.get(0).click();
			waitAndClick(iconEdit, 5);
			res = verifyQuickLinkDefault();
		} else {
			res.setResult(false);
			res.setMessage("List QuickLinks of Ungroup are not displayed");
		}

		return res;
	}
	
	String xpathFormat_lstIconMenuQuickLinks_viaGroupHeader = "//h3[text()='%s']/../../..//mws-quick-link//span[contains(@class,'mws-icon-options')]";
	public Result chooseIconMenu_firstQuickLink_viaGroupTitle(String groupTitle) {
		Result res = new Result();
		String actualFormat_lstIconMenuQuickLinks_viaGroupHeader = String.format(xpathFormat_lstIconMenuQuickLinks_viaGroupHeader, groupTitle);
		List<WebElement> lstIconMenuQuickLinks_viaGroupHeader = findElements(By.xpath(actualFormat_lstIconMenuQuickLinks_viaGroupHeader));
		if (waitListElementsDisplay(lstIconMenuQuickLinks_viaGroupHeader, 30)) {
			lstIconMenuQuickLinks_viaGroupHeader.get(0).click();
			waitAndClick(iconEdit, 5);
			res = verifyQuickLinkDefault();
		} else {
			res.setResult(false);
			res.setMessage("List QuickLinks of group '" + groupTitle + "' are not displayed");
		}

		return res;
	}

	/*
	 * @author: AIT1HC
	 */
	public Result clickOnAddCancelSave (String strAddOrCancelOrSave)
		{
			Result res = new Result ();
			try {
				if(strAddOrCancelOrSave.equalsIgnoreCase("Add")) {
					btnAddQuickLink.click();
					res.setResult(true);
					
				}else if(strAddOrCancelOrSave.equalsIgnoreCase("Save")) {
					btnSaveQuickLink.click();
					res.setResult(true);
				}
				else {
					btnCancelQuickLink.click();
					res.setResult(true);
				}
				
			}catch(Exception e) {
				res.setResult(false);
				res.setMessage("ERR [Try-Catch]: " + e.getMessage());
				return res;
			}
			
			res.setMessage(String.format("Method [%s] - [%s]", "clickOnAddCancelSave: " + strAddOrCancelOrSave, "Successfully"));
			return res;
	}
	
	public Result selectQLColor(String strColorQL) {
		Result res = new Result();

		try {
			if (!strColorQL.isEmpty() && !strColorQL.equals("")) {
				for (int i = 0; i < listColorQL.size(); i++) {
					if (listColorQL.get(i).getAttribute("class").equalsIgnoreCase("inner-circle bg-" + strColorQL)) {
						listColorQL.get(i).click();
						res.setResult(true);
						res.setMessage(String.format("Method [%s] - [%s]", "Select Colour", "Successfully"));
						break;
					}
					if (i == listColorQL.size() - 1) {
						res.setResult(false);
						res.setMessage(String.format("Not found color '" + strColorQL + "'"));
					}
				}
			} else {
				res.setResult(false);
				res.setMessage(String.format("Must input color for choosing"));
			}
		} catch (Exception e) {
			res.setResult(false);
			res.setMessage("ERR [Try-Catch]: " + e.getMessage());
		}

		return res;
	}
	
	/*
	 * @author: AIT1HC
	 */
	public Result selectQLGroup (String GroupType, String strGroupToSelect, String strNewGroupTitle, String strNewGroupColor)
		{
			Result res = new Result ();
			Boolean flag;
			try {
				
				//Existing Group: Select Group
				if(GroupType.equalsIgnoreCase("Existing Group"))
				{		
					Thread.sleep(1500);
					dropdownSelectGroup.click();
					waitForTrue(listExistingGroup.size() > 2, 5);
					Thread.sleep(1500);
					System.out.println("sleep 5 seconds: " + listExistingGroup.size());
					flag = false;
					for(WebElement lsGroupitem : listExistingGroup)
					{
						String t = lsGroupitem.getText();
						System.out.println(t);
						if(t.equalsIgnoreCase(strGroupToSelect)) {
							flag = true;
							lsGroupitem.click();
							Thread.sleep(1000);
							break;
						}
					}
					if(!flag) {
						res.setResult(false);
						res.setMessage("ERR: " + "This group ["+ strGroupToSelect +"] does NOT exist in list. Please check!!!");
						return res;
					}
				}else if (GroupType.equalsIgnoreCase("New Group")) {
					btnNewGroup.click();
					tbxTitleNewGroup.sendKeys(strNewGroupTitle);
					if(!strNewGroupColor.isEmpty() &&  !strNewGroupColor.equals("")) {
						for (WebElement lsColNewGroup : listColorNewGroup)
						{
							String t = lsColNewGroup.getAttribute("class");
							if(t.equalsIgnoreCase("inner-circle bg-" + strNewGroupColor)){
								lsColNewGroup.click();
								break;
							}
						}
					}
				}
				res.setResult(true);
				res.setMessage(String.format("Method [%s] - [%s]", "Select Group", "Successfully"));
			}catch(Exception e) {
				res.setResult(false);
				res.setMessage("ERR [Try-Catch]: " + e.getMessage());
				return res;
			}
			
			return res;	
	}
	
	

}
