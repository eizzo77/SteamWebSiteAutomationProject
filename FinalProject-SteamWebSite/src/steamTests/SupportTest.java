package steamTests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import steamPages.FindAccountPage;
import steamPages.StorePage;
import steamPages.SupportMainPage;
import steamPages.SupportPage;
import utills.ExcelUtils;

public class SupportTest extends BaseTest {

	@Test(dataProvider = "fetchUseremailOrPhoneData", 
			description = "validates the specified Error Message is thrown once no input is inserted or CAPTCHA error (for Chrome only)")
	public void tc01_ValidateErrorMessage(String emailOrPhone) {
		StorePage sPage = new StorePage(driver);
		sPage.clickSupportPageButton();
		SupportMainPage supportMainPage = new SupportMainPage(driver);
		supportMainPage.clickCantSignInButton();
		SupportPage supportPage = new SupportPage(driver);
		supportPage.clickForgotAccountOrPassword();
		FindAccountPage findAccountPage = new FindAccountPage(driver);
		findAccountPage.insertEmailOrPhone(emailOrPhone).searchButtonClick();
		assertTrue(findAccountPage.isFormErrorDisplayed());

	}

	@Test(description = "validates we are on 'FindAccount' Page")
	public void tc02_IsInFindAccountPage() {
		StorePage sPage = new StorePage(driver);
		sPage.clickSupportPageButton();
		SupportMainPage supportMainPage = new SupportMainPage(driver);
		supportMainPage.clickCantSignInButton();
		SupportPage supportPage = new SupportPage(driver);
		supportPage.clickForgotAccountOrPassword();
		FindAccountPage findAccountPage = new FindAccountPage(driver);
		assertTrue(findAccountPage.isInPage());
	}

	@DataProvider
	public Object[][] fetchUseremailOrPhoneData() {
		return ExcelUtils.fetchExcelData("./src/resources/ExcelResources.xlsx", "Support");
	}
}
