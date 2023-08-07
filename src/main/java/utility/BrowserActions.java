package utility;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Wrapper for Selenium WebDriver actions which will be performed on browser
 * 
 * Wrappers are provided with exception handling which throws Skip Exception on
 * occurrence of NoSuchElementException
 * 
 */
public class BrowserActions {

	
	/**
	 * Wrapper to type a text in browser text field
	 * 
	 * @param txt
	 *            : WebElement of the Text Field
	 * @param txtToType
	 *            : Text to type [String]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception
	 */
	public static void typeOnTextField(WebElement txt, String txtToType,
			WebDriver driver, String elementDescription) throws Exception {

		if (!Utils.waitForElement(driver, txt))
			throw new Exception(elementDescription
					+ " field not found in page!!");

		try {
			txt.clear();
			txt.click();
			txt.sendKeys(txtToType);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription
					+ " field not found in page!!");

		}

	}// typeOnTextField

	
	/**
	 * Wrapper to type a text in browser text field
	 * 
	 * @param txt
	 *            : String Input (CSS Locator)
	 * @param txtToType
	 *            : Text to type [String]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception
	 */
	public static void typeOnTextField(String txt, String txtToType,
			WebDriver driver, String elementDescription) throws Exception {

		WebElement element = checkLocator(driver, txt);
		if (!Utils.waitForElement(driver, element, 1))
			throw new Exception(elementDescription
					+ " field not found in page!!");

		try {
			element.clear();
			element.click();
			element.sendKeys(txtToType);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription
					+ " field not found in page!!");

		}

	}// typeOnTextField

	public static void clickOnElement(WebElement btn, WebDriver driver,
			String elementDescription) throws Exception {
		if (!Utils.waitForElement(driver, btn, 15))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			btn.click();
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

	}// clickOnButton

	/**
	 * Wrapper to click on button/text/radio/checkbox in browser
	 * 
	 * @param btn
	 *            : String Input (CSS Locator) [of the Button Field]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception
	 */
	public static void clickOnElement(String btn, WebDriver driver,
			String elementDescription) throws Exception {

		WebElement element = checkLocator(driver, btn);
		if (!Utils.waitForElement(driver, element, 1))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			element.click();
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

	}// clickOnButton

	public static void actionClick(WebElement element, WebDriver driver,
			String elementDescription) throws Exception {
		if (!Utils.waitForElement(driver, element, 5))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click(element).build().perform();
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
	}

	public static void javascriptClick(WebElement element, WebDriver driver,
			String elementDescription) throws Exception {
		if (!Utils.waitForElement(driver, element, 5))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
	}

	/**
	 * Select drop down value and doesn't wait for spinner.
	 *
	 * @param elementLocator
	 *            the element locator
	 * @param valueToBeSelected
	 *            the value to be selected
	 */
	public static void selectDropDownValue(WebDriver driver,
			WebElement dropDown, String valueToBeSelected) {
		Select select = new Select(dropDown);
		try{
			select.selectByVisibleText(valueToBeSelected);
		}catch(Exception e1)
		{
			select.selectByValue(valueToBeSelected);
		}
	}

	/**
	 * Select drop down value and doesn't wait for spinner.
	 *
	 * @param elementLocator
	 *            the element locator
	 * @param valueToBeSelected
	 *            the value to be selected
	 */
	public static void selectDropDownIndex(WebDriver driver,
			WebElement dropDown, String valueToBeSelected) {
		dropDown.click();
		Select dropdown = new Select(dropDown);
		int closuretype = Integer.parseInt(valueToBeSelected);
		dropdown.selectByIndex(closuretype);
	}

	/**
	 * To check whether locator string is xpath or css
	 * 
	 * @param driver
	 * @param locator
	 * @return elements
	 */
	@SuppressWarnings("deprecation")
	public static List<WebElement> checkLocators(WebDriver driver,
			String locator) {
		List<WebElement> elements = null;
		if (locator.startsWith("//")) {
			elements = (new WebDriverWait(driver, 10).pollingEvery(500,
					TimeUnit.MILLISECONDS).ignoring(
							NoSuchElementException.class,
							StaleElementReferenceException.class)
							.withMessage("Couldn't find " + locator))
							.until(ExpectedConditions
									.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
		} else {
			elements = (new WebDriverWait(driver, 10).pollingEvery(500,
					TimeUnit.MILLISECONDS).ignoring(
							NoSuchElementException.class,
							StaleElementReferenceException.class)
							.withMessage("Couldn't find " + locator))
							.until(ExpectedConditions
									.visibilityOfAllElementsLocatedBy(By
											.cssSelector(locator)));
		}
		return elements;
	}

	/**
	 * To check whether locator string is xpath or css
	 * 
	 * @param driver
	 * @param locator
	 * @return element
	 */
	@SuppressWarnings("deprecation")
	public static WebElement checkLocator(WebDriver driver, String locator) {

		WebElement element = null;
		if (locator.startsWith("//")) {
			element = (new WebDriverWait(driver, 10).pollingEvery(500,
					TimeUnit.MILLISECONDS).ignoring(
							NoSuchElementException.class,
							StaleElementReferenceException.class)
							.withMessage("Couldn't find " + locator))
							.until(ExpectedConditions.visibilityOfElementLocated(By
									.xpath(locator)));
		} else {
			element = (new WebDriverWait(driver, 10).pollingEvery(500,
					TimeUnit.MILLISECONDS).ignoring(
							NoSuchElementException.class,
							StaleElementReferenceException.class)
							.withMessage("Couldn't find " + locator))
							.until(ExpectedConditions.visibilityOfElementLocated(By
									.cssSelector(locator)));
		}
		return element;
	}

	public static void ScrollToViewTop(WebDriver driver)
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)", "");
	}

	/**
	 * Open a new tab on the browser
	 * 
	 * @param driver
	 */
	public static void openNewTab(WebDriver driver) {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
	}

	/**
	 * To Verify the element is present
	 * 
	 */
	public static boolean elementPresent(WebElement element, WebDriver driver,
			String elementDescription) throws Exception {
		if (!Utils.waitForElement(driver, element, 5))
			throw new Exception(elementDescription + " not found in page!!");
		try {
			if (element.isDisplayed()) {
				return true;
			}
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
		return false;
	}

	/*
	 * double click
	 */
	public static void doubleClick(WebElement element) throws Exception {
		for (int count = 0; count < 2; count++) {
			element.click();
		}
	}

	public static void ScrollToViewBottom(WebDriver driver)	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
	} 
	
}// BrowserActions