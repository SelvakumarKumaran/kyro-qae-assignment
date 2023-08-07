package utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.SigninPage;

public class BaseTest {
	
	public static WebDriver createWebDriver() {
		WebDriver driver = null;
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	public static SigninPage launchWebsite(WebDriver driver, String url) {
		driver.get(url);
		return new SigninPage(driver).get();
	}

}
