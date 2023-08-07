package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import utility.BrowserActions;
import utility.Log;
import utility.Utils;


public class HomePage extends LoadableComponent<HomePage> {

	private WebDriver driver;
	public String env;
	private boolean isPageLoaded;
	public Header header;
	
	/******************************************************
	 ***Locator section***
	 ******************************************************/

	@FindBy(xpath = "//h2[contains(text(),'Welcome')]")
	WebElement readyElement;
	
	@FindBy(xpath = "//h3[contains(text(),'Create Project')]")
	WebElement txtCreateProjectOnPopup;
	
	@FindBy(xpath = "//label/span[contains(text(),'Mark as Public')]")
	WebElement txtMarkAsPublic;
	
	@FindBy(xpath = "//button[contains(text(),'Next')]")
	WebElement btnNext;
	
	@FindBy(name = "project_name")
	WebElement inputProjectName;
	
	@FindBy(id = "project-manager")
	WebElement dropDownPM;
	
	@FindBy(name = "project_number")
	WebElement inputBillingCode;
	
	@FindBy(xpath = "//textarea[@name='description']")
	WebElement inputDescription;
	
	@FindBy(xpath = "//button[contains(text(),'Submit')]")
	WebElement btnSubmit;
	
	@FindBy(xpath = "//div/h6[contains(text(),'Project Name already exists')]")
	WebElement txtProjAlreadyExistErrorMessage;
	
	/******************************************************
	 ***Method section***
	 ******************************************************/

	public HomePage(WebDriver driver) {
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, Utils.maxElementWait);
		PageFactory.initElements(finder, this);
		header = new Header(driver).get();
	}// HomePage

	@Override
	protected void isLoaded() {
		if (!isPageLoaded) {
			Assert.fail();
		}
		//driver.manage().deleteAllCookies();
		if (isPageLoaded && !(Utils.waitForElement(driver, readyElement))) 
			Log.fail(this.getClass().getSimpleName() + " Page did not open up. Site might be down.");

		header = new Header(driver);
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
	
	
	public void enableMarkAsPublicCheckbox(){
		try{
			Utils.waitForElement(driver, txtMarkAsPublic);
			BrowserActions.clickOnElement(txtMarkAsPublic, driver, "Mark as public checkbox");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+txtMarkAsPublic, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on click mark as public checkbox", driver);
		}
	}
	
	public void clickNext(){
		try{
			Utils.waitForElement(driver, btnNext);
			BrowserActions.clickOnElement(btnNext, driver, "Next button");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+btnNext, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on click Next button", driver);
		}
	}
	
	public void enterProjectName(String projectName){
		try{
			Utils.waitForElement(driver, inputProjectName);
			BrowserActions.typeOnTextField(inputProjectName, projectName, driver, "project name textbox");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+inputProjectName, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on enter the text on project name field", driver);
		}
	}
	
	public void selectProjectManager(){
		try{
			Utils.waitForElement(driver, dropDownPM);
			BrowserActions.clickOnElement(dropDownPM, driver, "project manager dropdown");
			dropDownPM.sendKeys(Keys.ARROW_DOWN);
			dropDownPM.sendKeys(Keys.ENTER);
			Thread.sleep(3000);
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the elements", driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on select project manager from the dropdown", driver);
		}
	}
	
	
	public void enterBillingCode(String billingCode){
		try{
			Utils.waitForElement(driver, inputBillingCode);
			BrowserActions.typeOnTextField(inputBillingCode, billingCode, driver, "billing code textbox");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+inputBillingCode, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on enter the text on billing code field", driver);
		}
	}
	
	public void enterDescription(String description){
		try{
			Utils.waitForElement(driver, inputDescription);
			BrowserActions.typeOnTextField(inputDescription, description, driver, "project description textbox");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+inputDescription, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on enter the text on project description field", driver);
		}
	}
	
	public ProjectDetailsPage clickSubmit(){
		try{
			Utils.waitForElement(driver, btnSubmit);
			BrowserActions.clickOnElement(btnSubmit, driver, "Submit button");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+btnSubmit, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on click Submit button", driver);
		}
		return new ProjectDetailsPage(driver).get();
	}
	
	public void clickSubmitButton(){ 
		try{
			Utils.waitForElement(driver, btnSubmit);
			BrowserActions.clickOnElement(btnSubmit, driver, "Submit button");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+btnSubmit, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on click Submit button", driver);
		}
	}
	
	public boolean verifyProjectAlreadyExistErrorMessageDisplayed(){ 
		Boolean status = false;
		try{
			Utils.waitForElement(driver, txtProjAlreadyExistErrorMessage);
			status = txtProjAlreadyExistErrorMessage.isDisplayed();
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+btnSubmit, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on click Submit button", driver);
		}
		return status;
	}

	


}
