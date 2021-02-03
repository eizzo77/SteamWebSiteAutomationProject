package steamPages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends MenuBar implements IsInPageable{

	@FindBy(css = "#input_username")
	private WebElement userNameBox;
	@FindBy(css = "#input_password")
	private WebElement passwordBox;
	@FindBy(css = "#login_btn_signin")
	private WebElement signInButton;
	@FindBy(css = "#link_forgot_password")
	private WebElement forgotPassword;
	@FindBy(css = ".login_title")
	private WebElement loginTitle;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void login(String userName, String password)
	{
		fillText(this.userNameBox, userName);
		fillText(this.passwordBox, password);
		click(this.signInButton);
	}
	
	public String getErrorMessage()
	{
		waitForErrorMessage();
		return getText(driver.findElement(By.cssSelector("#error_display")));
	}
	
	public boolean isErrorDisplayed()
	{
		waitForErrorMessage();
		return this.driver.findElement(By.cssSelector("#error_display")).isDisplayed();
	}
	
	/* waiting for the visibility of the error Message. when the Message is not supposed to be displayed (like tc01\02),
	   we catch the Exception to keep the test going.*/
	private void waitForErrorMessage()
	{
		try {
			webDriverWait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.cssSelector("#error_display"))));
		} catch (TimeoutException e) {
		
		}	
	}

	@Override
	public boolean isInPage() {
		return this.loginTitle.isDisplayed() && driver.getTitle().equalsIgnoreCase("Sign in");
		
	}
	
	public void forgotYourPassClick()
	{
		click(forgotPassword);
	}
	
}
