package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import utility.Log;
import utility.Utils;


public class ProjectDetailsPage extends LoadableComponent<ProjectDetailsPage> {

	private WebDriver driver;
	private boolean isPageLoaded;
	
	/******************************************************
	 ***Locator section***
	 ******************************************************/

	@FindBy(xpath = "//main/div/div/div/div[2]/div[1]/div")
	WebElement readyElement;
	
	@FindBy(xpath = "//main//h3")
	WebElement txtProjectName;
	
	@FindBy(xpath = "//main/div/div/div/div[2]/div[1]/div/div[1]//input")
	WebElement txtProjectManagerName;

	@FindBy(xpath = "//main/div/div/div/div[2]/div[1]/div/div[2]//input")
	WebElement txtBillingCode;
	
	@FindBy(xpath = "//main/div/div/div/div[2]/div[1]/div/div[3]//input")
	WebElement txtProjectStatus;
	
	
	/******************************************************
	 ***Method section***
	 ******************************************************/

	public ProjectDetailsPage(WebDriver driver) {
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, Utils.maxElementWait);
		PageFactory.initElements(finder, this);
	}// ProjectDetailsPage

	@Override
	protected void isLoaded() {
		if (!isPageLoaded) {
			Assert.fail();
		}
		//driver.manage().deleteAllCookies();
		if (isPageLoaded && !(Utils.waitForElement(driver, readyElement))) 
			Log.fail(this.getClass().getSimpleName() + " page did not open up. Site might be down.");
		Log.event("--" + this.getClass().getSimpleName() + " Loaded Successfully.");

	}// isLoaded

	@Override
	protected void load() {
		Utils.waitForPageLoad(driver);
		isPageLoaded = true;
	}// load


	public void waitForPageToLoad() {
			Utils.waitForElement(driver, readyElement);
	}
	
	public Boolean verifyProjectName(String projectName){
		boolean status = false;
		try{
			Utils.waitForElement(driver, txtProjectName);
			String projNameFromPage = txtProjectName.getText();
			status = projectName.equals(projNameFromPage);
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+txtProjectName, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on get the text for project name", driver);
		}
		return status;
	}
	
	public Boolean verifyProjectManagerName(String projectManagerName){
		boolean status = false;
		try{
			Utils.waitForElement(driver, txtProjectManagerName);
			String pmNameFromPage = txtProjectManagerName.getAttribute("value");
			status = projectManagerName.equals(pmNameFromPage);
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+txtProjectManagerName, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on get the value for project manager name", driver);
		}
		return status;
	}
	
	public Boolean verifyProjectBillingCode(String billingCode){
		boolean status = false;
		try{
			Utils.waitForElement(driver, txtBillingCode);
			String billingCodeFromPage = txtBillingCode.getAttribute("value");
			status = billingCode.equals(billingCodeFromPage);
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+txtBillingCode, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on get the value for billing code", driver);
		}
		return status;
	}
	
	public Boolean verifyProjectStatus(String projectStatus){
		boolean status = false;
		try{
			Utils.waitForElement(driver, txtProjectStatus);
			String projectStatusFromPage = txtProjectStatus.getAttribute("value");
			status = projectStatus.equals(projectStatusFromPage);
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+txtProjectStatus, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on get the value for project status", driver);
		}
		return status;
	}

	
}
