package rbvh.etm.validation.bh;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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
public class TC1669_ToVerifyEnterpriseSearch extends TestCase {
	Result res;
	MainPage mainPage;
	EnterpriseSearchPage enterpriseSearchPage;
	MyWorkspacePage myWorkspacePage;
	SettingsPage settingsPage;
	QuickLinksPage quickLinksPage;
	String language;
	
	public TC1669_ToVerifyEnterpriseSearch(String language) {
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
		new TC1669_ToVerifyEnterpriseSearch(language);
		factory.connectTo();
		res = new Result();
		mainPage = PageObjectFactory.initPageObject(MainPage.class, driver);
		enterpriseSearchPage = PageObjectFactory.initPageObject(EnterpriseSearchPage.class, driver);
		myWorkspacePage = PageObjectFactory.initPageObject(MyWorkspacePage.class, driver);
		quickLinksPage = PageObjectFactory.initPageObject(QuickLinksPage.class, driver);
		settingsPage = PageObjectFactory.initPageObject(SettingsPage.class, driver);
	}

	@After
	public void post() {

	}
	
	@Test
	public void TestCase() throws InterruptedException {
		testCase = extent.createTest("Verify Enterprise Search with language " + language);
		
		testCase.info("Change to " + language + " language");
		res = mainPage.changeLanguage(language);
		Assertion.isTrue(testCase, res);
		
		String strSearch1 = "Änderungsschein";
		String strSearch2 = "ß";
		String strSearch3 = "*a";
		String strSearch4 = "Çali";
		String strSearch5 = "Üm";
		String strSearch6 = "ångström";
		//String strSearch7 = "Schließen"; not available for search suggestion
		String strSearch7 = "Schließ";
		String strSearch8 = "añ";
		
		testCase.info("Click on button Enterprise Search");
		res = mainPage.clickEnterpriseSearch();
		Assertion.isTrue(testCase, res);

		testCase.info("Verify Enterprise Search displaying");
		res = enterpriseSearchPage.IsEnterpriseSearchDisplayed();
		Assertion.isTrue(testCase, res);
		
		testCase.info("Verify Search suggestion box displaying");
		res = enterpriseSearchPage.doSearch_verifySuggestion(strSearch1);
		Assertion.isTrue(testCase, res);
		
		testCase.info("Verify Search Result page and list recently search");
		res = enterpriseSearchPage.chooseFirstSuggestionAfterSearch(strSearch1);
		Assertion.isTrue(testCase, res);

		testCase.info("Search by search button");
		res = enterpriseSearchPage.doSearch_byIcon(strSearch2);
		Assertion.isTrue(testCase, res);
	
		testCase.info("Verify Search suggestion box displaying");
		res = enterpriseSearchPage.doSearch_verifySuggestion(strSearch3);
		Assertion.isTrue(testCase, res);
		
		testCase.info("Cancel Search");
		res = enterpriseSearchPage.cancelSearch();
		Assertion.isTrue(testCase, res);

		testCase.info("Search by link SeeAllResults");
		res = enterpriseSearchPage.doSearch_byLinkSeeAllResults(strSearch4);
		Assertion.isTrue(testCase, res);

		testCase.info("Verify Search suggestion box displaying");
		res = enterpriseSearchPage.doSearch_verifySuggestion(strSearch5);
		Assertion.isTrue(testCase, res);
		
		testCase.info("Verify Search Result page and list recently search");
		String actualStringSearch5 = enterpriseSearchPage.getActualSearchFromSuggestion();
		res = enterpriseSearchPage.chooseFirstSuggestionAfterSearch(strSearch5);
		Assertion.isTrue(testCase, res);

		testCase.info("Search by search button");
		res = enterpriseSearchPage.doSearch_byIcon(strSearch6);
		Assertion.isTrue(testCase, res);
	
		testCase.info("Verify Search suggestion box displaying");
		res = enterpriseSearchPage.doSearch_verifySuggestion(strSearch7);
		Assertion.isTrue(testCase, res);
		
		testCase.info("Cancel Search");
		res = enterpriseSearchPage.cancelSearch();
		Assertion.isTrue(testCase, res);

		testCase.info("Search by link SeeAllResults");
		res = enterpriseSearchPage.doSearch_byLinkSeeAllResults(strSearch8);
		Assertion.isTrue(testCase, res);
		
		testCase.info("Verify maximum list MyLatestSearches");
		List<String> expectedMyLatestSearches = Arrays.asList(strSearch8, strSearch6, actualStringSearch5, strSearch4, strSearch2);
		res = enterpriseSearchPage.verifyMaximumListLatestSearches(expectedMyLatestSearches);
		Assertion.isTrue(testCase, res);
		
		testCase.info("Add all LatestSearches to FavoriteSearches");
		res = enterpriseSearchPage.addAllLatestSearches_toFavoriteSearches();
		Assertion.isTrue(testCase, res);
		
		testCase.info("Remove a FavoriteSearches");
		res = enterpriseSearchPage.removeSearchTermInFavoriteSearches();
		Assertion.isTrue(testCase, res);
		
		testCase.info("Expand settings of MyLatestSearches");
		res = enterpriseSearchPage.expandSettingsOfMyLatestSearches();
		Assertion.isTrue(testCase, res);
		
		testCase.info("Delete all latest searches history");
		res = enterpriseSearchPage.deleteAllLatestSearchesHistory();
		Assertion.isTrue(testCase, res);
		
		testCase.info("Expand settings of MyLatestSearches");
		res = enterpriseSearchPage.expandSettingsOfMyFavoriteSearches();
		Assertion.isTrue(testCase, res);
		
		testCase.info("Delete all latest searches history");
		res = enterpriseSearchPage.deleteAllFavoriteSearchesHistory();
		Assertion.isTrue(testCase, res);
	}
}