package com.bosch.validation.fw;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.bosch.validation.fw.model.Result;

public class Assertion {
	
	public static void isTrue(ExtentTest testCase, Result res) {
		if (res.isResult()) {
			testCase.pass(res.getMessage());
		} else {
			testCase.fail(res.getMessage());
			Assert.assertTrue(res.getMessage(), res.isResult());
		}
	}
	
	public static void isTrue(String positiveFeedback, String negativeFeedback, ExtentTest testCase, Result res) {
		if (res.isResult()) {
			testCase.pass(positiveFeedback);
		} else {
			testCase.fail(negativeFeedback);
			Assert.assertTrue(negativeFeedback, res.isResult());
		}
	}
	
	public static void isTrue(String customMessage, ExtentTest testCase, Result res) {
		if (res.isResult()) {
			testCase.pass(res.getMessage());
		} else {
			testCase.fail(customMessage);
			Assert.assertTrue(customMessage, res.isResult());
		}
	}
	
	public static void isTrue(WebDriver driver, String customMessage, ExtentTest testCase, Result res) {
		if (res.isResult()) {
			testCase.pass(res.getMessage());
		} else {
			testCase.fail(customMessage);
			screenshotWriteFile(driver, testCase);
			Assert.assertTrue(customMessage, res.isResult());
		}
	}
	
	public static void isFalse(ExtentTest testCase, Result res) {
		if (!res.isResult()) {
			testCase.pass(res.getMessage());
		} else {
			testCase.fail(res.getMessage());
			Assert.assertFalse(res.getMessage(), res.isResult());
		}
	}
	
	public final static String REPORT_DIR = "reports/screen-shot/";
	
	private static void screenshotWriteFile(WebDriver driver, ExtentTest testCase) {
		try {
			long time = System.currentTimeMillis();
			
			new File(REPORT_DIR).mkdirs();
			String linkImage = REPORT_DIR + time + ".png";
			FileOutputStream out = new FileOutputStream(linkImage);
			out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
			out.close();
			
			testCase.addScreenCaptureFromPath("../" + linkImage);
		} catch (Exception e) {
			System.out.println("Screenshot write failed! " + e.getMessage());
		}
	}
}
