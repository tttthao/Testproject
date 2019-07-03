package com.bosch.validation.fw.setup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestName;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bosch.validation.fw.utils.JodaUtils;

/**
 * Base JUnit TestCase class that offers {@link PageObjectFactory} and
 * {@link WebDriver} lifecycle management as well as an automatic Screenshot on
 * failure functionality.
 */
public class TestCase {
	/** Target directory for screenshots and page markup */
	public final static String REPORT_DIR = "reports/screen-shot/";
	public final static String SCREENSHOT_POSTFIX = "-screenshot.png";
	public final static String PAGESOURCE_POSTFIX = "-pagesource.html";
	public static WebDriver driver = null;
	public static PageObjectFactory factory = null;

	public static ExtentReports extent;
	public ExtentTest testCase;
	
	private static final Logger log = LogManager.getLogger(TestCase.class);
	
	private static void prepareSikuliEnvironment() {
		try {
			String jvm = System.getProperty("sun.arch.data.model");
			File srcFile = null;
			if (jvm.equals("32")) {
				srcFile = new File(String.format("src/test/resources/libs32"));
			} else {
				srcFile = new File(String.format("src/test/resources/libs64"));
			}
			File desFile = new File(String.format("%s/AppData/Roaming/Sikulix/SikulixLibs_201510081336",
					System.getProperty("user.home")));

			if (!desFile.exists()) {
				FileUtils.copyDirectory(srcFile, desFile);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public static void before_initFactory() {
		try {			
			initDetailReport("BGN - StartPage");
			
			prepareSikuliEnvironment();
			
			factory = PageObjectFactory.initWebDriver();
			driver = factory.getDriver();

			// Setting for the implicit wait to wait for webdriver find element
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void after_quitFactory() {

		driver.quit();
		factory = null;
		driver = null;

		try {
			if (System.getProperty(PageObjectFactory.PROPS_BROWSER, PageObjectFactory.PROPS_BROWSER_DEFAULT)
					.equals("CHROME")) {
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			} else if (System.getProperty(PageObjectFactory.PROPS_BROWSER, PageObjectFactory.PROPS_BROWSER_DEFAULT)
					.equals("IE")) {
				Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		extent.flush();
	}

	//@Rule
	//public ScreenshotOnFailureRule screenshotRule = new ScreenshotOnFailureRule();

	@Rule
	public TestName testName = new TestName();

	public class ScreenshotOnFailureRule implements MethodRule {
		public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
			return new Statement() {
				@Override
				public void evaluate() throws Throwable {
					try {
						log.info(String.format("Test Case name - %s", frameworkMethod.getName()));

						statement.evaluate();
						
						log.info("DONE");
					} catch (org.junit.internal.AssumptionViolatedException a) {
						log.error("TEST IGNORED: " + frameworkMethod.getName() + "! " + a.getClass().getName()
								+ ": " + a.getMessage());
						throw a;
					} catch (Throwable t) {
						long time = System.currentTimeMillis();
						final String methodname = frameworkMethod.getName();
						final String filename = generateFileName(methodname, time, SCREENSHOT_POSTFIX);
						//final String filename_pagesource = generateFileName(methodname, time, PAGESOURCE_POSTFIX);
						screenshotWriteFile(filename);
						// pageSourceWriteFile(filename_pagesource);
						// driver.getPageSource()
						System.out.println(
								"TEST FAILED: " + methodname + "! " + t.getClass().getName() + ": " + t.getMessage());
						System.out.println("SCREENSHOT: " + REPORT_DIR + filename);
						//System.out.println("HTML page source has been written to: " + REPORT_DIR + filename_pagesource);
						t.printStackTrace(System.out);

						// rethrow to allow the failure to be reported to JUnit
						throw t;
					}
				}

				private String generateFileName(final String methodName, long time, String postfix) {
					return methodName + "-" + time + postfix;
				}

				public void screenshotWriteFile(String fileName) {
					try {
						new File(REPORT_DIR).mkdirs();
						String linkImage = REPORT_DIR + fileName;
						FileOutputStream out = new FileOutputStream(REPORT_DIR + fileName);
						out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
						out.close();
						
						testCase.addScreenCaptureFromPath("../" + linkImage);
					} catch (Exception e) {
						System.out.println("Screenshot write failed! " + e.getMessage());
					}
				}
			};
		}
	}
	
	public static void initDetailReport(String name) {		
		if (extent == null) {
			File report = new File(String.format("%s/reports", new File("").getAbsolutePath(), name));
			if (!report.exists()) {
				report.mkdirs();
			}

			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(String.format("%s/reports/%s-%s.html",
					new File("").getAbsolutePath(), name, JodaUtils.getDateTimeToString()));
			htmlReporter.config().setDocumentTitle(String.format("Automation - Test Case"));
			htmlReporter.config().setReportName(String.format("Automation - Test Case"));
			htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
			htmlReporter.config().setTheme(Theme.STANDARD);

			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setReportUsesManualConfiguration(true);
		}		
	}
}