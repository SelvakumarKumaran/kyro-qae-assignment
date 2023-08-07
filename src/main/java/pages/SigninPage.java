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


public class SigninPage extends LoadableComponent<SigninPage>  {

	private WebDriver driver;
	private boolean isPageLoaded;

	/******************************************************
	 ***Locator section***
	 ******************************************************/
		
	@FindBy(name = "email")
	WebElement inputEmail;
	
	@FindBy(name = "password")
	WebElement inputPassword;
	
	@FindBy(name = "submit")
	WebElement btnSignIn;
	
	
	/******************************************************
	 ***Method section***
	 ******************************************************/
	
	public SigninPage(WebDriver driver) {
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, Utils.maxElementWait);
		PageFactory.initElements(finder, this);
	}

	@Override
	protected void isLoaded() {
		if (!isPageLoaded) {
			Assert.fail();
		}
		if (isPageLoaded && !(Utils.waitForElement(driver, inputEmail))) 
			Log.fail(this.getClass().getSimpleName() + " not loaded.");
		Log.event(this.getClass().getSimpleName() + " Loaded Successfully.");
	}

	@Override
	protected void load() {
		Utils.waitForPageLoad(driver);
		isPageLoaded = true;
	}

	
	public void enterEmailAddress(String email){
		try{
			Utils.waitForPageLoad(driver);
			Utils.waitForElement(driver, inputEmail);
			BrowserActions.typeOnTextField(inputEmail, email, driver, "email address");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+inputEmail, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on enter email address on the field", driver);
		}
	}
	
	public void enterPassword(String password){
		try{
			Utils.waitForElement(driver, inputPassword);
			BrowserActions.typeOnTextField(inputPassword, password, driver, "password");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+inputPassword, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on enter password on the field", driver);
		}
	}

	public HomePage clickSignIn(){
		try{
			Utils.waitForPageLoad(driver);
			Utils.waitForElement(driver, btnSignIn);
			BrowserActions.clickOnElement(btnSignIn, driver, "signin button");
			Utils.waitForPageLoad(driver);
			Thread.sleep(6000);
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the element "+btnSignIn, driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed on click signin button", driver);
		}
		return new HomePage(driver).get();
	}
	
	public HomePage loginApplication(String email, String password){
		try{
			Utils.waitForPageLoad(driver);
			this.enterEmailAddress(email);
			this.enterPassword(password);
			this.clickSignIn();
			Utils.waitForPageLoad(driver);
			Thread.sleep(6000);
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Log.fail("Unable to locate the elements", driver);
		}catch(Exception e){
			e.printStackTrace();
			Log.fail("Failed to login the application", driver);
		}
		return new HomePage(driver).get();
	}

}
