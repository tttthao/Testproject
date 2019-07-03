package com.bosch.validation.socos.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;
import com.bosch.validation.fw.setup.PageObjectSkeleton;

public class SocosPage extends PageObjectSkeleton implements PageObject {

	@FindBy(how = How.XPATH, using = "//iframe[@id='ivuFrm_page0ivu0']")
	WebElement iframe;

	@FindBy(how = How.XPATH, using = "//iframe[@id='urlFrame']")
	WebElement iframeURL;

	@FindBy(how = How.XPATH, using = "//div[@id='documentTableWrapper']")
	WebElement socosTable;
	@FindBy(how = How.XPATH, using = "//input[@id='searchstr']")
	WebElement textSearch;
	@FindBy(how = How.XPATH, using = "//input[@id='search_btn']")
	WebElement buttonSearch;
	@FindBy(how = How.XPATH, using = "//table[@id='documentTableHeader']")
	WebElement tableSearchResult;
	
	@FindBy(how = How.XPATH, using = "//img[@id='info_menu_link_header']")
	WebElement iconSupport;
	@FindBy(how = How.XPATH, using = "//span[@id='big_headline_image_headline_level_1']")
	WebElement textQuick;
	@FindBy(how = How.XPATH, using = "//span[@class='big_headline_image_background']")
	WebElement test;

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
		return null;
	}

	private WebDriver driver;

	public SocosPage(WebDriver driver) {
		this.driver = driver;
	}

	public Result searhSocos(ExtentTest testCase) {
		Result res = new Result();

		driver.switchTo().frame(iframe);
		driver.switchTo().frame(iframeURL);

		driver.findElements(By.xpath("//div[@id='documentTableWrapper']")).get(0).getSize().getWidth();

		testCase.info("STEP: Check IFRAME SIZE");
		int orgW = socosTable.getSize().getWidth();

		testCase.info("STEP: Enter Search Term");
		sendKeys(textSearch, "Bosch");

		testCase.info("STEP: Click button Search");
		waitAndClick(buttonSearch, 3);
		
		testCase.info("STEP: Wait for search result");
		sleep(10000);

		testCase.info("STEP: Verify IFRAME size");
		int desW = socosTable.getSize().getWidth();
		
		socosTable.getLocation().getX();
		socosTable.getSize().getWidth();
		driver.findElement(By.xpath("//img[@id='info_menu_link_header']")).getLocation().getX() ;
		driver.findElement(By.xpath("//img[@id='info_menu_link_header']")).getSize().getWidth();
		
		System.out.println(socosTable.getLocation().getX());
		System.out.println(socosTable.getSize().getWidth());
		System.out.println(driver.findElement(By.xpath("//img[@id='info_menu_link_header']")).getLocation().getX());
		System.out.println(driver.findElement(By.xpath("//img[@id='info_menu_link_header']")).getSize().getWidth());

		if (orgW != desW) {
			res.setMessage(String.format("IFrame size is changed - Expected [%s] - Actual [%s]", orgW, desW));
			res.setResult(false);
			return res;
		}
		
		scrollIntoView(driver, textQuick);
		
		/*deviceWebElementDisplay(driver, test, 3);
		
		if (!deviceWebElementDisplay(driver, iconSupport, 3)) {
			res.setMessage(String.format("Icon is outside"));
			res.setResult(false);
			return res;
		}*/

		testCase.info("STEP: Verify Search result");

		return res;
	}
}
