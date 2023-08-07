package utility;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporter {
	
	static ExtentReports report = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReportResults.html");
	static ExtentTest test = report.startTest("KYRO QAE Assignment Test Report");
	private static HashMap<String, ExtentTest> tests = new HashMap<String, ExtentTest>();
	
	/**
	 * To form a unique test name in the format
	 * "PackageName.ClassName#MethodName"
	 * 
	 * @param iTestResult
	 * @return String - test name
	 */
	private static String getTestName(ITestResult iTestResult) {
		String testClassName = iTestResult.getTestClass().getRealClass().getName();
		String testMethodName = iTestResult.getName();
		return testClassName + "#" + testMethodName;
	}
	
	/**
	 * To convert milliseconds to Date object
	 * 
	 * @param millis
	 * @return Date
	 */
	@SuppressWarnings("unused")
	private static Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**
	 * To set run status for interrupted tests
	 * 
	 * @param iTestResult
	 * @param extentTest
	 */
	@SuppressWarnings("unused")
	private static void setInterruptedTestStatus(ITestResult iTestResult, ExtentTest extentTest) {
		if (!extentTest.getRunStatus().equals(LogStatus.UNKNOWN)) {
			return;
		}
		switch (iTestResult.getStatus()) {
		case 1:
			extentTest.log(LogStatus.PASS, "<font color=\"green\">Test Passed</font>");
			break;
		case 2:
			if (iTestResult.getThrowable() == null)
				extentTest.log(LogStatus.FAIL, "<font color=\"red\">Test Failed</font>");
			else
				extentTest.log(LogStatus.FAIL, "<div class=\"stacktrace\">" + ExceptionUtils.getStackTrace(iTestResult.getThrowable()) + "</div>");
			break;
		case 3:
			if (iTestResult.getThrowable() == null)
				extentTest.log(LogStatus.SKIP, "<font color=\"orange\">Test Skipped</font>");
			else
				extentTest.log(LogStatus.SKIP, "<div class=\"stacktrace\">" + ExceptionUtils.getStackTrace(iTestResult.getThrowable()) + "</div>");
			break;
		}
	}
	
	/**
	 * Returns an ExtentReports instance if already exists. Creates new and
	 * returns otherwise.
	 * 
	 * @param iTestResult
	 * @return {@link ExtentReports} - Extent report instance
	 */
	private static synchronized ExtentReports getReportInstance(ITestResult iTestResult) {
		if (report == null) {
			String reportFilePath = new File(iTestResult.getTestContext().getOutputDirectory()).getParent() + File.separator + "ExtentReport.html";
			report = new ExtentReports(reportFilePath, true);
		}
		return report;
	}
	
	/**
	 * To start and return a new extent test instance with given test case
	 * description. Returns the test instance if the test has already been
	 * started
	 * 
	 * @param description
	 *            - test case description
	 * @return {@link ExtentTest} - ExtentTest Instance
	 */
	private static ExtentTest startTest(String description) {
		ExtentTest test = null;
		String testName = getTestName(Reporter.getCurrentTestResult());
		if (tests.containsKey(testName)) {
			test = tests.get(testName);
			if (description != null && !description.isEmpty()) {
				test.setDescription(description);
			}
		} else {
			test = getReportInstance(Reporter.getCurrentTestResult()).startTest(testName, description);
			test.assignCategory(Reporter.getCurrentTestResult().getMethod().getGroups());
			tests.put(testName, test);
		}
		return test;
	}
	
	/**
	 * Returns the test instance if the test has already been started. Else
	 * creates a new test with empty description
	 * 
	 * @return {@link ExtentTest} - ExtentTest Instance
	 */
	private static ExtentTest getTest() {
		return startTest("");
	}

	/**
	 * To start a test with given test case info
	 * 
	 * @param testCaseInfo
	 */
	public static void testCaseInfo(String testCaseInfo) {
		startTest(testCaseInfo);
	}
	
	/**
	 * To log the given message to the reporter at PASS level
	 * 
	 * @param passMessage
	 */
	public static void pass(String passMessage){
		getTest().log(LogStatus.PASS, "<font color=\"green\">" + passMessage + "</font>");
	}

	/**
	 * To log the given message to the reporter at FAIL level
	 * 
	 * @param failMessage
	 */
	public static void fail(String failMessage) {
		getTest().log(LogStatus.FAIL, "<font color=\"red\">" + failMessage + "</font>");
	}

	/**
	 * To log the given message to the reporter at SKIP level
	 * 
	 * @param message
	 */
	public static void skip(String message) {
		getTest().log(LogStatus.SKIP, "<font color=\"orange\">" + message + "</font>");
	}
	
	/**
	 * To log the given message to the reporter at INFO level
	 * 
	 * @param message
	 */
	public static void info(String message) {
		getTest().log(LogStatus.INFO, message);
	}
	
	/**
	 * To print the stack trace of the given error/exception
	 * 
	 * @param t
	 */
	public static void logStackTrace(Throwable t) {
		if (t instanceof Error) {
			getTest().log(LogStatus.ERROR, "<div class=\"stacktrace\">" + ExceptionUtils.getStackTrace(t) + "</div>");
		} else if (t instanceof SkipException) {
			getTest().log(LogStatus.SKIP, "<div class=\"stacktrace\">" + ExceptionUtils.getStackTrace(t) + "</div>");
		} else {
			getTest().log(LogStatus.FATAL, "<div class=\"stacktrace\">" + ExceptionUtils.getStackTrace(t) + "</div>");
		}
	}
	
	/**
	 * To end an extent test instance
	 */
	public static void endTest() {
		getReportInstance(Reporter.getCurrentTestResult()).endTest(getTest());
	}
	
}
