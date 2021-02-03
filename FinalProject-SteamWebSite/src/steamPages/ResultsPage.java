package steamPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ResultsPage extends StoreSubMenuBar {

	private Table table;
	@FindBy(css = "#term")
	private WebElement searchBar;
	@FindBy(xpath = "//span[contains(text(),'Search')]")
	private WebElement searchButton;
	List<WebElement> sortByDropDown;
	@FindBy(xpath = "//*[@class='tab_filter_control_row ' and @data-param='tags' and not(@style='display: none;')]//span[1]")
	private WebElement tagsRows;
	@FindBy(css = "#TagSuggest")
	private WebElement tagSuggestBar;
	@FindBy(css = "#sort_by_trigger")
	private WebElement sortByDropDowButton;
	@FindBy(xpath = "//span[contains(text(),'Mac OS X')]")
	private WebElement macSupportedCheck;

	public ResultsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sortByDropDown = driver.findElements(By.cssSelector(".dropdownhidden>li"));
		table = new Table(driver);
	}

	public void pickGameTag(String _tag) {
		fillText(tagSuggestBar, _tag);
		click(tagsRows.findElement(By.xpath("span//span[2 and text()='" + _tag + "']")));
		waitTillTableReconstructed();
	}

	public int getNumOfCheckedTags() {
		return driver.findElements(By.cssSelector(".searchtag.tag_dynamic")).size();
	}

	public void clickSortByButton() {
		click(sortByDropDowButton);
		waitTillDropDownIsVisible();
	}

	public void clickMacSupportedCheckBox() {
		click(macSupportedCheck);
		waitTillTableReconstructed();
	}

	public void clickSortByDropDownOption(String _dropDownOption) {
		clickSortByButton();
		click(driver.findElement(By.xpath("//ul[@class='dropdownvisible']//a[text()='" + _dropDownOption + "']")));
		waitTillTableReconstructed();
	}

	// exclude Tag by clicking the box next to it.
	public void excludeFromTagsList(String _tagName) {
		List<WebElement> checkedTags = driver.findElements(By.cssSelector(".tab_filter_control_row.checked"));
		try {

			for (WebElement tagElement : checkedTags) {
				if (getText(tagElement.findElement(By.cssSelector("span>span:nth-child(2)")))
						.equalsIgnoreCase(_tagName)) {
					click(tagElement.findElement(By.xpath("span[2]/img")));
					waitTillTableReconstructed();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("No tag checked with the given genre");
		}
	}

	// exclude Tag by clicking the 'x' on top tags elements.
	public void excludeAnUpperTag(String _tagName) {
		List<WebElement> pickedTags = driver.findElements(By.cssSelector(".searchtag.tag_dynamic"));
		try {

			for (WebElement tagElement : pickedTags) {
				if (getText(tagElement).equalsIgnoreCase(_tagName))
				{
					click(tagElement.findElement(By.cssSelector("a")));
					waitTillDropDownIsVisible();
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getDropDownPicked() {
		return getText(driver.findElement(By.cssSelector("#sort_by_trigger")));
	}

	// creating a wait until the table fields are refreshed
	private void waitTillTableReconstructed() {
		webDriverWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				if (driver.findElement(By.cssSelector("#search_result_container")).getAttribute("style")
						.contains("opacity")) {
					return false;
				} else {
					return true;
				}
			}
		});
	}

	private void waitTillDropDownIsVisible() {
		webDriverWait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("#sort_by_droplist")),
				"class", "dropdownvisible"));
	}

	// passing the table to Table's generic method
	public List<Double> getDiscountedPrices() {
		List<Double> parsedDiscountedPrices = new ArrayList<Double>();
		for (String priceStr : table.getDiscountedFinalPrices(driver.findElement(By.cssSelector("#search_results")))) {
			parsedDiscountedPrices.add(Double.parseDouble(priceStr));
		}

		return parsedDiscountedPrices;

	}

	public int getNumOfRowsShownOnTable() {
		return table.getNumOfRowsShown(driver.findElement(By.cssSelector("#search_results")));
	}

	public int getNumOfSupportedMacGames() {
		return table.getMacSupportedGamesSize(".platform_img.mac");
	}

	public boolean isGameGenreTagExistsDeepSearch(String _gameGenre) {
		return table.isGameGenreTagExistsDeepSearch(driver.findElement(By.cssSelector("#search_resultsRows")),
				_gameGenre);
	}

}
