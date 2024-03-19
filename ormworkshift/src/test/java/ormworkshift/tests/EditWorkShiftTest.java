package ormworkshift.tests;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

public class EditWorkShiftTest extends BaseTest {
	
	@Test
	public void testEditWorkShift() throws InterruptedException {
		loginPage.doLogin("Admin", "admin123");

		loginPage.clickAdminButton();
		Thread.sleep(1000);
		try {
//	editWrkShift(String oldWrkShiftName, String newWrkShiftName, String newWrkShiftFrmHrs, String newWrkShiftToHrs)	
		adminPage.editWrkShift("ABCD","11111","1:00 pm","1:45 pm");
		}catch(NoSuchElementException e) {
			adminPage.addWorkShift("ABCD","9:00 pm", "11:00 pm");
			adminPage.editWrkShift("ABCD","11111","1:00 pm","1:45 pm");
		}
	
	}
}
