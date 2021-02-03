package steamPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

abstract class StoreSubMenuBar extends MenuBar {

	@FindBy(css = "#foryou_tab")
	protected WebElement yourStoreTab;
	@FindBy(css = "#genre_tab")
	protected WebElement browseTab;
	protected List<WebElement> subMenuTabs;
	@FindBy(css = "#store_nav_search_term")
	protected WebElement searchBar;
	@FindBy(css = "#store_search_link>img")
	protected WebElement storeSearchButton;
	protected List<WebElement> yourStoreSubMenuItems;
	protected List<WebElement> browseSubMenuItems;

	StoreSubMenuBar(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		subMenuTabs = driver
				.findElements(By.xpath("//div[@class='store_nav']//*[@class='tab  ' or contains(@id,'_tab')]"));
		yourStoreSubMenuItems = driver
				.findElements(By.cssSelector("#foryou_flyout>.popup_body.popup_menu>.popup_menu_item"));
		browseSubMenuItems = driver.findElements(
				By.cssSelector("#genre_flyout>.popup_body.popup_menu_twocol>.popup_menu>.popup_menu_item"));
	}


	// click an item on sub menu 'Store', could be more generic.
	public void clickYourStoreDropDownItem(String _subMenuStoreItem) {
		this.hover(popElement(driver
				.findElements(By.xpath("//div[@class='store_nav']//*[@class='tab  ' or contains(@id,'_tab')]")), "your"))
				.click(popElement(driver.findElements(
						By.cssSelector("#genre_flyout>.popup_body.popup_menu_twocol>.popup_menu>.popup_menu_item")), _subMenuStoreItem));
	}

	// click an item on sub menu 'Browse'
	public void clickBrowseDropDownItem(String _subMenuBrowseItem) {
		this.hover(popElement(driver
				.findElements(By.xpath("//div[@class='store_nav']//*[@class='tab  ' or contains(@id,'_tab')]")), "Brow"));
		this.click(popElement(driver.findElements(
				By.cssSelector("#genre_flyout>.popup_body.popup_menu_twocol>.popup_menu>.popup_menu_item")), _subMenuBrowseItem));
	}

	public void clickSubMenuTab(String _tabName) {
		click(popElement(subMenuTabs, _tabName));
	}

	private StoreSubMenuBar hover(WebElement _elmement) {
		actions.moveToElement(_elmement).build().perform();
		return this;
	}

	StoreSubMenuBar enterInputSearchBar(String _input) {
		fillText(searchBar, _input);
		return this;
	}

	public StoreSubMenuBar clickSearchButton() {
		click(this.storeSearchButton);
		return this;
	}

	public void selectGenrePage(String _genre) {
		clickBrowseDropDownItem(_genre);
	}

}