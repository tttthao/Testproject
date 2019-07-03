package com.bosch.validation.fw.setup;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bosch.validation.bh.ESiteUnderTest;
import com.bosch.validation.bh.SiteUnderTest;
import com.bosch.validation.fw.model.Result;


/**
 * Central WebTest Framework PageObjectFactory.
 */
public class PageObjectFactory {


    public static final String PROPS_TIMEOUT = "timeout";
    public static final String PROPS_TIMEOUT_DEFAULT = "1";
    public static final String PROPS_REMOTEDRIVER_URL = "bosch.environment.webtest.remotewebdriver.url";

    public static final String PROPS_BROWSER = "browser.name";
    public static final String PROPS_BROWSER_DEFAULT = "FIREFOX";

    public static final String PROPS_SYSTEM = "target.system";
    public static final String PROPS_SYSTEM_DEFAULT = "Q";

    protected final WebDriver driver;
    protected static final int timeout = retrieveTimeoutValue();
    
    public List<String> testName = new ArrayList<>();
    public List<Result> testResult = new ArrayList<>();

	private static final Logger log = LogManager.getLogger(PageObjectFactory.class);

    /**
     * private constructor!
     *
     * @param driver
     *            the webdriver
     * @param timeout
     *            timeout value in seconds
     */
    private PageObjectFactory(WebDriver driver, int timeout) {
        super();
        this.driver = driver;
    }

    public static PageObjectFactory initWebDriver() {
        WebDriver driver = null;
        
        final String props_url = System.getProperty(PROPS_REMOTEDRIVER_URL);
        final String props_browser = System.getProperty(PROPS_BROWSER, PROPS_BROWSER_DEFAULT);

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName(props_browser);

        // add auto proxy configuration for jenkins selenium execution so that it is not blocked
        Proxy pr = new Proxy();
        pr.setProxyAutoconfigUrl("http://rbins.bosch.com/fe.pac");
        cap.setCapability(CapabilityType.PROXY, pr);

        if (props_url != null) {
            try {
                driver = new RemoteWebDriver(new URL(props_url), cap);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        if (driver == null) {
        	if (props_browser.equals("IE")) {
        		System.setProperty("webdriver.ie.driver", getIEDriver());
				try {
					cap.setCapability("nativeEvents", true);
					driver = new InternetExplorerDriver();
				} catch (SessionNotCreatedException e) {

					try {
						try {
							Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
								true);
						cap.setCapability("nativeEvents", true);

						driver = new InternetExplorerDriver();
					} catch (SessionNotCreatedException e1) {
						e1.printStackTrace();

						try {
							Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
						} catch (IOException e3) {
							e3.printStackTrace();
						}
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e2) {
							e2.printStackTrace();
						}
					}
				}
        	} else if (props_browser.equals("CHROME")) {
        		System.setProperty("webdriver.chrome.driver", getChromerDriver());
				driver = new ChromeDriver();
        	} else {
        		System.setProperty("webdriver.gecko.driver",getGeckoDriver());
        		
				cap.setCapability("marionette", false);

				FirefoxOptions ffOption = new FirefoxOptions(cap);
				ffOption.addPreference("network.automatic-ntlm-auth.trusted-uris", "bosch.com,bosch.de");
				ffOption.addPreference("network.negotiate-auth.delegation-uris", "bosch.com,bosch.de");
				ffOption.addPreference("network.negotiate-auth.trusted-uris", "bosch.com,bosch.de");

				driver = new FirefoxDriver(ffOption);    			
        	}
        }

        return new PageObjectFactory(driver, timeout);
    }

    private static int retrieveTimeoutValue() {
        int timeout = 10;
        final String props_timeout = System.getProperty(PROPS_TIMEOUT, PROPS_TIMEOUT_DEFAULT);
        try {
            timeout = Integer.parseInt(props_timeout);
        } catch (NumberFormatException e1) {
            // ignore and use default
            System.out.println("Illegal value for " + PROPS_TIMEOUT + ": " + props_timeout
                    + ". Using default value instead. Exception: " + e1.getMessage());
        }
        return timeout;
    }

    public void quit() {
        driver.quit();
    }    
	
	public void connectTo(String url) {
		driver.navigate().to(url);
	}
	
	public void connectTo() {
		String system = System.getProperty(PROPS_SYSTEM, PROPS_SYSTEM_DEFAULT);
		switch (ESiteUnderTest.valueOf(system)) {
		case P:
			driver.navigate().to(SiteUnderTest.P);
			break;
		default:
			driver.navigate().to(SiteUnderTest.Q);
			break;
		}
	}

    public <P extends PageObject> P initPageObject(Class<P> pageObjectClass) {
        final P result = PageFactory.initElements(driver, pageObjectClass);
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return result.isReady();
            }
        });
        return result;
    }

    public static <P extends PageObject> P initPageObject(Class<P> pageObjectClass, WebDriver driver) {
        final P result = PageFactory.initElements(driver, pageObjectClass);
        try {
			(new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
			    public Boolean apply(WebDriver d) {
			        return result.isReady();
			    }
			});
	        return result;
		} catch (Exception e) {
			log.info(String.format("Can't init page [%s] success", pageObjectClass.getName()));
			return null;
		}
    }

    public WebDriver getDriver() {
        return driver;
    }

    public int getTimeout() {
        return timeout;
    }

    public static WebDriverWait doWait(WebDriver driver) {
        return new WebDriverWait(driver, timeout);
    }

    public static String getChromerDriver() {
		return new File("").getAbsolutePath() + "/src/test/resources/driver/chromedriver.exe";
	}

	public static String getIEDriver() {
		return new File("").getAbsolutePath() + "/src/test/resources/driver/IEDriverServer.exe";
	}
	
	public static String getGeckoDriver() {
		return new File("").getAbsolutePath() + "/src/test/resources/driver/geckodriver.exe";
	}

}