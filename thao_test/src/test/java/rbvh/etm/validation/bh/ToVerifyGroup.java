package rbvh.etm.validation.bh;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.bosch.validation.bh.pages.AddGroupPage;
import com.bosch.validation.bh.pages.MainPage;
import com.bosch.validation.bh.pages.QuickLinksPage;
import com.bosch.validation.fw.Assertion;
import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObjectFactory;
import com.bosch.validation.fw.setup.TestCase;

@RunWith(Parameterized.class)
public class ToVerifyGroup extends TestCase {
	Result res;
	MainPage mainPage;
	QuickLinksPage quickLinksPage;
	AddGroupPage addGroupPage;
	String language;
	
	public ToVerifyGroup(String language) {
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
		new ToVerifyGroup(language);
		factory.connectTo();
		res = new Result();
		mainPage = PageObjectFactory.initPageObject(MainPage.class, driver);
		quickLinksPage = PageObjectFactory.initPageObject(QuickLinksPage.class, driver);
		addGroupPage = PageObjectFactory.initPageObject(AddGroupPage.class, driver);
	}

	@After
	public void post() {

	}

	@Test
	public void TestCase() {
		testCase = extent.createTest("To Verify Group with language " + language);
		
		testCase.info("Change to " + language + " language");
		res = mainPage.changeLanguage(language);
		Assertion.isTrue(testCase, res);
		
		List<String> expectedContent_errorMessage_title = Arrays.asList("Please choose a title with at least two characters.", "Bitte wählen Sie einen Titel mit mindestens zwei Zeichen.");	
		
		testCase.info("Click on button 'All Quick Link'");
		mainPage.clickAllQuickLinks();
	    res = quickLinksPage.IsMyWorkspaceDisplayed();
	    Assert.assertTrue(res.getMessage(), res.isResult());
	     
		testCase.info("Click on Add Group button");    
		res = quickLinksPage.ClickAddGroup();
	    Assert.assertTrue(res.getMessage(), res.isResult());
		
		testCase.info("Enter whitespaces for [Title] field");
	    res = addGroupPage.AddGroup(" ","", "Add",expectedContent_errorMessage_title);
	    Assert.assertTrue(res.getMessage(), res.isResult());
	          
		testCase.info("Enter only 1 character for [Title] field");
		quickLinksPage.ClickAddGroup();
	    res = addGroupPage.AddGroup("t","", "Add",expectedContent_errorMessage_title);
	    Assert.assertTrue(res.getMessage(), res.isResult());
	
		testCase.info("Enter valid characters for [Title] field");
		quickLinksPage.ClickAddGroup();
	    res = addGroupPage.AddGroup("groupname1","dark-green", "Add", null);
	    Assert.assertTrue(res.getMessage(), res.isResult());

		testCase.info("Click Add");
		quickLinksPage.ClickAddGroup();
		addGroupPage.ClickPredefinedGroup();
	    res = addGroupPage.IsPredefinedGroupDisplayed();
	    Assert.assertTrue(res.getMessage(), res.isResult());
	    
		testCase.info("Create Predefined Group");	     
	    res = addGroupPage.AddPredefinedGroup("Mobile Devices");
	    Assert.assertTrue(res.getMessage(), res.isResult());
  	    
		testCase.info("Edit group");
		testCase.info("Enter whitespaces for [Title] field");
	    res = addGroupPage.EditGroup("groupname1"," ","", expectedContent_errorMessage_title, "Edit");
	    Assert.assertTrue(res.getMessage(), res.isResult());
	          
	    testCase.info("Enter only 1 character for [Title] field");
	    res = addGroupPage.EditGroup("groupname1","t","", expectedContent_errorMessage_title, "Edit");
	    Assert.assertTrue(res.getMessage(), res.isResult());
	     
	    testCase.info("Enter valid characters for [Title] field");
	    res = addGroupPage.EditGroup("groupname1","editgroupname1","magenta", null, "Edit");
	    Assert.assertTrue(res.getMessage(), res.isResult());
	     
	    testCase.info("Delete predefined group using Delete option");
	    res = addGroupPage.DeleteGroup("Mobile Devices");
	    Assert.assertTrue(res.getMessage(), res.isResult());
     
	    testCase.info("Click on Select Mode");
	    quickLinksPage.changeToSelectMode();
	    String groupnamelist[]= {"editgroupname1"};
	     
	    res = addGroupPage.IsSelectModeDisplayed(groupnamelist);
	    Assert.assertTrue(res.getMessage(), res.isResult());
	     
	    testCase.info("Delete group using Select Mode");
	    res = addGroupPage.DeleteSelectModeGroup(groupnamelist[0]);
	    Assert.assertTrue(res.getMessage(), res.isResult());
	          
	}
}
