package com.bosch.validation.bh.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bosch.validation.fw.setup.PageObject;
import com.aventstack.extentreports.ExtentTest;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObjectFactory;

public class AddGroupPage extends MasterPage {

	private static final Logger log = LogManager.getLogger(QuickLinksPage.class);

	@FindBy(how = How.XPATH, using = "//h2/span[text()='Add Group'] | //h2/span[text()='Gruppe anlegen']")
	WebElement lblAddGroup;

	@FindBy(how = How.XPATH, using = "//h2/span[contains(text(),'Gr')]")
	WebElement labelAddGroup;

	@FindBy(how = How.XPATH, using = "//div[text()='New Group'] | //div[text()='Neue Gruppe']")
	WebElement btnNewGroup;

	@FindBy(how = How.XPATH, using = "//span[text()='Predefined Group'] | //span[text()='Empfohlene Gruppen']")
	WebElement btnPredefinedGroup;

	@FindBy(how = How.XPATH, using = "//li/div/span[contains(text(),'Gr')]/../..//input")
	WebElement buttonPredefinedGroup;

	@FindBy(how = How.XPATH, using = "//li/div/span[contains(text(),'U')]")
	WebElement lablelUpdate;

	@FindBy(how = How.XPATH, using = "//input[@name='title']")
	WebElement txtTitle;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'inner-circle')]")
	List<WebElement> listColor;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Cancel')] | //button[contains(text(),'Anlegen')]")
	WebElement btnCancel;

	@FindBy(how = How.XPATH, using = "//div/button[@class='ng-tns-c11-3']")
	WebElement buttonCancel;

	@FindBy(how = How.XPATH, using = "//div[not(contains(@class,'invisible'))]//button[contains(text(),'Add')] | //div[not(contains(@class,'invisible'))]//button[contains(text(),'Anlegen')]")
	WebElement btnAdd;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'restricted')]/div/button[@class='default ng-tns-c11-3']")
	WebElement buttonAdd;
	
	@FindBy(how = How.XPATH, using = "//div/button[contains(text(),'S')]")
	WebElement buttonSave;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Popular Groups')]")
	WebElement lblPopularGroups;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'group-header')]/label/span")
	List<WebElement> listPopolarGroupCheckbox;

	@FindBy(how = How.XPATH, using = "//span[@class='fontStyle redFont']")
	WebElement lblMessage;

	@FindBy(how = How.XPATH, using = "//span[text()='Delete'] | //span[text()='Löschen']")
	WebElement btnDelete;

	@FindBy(how = How.XPATH, using = "//span[text()='Edit'] | //span[text()='Bearbeiten']")
	WebElement btnEdit;

	@FindBy(how = How.XPATH, using = "//button[text()='Yes'] | //button[text()='Ja']")
	WebElement btnYes;
	
	@FindBy(how = How.XPATH, using = "//div [@class='footer']/button[1]")
	WebElement buttonYes;

	@FindBy(how = How.XPATH, using = "//button[text()='No'] | //button[text()='Nein']")
	WebElement btnNo;

	@FindBy(how = How.XPATH, using = "//div [@class='footer']/button[2]")
	WebElement buttonNo;
	
	@FindBy(how = How.XPATH, using = "//h1[@class='headline']")
	WebElement lbltitle;

	@FindBy(how = How.XPATH, using = "//button[text()='Create Group'] | //button[text()='Anlegen']")
	WebElement btnCreateGroup;
	
	@FindBy(how = How.XPATH, using = "//div/button[@class='default button-left']")
	WebElement buttonCreateGroup;

	@FindBy(how = How.XPATH, using = "//button[text()='Delete'] | //button[text()='Löschen']")
	WebElement btnSelectModeDelete;
	
	@FindBy(how = How.XPATH, using = "//div/button[@class='default']")
	WebElement buttonSelectModeDelete;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'container group-headline-container-select-view')]/h3")
	List<WebElement> listGroup;

	@Override
	public <P extends PageObject> P initPageObject(Class<P> pageObjectClass) {
		return null;
	}

	public AddGroupPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
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

	/*
	 * @author: ttr2hc
	 */
	public Result IsAddGroupDisplayed() {
		Result res = new Result();
		int i = 0;
		String entry[] = { "green", "dark-green", "cyan", "blue", "dark-blue", "purple", "magenta", "red" };
		waitForElementDisplay(lblAddGroup, 30);
		if (lblAddGroup.getText().equals("Add Group")) {
			verifyWebElementExist(btnNewGroup, "New Group");
			verifyWebElementExist(btnPredefinedGroup, "Predefined Group");
			verifyWebElementExist(lablelUpdate, "UPDATE");
			verifyWebElementExist(txtTitle, "Title textbox");
			verifyWebElementExist(btnCancel, "Cancel");
			verifyWebElementExist(btnAdd, "Add");
			for (WebElement temp : listColor) {
				if (temp.getText().contains(entry[i])) {
					res.setResult(true);
					res.setMessage(String.format("The color [%s] is displayed", entry[i]));
				} else {

					res.setResult(false);
					res.setMessage(String.format("The color [%s] is not displayed", entry[i]));
				}

				i += 1;
			}
			res.setResult(true);
			res.setMessage(String.format("Add Group page is displayed"));
		} else {
			res.setResult(false);
			res.setMessage(String.format("Add Group page is not displayed"));
		}
		return res;
	}

	/*
	 * @author: ttr2hc
	 * edit: htr2hc
	 */
	public Result AddGroup(String vTitle, String vColor, String vAction, List<String> lstMessage) {
		Result res = new Result();
		String xpath = "";

		if (vTitle == "NA")
			txtTitle.sendKeys(Keys.DELETE);
		else
			txtTitle.sendKeys(vTitle);

		sleep(1000);
		txtTitle.sendKeys(Keys.ENTER);
		String vMessage = "";
		try {
			if (lblMessage.getCssValue("color").equalsIgnoreCase("rgba(226, 0, 21, 1)")) {
				if (lstMessage != null) {
					for (int i = 0; i < lstMessage.size(); i++) {
						vMessage = lstMessage.get(i);
						if (lblMessage.getText().equals(vMessage)) {
							res.setResult(true);
							res.setMessage(String.format("The error mesage [%s] exists", vMessage));
							break;
						} else {
							if (i == lstMessage.size() - 1) {
								res.setResult(false);
								res.setMessage(String.format("The error mesage [%s] exists", vMessage));
							}
						}
					}
				} else {
					res.setResult(true);
					res.setMessage("Add group successful");
				}
			} else {
				res.setResult(false);
				res.setMessage(String.format("The error mesage [%s] is not displayed correctly", vMessage));
			}
			buttonCancel.click();
		} 
		catch (NoSuchElementException ex) 
		{
			xpath = "//div[contains(@class,'" + vColor + "')]";
			WebElement color1 = driver.findElement(By.xpath(xpath));
			color1.click();
			
		if (vAction == "Add")
		{
			btnAdd.click();
		}
		else if (vAction == "Edit")
		{
			buttonSave.click();
		}
			sleep(2000);
			// Verify new group is created
			xpath = "//h3[contains(text(),'" + vTitle + "')]";
			WebElement group = driver.findElement(By.xpath(xpath));
			verifyWebElementExist(group, vTitle);
			res.setResult(true);
			res.setMessage(String.format("The new group [%s] is created", vTitle));
		}
		return res;
	}

	/*
	 * @author: ttr2hc
	 */
	public Result IsPredefinedGroupDisplayed() {
		Result res = new Result();
		int count = 0;

		waitForElementDisplay(labelAddGroup,30);
		if (labelAddGroup.getText().equals("Add Group") || labelAddGroup.getText().equals("Gruppe anlegen")) {
			verifyWebElementExist(btnNewGroup, "New Group");
			verifyWebElementExist(buttonPredefinedGroup, "Predefined Group");
			verifyWebElementExist(lblPopularGroups, "Popular Groups");
			verifyWebElementExist(lablelUpdate, "UPDATE message in red color");
			verifyWebElementExist(btnCancel, "Cancel");
			verifyWebElementExist(btnAdd, "Add");
			for (WebElement temp : listPopolarGroupCheckbox) {
				if (!temp.getCssValue("background-color").equals("rgba(255,​ 255,​ 255)")) {
					count = count + 1;
				}
			}
			if (count > 0) {
				res.setResult(true);
				res.setMessage(String.format("All predefined groups are unchecked"));
			} else {
				res.setResult(false);
				res.setMessage(String.format("Some predefined groups are checked"));
			}
		} else {
			res.setResult(false);
			res.setMessage(String.format("Add Group title is not displayed on Predefined Group page  "));
		}
		return res;

	}

	/*
	 * @author: ttr2hc
	 */

	public Result ClickPredefinedGroup() {
		Result res = new Result();

		waitAndClick(buttonPredefinedGroup, 10);
		return res;
	}

	/*
	 * @author: ttr2hc
	 */
	public Result AddPredefinedGroup(String vPopularGroup) {
		Result res = new Result();
		String xpath = "";

		// Add Popular group
		xpath = "//h4[contains(text(),'" + vPopularGroup + "')]/..//label/span";
		WebElement populargroupname = driver.findElement(By.xpath(xpath));
		populargroupname.click();
		sleep (2000);
		buttonAdd.click();

		// Verify Popular group
		xpath = "//h3[contains(text(),'" + vPopularGroup + "')]";
		WebElement populargroupgroup = driver.findElement(By.xpath(xpath));
		verifyWebElementExist(populargroupgroup, vPopularGroup);
		res.setResult(true);
		res.setMessage(String.format("The new polular group [%s] is created", vPopularGroup));
		return res;
	}

	/*
	 * @author: ttr2hc
	 */
	public Result DeleteGroup(String vGroupName) {
		Result res = new Result();
		String xpath = "";
		String confirmMsg = "Do you want to delete Mobile Devices and all Quick Links in that Group?";

		xpath = "//h3[text()='" + vGroupName + "']//..//..//span";
		WebElement populargroupgroup = driver.findElement(By.xpath(xpath));
		if (populargroupgroup.isDisplayed()) {
			populargroupgroup.click();
			sleep(1000);
			btnDelete.click();
			sleep(1000);
			confirmMsg = "Do you want to delete " + vGroupName + " and all Quick Links in that Group?";
			xpath = "//div[contains(text(),'" + vGroupName + "')]";
			waitForElementDisplay(driver.findElement(By.xpath(xpath)), 10);
			WebElement ConfirmMessage = driver.findElement(By.xpath(xpath));
			if (ConfirmMessage.isDisplayed()) 
			{
				buttonYes.click();
				sleep(1000);
				res.setResult(true);
				res.setMessage(String.format("The predefined group [%s] is deleted", confirmMsg));
			} 
			else 
			{
				res.setResult(false);
				res.setMessage(String.format("The confirm message [%s] is not displayed", vGroupName));
			}
		} else {
			res.setResult(false);
			res.setMessage(String.format("The predefined group [%s] does not exist", vGroupName));
		}

		return res;
	}

	/*
	 * @author: ttr2hc
	 */
	public Result EditGroup(String vGroupName, String vGroupNewName, String vColor, List<String> lstMessage, String vAction) {
		Result res = new Result();
		String xpath = "";
		xpath = "//h3[text()='" + vGroupName + "']//..//..//span";
		WebElement populargroupgroup = driver.findElement(By.xpath(xpath));
		if (populargroupgroup.isDisplayed()) {
			sleep(1000);
			populargroupgroup.click();
			sleep(1000);
			btnEdit.click();
			sleep(1000);
			txtTitle.clear();
			sleep(1000);
			AddGroup(vGroupNewName, vColor, vAction, lstMessage);
			res.setResult(true);
			res.setMessage(String.format("The group name [%s] is edited", vGroupNewName));
		} else {
			res.setResult(false);
			res.setMessage(String.format("The group name [%s] is not edited", vGroupName));
		}

		return res;
	}

	/*
	 * @author: ttr2hc
	 */
	public Result IsSelectModeDisplayed(String groupname[]) {
		Result res = new Result();
		int i = 0;
		int count = groupname.length;

		waitForElementDisplay(lbltitle, 30);
		if (lbltitle.getText().contains("Select Mode (0)") || lbltitle.getText().contains("Auswahl-Modus (0)")) {

			verifyWebElementExist(buttonCreateGroup, "Create Group");
			verifyWebElementExist(buttonSelectModeDelete, "Delete");
			for (WebElement temp : listGroup) {
				if (temp.getText().equals(groupname[i])) 
				{
					i = i + 1;
				}
			}
			if (count == i) 
			{
				res.setResult(true);
				res.setMessage(String.format("All groups are displayed in Select Mode page"));
			} 
			else 
			{
				res.setResult(false);
				res.setMessage(String.format("All groups are not displayed in Select Mode page"));
			}
		} 
		else 
		{
			res.setResult(false);
			res.setMessage(String.format("The default title of page is not displayed as Select Mode (0)"));
		}
		return res;

	}

	/*
	 * @author: ttr2hc
	 */
	public Result DeleteSelectModeGroup(String vGroupName) {
		Result res = new Result();
		String xpath = "";
		//xpath = "//h3[contains(text(),'%s')]/../..//*[name()='path'][@class='background']";
		xpath = "//h3[text()='%s']/../../mws-checkmark";	
		//driver.findElements(By.xpath(String.format("//h3[text()='%s']/../../mws-checkmark", vGroupName)));
		//xpath = "//h3[contains(text(),'" + vGroupName + "')]/../..//*[name()='path'][@class='background']";
		WebElement groupgroup = driver.findElement(By.xpath(String.format(xpath, vGroupName)));
		if (groupgroup.isDisplayed()) {
			groupgroup.click();
			sleep(1000);
			buttonSelectModeDelete.click();
			sleep(1000);
			buttonNo.click();
			sleep(1000);
			if (!isDisplayed(buttonNo)) 
			{
				sleep(1000);
				buttonSelectModeDelete.click();
				sleep(1000);
				buttonYes.click();
				sleep(1000);
				res.setResult(true);
				res.setMessage(String.format("Delete the selected group [%s] successfully", vGroupName));
			} 
			else 
			{
				res.setResult(false);
				res.setMessage(String.format("Unable to close the Delete confirmation when clicks on No button"));
			}
			return res;

		} 
		else 
		{
			res.setResult(false);
			res.setMessage(String.format("The group name [%s] does not exist", vGroupName));
		}
		return res;

	}
}
