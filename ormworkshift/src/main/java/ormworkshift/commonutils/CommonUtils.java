package ormworkshift.commonutils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CommonUtils {

	private final WebDriver driver;

	public CommonUtils(WebDriver driver) {
		this.driver = driver;
	}

	public static void waitForElement(WebDriver driver, WebElement webElement, int timeUnit_sec) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeUnit_sec));
		wait.until(ExpectedConditions.visibilityOf(webElement));
	}

	public static WebDriver createDriver(String browser) {
		WebDriver driver = null;
		switch (browser.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			System.out.println("Invalid browser specified. Defaulting to Chrome.");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		}
		return driver;
	}

	public static void scrollDown(WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 300);");
		Thread.sleep(1000);
	}
	
	public static void scrollUp(WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -300);");
		Thread.sleep(1000);
	}

}
