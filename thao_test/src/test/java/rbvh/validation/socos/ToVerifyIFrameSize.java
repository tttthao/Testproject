package rbvh.validation.socos;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bosch.validation.fw.model.Result;
import com.bosch.validation.fw.setup.PageObjectFactory;
import com.bosch.validation.fw.setup.TestCase;
import com.bosch.validation.socos.page.SocosPage;

public class ToVerifyIFrameSize extends TestCase {

	@Before
	public void pre() {
		factory.connectTo();
	}

	@After
	public void post() {

	}

	@Test
	public void TestCase() {
		testCase = extent.createTest("To Check IFrame size in SOCOS");
		
		factory.connectTo(
				"https://inside-q.bosch.com/irj/portal?NavigationTarget=navurl://a2af783ee9086d0624a88d2bc848126c");
		
		SocosPage socos = PageObjectFactory.initPageObject(SocosPage.class, driver);
		Result res = socos.searhSocos(testCase);
		
		Assert.assertTrue(res.getMessage(), res.isResult());		
	}
}
