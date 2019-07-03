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

public class MainPage_old_willbedeleted extends MasterPage {
	private static final Logger log = LogManager.getLogger(MainPage_old_willbedeleted.class);

	@FindBy(how = How.XPATH, using = "//title")
	WebElement pageTitle;

	@FindBy(how = How.XPATH, using = "//iframe[@id='ivuFrm_page0ivu0']")
	WebElement iFramePage;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Teaser-WelcomeTeaser__greeting']")
	WebElement labelWelcomeBoxName;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Link A-Link--primary']//span[text()='Ho Chi Minh City']")
	WebElement labelWelcomeBoxLocation;

	@FindBy(how = How.XPATH, using = "//div[@class='M-SocialShareIcons M-SocialShareIcons--leftAligned']//a[@title='Facebook']")
	WebElement linkWelcomeBoxFacebook;

	@FindBy(how = How.XPATH, using = "//div[@class='M-SocialShareIcons M-SocialShareIcons--leftAligned']//a[@title='Twitter']")
	WebElement linkWelcomeBoxTwitter;

	@FindBy(how = How.XPATH, using = "//div[@class='M-SocialShareIcons M-SocialShareIcons--leftAligned']//a[@title='Youtube']")
	WebElement linkWelcomeBoxYoutube;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Teaser-WelcomeTeaser__internal-links']//span[text()='Bosch Zünder']")
	WebElement linkWelcomeBoxBoschZunder;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Teaser-WelcomeTeaser__internal-links']//span[text()='My HR']")
	WebElement linkWelcomeBoxMyHR;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Teaser-LocationTeaser__content is-loaded']//h4")
	WebElement labelLocationHeadline;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Teaser-LocationTeaser__location-list']//span[@class='A-Link__linkText']")
	List<WebElement> listLocationObjects;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Project')]")
	WebElement labelProject;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Project')]/../h4")
	WebElement labelProjectTitle;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Project')]/../div[@class='A-Teaser-CardTeaser__description']")
	WebElement labelProjectDecription;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Project')]/../div[@class='A-Link A-Link--primary ']")
	WebElement linkProjectMoreInfomation;

	@FindBy(how = How.XPATH, using = "//h5[@class='A-Teaser-TopicServiceTeaser__label u-Text--small']")
	WebElement labelInformation;

	@FindBy(how = How.XPATH, using = "//h4[@class='A-Teaser-TopicServiceTeaser__title u-TextColor--violet h4']")
	WebElement labelInformationTopicAndServices;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Teaser-TopicServiceTeaser__location-list']//span[@class='A-Link__linkText']")
	List<WebElement> listInformationObjects;

	@FindBy(how = How.XPATH, using = "//h5[@class='A-Teaser-DirectoryTeaser__label u-Text--small']")
	WebElement labelManager;

	@FindBy(how = How.XPATH, using = "//h4[@class='A-Teaser-DirectoryTeaser__title u-TextColor--violet h4']")
	WebElement labelManagerTitle;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'org.manager')]/../div[@class='A-Teaser-CardTeaser__description']")
	WebElement labelManagerDescription;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Teaser-DirectoryTeaser__entries']/div[@class='A-Teaser-DirectoryTeaser__entry']")
	List<WebElement> listManagerObject;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'SOCOS-C')]")
	WebElement labelSOCOSC;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'SOCOS-C')]/../h4")
	WebElement labelSOCOSCTitle;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'SOCOS-C')]/../div[@class='A-Teaser-CardTeaser__description']")
	WebElement labelSOCOSCDescription;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'SOCOS-C')]/../div[@class='A-Link A-Link--primary ']")
	WebElement linkSOCOSCSearch;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Bosch Zünder')]")
	WebElement labelBoschZunder;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Bosch Zünder')]/../h4")
	WebElement labelBoschZunderTitle;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Bosch Zünder')]/../div")
	WebElement linkBoschZunderRead;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Social Box')]")
	List<WebElement> listSocial;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Social Box')]/../h4")
	List<WebElement> listSocialTitle;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Social Box')]/../div")
	List<WebElement> listSociallink;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Products')]")
	List<WebElement> listProduct;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Products')]/../h4")
	List<WebElement> listProductTitle;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Teaser-ProductTeaser__text']/div[@class='A-Teaser-ProductTeaser__description']")
	List<WebElement> listProductDecription;

	@FindBy(how = How.XPATH, using = "//div[@class='A-Teaser-ProductTeaser__text']/div[@class='A-Link A-Link--primary ']")
	List<WebElement> listProductLink;

	@FindBy(how = How.XPATH, using = "//a[@href='bzo.bosch.com']//..//..//img")
	WebElement imageBzoBox;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Bosch Zünder')]/..//a/span")
	WebElement linkReadArticle;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Bosch Zünder')]/..//../span/span")
	WebElement linkReadArticleforward;
	
	@FindBy(how = How.XPATH, using = "///footer//strong")
	WebElement footer_labelRobertBosch;

	@FindBy(how = How.XPATH, using = "//footer//a[@class='footer__link js-open-modal']")
	WebElement footer_linkImprint;
	
	@FindBy(how = How.XPATH, using = "//footer/p[text()='Portal v7.0.0']")
	WebElement footer_lablePortalVersion;
	
	@FindBy(how = How.XPATH, using = "//header[@class='dialog__header']")
	WebElement dialogImprint;
	
	
	//Location Box
	@FindBy(how = How.XPATH, using = "//h5[@class='A-Teaser-LocationTeaser__label u-Text--small']/span")
	WebElement lableLocationName;
	
	@FindBy(how = How.XPATH, using = "//h4[@class='A-Teaser-LocationTeaser__title u-TextColor--darkGreen h4']")
	WebElement lableLocationDescription;
	
	@FindBy(how = How.XPATH, using = "html/body/main/div[2]/div[2]/div/div/div[2]/div/div/a/span")
	WebElement linkAssociateNetworks;

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

	public MainPage_old_willbedeleted(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageObjectFactory.initPageObject(MainPage_old_willbedeleted.class, driver);
	}
	

	public Result isPageDisplayByName(String pageName, ExtentTest testReport) {
		Result res = new Result();
		waitForElementDisplay(pageTitle, 60);

		if (!pageTitle.getText().equals(pageName)) {
			res.setMessage(String.format("Page Expected [%s] - Actual [%s]", pageName, pageTitle.getText()));
			res.setResult(false);
			testReport.info(String.format("Page [%s] - <NOT DISPLAYED>", pageName));
			testReport.fail(String.format("Page [%s] - <NOT DISPLAYED>", pageName));
			return res;
		}

		testReport.info(String.format("Page [%s] - <DISPLAYED>", pageName));
		testReport.pass(String.format("Page [%s] - <DISPLAYED>", pageName));
		res.setResult(true);
		return res;
	}

	public Result VerifyCSSValue(String component, String cssProperty, String cssValue, ExtentTest testReport) {
		Result res = new Result();
		String tem;
		tem = "";

		waitForElementDisplay(labelBoschZunder, 20);
		// Get the CSS value
		if (component == "label") {
			if (labelBoschZunder.isDisplayed()) {
				testReport.info(String.format("The Element [%s] - <DISPLAYED>", "BoschZunder"));
				tem = labelBoschZunder.getCssValue(cssProperty);
			}
		}

		if (component == "title") {
			if (labelBoschZunderTitle.isDisplayed()) {
				testReport.info(String.format("The Element [%s] - <DISPLAYED>", "Bosch Zunder Title"));
				tem = labelBoschZunderTitle.getCssValue(cssProperty);
			}
		}
		
		if (component == "link") {
			if (linkBoschZunderRead.isDisplayed()) {
				testReport.info(String.format("The Element [%s] - <DISPLAYED>", "Read Article link"));
				tem = linkBoschZunderRead.getCssValue(cssProperty);
			}
		}

		if (component == "image") {
			if (imageBzoBox.isDisplayed()) {
				testReport.info(String.format("The Element [%s] - <DISPLAYED>", "Read Article link"));
				tem = imageBzoBox.getCssValue(cssProperty);
			}
		}

		if (tem.equals(cssValue)) {
			res.setMessage(String.format("CSS Expected [%s] - Actual [%s]", cssValue, tem));
			res.setResult(true);
			testReport.pass(String.format("CSS Expected [%s] is Actual [%s]", cssValue, tem));
		} else {
			res.setMessage(String.format("CSS Expected [%s] - Not AS Actual [%s]", cssValue, tem));
			res.setResult(false);
			testReport.fail(String.format("CSS Expected [%s] is not Actual [%s]", cssValue, tem));
		}
		
		return res;
	}

	/*
	 * public BzoBoxDetailsPage ClickReadArtilelink(ExtentTest testReport) {
	 * Result res = new Result();
	 * 
	 * waitForElementDisplay(linkReadArticle, 20);
	 * testReport.info(String.format("[CLICK] - Read Article @ Main Page"));
	 * linkReadArticle.click(); res.setResult(true); return
	 * PageObjectFactory.initPageObject(BzoBoxDetailsPage.class, driver); }
	 * 
	 * public BzoBoxDetailsPage ClickReadArticleforwardlink(ExtentTest
	 * testReport) { Result res = new Result();
	 * 
	 * waitForElementDisplay(linkReadArticleforward, 20);
	 * testReport.info(String.
	 * format("[CLICK] - Read Article  forward@ Main Page"));
	 * linkReadArticleforward.click(); res.setResult(true); return
	 * PageObjectFactory.initPageObject(BzoBoxDetailsPage.class, driver); }
	 */

	/*
	 * @author: NNQ1HC
	 */
	public Result isProjectDisplayByName(String pageName, ExtentTest testReport) {
		Result res = new Result();
		waitForElementDisplay(pageTitle, 60);

		driver.switchTo().frame(iFramePage);
		boolean bProject = labelProject.isDisplayed();
		if (!bProject) {

			testReport.info(String.format("Page [%s] - <NOT DISPLAYED>", pageName));
			testReport.fail(String.format("Page [%s] - <NOT DISPLAYED>", pageName));
			return res;
		}

		testReport.info(String.format("Page [%s] - <DISPLAYED>", pageName));
		testReport.pass(String.format("Page [%s] - <DISPLAYED>", pageName));
		res.setResult(true);
		return res;
	}

	public void verifyAssociateNetworkLink(String pageTitle, ExtentTest testcase) {
		switchToIframe(driver, iFramePage);
		verifyLink(linkAssociateNetworks, "[Associate Networks] link", pageTitle, testcase);
		
	}
	
	public void verifyLocationInLocationBox(String locationName, String locationDescription, ExtentTest extentTest){
		switchToIframe(driver, iFramePage);
		verifyWebElementContent(lableLocationName, "[Location Name] label", locationName, extentTest);
		verifyWebElementContent(lableLocationDescription, "[Location Description] label", locationDescription, extentTest);
	}

	/*
	 * @author: HTR2HC
	 */
	/*
	 * public Result isWelcomeBoxDisplay(ExtentTest testReport) { Result res =
	 * new Result(); waitForElementDisplay(boxWelcome, 60);
	 * 
	 * driver.switchTo().frame(iFramePage); boolean bWelcome =
	 * boxWelcome.isDisplayed(); if () {
	 * 
	 * 
	 * } }
	 */

	public void VeriryFooter(String strRoberBosch, String portalVersion, String im, ExtentTest extentTest){	
		verifyWebElementContent(footer_labelRobertBosch, "[Rober Bosch GmbH] lable", strRoberBosch, extentTest);
		verifyWebElementContent(footer_lablePortalVersion, "[Portal Version] link", portalVersion, extentTest);		
	}
	
}