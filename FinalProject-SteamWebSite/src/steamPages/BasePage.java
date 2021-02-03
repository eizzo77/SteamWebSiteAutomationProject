package steamPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage  {
	
	WebDriver driver;
	WebDriverWait webDriverWait;
	Actions actions;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		webDriverWait = new WebDriverWait(driver, 15);
		actions = new Actions(driver);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void fillText(WebElement el,String text) {
		el.clear();
		el.sendKeys(text);
	}

	public void click(WebElement el) {
		el.click();
	}

	public String getText(WebElement el) {
		return el.getText();
	}

	public void sleep(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void alertOK(String text) {
		driver.switchTo().alert().sendKeys(text);
		driver.switchTo().alert().accept();	
	}
	
	public void alertConfirm()
	{
		driver.switchTo().alert().accept();
	}
	
	public void doSelectByValue(Select s,String value)
	{
		s.selectByValue(value);
	}
	
	// fetching the element with the given option string. mainly for dealing with menus.
	protected WebElement popElement(List<WebElement> _list, String _option) {
		WebElement chosenElement = null;
		for (WebElement webElement : _list) {
			if (webElement.getAttribute("innerHTML").toLowerCase().contains(_option.toLowerCase())
					|| webElement.getAttribute("innerHTML").toLowerCase().equalsIgnoreCase(_option.toLowerCase()))
				chosenElement = webElement;
		}
		return chosenElement;
	}
}
