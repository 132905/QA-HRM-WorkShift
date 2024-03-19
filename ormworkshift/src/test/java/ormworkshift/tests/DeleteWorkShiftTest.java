package ormworkshift.tests;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

public class DeleteWorkShiftTest extends BaseTest {
	
	@Test
	public void testDeleteWorkShift() throws InterruptedException {
		loginPage.doLogin("Admin", "admin123");

		loginPage.clickAdminButton();
		Thread.sleep(2000);

		
		try {
			adminPage.deleteWorkShift("ABCD");
			}catch(NoSuchElementException e) {
				adminPage.addWorkShift("ABCD","2:00 pm", "2:15 pm");
				adminPage.deleteWorkShift("ABCD");
			}
	}

}
