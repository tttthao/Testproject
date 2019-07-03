package com.bosch.validation.fw.setup;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.bosch.validation.fw.utils.JodaUtils;

abstract public class PageObjectSkeleton {

	public final static String IMAGE_DIR = "reports/screen-shot/";

	private Screen screen = new Screen();
	private static final Logger log = LogManager.getLogger(PageObjectSkeleton.class);

	public boolean waitForElementDisplay(WebElement webElement, int timeout) {
		for (int i = 0; i < timeout; i++) {
			try {
				webElement.isDisplayed();
				return true;
			} catch (Exception e) {
				log.info(String.format("DEBUG: WAITING FOR '%s' DISPLAY - [%s] secs", webElement, i));
				sleep(1000);
			}
		}
		return false;
	}

	public boolean waitForElementDisappear(WebElement webElement, int timeout) {
		for (int i = 0; i < timeout / 1; i++) {
			try {
				log.info(String.format("DEBUG: WAITING FOR '%s' DISAPPEAR - [%s] secs", webElement, i));
				webElement.isDisplayed();
				sleep(1000);
			} catch (Exception e) {
				return true;
			}
		}
		return false;
	}

	public boolean waitForWebElementAttribute(WebElement webElement, String attribute, String value, int timeout) {
		log.info("Wait for element attributes");
		for (int i = 0; i < timeout; i++) {
			try {
				if (webElement.getAttribute(attribute) == null && webElement.getAttribute(attribute) == value) {
					return true;
				} else if (webElement.getAttribute(attribute).equals(value)) {
					return true;
				}
			} catch (Exception e) {
				sleep(1000);
			}
		}
		return false;
	}

	public WebElement getWebElementByText(List<WebElement> items, String text) {
		for (WebElement temp : items) {
			if (temp.getText().equals(text)) {
				return temp;
			}
		}
		return null;
	}

	public boolean waitForTrue(Boolean condition, int timeout) {
		for (int i = 0; i < timeout; i++) {
			log.info(String.format("WAIT FOR CONDITION true - [%s]", condition));
			try {
				if (condition) {
					return true;
				} else {
					sleep(1000);
				}
			} catch (Exception e) {
				sleep(1000);
			}
		}
		return false;
	}

	public WebElement getWebTableCell(WebElement webTable, int colIndex, String textMatch) {
		try {
			List<WebElement> rows = webTable.findElements(By.xpath("tbody/tr"));
			for (WebElement row : rows) {
				WebElement cell = row.findElement(By.xpath(String.format("td[%s]", colIndex)));
				if (cell.getText().equals(textMatch)) {
					return cell;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement getWebTableRow(WebElement webTable, int rowIndex) {
		List<WebElement> rows = webTable.findElements(By.xpath("tbody/tr"));
		return rows.get(rowIndex);
	}

	public WebElement getWebElementInTableCell(WebElement webTable, String textMatch, String xPath) {
		try {
			for (int i = 0; i < 60; i++) {
				List<WebElement> rows = webTable.findElements(By.xpath("tbody/tr"));
				for (WebElement row : rows) {
					if (row.getText().contains(textMatch)) {
						return row.findElement(By.xpath(xPath));
					}
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void moveToElementAndClick(WebDriver driver, WebElement element) {
		Actions mouseOver = new Actions(driver);
		mouseOver.moveToElement(element).click().build().perform();
	}

	public boolean deviceType(String sendValue, int thinkTime, ExtentTest testCase) {
		testCase.info(String.format("[KEYBOARD]: %s", sendValue.toString()));
		screen.type(sendValue);
		sleep(thinkTime);
		return true;
	}

	public boolean deviceSendKeys(String sendValue, int thinkTime) {
		sleep(thinkTime);
		screen.type(sendValue);
		sleep(thinkTime);
		return true;
	}

	public boolean deviceWebElementDisplay(WebDriver driver, WebElement webElement, int thinkTime) {
		try {

			String temp = capture(driver, webElement);
			screen.exists(temp, thinkTime).highlight(2);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deviceWebElementClick(WebDriver driver, WebElement webElement, int thinkTime) {
		try {
			String temp = capture(driver, webElement);
			screen.exists(temp, thinkTime).highlight(thinkTime);
			screen.click(temp, thinkTime);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deviceWebElementClick(WebDriver driver, WebElement webElement, String fileName, int thinkTime) {
		try {
			String temp = capture(driver, webElement, fileName);
			screen.exists(temp, thinkTime).highlight(thinkTime);
			screen.click(temp, thinkTime);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deviceClick(String fileName, int thinkTime) {
		try {
			String temp = String.format("src/test/img/%s.png", fileName);
			screen.exists(temp, thinkTime).highlight(thinkTime);
			screen.click(temp, thinkTime);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deviceMouseHover(WebDriver driver, WebElement webElement, int thinkTime) {
		try {
			String temp = capture(driver, webElement);
			screen.exists(temp, thinkTime).highlight(thinkTime);
			screen.mouseMove(temp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param timeOut
	 *            milisecond
	 * @return
	 */
	public boolean sleep(int timeOut) {
		try {
			Thread.sleep(timeOut);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isDisplayed(WebElement webElement) {
		try {
			return webElement.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean sendKeys(WebElement webElement, String value) {

		if (isDisplayed(webElement)) {
			webElement.clear();
			webElement.sendKeys(value);

			return true;
		}

		return false;
	}

	public void scrollIntoView(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void clickByJS(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean waitAndClick(WebElement webElement, int timeOut) {
		Boolean preExec = true;
		Boolean isDisplay = waitForElementDisplay(webElement, timeOut);
		int i = 0;

		if (isDisplay) {
			while (preExec && i <= timeOut) {
				try {
					webElement.click();
					preExec = false;
				} catch (Exception e) {
					sleep(1000);
					i = i + 1;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean waitAndSelectVisibleText(WebElement webElement, String visibleText, int timeOut) {
		Boolean isDisplay = waitForElementDisplay(webElement, timeOut);
		if (isDisplay) {
			try {
				Select select = new Select(webElement);
				select.selectByVisibleText(visibleText);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean waitElementTextNotEmpty(WebElement element, int timeoutInSeconds) {
		try {
			String currentText = element.getText();
			int currentTime = 0;
			while (currentText.equals("") && currentTime < timeoutInSeconds) {
				currentText = element.getText();
				currentTime++;
				sleep(1000);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean waitListElementsDisplay(List<WebElement> lstElements, int timeoutInSeconds) {
		try {
			int size = lstElements.size();
			int currentTime = 0;
			while (size == 0 && currentTime < timeoutInSeconds) {
				size = lstElements.size();
				currentTime++;
				sleep(1000);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean waitListElementsDisappear(List<WebElement> lstElements, int timeoutInSeconds) {
		try {
			int size = lstElements.size();
			int currentTime = 0;
			while (size > 0 && currentTime < timeoutInSeconds) {
				size = lstElements.size();
				currentTime++;
				sleep(1000);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String capture(WebDriver driver, WebElement webElement) {
		try {
			/**
			 * Capture entire Screen shot
			 */
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage fullImg = ImageIO.read(screenshot);

			// Get the location of element on the page
			Point point = webElement.getLocation();

			// Get width and height of the element
			int eleWidth = webElement.getSize().getWidth();
			int eleHeight = webElement.getSize().getHeight();

			// Crop the entire page screenshot to get only element screenshot
			BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
			ImageIO.write(eleScreenshot, "png", screenshot);

			// Copy the element screenshot to disk
			String path = String.format("%s%s.png", IMAGE_DIR, JodaUtils.getDateTimeToString());
			File screenshotLocation = new File(path);
			FileUtils.copyFile(screenshot, screenshotLocation);

			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String capture(WebDriver driver, WebElement webElement, String fileName) {
		try {
			/**
			 * Capture entire Screen shot
			 */
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage fullImg = ImageIO.read(screenshot);

			// Get the location of element on the page
			Point point = webElement.getLocation();

			// Get width and height of the element
			int eleWidth = webElement.getSize().getWidth();
			int eleHeight = webElement.getSize().getHeight();

			// Crop the entire page screenshot to get only element screenshot
			BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
			ImageIO.write(eleScreenshot, "png", screenshot);

			// Copy the element screenshot to disk
			String path = String.format("%s%s.png", IMAGE_DIR, fileName);
			File screenshotLocation = new File(path);
			FileUtils.copyFile(screenshot, screenshotLocation);

			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Switch to iframe based on iframe element
	 * 
	 * @throws Exception
	 */
	public void switchToIframe(WebDriver driver, WebElement element) {
		try {
			if (element != null) {
				driver.switchTo().frame(element);
			}

		} catch (Exception e) {

		}
	}

	/**
	 * De switch from iframe
	 * 
	 * @throws Exception
	 */
	public void deSwitch(WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
		}

	}
}