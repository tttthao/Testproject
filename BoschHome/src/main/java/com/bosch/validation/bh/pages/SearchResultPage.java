package com.bosch.validation.bh.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;

public class SearchResultPage extends MasterPage {
	private static final Logger log = LogManager.getLogger(EnterpriseSearchPage.class);
	Result res = new Result();
	
	@FindBy(how = How.CSS, using = "#SearchBox input")
	WebElement tbxSearch;
	
	@FindBy(how = How.ID, using = "ietab2-updated-page")
	WebElement lblTitleIETab;
	
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

	public SearchResultPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public boolean verifySearchResultPageDisplay(String keyword) {
		boolean isTrue = false;
		List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		try {
			driver.switchTo().window(browserTabs.get(1));
			if(!waitForElementDisappear(lblTitleIETab, 3))
				driver.close();
			if (waitForElementDisplay(tbxSearch, 60)) {
				String strSearch = tbxSearch.getAttribute("value");
				if (strSearch.toLowerCase().equals(keyword.toLowerCase()))
					isTrue = true;
				else
					isTrue = false;
				driver.close();
				driver.switchTo().window(browserTabs.get(0));
				return isTrue;
			} else
				isTrue = false;
		} catch (Exception ex) {
			isTrue = false;
		}
		
		return isTrue;
	}
}
