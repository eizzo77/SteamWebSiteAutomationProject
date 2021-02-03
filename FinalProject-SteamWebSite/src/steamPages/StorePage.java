package steamPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StorePage extends StoreSubMenuBar implements IsInPageable {

	Table table;
	@FindBy(css = "#topsellers_tier")
	private WebElement topSellers;
	private List<WebElement> tableTabs;

	public StorePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		table = new Table(driver);
		tableTabs = driver.findElements(By.cssSelector(".tab_content"));
	}

	public void clickLoginButton() {
		click(loginButton);
	}

	public StorePage enterInputSearchBar(String _input) {
		super.enterInputSearchBar(_input);
		return this;
	}

	// checks that the game name is shown on every row in the list displayed to user.
	public boolean areSearchResultsEqual(String _gameName) {
		this.enterInputSearchBar(_gameName);
		waitTillVisibile(driver.findElement(By.cssSelector("#searchterm_options")));
		List<WebElement> resultsList = driver
				.findElements(By.cssSelector("#searchterm_options>div>div>a>div:nth-child(1)"));
		for (WebElement result : resultsList) {
			if (!result.getText().toLowerCase().matches(".*" + _gameName.toLowerCase() + ".*")) {
				return false;
			}
		}
		return true;
	}

	private void waitTillVisibile(WebElement _webElement) {
		webDriverWait.until(ExpectedConditions.visibilityOf(_webElement));
		sleep(800);
	}

	@Override
	public boolean isInPage() {
		return driver.getTitle().equalsIgnoreCase("Welcome to Steam");
	}
}
