package utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;


/**
 * Log class consists capturing and printing screenshots,methods for writing log
 * status as pass/fail logs,methods to print test case info and messages
 *
 */
public class Log {

	public static boolean printconsoleoutput;
	private static String screenShotFolderPath;
	private static AtomicInteger screenShotIndex = new AtomicInteger(0);
	

	static final String TEST_TITLE_HTML_BEGIN = "&emsp;<div class=\"test-title\"> <strong><font size = \"3\" color = \"#000080\">";
	static final String TEST_TITLE_HTML_END = "</font> </strong> </div>&emsp;<div><strong>Steps:</strong></div>";

	static final String TEST_COND_HTML_BEGIN = "&emsp;<div class=\"test-title\"> <strong><font size = \"3\" color = \"#0000FF\">";
	static final String TEST_COND_HTML_END = "</font> </strong> </div>&emsp;";

	static final String MESSAGE_HTML_BEGIN = "<div class=\"test-message\">&emsp;";
	static final String MESSAGE_HTML_END = "</div>";

	static final String PASS_HTML_BEGIN = "<div class=\"test-result\"><br><font color=\"green\"><strong> ";
	static final String PASS_HTML_END1 = " </strong></font> ";
	static final String PASS_HTML_END2 = "</div>&emsp;";

	static final String FAIL_HTML_BEGIN = "<div class=\"test-result\"><br><font color=\"red\"><strong> ";
	static final String FAIL_HTML_END1 = " </strong></font> ";
	static final String FAIL_HTML_END2 = "</div>&emsp;";

	static final String SKIP_EXCEPTION_HTML_BEGIN = "<div class=\"test-result\"><br><font color=\"orange\"><strong> ";
	static final String SKIP_HTML_END1 = " </strong></font> ";
	static final String SKIP_HTML_END2 = " </strong></font> ";

	static final String EVENT_HTML_BEGIN = "<div class=\"test-event\"> <font color=\"maroon\"> <small> &emsp; &emsp;--- ";
	static final String EVENT_HTML_END = "</small> </font> </div>";
	
	static final String TRACE_HTML_BEGIN = "<div class=\"test-event\"> <font color=\"brown\"> <small> &emsp; &emsp;--- ";
	static final String TRACE_HTML_END = "</small> </font> </div>";

	/**
	 * Static block clears the screenshot folder if any in the output during
	 * every suite execution and also sets up the print console flag for the run
	 */
	static {
		
		try {
			Properties props = new Properties();
			InputStream cpr = Log.class.getResourceAsStream("/log4j.properties");
			props.load(cpr);
			PropertyConfigurator.configure(props);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		File screenShotFolder = new File(Reporter.getCurrentTestResult().getTestContext().getOutputDirectory());
		screenShotFolderPath = screenShotFolder.getParent() + File.separator + "ScreenShot" + File.separator;
		screenShotFolder = new File(screenShotFolderPath);

		if (!screenShotFolder.exists()) {
			screenShotFolder.mkdir();
		}

		File[] screenShots = screenShotFolder.listFiles();

		// delete files if the folder has any
		if (screenShots != null && screenShots.length > 0) {
			for (File screenShot : screenShots) {
				screenShot.delete();
			}
		}

		final Map<String, String> params = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getAllParameters();

		if (params.containsKey("printconsoleoutput")) {
			Log.printconsoleoutput = Boolean.parseBoolean(params.get("printconsoleoutput"));
		}

	} // static block

	/**
	 * lsLog4j returns name of the logger from the current thread
	 */
	public static Logger lsLog4j() {
		return Logger.getLogger(Thread.currentThread().getName());
	}
	
	/**
	 * exception prints the exception message as fail/skip in the log
	 * (level=fatal)
	 * 
	 * @param e
	 *            exception message
	 * @throws Exception
	 */
	public static void exception(Exception e) throws Exception {

		String eMessage = e.getMessage();

		if (eMessage != null && eMessage.contains("\n")) {
			eMessage = eMessage.substring(0, eMessage.indexOf("\n"));
		}
		lsLog4j().log(callerClass(), Level.FATAL, eMessage, e);
		if (e instanceof SkipException) {
			Reporter.log(SKIP_EXCEPTION_HTML_BEGIN + eMessage + SKIP_HTML_END1 + SKIP_HTML_END2);
			ExtentReporter.skip(eMessage);
			ExtentReporter.logStackTrace(e);
		} else {
			Reporter.log("<!--FAIL-->");
			Reporter.log(FAIL_HTML_BEGIN + eMessage + FAIL_HTML_END1 + FAIL_HTML_END2);
			ExtentReporter.fail(eMessage);
			ExtentReporter.logStackTrace(e);
		}
		throw e;
	}
	
	/**
	 * exception prints the exception message as fail/skip in the log with
	 * screenshot (level=fatal)
	 * 
	 * @param e
	 *            exception message
	 * @param driver
	 *            to take screenshot
	 * @throws Exception
	 */
	public static void exception(Exception e, WebDriver driver) throws Exception {

		String screenShotLink = "";
		try {
			String inputFile = takeScreenShot(driver);
			screenShotLink = getScreenShotHyperLink(inputFile);
		} catch (Exception ex) {

		}
		String eMessage = e.getMessage();
		if (eMessage != null && eMessage.contains("\n")) {
			eMessage = eMessage.substring(0, eMessage.indexOf("\n"));
		}
		lsLog4j().log(callerClass(), Level.FATAL, eMessage, e);
		if (e instanceof SkipException) {
			Reporter.log(SKIP_EXCEPTION_HTML_BEGIN + eMessage + SKIP_HTML_END1 + screenShotLink + SKIP_HTML_END2);
			ExtentReporter.skip(eMessage + " " + screenShotLink);
			ExtentReporter.logStackTrace(e);
		} else {
			Reporter.log("<!--FAIL-->");
			Reporter.log(FAIL_HTML_BEGIN + eMessage + FAIL_HTML_END1 + screenShotLink + FAIL_HTML_END2);
			ExtentReporter.fail(eMessage + " " + screenShotLink);
			ExtentReporter.logStackTrace(e);
		}
		throw e;
	}
	
	/**
	 * message print the test case custom message in the log
	 * (level=info)
	 * 
	 * @param description
	 *            test case
	 */
	
	public static void message(String description) {
		Reporter.log(MESSAGE_HTML_BEGIN + description + MESSAGE_HTML_END);
		lsLog4j().log(callerClass(), Level.INFO, description, null);
		ExtentReporter.info(description);
	}
	
	/**
	 * message print the test case custom message in the log with screenshot
	 * (level=info)
	 * 
	 * @param description
	 *            test case
	 * @param driver
	 *            to take screenshot
	 * @param takeScreenShot
	 *            [OPTIONAL] flag to override taking screenshot
	 */
	
	public static void message(String description, WebDriver driver, Boolean... takeScreenShot) {

		boolean finalDecision = true;
		if (takeScreenShot.length > 0 && takeScreenShot[0].equals(Boolean.FALSE)) {
			finalDecision = false;
		}
		
		if (finalDecision) {
			String inputFile = takeScreenShot(driver);
			Reporter.log(MESSAGE_HTML_BEGIN + description + "&emsp;" + getScreenShotHyperLink(inputFile) + MESSAGE_HTML_END);
			ExtentReporter.info(description + " " + getScreenShotHyperLink(inputFile));
		} else {
			Reporter.log(MESSAGE_HTML_BEGIN + description + MESSAGE_HTML_END);
			ExtentReporter.info(description);
		}
		lsLog4j().log(callerClass(), Level.INFO, description, null);
	}
	
	/**
	 * takeScreenShot will take the screenshot by sending driver as parameter in
	 * the log and puts in the screenshot folder
	 * 
	 * depends on system variable isTakeScreenShot status, if true it will take
	 * the screenshot, else return the empty string
	 * 
	 * @param driver
	 *            to take screenshot
	 */
	public static String takeScreenShot(WebDriver driver) {
		String inputFile = "";
		inputFile = Reporter.getCurrentTestResult().getName() + "_" + screenShotIndex.incrementAndGet() + ".png";
		ScreenshotManager.takeScreenshotFull(driver, screenShotFolderPath + inputFile);
		return inputFile;
	}
	
	/**
	 * event print the page object custom message in the log which can be seen
	 * through short cut keys used during debugging (level=info)
	 * 
	 * @param description
	 *            test case
	 */
	public static void event(String description) {

		String currDate = new SimpleDateFormat("dd MMM HH:mm:ss SSS").format(Calendar.getInstance().getTime());
		Reporter.log(EVENT_HTML_BEGIN + currDate + " - " + description + EVENT_HTML_END);
		lsLog4j().log(callerClass(), Level.DEBUG, description, null);
	}
	
	/**
	 * callerClass method used to retrieve the Class Name
	 */
	public static String callerClass() {
		return Thread.currentThread().getStackTrace()[2].getClassName();
	}
		
	/**
	 * getScreenShotHyperLink will convert the log status to hyper link
	 * 
	 * depends on system variable isTakeScreenShot status, if true it will take
	 * the screenshot, else return the empty string
	 * 
	 * @param inputFile
	 *            converts log status to hyper link
	 */
	public static String getScreenShotHyperLink(String inputFile) {
		String screenShotLink = "";
		screenShotLink = "<a href=\"." + File.separator + "ScreenShot" + File.separator + inputFile + "\" target=\"_blank\" >[ScreenShot]</a>";
		return screenShotLink;
	}
	
	/**
	 * fail print test case status as Fail with custom message (level=error)
	 * 
	 * @param description
	 *            custom message in the test case
	 */
	public static void fail(String description) {
 		lsLog4j().log(callerClass(), Level.ERROR, description, null);
		Reporter.log("<!--FAIL-->");
		Reporter.log(FAIL_HTML_BEGIN + description + FAIL_HTML_END1 + FAIL_HTML_END2);
		ExtentReporter.fail(description);
		ExtentReporter.logStackTrace(new AssertionError(description));
		Assert.fail(description);
	}
	
	/**
	 * fail print test case status as Fail with custom message and take
	 * screenshot (level=error)
	 * 
	 * @param description
	 *            custom message in the test case
	 * @param driver
	 *            to take screenshot
	 */
	public static void fail(String description, WebDriver driver) {
		String inputFile = takeScreenShot(driver);
		Reporter.log("<!--FAIL-->");
		Reporter.log(FAIL_HTML_BEGIN + description + FAIL_HTML_END1 + getScreenShotHyperLink(inputFile) + FAIL_HTML_END2);
		ExtentReporter.fail(description + " " + getScreenShotHyperLink(inputFile));
		ExtentReporter.logStackTrace(new AssertionError(description));
		lsLog4j().log(callerClass(), Level.ERROR, description, null);
		Assert.fail(description);
	}
	
	/**
	 * Asserts that a condition is true or false, depends upon the status. Then
	 * it will print the verified message with screen shot if status is true,
	 * else stop the script and print the failed message with screen shot
	 * 
	 * @param status
	 *            - boolean or expression returning boolean
	 * @param passmsg
	 *            -message to be logged when assert status is true
	 * @param failmsg
	 *            -message to be logged when assert status is false
	 * @param driver
	 *            - WebDriver, using this driver will taking the screen shot and
	 *            mapping to log report
	 */
	public static void assertThat(boolean status, String passmsg, String failmsg, WebDriver driver) {
		if (!status) {
			Log.fail(failmsg, driver);
			ExtentReporter.fail(failmsg);
		} else {
			Log.message(passmsg, driver);
			ExtentReporter.pass(passmsg);
		}
	}
	
	/**
	 * endTestCase to print log in the console as a part of ending the test case
	 */
	public static void endTestCase() {
		lsLog4j().info("****             " + "-E---N---D-" + "             *****");
		ExtentReporter.endTest();
		ExtentReporter.report.endTest(ExtentReporter.test);
		ExtentReporter.report.flush();
	}

	
}