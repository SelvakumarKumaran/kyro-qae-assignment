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


public class Header extends LoadableComponent<Header> {

	private WebDriver driver;
	public String env;
	private boolean isPageLoaded;
	
	/******************************************************
	 ***Locator section***
	 ******************************************************/

	@FindBy(xpath = "//h2[contains(text(),'Welcome')]")
	WebElement readyElement;
	
	@FindBy(xpath = "//header/div/div/div[2]//button")
	WebElement btnCreateProject;
	
	@FindBy(xpath = "//header/div/div/div[2]/div/div[2]/div[2]/span[1]/span")
	WebElement iconNotification;
	
	@FindBy(xpath = "//header/div/div/div[2]/div/div[2]/div[2]/span[1]/span/span")
	WebElement txtNotificationCount;
	
	@FindBy(xpath = "//header/div/div/div[2]/div/div[2]/div[2]/span[2]/div")
	WebElement iconProfile;
	
	@FindBy(xpath = "//div[contains(text(),'Logout')]")
	WebElement btnLogout;
	
	
	
	/******************************************************
	 ***Method section***
	 ******************************************************/

	public Header(WebDriver driver) {
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, Utils.maxElementWait);
		PageFactory.initElements(finder, this);
	}// HomePage

	@Override
	protected void isLoaded() {
		if (!isPageLoaded) {
			Assert.fail();
		}
		if (isPageLoaded && !(Utils.waitForElement(driver, readyElement))) 
			Log.fail(this.getClass().getSimpleName() + " Page did not open up. Site might be down.");

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
	
	public void clickCreateProject(){
		try{
			Utils.waitForPageLoad(driver);
			Utils.waitForElement(driver, btnCreateProject);
			BrowserActions.clickOnElement(btnCreateProject, driver, "create project button");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+btnCreateProject, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on click create project button", driver);
		}
	}
	
	public void clickNotificationIcon(){
		try{
			Utils.waitForPageLoad(driver);
			Utils.waitForElement(driver, iconNotification);
			BrowserActions.clickOnElement(iconNotification, driver, "notification icon");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+iconNotification, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on click notification icon", driver);
		}
	}
	
	public int getNotificationCount(){
		int count = 0;
		try{
			Utils.waitForPageLoad(driver);
			Utils.waitForElement(driver, iconNotification);
			count = Integer.parseInt(txtNotificationCount.getText());
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+iconNotification, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on click notification icon", driver);
		}
		return count;
	}
	
	public ProjectDetailsPage acceptProject(String projectName){
		try{
			Utils.waitForPageLoad(driver);
			Utils.waitForElement(driver, iconNotification);
			BrowserActions.clickOnElement(iconNotification, driver, "notification icon");
			Thread.sleep(3000);
			WebElement projNotification = driver.findElement(By.xpath("//div[@data-test-id='notification-content']/../div[contains(text(),'"+projectName+"')]"));
			BrowserActions.javascriptClick(projNotification, driver, "project assigned notification link");
			BrowserActions.clickOnElement(iconNotification, driver, "notification icon");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to find the notification with the project name "+projectName, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Unable to find the notification with the project name "+projectName, driver);
		}
		return new ProjectDetailsPage(driver).get();
	}
	
	
	public SigninPage clickLogout(){
		try{
			Utils.waitForPageLoad(driver);
			Utils.waitForElement(driver, iconProfile);
			BrowserActions.clickOnElement(iconProfile, driver, "profile icon");
			Utils.waitForElement(driver, btnLogout);
			BrowserActions.clickOnElement(btnLogout, driver, "logout button");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the elements", driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on do the logout", driver);
		}
		return new SigninPage(driver).get();
	}


	


}
