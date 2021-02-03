package steamPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class MenuBar extends BasePage{
	
	@FindBy(linkText = "login")
	protected WebElement loginButton;
	@FindBy(css = "#language_pulldown")
	protected WebElement languagePulldown;
	@FindBy(css = "#logo_holder")
	protected WebElement homePageButton;
	@FindBy(partialLinkText = "STORE")
	protected WebElement storeSubMenuNav;
	@FindBy(partialLinkText = "COMMUNI")
	protected WebElement communitySubMenuNav;
	@FindBy(partialLinkText = "ABOUT")
	protected WebElement aboutMenuItem;
	@FindBy(xpath = "//a[contains(text(),'SUPPORT')]")
	protected WebElement supportMenuItem;
	@FindBy(linkText = "Stats")
	protected WebElement statsMenuItem;
	@FindBy(linkText = "Broadcasts")
	protected WebElement broadcastMenuItem;
	
	public MenuBar(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void clickHomePageButton()
	{
		click(homePageButton);
	}
	
	public void clickSupportPageButton()
	{
		click(supportMenuItem);
	}

}
