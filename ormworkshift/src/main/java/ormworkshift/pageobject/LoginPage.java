package ormworkshift.pageobject;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ormworkshift.commonutils.CommonUtils;

public class LoginPage {
	
	private WebDriver driver;
	private CommonUtils commonUtils;
	
	public static String loadProperties() throws IOException {
		Properties properties = new Properties();
		FileInputStream file = new FileInputStream("src/main/resources/config.properties");
		properties.load(file);
		String browser = properties.getProperty("browser");
		return browser;

	}
	
	public void closeBrowser() {
		try {
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void browserSetup(WebDriver driver)

	{

		driver.manage().window().maximize();

		driver.manage().deleteAllCookies();

		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);

		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

	}
	
	public LoginPage(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);

	}
	
	@FindBy(how = How.CSS, using = "[name='username']")

	private WebElement usernameElement;

	@FindBy(how = How.CSS, using = "[name='password']")

	private WebElement passwordElement;

	@FindBy(how = How.XPATH, using = "//button[@type=\"submit\"]")

	private WebElement loginButton;

	@FindBy(how = How.XPATH, using = "//li[contains(@class, 'oxd-main-menu-item-wrapper')]//a[contains(@href, \"/web/index.php/admin/viewAdminModule\")]")
	//

	private WebElement adminButton;
	
	public void doLogin(String username, String password) {
		commonUtils.waitForElement(driver, usernameElement, 2);
		usernameElement.sendKeys(username);
		commonUtils.waitForElement(driver, passwordElement, 2);
		passwordElement.sendKeys(password);
		commonUtils.waitForElement(driver, loginButton, 2);
		loginButton.click();
	}

	public void clickAdminButton() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.visibilityOf(adminButton));

		adminButton.click();

	}

}
