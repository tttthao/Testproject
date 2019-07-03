package rbvh.etm.validation.bh;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.bosch.validation.bh.pages.EditQuickLinkPage;
import com.bosch.validation.bh.pages.EnterpriseSearchPage;
import com.bosch.validation.bh.pages.MainPage;
import com.bosch.validation.bh.pages.MyWorkspacePage;
import com.bosch.validation.bh.pages.QuickLinksPage;
import com.bosch.validation.bh.pages.SettingsPage;
import com.bosch.validation.fw.Assertion;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObjectFactory;
import com.bosch.validation.fw.setup.TestCase;

@RunWith(Parameterized.class)
public class TC1677_ToVerifySettingFunctions extends TestCase {
	Result res;
	MainPage mainPage;
	EnterpriseSearchPage enterpriseSearchPage;
	SettingsPage settingsPage;
	MyWorkspacePage myWorkspacePage;
	QuickLinksPage quickLinksPage;
	EditQuickLinkPage editQuickLinkPage;
	String strURL;
	String strTitle; 
	String strColorQL; 
	String GroupType;
	String strGroupToSelect;
	String strNewGroupTitle; 
	String strNewGroupColor;
	String strCancelOrAdd;
	String language;//Testing
	
	public TC1677_ToVerifySettingFunctions(String language) {
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
		new TC1677_ToVerifySettingFunctions(language);
		factory.connectTo();
		res = new Result();
		mainPage = PageObjectFactory.initPageObject(MainPage.class, driver);
		enterpriseSearchPage = PageObjectFactory.initPageObject(EnterpriseSearchPage.class, driver);
		settingsPage = PageObjectFactory.initPageObject(SettingsPage.class, driver);
		myWorkspacePage = PageObjectFactory.initPageObject(MyWorkspacePage.class, driver);
		quickLinksPage  = PageObjectFactory.initPageObject(QuickLinksPage.class, driver);
		editQuickLinkPage = PageObjectFactory.initPageObject(EditQuickLinkPage.class, driver);	
	}

	@After
	public void post() {

	}

	@Test
	public void TestCase() throws InterruptedException {
		testCase = extent.createTest("Verity Workspace settings with language " + language);
		
		testCase.info("Change to " + language + " language");
		res = mainPage.changeLanguage(language);
		Assertion.isTrue(testCase, res);
		
		testCase.info("Pre-condition: 1 group, 1 quick link, 1 my latest search term, 1 my favorite search term has been added");
		mainPage.clickAllQuickLinks();
		quickLinksPage.clickOnAddQuickLink();
		String urlQL = "bosch.com";
		String titleQL = "testWorkspaceSettings_titleQL";
		String titleGroup = "testWorkspaceSettings_titleGroup";
		editQuickLinkPage.InputURL(urlQL);
		editQuickLinkPage.InputTitle(titleQL);
		editQuickLinkPage.switchToNewGroup();
		editQuickLinkPage.InputTitleNewGroup(titleGroup);
		editQuickLinkPage.clickAddNewQuickLink_createNewGroup();
		quickLinksPage.closeQuickLinks();		
		String strSearch = "test";
		mainPage.clickEnterpriseSearch();
		enterpriseSearchPage.doSearch_byLinkSeeAllResults(strSearch);
		enterpriseSearchPage.addAllLatestSearches_toFavoriteSearches();
		enterpriseSearchPage.closeEnterpriseSearch();
		
		// Delete latest searches
		Assertion.isTrue(testCase, mainPage.clickEnterpriseSearch());
		testCase.info("Click on (…) at My Workspace header and Click on Settings link");
		Assertion.isTrue(testCase, myWorkspacePage.selectSettingBesideMyWorkspace(testCase));		
		testCase.info("Click on Delete Latest Searches link - [No]");
		Assertion.isTrue(testCase, settingsPage.selectDeleteLatestSearches("no", testCase));
		testCase.info("Click on Delete Latest Searches link - [Yes]");
		Assertion.isTrue(testCase, settingsPage.selectDeleteLatestSearches("yes", testCase));
		List<String> expectedContentMessage_deleteLatestSeaches = Arrays.asList("Latest Searches successfully deleted", "Letzte Suchanfragen erfolgreich gelöscht");
		Assertion.isTrue(testCase, settingsPage.verifyResultMessage(expectedContentMessage_deleteLatestSeaches, testCase));	
		Assertion.isTrue(testCase, settingsPage.NavigateBack(testCase));
		int aft_totalLatestSearches = enterpriseSearchPage.countMyLatestSearches(testCase);
		if (aft_totalLatestSearches != 0) {
			res.setResult(false);
			res.setMessage(String.format("Total latest search [%s] - [%s]", aft_totalLatestSearches,
					"Total of searches must be zero after reset"));
			Assertion.isTrue(testCase, res);
		}
		Assertion.isTrue(testCase, myWorkspacePage.closeMyWorkspace(testCase));
		
		// Reset Quick Link
		Assertion.isTrue(testCase, mainPage.clickAllQuickLinks());
		testCase.info("Click on (…) at My Workspace header and Click on Settings link");
		Assertion.isTrue(testCase, myWorkspacePage.selectSettingBesideMyWorkspace(testCase));
		testCase.info("Reset Quick Links - [No]");
		Assertion.isTrue(testCase, settingsPage.selectResetQuickLinks("no", testCase));
		testCase.info("Reset Quick Links - [Yes]");
		Assertion.isTrue(testCase, settingsPage.selectResetQuickLinks("yes", testCase));
		List<String> expectedContentMessage_resetQuickLinks = Arrays.asList("Quick Links reset successful", "Quick Links erfolgreich zurückgesetzt");
		Assertion.isTrue(testCase, settingsPage.verifyResultMessage(expectedContentMessage_resetQuickLinks, testCase));
		Assertion.isTrue(testCase, settingsPage.NavigateBack(testCase));
		testCase.info("===== Verify quick link is NOT existing in specific group after reset ");
		res = quickLinksPage.isExistInAGroup(titleQL, titleGroup, driver, testCase);
		if(res.isResult()) {
			testCase.fail(String.format("Title of quick link [%s] in group [%s] - [%s]", titleQL, titleGroup , "STILL EXIST IN COMMON APPLICATION QUICK LINKS"));
			Assertion.isTrue(testCase, res);
		}
		Assertion.isTrue(testCase, myWorkspacePage.closeMyWorkspace(testCase));
		
		// Reset Search
		Assertion.isTrue(testCase, mainPage.clickEnterpriseSearch());
		testCase.info("Click on (…) at My Workspace header and Click on Settings link");
		Assertion.isTrue(testCase, myWorkspacePage.selectSettingBesideMyWorkspace(testCase));
		testCase.info("Reset Search - [No]");
		Assertion.isTrue(testCase, settingsPage.selectResetSearchs("no", testCase));
		testCase.info("Reset Search - [Yes]");
		Assertion.isTrue(testCase, settingsPage.selectResetSearchs("yes", testCase));
		List<String> expectedContentMessage_resetSearch = Arrays.asList("Search reset successful", "Suchanfragen erfolgreich zurückgesetzt");
		Assertion.isTrue(testCase, settingsPage.verifyResultMessage(expectedContentMessage_resetSearch, testCase));
		Assertion.isTrue(testCase, settingsPage.NavigateBack(testCase));
		aft_totalLatestSearches = enterpriseSearchPage.countMyLatestSearches(testCase);
		int aft_totalFavoriteSearches = enterpriseSearchPage.countMyFavoriteSearches(testCase);
		if(aft_totalLatestSearches != 0 || aft_totalFavoriteSearches !=0) {
			res.setResult(false);
			testCase.fail(String.format("Total latest search [%s] - Total favorite searches [%s] - [%s]", aft_totalLatestSearches , aft_totalFavoriteSearches, "Total of searches must be zero after reset"));
			Assertion.isTrue(testCase, res);
		}
		Assertion.isTrue(testCase, myWorkspacePage.closeMyWorkspace(testCase));
		
		testCase.info("To verity Setting link at the bottom of page");
		res = mainPage.clickEnterpriseSearch();
		Assertion.isTrue(testCase, res);	
		testCase.info("===== Click on Setting links at the bottom of page");
		res = myWorkspacePage.clickOnSettingLabelAtTheBottom(testCase);
		Assertion.isTrue(testCase, res);	
		testCase.info("===== Verify setting page");
		settingsPage.VerifySettingPage(testCase);	
		testCase.info("===== Navigate back to My Workspace");
		res = settingsPage.NavigateBack(testCase);
		Assertion.isTrue(testCase, res);	
		testCase.info("===== Close My workspace");
		res = myWorkspacePage.closeMyWorkspace(testCase);
		Assertion.isTrue(testCase, res);
	}
}
