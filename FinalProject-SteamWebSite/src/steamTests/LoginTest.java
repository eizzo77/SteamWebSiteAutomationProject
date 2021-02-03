package steamTests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import steamPages.LoginPage;
import steamPages.StorePage;
import utills.ExcelUtils;

public class LoginTest extends BaseTest {

	StorePage sPage;
	LoginPage lPage;
	
	@BeforeClass
	public void initializeLoginTests()
	{
		sPage = new StorePage(driver);
		sPage.clickLoginButton();
		lPage = new LoginPage(driver);
	}
	
	@Test(dataProvider = "fetchLoginUserNameAndPasswordExcel" ,description = "logins failures, validates an error Message is invoked")
	public void tc01_errorMessageDisplayedWhenLoginFailed(String userName, String password)
	{
		lPage.login(userName, password);
		assertTrue(lPage.isErrorDisplayed());
	}
	
	@Test(description = "checks currect page")
	public void tc02_IsInLoginPage()
	{
		assertTrue(lPage.isInPage());
	}
	
	@DataProvider
	public Object[][] fetchLoginUserNameAndPasswordExcel()
	{
		return ExcelUtils.fetchExcelData("./src/resources/ExcelResources.xlsx", "Login");
	}
	
}
