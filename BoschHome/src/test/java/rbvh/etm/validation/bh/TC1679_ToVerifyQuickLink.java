package rbvh.etm.validation.bh;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Keys;

import com.bosch.validation.bh.pages.EditQuickLinkPage;
import com.bosch.validation.bh.pages.MainPage;
import com.bosch.validation.bh.pages.MyWorkspacePage;
import com.bosch.validation.bh.pages.QuickLinksPage;
import com.bosch.validation.bh.pages.SelectModePage;
import com.bosch.validation.fw.Assertion;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObjectFactory;
import com.bosch.validation.fw.setup.TestCase;

@RunWith(Parameterized.class)
public class TC1679_ToVerifyQuickLink extends TestCase {	
	Result res;
	MainPage mainPage;
	QuickLinksPage quickLinksPage;
	EditQuickLinkPage editQuickLinkPage;
	MyWorkspacePage myWorkspacePage;
	SelectModePage selectModePage;
	String language;
	
	public TC1679_ToVerifyQuickLink(String language) {
	      this.language = language;
	   }
	
	@Parameterized.Parameters
	   public static Collection<Object[]> language() {
	      return Arrays.asList(new Object[][] {
	         { "EN" },
	         { "DE" }
	      });
	   }
	
	@Before
	public void pre() {
		new TC1679_ToVerifyQuickLink(language);
		factory.connectTo();
		res = new Result();
		mainPage = PageObjectFactory.initPageObject(MainPage.class, driver);
		quickLinksPage  = PageObjectFactory.initPageObject(QuickLinksPage.class, driver);
		editQuickLinkPage = PageObjectFactory.initPageObject(EditQuickLinkPage.class, driver);
		myWorkspacePage = PageObjectFactory.initPageObject(MyWorkspacePage.class, driver);
		selectModePage = PageObjectFactory.initPageObject(SelectModePage.class, driver);
	}

	@After
	public void post() {

	}
	
	@Test
	public void TestCase() throws InterruptedException {
		testCase = extent.createTest("To Verify Quick Link with language " + language);
		
		testCase.info("Change to " + language + " language");
		res = mainPage.changeLanguage(language);
		Assertion.isTrue(testCase, res);
		
		testCase.info("Click on button 'All Quick Link'");
		mainPage.clickAllQuickLinks();
		testCase.info(" Click on button 'Add Quick Link'");
		quickLinksPage.clickOnAddQuickLink();
		testCase.info("Verify default elements in Add Quick Link");
		editQuickLinkPage.verifyQuickLinkDefault();
	
		String strWhitespaces = " ";
		String strBlank = "";
		String urlWithoutDomain = "bosch";
		String urlValid = "bosch.com";
		String titleOneCharacter = "a";
		String titleTwoCharacter1 = "a1";
		String titleTwoCharacter2 = "a2";
		String titleTwoCharacter3 = "a3";	
		List<String> expectedContent_warningMessage_url = Arrays.asList("! The entered URL seems to be invalid. Please Check.", "! Die eingegebene URL scheint ungültig zu sein. Bitte prüfen Sie das.");
		List<String> expectedContent_errorMessage_url = Arrays.asList("Please enter a URL.", "Bitte geben Sie eine URL ein.");
		List<String> expectedContent_validMessage_url = Arrays.asList("The entered URL is valid.", "Die eingegebene URL ist gültig.");
		List<String> expectedContent_errorMessage_title1 = Arrays.asList("Please choose a title with at least two characters.", "Bitte wählen Sie einen Titel mit mindestens zwei Zeichen.");
		List<String> expectedContent_errorMessage_title2 = Arrays.asList("Please enter a title.", "Bitte geben Sie einen Titel ein.");
		String expectedColorMessage_orangeCode = "#ffc300";
		String expectedColorMessage_redCode = "#e20015";
		String expectedColorMessage_greenCode = "#78be20";		
		String existedGroupHeader = "Common Applications";
		String newGroupTitle1 = "New Group Title 1";
		String newGroupTitle2 = "New Group Title 2";
		String newGroupTitle3 = "New Group Title 3";		
		String selectColorQL = "red";
		
		testCase.info("Verify URL with whitespaces");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(strWhitespaces));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_warningMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_orangeCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		testCase.info("Verify URL as blank");
		Assertion.isTrue(testCase, editQuickLinkPage.sendkeyURL(Keys.BACK_SPACE));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_errorMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify invalid URL ");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(urlWithoutDomain));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_warningMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_orangeCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify valid URL");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(urlValid));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_validMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_greenCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));			
		testCase.info("Verify title with whitespaces");
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strWhitespaces));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify blank title");
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strBlank));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title2));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify title with 1 character");
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleOneCharacter));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify title >= 2 characters");
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleTwoCharacter1));
		Assertion.isTrue(testCase, editQuickLinkPage.isTitleMessageNotDisplayed());
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", true));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify initial state of new quick link in an existed group");
		Assertion.isTrue(testCase, editQuickLinkPage.clickAddNewQuickLink_ungroup());
		
		testCase.info(" Click on button 'Add Quick Link'");
		quickLinksPage.clickOnAddQuickLink();
		testCase.info("Repeat to test valid url, title with whitespaces, blank title, title with 1 character, title >= 2 characters => with select the existed group");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(urlValid));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_validMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_greenCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strWhitespaces));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strBlank));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title2));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleOneCharacter));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleTwoCharacter2));
		Assertion.isTrue(testCase, editQuickLinkPage.isTitleMessageNotDisplayed());
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", true));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		testCase.info("Verify initial state of new quick link in an existed group");
		Assertion.isTrue(testCase, editQuickLinkPage.clickAddNewQuickLink_selectExistedGroup(existedGroupHeader));
		
		testCase.info(" Click on button 'Add Quick Link'");
		quickLinksPage.clickOnAddQuickLink();
		testCase.info("Repeat to test valid url, title with whitespaces, blank title, title with 1 character, title >= 2 characters => with create new group");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(urlValid));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_validMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_greenCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strWhitespaces));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strBlank));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title2));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleOneCharacter));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleTwoCharacter3));
		Assertion.isTrue(testCase, editQuickLinkPage.switchToNewGroup());
		Assertion.isTrue(testCase, editQuickLinkPage.isTitleMessageNotDisplayed());
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitleNewGroup(newGroupTitle1));
		Assertion.isTrue(testCase, editQuickLinkPage.isTitleNewGroupMessageNotDisplayed());
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("add", true));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify initial state of new quick link in a new created group");
		Assertion.isTrue(testCase, editQuickLinkPage.clickAddNewQuickLink_createNewGroup());
		
		testCase.info("Verify initial state of Edit mode form");
		Assertion.isTrue(testCase, editQuickLinkPage.chooseIconMenu_firstQuickLink_ungroup());
		testCase.info("Verify URL with whitespaces");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(strWhitespaces));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_warningMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_orangeCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify URL as blank");
		Assertion.isTrue(testCase, editQuickLinkPage.sendkeyURL(Keys.BACK_SPACE));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_errorMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify invalid URL ");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(urlWithoutDomain));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_warningMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_orangeCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify valid URL");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(urlValid));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_validMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_greenCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", true));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));			
		testCase.info("Verify title with whitespaces");
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strWhitespaces));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify blank title");
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strBlank));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title2));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify title with 1 character");
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleOneCharacter));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify title >= 2 characters");
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleTwoCharacter1));
		Assertion.isTrue(testCase, editQuickLinkPage.isTitleMessageNotDisplayed());
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", true));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		testCase.info("Select " + selectColorQL + " color");
		Assertion.isTrue(testCase, editQuickLinkPage.selectQLColor(selectColorQL));
		testCase.info("Verify initial state of individually quick link");
		Assertion.isTrue(testCase, editQuickLinkPage.clickSaveEditQuickLink_ungroup(selectColorQL));
		
		testCase.info("Verify initial state of quick link in an existed group");
		Assertion.isTrue(testCase, editQuickLinkPage.chooseIconMenu_firstQuickLink_ungroup());
		testCase.info("Repeat to test valid url, title with whitespaces, blank title, title with 1 character, title >= 2 characters => with select the existed group");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(urlValid));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_validMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_greenCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", true));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strWhitespaces));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strBlank));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title2));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleOneCharacter));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleTwoCharacter1));
		Assertion.isTrue(testCase, editQuickLinkPage.isTitleMessageNotDisplayed());
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", true));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		testCase.info("Verify initial state of quick link in an existed group");
		Assertion.isTrue(testCase, editQuickLinkPage.clickSaveEditQuickLink_selectExistedGroup(existedGroupHeader));
		
		testCase.info("Verify initial state of quick link in a new created group");
		Assertion.isTrue(testCase, editQuickLinkPage.chooseIconMenu_firstQuickLink_viaGroupTitle(newGroupTitle1));
		testCase.info("Repeat to test valid url, title with whitespaces, blank title, title with 1 character, title >= 2 characters => with create new group");
		Assertion.isTrue(testCase, editQuickLinkPage.InputURL(urlValid));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("url", expectedContent_validMessage_url));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("url", expectedColorMessage_greenCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", true));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strWhitespaces));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(strBlank));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title2));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleOneCharacter));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyContentOfMessage("title", expectedContent_errorMessage_title1));
		Assertion.isTrue(testCase, editQuickLinkPage.verifyColorOfMessage("title", expectedColorMessage_redCode));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitle(titleTwoCharacter3));
		Assertion.isTrue(testCase, editQuickLinkPage.switchToNewGroup());
		Assertion.isTrue(testCase, editQuickLinkPage.isTitleMessageNotDisplayed());
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", false));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));
		Assertion.isTrue(testCase, editQuickLinkPage.InputTitleNewGroup(newGroupTitle2));
		Assertion.isTrue(testCase, editQuickLinkPage.isTitleNewGroupMessageNotDisplayed());
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("save", true));
		Assertion.isTrue(testCase, editQuickLinkPage.isButtonEnabled("cancel", true));	
		testCase.info("Verify initial state of new quick link in a new created group");
		Assertion.isTrue(testCase, editQuickLinkPage.clickSaveEditQuickLink_createNewGroup());
		
		testCase.info("Change to select mode");
		Assertion.isTrue(testCase, quickLinksPage.changeToSelectMode());
		Assertion.isTrue(testCase, selectModePage.isButtonEnabled("create group", false));
		Assertion.isTrue(testCase, selectModePage.isButtonEnabled("delete", false));
		Assertion.isTrue(testCase, selectModePage.verifyNumberSelectedOnHeader(0));
		
		testCase.info("Select 1 quicklink");
		Assertion.isTrue(testCase, selectModePage.selectQL_viaGroupAndTitle(newGroupTitle2, titleTwoCharacter3));
		Assertion.isTrue(testCase, selectModePage.verifyAllIconsSelectGroupDeactived());
		Assertion.isTrue(testCase, selectModePage.isButtonEnabled("create group", true));
		Assertion.isTrue(testCase, selectModePage.isButtonEnabled("delete", true));
		Assertion.isTrue(testCase, selectModePage.verifyNumberSelectedOnHeader(1));
		
		testCase.info("Select 2 more quicklinks");
		Assertion.isTrue(testCase, selectModePage.selectQL_viaGroupAndTitle(existedGroupHeader, titleTwoCharacter1));
		Assertion.isTrue(testCase, selectModePage.selectQL_viaGroupAndTitle(existedGroupHeader, titleTwoCharacter2));
		Assertion.isTrue(testCase, selectModePage.verifyAllIconsSelectGroupDeactived());
		Assertion.isTrue(testCase, selectModePage.isButtonEnabled("create group", true));
		Assertion.isTrue(testCase, selectModePage.isButtonEnabled("delete", true));
		Assertion.isTrue(testCase, selectModePage.verifyNumberSelectedOnHeader(3));
		
		testCase.info("Create new group with selected items before");
		Assertion.isTrue(testCase, selectModePage.createGroupAfterSelectedQLs(newGroupTitle3));
		List<String> lstExpectedTitle = Arrays.asList(titleTwoCharacter1, titleTwoCharacter2, titleTwoCharacter3);
		Assertion.isTrue(testCase, quickLinksPage.verifyAllQLsInGroup(newGroupTitle3, lstExpectedTitle));
		
		testCase.info("Delete 1 quicklink");
		Assertion.isTrue(testCase, quickLinksPage.changeToSelectMode());
		Assertion.isTrue(testCase, selectModePage.selectQL_viaGroupAndTitle(newGroupTitle3, titleTwoCharacter1));
		Assertion.isTrue(testCase, selectModePage.deleteQLsAfterSelectedQLs());
		List<String> lstDeleteQLs = Arrays.asList(titleTwoCharacter1);
		Assertion.isTrue(testCase, quickLinksPage.verifyListDeletedQLs_notDisplay(lstDeleteQLs));
		
		testCase.info("Delete multi quicklinks");
		Assertion.isTrue(testCase, quickLinksPage.changeToSelectMode());
		Assertion.isTrue(testCase, selectModePage.selectQL_viaGroupAndTitle(newGroupTitle3, titleTwoCharacter2));
		Assertion.isTrue(testCase, selectModePage.selectQL_viaGroupAndTitle(newGroupTitle3, titleTwoCharacter3));
		Assertion.isTrue(testCase, selectModePage.deleteQLsAfterSelectedQLs());
		lstDeleteQLs = Arrays.asList(titleTwoCharacter2, titleTwoCharacter3);
		Assertion.isTrue(testCase, quickLinksPage.verifyListDeletedQLs_notDisplay(lstDeleteQLs));
		
		testCase.info("Delete group test");
		Assertion.isTrue(testCase, quickLinksPage.deleteGroup_viaGroupTitle(newGroupTitle1));
		Assertion.isTrue(testCase, quickLinksPage.deleteGroup_viaGroupTitle(newGroupTitle2));
		Assertion.isTrue(testCase, quickLinksPage.deleteGroup_viaGroupTitle(newGroupTitle3));
		testCase.info("Delete quick link test");
	}
}
