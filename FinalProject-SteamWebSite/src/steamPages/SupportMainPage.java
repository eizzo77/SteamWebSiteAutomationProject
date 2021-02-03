package steamPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SupportMainPage extends MenuBar{

	@FindBy(xpath = "//span[contains(text(),\"Help, I can't sign in\")]")
	private WebElement cantSignInButton;
	
	public SupportMainPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void clickCantSignInButton()
	{
		click(cantSignInButton);
	}
	
	
}
