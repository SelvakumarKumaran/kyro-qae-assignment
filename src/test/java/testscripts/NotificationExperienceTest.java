package testscripts;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.ProjectDetailsPage;
import pages.SigninPage;
import utility.BaseTest;
import utility.Log;

public class NotificationExperienceTest extends BaseTest{
	
	static WebDriver driver = null;
	String url = "https://stage.kyro.ai/";
		
	@Test(description = "Verify whether the project manager received the notification when the new project is assigned to them by another project manager")
	public void NotificationTest_01() throws Exception {
		try {
			int i = 1;
			
			String projectManagerEmailAddress = "qatestuser191996@gmail.com";
			String invitedUserEmailAddress = "qatestuser181996@gmail.com";
			String password = "Test@123";
			
			String randomString = RandomStringUtils.randomAlphabetic(5);
			String projectName = "QAE_Test_Project_"+randomString;
			String projectManagerName = "Test User3";
			String projectBillingCode = randomString;
			String projectDescription = "Project description goes here...";
			String projectStatus = "Active";
			
			driver = BaseTest.createWebDriver();
			
			SigninPage signinPage = BaseTest.launchWebsite(driver, url);
			Log.message(i++ + ". Successfully launched kyro website", driver);
			
			HomePage homePage = signinPage.loginApplication(projectManagerEmailAddress, password);
			Log.message(i++ + ". Successfully logged in the application with project manager credentials", driver);
			
			homePage.header.clickCreateProject();
			Log.message(i++ + ". Clicke on Create Project button", driver);
			
			homePage.enableMarkAsPublicCheckbox();
			Log.message(i++ + ". Clicke on Mark as public checkbox", driver);
			
			homePage.clickNext();
			Log.message(i++ + ". Clicke on Next button", driver);
			
			homePage.enterProjectName(projectName);
			Log.message(i++ + ". Entered project name", driver);
			
			homePage.selectProjectManager();
			Log.message(i++ + ". Selected project manager", driver);
			
			homePage.enterBillingCode(projectBillingCode);
			Log.message(i++ + ". Entered billing code", driver);
			
			homePage.enterDescription(projectDescription);
			Log.message(i++ + ". Entered description", driver);
			
			ProjectDetailsPage projDetailsPage = homePage.clickSubmit();
			Log.message(i++ + ". Clicke on Submit button", driver);
			
			Log.assertThat(projDetailsPage.verifyProjectName(projectName), 
					i++ + ". Project created and assigned successfully", 
					". Project is not created and assigned successfully",driver);
			
			Log.assertThat(projDetailsPage.verifyProjectManagerName(projectManagerName), 
					i++ + ". Project manager name is displayed as expected", 
					". Project manager name is not displayed as expected", driver);
			
			Log.assertThat(projDetailsPage.verifyProjectBillingCode(projectBillingCode), 
					i++ + ". Billing code is displayed as expected", 
					". Billing code is not displayed as expected", driver);
						
			Log.assertThat(projDetailsPage.verifyProjectStatus(projectStatus), 
					i++ + ". Project status is displayed as expected", 
					". Project status is not displayed as expected", driver);
						
			signinPage = homePage.header.clickLogout();
			Log.message(i++ + ". Successfully logged out the application", driver);
			
			homePage = signinPage.loginApplication(invitedUserEmailAddress, password);
			Log.message(i++ + ". Successfully logged in the application with invited user's credentials", driver);
				
			Log.assertThat(homePage.header.getNotificationCount() > 0, 
					i++ + ". Notification count is greater than 0", 
					". Notification count is equal to 0", driver);
			
			projDetailsPage = homePage.header.acceptProject(projectName);
			Log.message(i++ + ". Clicked the notification and successfully redirected to the project details page", driver);
			
			Log.assertThat(projDetailsPage.verifyProjectName(projectName), 
					i++ + ". Project name is displayed as expected in the project details page", 
					". Project name is not displayed as expected in the project details page", driver);
			
			Log.assertThat(projDetailsPage.verifyProjectManagerName(projectManagerName), 
					i++ + ". Project manager name is displayed as expected in the project details page", 
					". Project manager name is not displayed as expected in the project details page", driver);
			
			Log.assertThat(projDetailsPage.verifyProjectBillingCode(projectBillingCode), 
					i++ + ". Billing code is displayed as expected in the project details page", 
					". Billing code is not displayed as expected in the project details page", driver);
			
			Log.assertThat(projDetailsPage.verifyProjectStatus(projectStatus), 
					i++ + ". Project status is displayed as expected in the project details page", 
					". Project status is not displayed as expected in the project details page", driver);
		
		}catch(Exception e) {
			Log.exception(e, driver);
		}finally {
		Log.endTestCase();
		driver.quit();
		}
	}
	
	@Test(description = "Verify whether no notification is triggered when the project managed assign a new project to them")
	public void NotificationTest_02() throws Exception {
		try {
			int i = 1;
			
			String projectManagerEmailAddress = "qatestuser191996@gmail.com";
			String password = "Test@123";
			
			String randomString = RandomStringUtils.randomAlphabetic(5);
			String projectName = "QAE_Test_Project_"+randomString;
			String projectManagerName = "Test User1";
			String projectBillingCode = randomString;
			String projectDescription = "Project description goes here...";
			String projectStatus = "Active";
			
			driver = BaseTest.createWebDriver();
			
			SigninPage signinPage = BaseTest.launchWebsite(driver, url);
			Log.message(i++ + ". Successfully launched kyro website", driver);
		
			HomePage homePage = signinPage.loginApplication(projectManagerEmailAddress, password);
			Log.message(i++ + ". Successfully logged in the application with project manager credentials", driver);
			
			homePage.header.clickCreateProject();
			Log.message(i++ + ". Clicke on Create Project button", driver);
			
			homePage.enableMarkAsPublicCheckbox();
			Log.message(i++ + ". Clicke on Mark as public checkbox", driver);
			
			homePage.clickNext();
			Log.message(i++ + ". Clicke on Next button", driver);
			
			homePage.enterProjectName(projectName);
			Log.message(i++ + ". Entered project name", driver);
			
			homePage.enterBillingCode(projectBillingCode);
			Log.message(i++ + ". Entered billing code", driver);
			
			homePage.enterDescription(projectDescription);
			Log.message(i++ + ". Entered description", driver);
			
			ProjectDetailsPage projDetailsPage = homePage.clickSubmit();
			Log.message(i++ + ". Clicke on Submit button", driver);
			
			Log.assertThat(projDetailsPage.verifyProjectName(projectName), 
					i++ + ". Project created and assigned successfully", 
					". Project is not created and assigned successfully", driver);
			
			Log.assertThat(projDetailsPage.verifyProjectManagerName(projectManagerName), 
					i++ + ". Project manager name is displayed as expected", 
					". Project manager name is not displayed as expected", driver);
			
			Log.assertThat(projDetailsPage.verifyProjectBillingCode(projectBillingCode), 
					i++ + ". Billing code is displayed as expected", 
					". Billing code is not displayed as expected", driver);
						
			Log.assertThat(projDetailsPage.verifyProjectStatus(projectStatus), 
					i++ + ". Project status is displayed as expected", 
					". Project status is not displayed as expected", driver);
							
			Log.assertThat(homePage.header.getNotificationCount() == 0, 
					i++ + ". Notification is not triggered when assigning the project to the same user", 
					". Notification is triggered when assigning the project to the same user", driver);
			
		}catch(Exception e) {
			Log.exception(e, driver);
		}finally {
		Log.endTestCase();
		driver.quit();
		}
	}
	
	@Test(description = "Verify whether the error message is displayed when the project manager create a new project with the existing project name")
	public void NotificationTest_03() throws Exception {
		try {
			int i = 1;
			
			String projectManagerEmailAddress = "qatestuser191996@gmail.com";
			String password = "Test@123";
			
			String projectName = "QAE Project 11";
			String projectBillingCode = "Test";
			String projectDescription = "Project description goes here...";
			
			driver = BaseTest.createWebDriver();
			
			SigninPage signinPage = BaseTest.launchWebsite(driver, url);
			Log.message(i++ + ". Successfully launched kyro website", driver);
		
			HomePage homePage = signinPage.loginApplication(projectManagerEmailAddress, password);
			Log.message(i++ + ". Successfully logged in the application with project manager credentials", driver);
			
			homePage.header.clickCreateProject();
			Log.message(i++ + ". Clicke on Create Project button", driver);
			
			homePage.enableMarkAsPublicCheckbox();
			Log.message(i++ + ". Clicke on Mark as public checkbox", driver);
			
			homePage.clickNext();
			Log.message(i++ + ". Clicke on Next button", driver);
			
			homePage.enterProjectName(projectName);
			Log.message(i++ + ". Entered project name", driver);
			
			homePage.selectProjectManager();
			Log.message(i++ + ". Selected project manager", driver);
			
			homePage.enterBillingCode(projectBillingCode);
			Log.message(i++ + ". Entered billing code", driver);
			
			homePage.enterDescription(projectDescription);
			Log.message(i++ + ". Entered description", driver);
			
			homePage.clickSubmitButton();
			Log.message(i++ + ". Clicke on Submit button", driver);
			
			Log.assertThat(homePage.verifyProjectAlreadyExistErrorMessageDisplayed(), 
					i++ + ". Project Already Exist error message is displayed", 
					". Project Already Exist error message is not displayed", driver);
			
		}catch(Exception e) {
			Log.exception(e, driver);
		}finally {
		Log.endTestCase();
		driver.quit();
		}
	}
	
}