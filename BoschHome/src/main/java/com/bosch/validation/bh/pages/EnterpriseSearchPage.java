package com.bosch.validation.bh.pages;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.interactions.MouseMoveToLocation;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObject;
import com.bosch.validation.fw.setup.PageObjectFactory;

public class EnterpriseSearchPage extends MasterPage {
	private static final Logger log = LogManager.getLogger(EnterpriseSearchPage.class);
	Result res = new Result();
	
	@FindBy(how = How.XPATH, using = "//div[@class='search-area-container']/div[not(contains(@class,'favorite-searches'))]//span[@class='custom-row-content']")
	List<WebElement> myLatestSearches;	
	@FindBy(how = How.XPATH, using = "//div[@class='search-area-container']/div[not(contains(@class,'favorite-searches'))]//span[contains(@class,'mws-icon-wishlist-outline-blue')]")
	List<WebElement> myLatestSearches_lstAddToFavoriteIcons;
	@FindBy(how = How.XPATH, using = "//div[@class='search-area-container']/div[not(contains(@class,'favorite-searches'))]//mws-context-menu")
	WebElement myLatestSearches_btnSettings;
	
	@FindBy(how = How.XPATH, using = "//div[@class='search-area-container']/div[contains(@class,'favorite-searches')]//span[@class='custom-row-content']")
	List<WebElement> myFavoriteSearches;
	@FindBy(how = How.XPATH, using = "//div[@class='search-area-container']/div[contains(@class,'favorite-searches')]//span[contains(@class,'mws-icon-wishlist-filled-blue')]")
	List<WebElement> myFavoriteSearches_lstRemoveFavoriteIcons;
	@FindBy(how = How.XPATH, using = "//div[@class='search-area-container']/div[contains(@class,'favorite-searches')]//mws-context-menu")
	WebElement myFavoriteSearches_btnSettings;
	
	@FindBy(how = How.CSS, using = ".body>div")
	WebElement popupDeleteSearchesHistoryConfirmation_bodyContent;
	@FindBy(how = How.XPATH, using = "//div[@class='footer']//button[text()='Yes'] | //div[@class='footer']//button[text()='Ja']")
	WebElement popupDeleteSearchesHistoryConfirmation_btnYes;
	@FindBy(how = How.XPATH, using = "//div[@class='footer']//button[text()='No'] | //div[@class='footer']//button[text()='Nein']")
	WebElement popupDeleteSearchesHistoryConfirmation_btnNo;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='Enterprise Search']")
	WebElement txtEnterpriseSearch;
	
	@FindBy(how = How.CSS, using = ".mws-icon-close")
	WebElement btnClose;

	@FindBy(how = How.CSS, using = ".mws-icon-search-blue")
	WebElement iconMagnifier;

	@FindBy(how = How.XPATH, using = "//div[@class='overflow-handling']/span")
	List<WebElement> listsearchSuggestion;

	@FindBy(how = How.XPATH, using = "//div[@class='results-box-container']")
	WebElement searchSuggestionbox;
	
	@FindBy(how = How.CSS, using = ".see-all-results-area>span.mws-icon-forward-right")
	WebElement lnkSeeAllResults;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'search-input active')]")
	WebElement searchTab;

	@FindBy(how = How.XPATH, using = "//div[@class='feedback-container']")
	WebElement feedBackSection;

	@FindBy(how = How.CSS, using = ".float-area")
	WebElement closeButtonicon;

	@FindBy(how = How.XPATH, using = "//div[@class='workspace-settings-label']")
	WebElement settingLink;

	@FindBy(how = How.XPATH, using = "//span[@class='mws-icon-lg mws-icon-up']")
	WebElement scrollTotheTop;

	@FindBy(how = How.CSS, using = ".search-input .mws-icon-close")
	WebElement iconDeleteSearch;

	@FindBy(how = How.XPATH, using = "//span[contains(@class,'mws-icon mws-icon-wishlist-outline-blue custom-star-position ng-tns-c9-8')])")
	WebElement addtoFavorIcon;
	
	@FindBy(how = How.CSS, using = ".mws-icon.mws-icon-delete")
	WebElement iconDeleteSearchHistory;
	

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

	public EnterpriseSearchPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	/*
	 * @author: AIT1HC
	 */
	public int countMyLatestSearches(ExtentTest testReport) {
		testReport.pass(String.format("WebElement [%s] - count [%s]", "My Latest Searches", myLatestSearches.size()));
		return myLatestSearches.size();
	}

	/*
	 * @author: AIT1HC
	 */
	public int countMyFavoriteSearches(ExtentTest testReport) {
		testReport
				.pass(String.format("WebElement [%s] - count [%s]", "My Favorite Searches", myFavoriteSearches.size()));
		return myFavoriteSearches.size();
	}

	/*
	 * @author: RTC1HC
	 */

	public Result IsEnterpriseSearchDisplayed() {
		if (waitForElementDisplay(searchTab, 60)) {
			verifyWebElementExist(settingLink, "Setting Link");
			verifyWebElementExist(closeButtonicon, "Close button & icon (x)");
			verifyWebElementExist(feedBackSection, "Feedback Section");
			verifyWebElementExist(scrollTotheTop, "Scroll to the Top");
			res.setResult(true);
			res.setMessage(String.format("Enterprise Search is displayed"));
		} else {
			res.setResult(false);
			res.setMessage(String.format("Enterprise Search is not displayed"));
		}
		return res;
	}

	public Result IsSearchSuggestionDisplayed(String keyword) {
		txtEnterpriseSearch.clear();
		txtEnterpriseSearch.sendKeys(keyword);
		if (waitForElementDisplay(searchSuggestionbox, 60)) {
			verifySearchSuggestion(keyword);
			res.setMessage(String.format("Auto suggestion is displayed"));
		} else {
			res.setResult(false);
			res.setMessage(String.format("Auto suggestion is not displayed"));
		}
		return res;
	}
	
	public Result doSearch_verifySuggestion(String keyword) {
		try {
			txtEnterpriseSearch.clear();
			txtEnterpriseSearch.sendKeys(keyword);
			if (waitForElementDisplay(searchSuggestionbox, 60)) {
				res.setResult(true);
				res.setMessage(String.format("Auto suggestion is displayed"));
			} else {
				res.setResult(false);
				res.setMessage(String.format("Auto suggestion is not displayed"));
			}
			return res;
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("ERR [No search results is displayed]: " + ex.getMessage());
			return res;
		}
	}
	
	public Result doSearch_byIcon(String keyword) {
		try {
			txtEnterpriseSearch.clear();
			txtEnterpriseSearch.sendKeys(keyword);
			if (waitForElementDisappear(searchSuggestionbox, 5)) {
				res.setResult(true);
				res.setMessage(String.format("Auto suggestion not is displayed"));
			} else {
				res.setResult(false);
				res.setMessage(String.format("Auto suggestion is displayed"));
				return res;
			}
			
			iconMagnifier.click();
			SearchResultPage searchResultPage = PageObjectFactory.initPageObject(SearchResultPage.class, driver);
			res.setResult(searchResultPage.verifySearchResultPageDisplay(keyword) && verifySearchTerminLatestSearch(keyword));
			res.setMessage(String.format(
					"The search result is displayed in a new tab & The search term will be displayed in My Latest Searches"));	
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("ERR [No search results is displayed]: " + ex.getMessage());
		}
		
		return res;
	}
	
	public Result doSearch_byLinkSeeAllResults(String keyword) {
		try {
			txtEnterpriseSearch.clear();
			txtEnterpriseSearch.sendKeys(keyword);
			if (waitForElementDisplay(searchSuggestionbox, 60)) {
				res.setResult(true);
				res.setMessage(String.format("Auto suggestion is displayed"));
			} else {
				res.setResult(false);
				res.setMessage(String.format("Auto suggestion is not displayed"));
				return res;
			}
			
			lnkSeeAllResults.click();
			SearchResultPage searchResultPage = PageObjectFactory.initPageObject(SearchResultPage.class, driver);
			res.setResult(searchResultPage.verifySearchResultPageDisplay(keyword) && verifySearchTerminLatestSearch(keyword));
			res.setMessage(String.format(
					"The search result is displayed in a new tab & The search term will be displayed in My Latest Searches"));	
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("ERR [No search results is displayed]: " + ex.getMessage());
		}
		
		return res;
	}
	
	public Result cancelSearch() {
		try {
			iconDeleteSearch.click();
			boolean isTrue = txtEnterpriseSearch.getText().equals("");
			res.setResult(isTrue);
			if (isTrue)
				res.setMessage(String.format("Textbox Search is cleared"));
			else
				res.setMessage(String.format("Textbox Search is not cleared"));
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("ERR [Textbox Search is not cleared]: " + ex.getMessage());
		}
		
		return res;
	}

	public String getActualSearchFromSuggestion() {
		return listsearchSuggestion.get(0).getText();
	}
	
	public Result chooseFirstSuggestionAfterSearch(String keyword) {
		try {
			String actualKeyword = getActualSearchFromSuggestion();
			listsearchSuggestion.get(0).click();
			SearchResultPage searchResultPage = PageObjectFactory.initPageObject(SearchResultPage.class, driver);
			res.setResult(searchResultPage.verifySearchResultPageDisplay(actualKeyword) && verifySearchTerminLatestSearch(keyword));
			res.setMessage(String.format(
					"The search result is displayed in a new tab & The search term will be displayed in My Latest Searches"));
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("ERR [No search results is displayed]: " + ex.getMessage());
		}

		return res;
	}

	public Result verifySearchSuggestion(String keyword) {
		try {
			for (WebElement suggestion : listsearchSuggestion) {
				if (!suggestion.getText().toLowerCase().contains(keyword.toLowerCase())) {
					res.setResult(false);
					res.setMessage("ERR [Cannot find out keyword]");
					return res;
				}
			}
			res.setResult(true);
			return res;
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("ERR [Failed to verify search suggestion] :" + ex.getMessage());
			return res;
		}

	}

	public boolean verifySearchTerminLatestSearch(String keyword) {
		boolean isTrue = false;
		for (WebElement webElement : myLatestSearches) {
			if (webElement.getText().toLowerCase().contains(keyword.toLowerCase())) {
				isTrue = true;
				break;
			}
		}

		return isTrue;
	}
	
	public Result addtoFavoriteSearh() {
		try {
			for (WebElement webElement : myFavoriteSearches) {
				if(!myFavoriteSearches.equals(5) && !webElement.equals(5)) {
				addtoFavorIcon.click();
				myFavoriteSearches.add((WebElement) myLatestSearches);
				}
			}

			res.setResult(true);
			return res;

		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("Cannot add latest search to favorite search " + ex.getMessage());
			return res;
		}
	}

	public Result verifyMaximumListLatestSearches(List<String> expectedLatestSearches) {
		boolean isTrue = true;
		try {
			if (myLatestSearches.size() != 5)
				isTrue = false;

			List<String> actualLatestSearches = new ArrayList<String>();
			for (WebElement lastestSearch : myLatestSearches)
				actualLatestSearches.add(lastestSearch.getText());

			for (int i = 0; i < expectedLatestSearches.size(); i++) {
				if (!actualLatestSearches.get(i).equals(expectedLatestSearches.get(i))) {
					isTrue = false;
					break;
				}
			}
			res.setResult(isTrue);
			if(isTrue)
				res.setMessage("Only 5 latest search terms are displayed in My Latest Searches");
			else
				res.setMessage("MyLatestSearches displays < or > 5 items");
			
			return res;
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("Failed when check MyLatestSearches: " + ex.getMessage());
			
			return res;
		}
	}
	
	public Result addAllLatestSearches_toFavoriteSearches() {
		try {
			List<String> lstStringLatestSearches = new ArrayList<String>();
			List<String> lstStringFavoriteSearches = new ArrayList<String>();
			List<WebElement> lstAddToFavoritesIcons = myLatestSearches_lstAddToFavoriteIcons;
			for (int i = 0; i < myLatestSearches.size(); i++) {
				lstStringLatestSearches.add(myLatestSearches.get(i).getText());
				moveToElementAndClick(driver, lstAddToFavoritesIcons.get(0));
				sleep(500);
				lstAddToFavoritesIcons = myLatestSearches_lstAddToFavoriteIcons;
				lstStringFavoriteSearches.add(myFavoriteSearches.get(0).getText());
			}
			
			if (lstStringLatestSearches.equals(lstStringFavoriteSearches)) {
				res.setResult(true);
				res.setMessage("All MyLatestSearches added to MyFavoriteSearches successfully");
			} else {
				res.setResult(false);
				res.setMessage("Cannot add latest search to favorite search");
			}
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("Cannot add latest search to favorite search " + ex.getMessage());
		}
		
		return res;
	}
	
	public Result removeSearchTermInFavoriteSearches() {
		try {
			boolean isTrue = true;
			String removeSearchTerm = myFavoriteSearches_lstRemoveFavoriteIcons.get(0).getText();
			myFavoriteSearches_lstRemoveFavoriteIcons.get(0).click();
			for (WebElement favoriteSearch : myFavoriteSearches)
				if (favoriteSearch.getText().equals(removeSearchTerm)) {
					isTrue = false;
					break;
				}
			res.setResult(isTrue);
			if(isTrue)
				res.setMessage("Remove a favorite searches successfully");
			else
				res.setMessage("Cannot remove a favorite searches");
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("Cannot remove a favorite searches" + ex.getMessage());
		}
		
		return res;
	}
	
	public Result expandSettingsOfMyLatestSearches() {
		myLatestSearches_btnSettings.click();
		if(waitForElementDisplay(iconDeleteSearchHistory, 60)) {
			res.setResult(true);
			res.setMessage("Delete Search History pop-up menu is displayed");
		} else {
			res.setResult(false);
			res.setMessage("Delete Search History pop-up menu is not displayed");
		}
		
		return res;
	}

	public Result deleteAllLatestSearchesHistory() {
		try {
			boolean isTrue = true;
			String expectedContentMessage_en = "You are about to delete your Latest Searches. Are you sure?";
			String expectedContentMessage_de = "Sie löschen Ihre letzten Suchanfragen. Sind Sie sicher?";
			iconDeleteSearchHistory.click();
			Thread.sleep(1000);
			if (!waitForElementDisplay(popupDeleteSearchesHistoryConfirmation_btnYes, 5)
					|| !waitForElementDisplay(popupDeleteSearchesHistoryConfirmation_btnNo, 5)
					|| (!popupDeleteSearchesHistoryConfirmation_bodyContent.getText()
							.contains(expectedContentMessage_en)
							&& !popupDeleteSearchesHistoryConfirmation_bodyContent.getText()
									.contains(expectedContentMessage_de)))
				isTrue = false;

			popupDeleteSearchesHistoryConfirmation_btnYes.click();
			if (!waitForElementDisappear(myLatestSearches.get(0), 5))
				isTrue = false;

			res.setResult(isTrue);
			if (isTrue)
				res.setMessage("Delete all latest searches successfully");
			else
				res.setMessage("Delete all latest searches unsuccessfully");
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("Delete all latest searches unsuccessfully" + ex.getMessage());
		}

		return res;
	}
	
	public Result expandSettingsOfMyFavoriteSearches() {
		myFavoriteSearches_btnSettings.click();
		if(waitForElementDisplay(iconDeleteSearchHistory, 60)) {
			res.setResult(true);
			res.setMessage("Delete Favorite Searches pop-up menu is displayed");
		} else {
			res.setResult(false);
			res.setMessage("Delete Favorite Searches pop-up menu is not displayed");
		}
		
		return res;
	}

	public Result deleteAllFavoriteSearchesHistory() {
		try {
			boolean isTrue = true;
			String expectedContentMessage_en = "You are about to delete your Favorite Searches. Are you sure?";
			String expectedContentMessage_de = "Sie löschen Ihre favorisierten Suchanfragen. Sind Sie sicher?";
			iconDeleteSearchHistory.click();
			Thread.sleep(1000);
			if (!waitForElementDisplay(popupDeleteSearchesHistoryConfirmation_btnYes, 5)
					|| !waitForElementDisplay(popupDeleteSearchesHistoryConfirmation_btnNo, 5)
					|| (!popupDeleteSearchesHistoryConfirmation_bodyContent.getText()
							.contains(expectedContentMessage_en)
							&& !popupDeleteSearchesHistoryConfirmation_bodyContent.getText()
									.contains(expectedContentMessage_de)))
				isTrue = false;

			popupDeleteSearchesHistoryConfirmation_btnYes.click();
			if (!waitForTrue(myFavoriteSearches.size() == 0, 5))
				isTrue = false;

			res.setResult(isTrue);
			if (isTrue)
				res.setMessage("Delete all favorite searches successfully");
			else
				res.setMessage("Delete all favorite searches unsuccessfully");
		} catch (Exception ex) {
			res.setResult(false);
			res.setMessage("Delete all favorite searches unsuccessfully" + ex.getMessage());
		}

		return res;
	}
	
	public Result closeEnterpriseSearch() {
		Result res = new Result();
		res.setResult(waitForElementDisplay(btnClose, 20));
		sleep(1000);
		btnClose.click();	
		return res;
	}
}
