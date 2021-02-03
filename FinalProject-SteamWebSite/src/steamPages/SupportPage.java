package steamPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SupportPage extends MenuBar implements IsInPageable{

	@FindBy (xpath = "//span[contains(text(),'I forgot my Steam Account name or password')]")
	private WebElement forgotSteamAccOrPasswordButton;
	@FindBy (xpath = "//span[contains(text(),'My Steam Account was stolen and I need help recove')]")
	private WebElement stolenAccHelpButton;
	@FindBy (xpath = "//span[contains(text(),\"I'm not receiving a Steam Guard code\")]")
	private WebElement notReceivingCodeButton;
	@FindBy (xpath = "//span[contains(text(),'I deleted or lost my Steam Guard Mobile Authentica')]")
	private WebElement deletedOrLostAccButton;
	@FindBy (linkText = "Steam Support")
	private WebElement supportTitle;
	
	public SupportPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isInPage() {
		return supportTitle.isDisplayed();
	}
	
	public void clickForgotAccountOrPassword()
	{
		click(forgotSteamAccOrPasswordButton);
	}
	
	
}
