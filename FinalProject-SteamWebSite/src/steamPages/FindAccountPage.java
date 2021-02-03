package steamPages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FindAccountPage extends MenuBar implements IsInPageable{

	@FindBy (css = "#error_contents")
	private WebElement supportOptionsTable;
	@FindBy (xpath = "//div[contains(text(),'I forgot my Steam Account name or password')]")
	private WebElement forgotSteamAccOrPasstitle;
	@FindBy (css = "#forgot_login_search")
	private WebElement forgotLoginSearchTextBox;
	@FindBy (css = ".account_recovery_submit>input")
	private WebElement searchButton;
	
	public FindAccountPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public boolean isInPage() {
		steamSupportTitleWait();
		return forgotLoginSearchTextBox.isEnabled() && driver.getTitle().contains("I forgot");
	}
	
	public FindAccountPage insertEmailOrPhone(String emailOrPhone)
	{
		fillText(forgotLoginSearchTextBox, emailOrPhone);
		return this;
	}
	
	public FindAccountPage searchButtonClick()
	{
		click(searchButton);
		return this;
	}
	
	private void steamSupportTitleWait()
	{
		try {
			webDriverWait.until(ExpectedConditions.visibilityOf(supportOptionsTable));
		} catch (TimeoutException e) {
			sleep(500L);
		}
	}
	
	private void formSubmitErrorWait()
	{
		try {
			webDriverWait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("#form_submit_error")), "style", ""));
		} catch (TimeoutException e) {
			sleep(500L);
		}
	}
	
	public boolean isFormErrorDisplayed()
	{
		formSubmitErrorWait();
		return driver.findElement(By.cssSelector("#form_submit_error")).isDisplayed();
	}
	
	public String getErrorMsg()
	{
		formSubmitErrorWait();
		return getText(driver.findElement(By.cssSelector("#form_submit_error")));
	}
}
