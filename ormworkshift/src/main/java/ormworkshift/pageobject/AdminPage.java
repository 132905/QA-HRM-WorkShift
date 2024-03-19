package ormworkshift.pageobject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import ormworkshift.commonutils.CommonUtils;



public class AdminPage {
	
	private final WebDriver driver;
	private CommonUtils commonUtils;
	private LoginPage loginPage;

	public static void browserSetup(WebDriver driver) {

		driver.manage().window().maximize();

		driver.manage().deleteAllCookies();

		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);

		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	}


	public AdminPage(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.XPATH, using = "//span[@class=\"oxd-topbar-body-nav-tab-item\" and contains(text(), 'Job')]")
	private WebElement admnJobBtn;

	@FindBy(how = How.XPATH, using = "//a[@class=\"oxd-topbar-body-nav-tab-link\" and contains(text(),'Work Shifts')]")
	private WebElement admnWkShftBtn;
	
	@FindBy(how = How.XPATH, using = "//button[@type=\"button\" and @class=\"oxd-button oxd-button--medium oxd-button--secondary\" ]")
	private WebElement addWkShftBtn;
	
	@FindBy(how = How.XPATH, using = "(//div/input[contains(@class, 'oxd-input') and contains(@class, 'oxd-input--active')])[2]")
	private WebElement addWkShftNameFieldBtn;
	
	@FindBy(how = How.XPATH, using = "(//input[@data-v-1f99f73c])[3]")
	private WebElement addWkShftFrmHrsBtn;
	
//	@FindBy(how = How.XPATH, using = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/div/div/input")
//	private WebElement addWkShftToHrsBtn;
	
	@FindBy(how = How.XPATH, using = "//button[@type=\"submit\"]")
	private WebElement addWkShftSaveBtn;

//	//EDIT
	@FindBy(how = How.XPATH, using = "(//input[@class=\"oxd-input oxd-input--active\"])[2]")
	private WebElement editWrkShiftNameFieldBtn;
	
	@FindBy(how=How.XPATH,using="(//button[@class=\"oxd-icon-button oxd-table-cell-action-space\" and @type=\"button\" ])[1]")
	private WebElement deleteSelectBtn;
	
	@FindBy(how=How.XPATH,using="//button[@type=\"button\" and @class=\"oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin\"]")
	private WebElement yesBtn;
	

	

	public void addWorkShift(String wrkShiftName, String wrkShiftFrmHrs, String wrkShiftToHrs) throws InterruptedException {
		commonUtils.waitForElement(driver, admnJobBtn, 10);
		admnJobBtn.click();
		Thread.sleep(2000);
		CommonUtils.waitForElement(driver, admnWkShftBtn, 10);
		admnWkShftBtn.click();
		Thread.sleep(1000);
		commonUtils.waitForElement(driver, addWkShftBtn, 10);
		addWkShftBtn.click();
		commonUtils.waitForElement(driver, addWkShftNameFieldBtn, 10);
		addWkShftNameFieldBtn.sendKeys(wrkShiftName);
		commonUtils.waitForElement(driver, addWkShftFrmHrsBtn, 10);
		addWkShftFrmHrsBtn.click();
		addWkShftFrmHrsBtn.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
		addWkShftFrmHrsBtn.sendKeys(wrkShiftFrmHrs);
		//addWkShftFrmHrsBtn.sendKeys(Keys.ENTER);
		//Thread.sleep(1000);
		WebElement addWkShftToHrsBtn = driver.findElement(By.xpath("(//input[@data-v-1f99f73c])[6]"));
		commonUtils.waitForElement(driver, addWkShftToHrsBtn, 10);
		addWkShftToHrsBtn.click();
		addWkShftToHrsBtn.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
		addWkShftToHrsBtn.sendKeys(wrkShiftToHrs);
		commonUtils.waitForElement(driver, addWkShftSaveBtn, 10);
		addWkShftSaveBtn.click();
		Thread.sleep(5000);
		
		try {
			WebElement alreadyExistsBtn = driver.findElement(By.xpath("//span[@class=\\\"oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message\\\" ]"));
			commonUtils.waitForElement(driver, alreadyExistsBtn, 4);
			if(alreadyExistsBtn != null) {
				String msg = alreadyExistsBtn.getText();
				if(msg.equals("Already exists")) {
						System.out.println("WORKSHIFT NAME ALREADY EXISTS");
//						loginPage.closeBrowser();
						driver.quit();
						//System.exit(0);
		}
			}else {
				WebElement popBtn = driver.findElement(By.xpath("//*[@id=\"oxd-toaster_1\"]"));
				commonUtils.waitForElement(driver, popBtn, 10);
				popBtn.click();
				String popTxt = popBtn.getText();
				System.out.println("ADD JOB CATERORY STATUS :"+popTxt+" //end");
				
				if(popTxt.contains("Success")) {
					System.out.println("ADD JOB CATERORY STATUS :"+popTxt+" //end");
				}else {
					System.out.println("ALERT MESSAGE FROM ADD JOB CATEGORY PAGE :"+popTxt);
					System.exit(0);
				}
			}
		}catch (NoSuchElementException e) {
            // Catching the exception without performing any action
        } 
	}
	
	
	public void editWrkShift(String oldWrkShiftName, String newWrkShiftName, String newWrkShiftFrmHrs, String newWrkShiftToHrs) throws InterruptedException {
		commonUtils.waitForElement(driver, admnJobBtn, 10);
		admnJobBtn.click();
		Thread.sleep(2000);
		commonUtils.waitForElement(driver, admnWkShftBtn, 10);
		admnWkShftBtn.click();
		Thread.sleep(4000);
		
		String xpath = String.format("//div[contains(@class, 'oxd-table-row') and .//div[contains(text(), '%s')]]//button[contains(@class, 'oxd-icon-button')][2]" ,oldWrkShiftName);
        WebElement edit = driver.findElement(By.xpath(xpath));
        commonUtils.waitForElement(driver, edit, 10);
        edit.click();
        
		commonUtils.waitForElement(driver, editWrkShiftNameFieldBtn, 10);//Thread.sleep(700);
		editWrkShiftNameFieldBtn.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
		editWrkShiftNameFieldBtn.sendKeys(newWrkShiftName);
		//Thread.sleep(1000);
		commonUtils.waitForElement(driver, addWkShftFrmHrsBtn, 10);
		addWkShftFrmHrsBtn.click();
		addWkShftFrmHrsBtn.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
		addWkShftFrmHrsBtn.sendKeys(newWrkShiftFrmHrs);
//		addWkShftFrmHrsBtn.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		WebElement addWkShftToHrsBtn = driver.findElement(By.xpath("(//input[@data-v-1f99f73c])[6]"));
		commonUtils.waitForElement(driver, addWkShftToHrsBtn, 10);
		addWkShftToHrsBtn.click();
		addWkShftToHrsBtn.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
		addWkShftToHrsBtn.sendKeys(newWrkShiftToHrs);
		commonUtils.waitForElement(driver, addWkShftSaveBtn, 10);
		addWkShftSaveBtn.click();
		Thread.sleep(5000);
		
		 try {
				WebElement alreadyExistsBtn = driver.findElement(By.xpath("//span[@class=\"oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message\" ]"));
				commonUtils.waitForElement(driver, alreadyExistsBtn, 4);
				if(alreadyExistsBtn != null) {
					String msg = alreadyExistsBtn.getText();
					if(msg.equals("Already exists")) {
							System.out.println("WORKSHIFT NAME ALREADY EXISTS");
//							loginPage.closeBrowser();
							driver.quit();
//							System.exit(0);
			}
				}else {
					WebElement popBtn = driver.findElement(By.xpath("//div[@id=\"oxd-toaster_1\"]"));
					commonUtils.waitForElement(driver, popBtn, 10);
					popBtn.click();
					String popTxt = popBtn.getText();
					System.out.println("EDIT WORKSHIFT STATUS :"+popTxt+" //end");
					
					if(popTxt.contains("Success")) {
						System.out.println("EDIT WORKSHIFT STATUS :"+popTxt+" //end");
					}else {
						System.out.println("ALERT MESSAGE FROM EDIT WORKSHIFT PAGE :"+popTxt);
						System.exit(0);
					}
				}
			}catch (NoSuchElementException e) {
	            // Catching the exception without performing any action
	        } 
	}
	
	public void deleteWorkShift(String deleteValue) throws InterruptedException {
		commonUtils.waitForElement(driver, admnJobBtn, 10);
		admnJobBtn.click();
		Thread.sleep(2000);
		commonUtils.waitForElement(driver, admnWkShftBtn, 10);
		admnWkShftBtn.click();
		Thread.sleep(1000);
		String xpath = String.format(
				"//div[contains(text(), '%s')]/ancestor::div[contains(@class, 'oxd-table-row')]/descendant::span[contains(@class, 'oxd-checkbox-input')]/i[contains(@class, 'bi-check')]",
				deleteValue);

		WebElement checkbox = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);

		commonUtils.waitForElement(driver, deleteSelectBtn, 10);
		deleteSelectBtn.click();
		Thread.sleep(2000);
		commonUtils.waitForElement(driver, yesBtn, 10);
		yesBtn.click();
		Thread.sleep(2000);
	}
	
	
	public void selectFrmDrpDwn(String string, WebElement btn) {
		CommonUtils.waitForElement(driver, btn, 2);
	    btn.click();
	    Actions actions = new Actions(driver);
	    if ( btn.isDisplayed() &&  btn.isEnabled()) {
	    	btn.sendKeys(Keys.ARROW_DOWN);
	    	while(! btn.getText().equals(string)) {
	    		actions.sendKeys(Keys.ARROW_DOWN).perform();
	    	}
	    	actions.sendKeys(Keys.ENTER).perform();
	    }
	    
	   
		
	}

}
