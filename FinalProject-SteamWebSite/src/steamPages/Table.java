package steamPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Table {

	WebDriver driver;
	List<WebElement> displayedRows;
	Actions actions;
	WebDriverWait webDriverWait;

	public Table(WebDriver _driver) {
		driver = _driver;
		actions = new Actions(driver);
		webDriverWait = new WebDriverWait(driver, 10);
	}

	// fetching the current table displaying to the user.
	public WebElement getCurrentlyDisplayedTable(List<WebElement> _list) {
		for (WebElement element : _list) {
			if (element.getAttribute("style").contains("block")) {
				return element;
			}
		}

		throw new NullPointerException();
	}

	// fetching the number of rows shown in a certain table.
	public int getNumOfRowsShown(WebElement _gameTable) {
		return _gameTable.findElements(By.cssSelector("div>div:nth-child(3)>a")).size();
	}

	// checking for each game row whether it has the provided tag.
	public boolean isGameGenreTagExistsOnEveryRow(WebElement _gameTable, String _gameGenre) {
		int i = 1;
		List<WebElement> _gamesFrontRows = _gameTable.findElements(By.xpath("div[contains(@id,'Rows')]//a"));
		for (WebElement gameRow : _gamesFrontRows) {
			if (hasTheString(getGameGenreTags(gameRow), _gameGenre) == false) {
				System.out.println("the Game on row #" + i + " doesn't have the Tag on front");
				return false;
			}
			i++;
		}

		return true;
	}

	// return all game row's Tags.
	private List<WebElement> getGameGenreTags(WebElement _GameRow) {
		List<WebElement> tagsList = _GameRow
				.findElements(By.cssSelector("div:nth-child(3)>div:nth-child(2)>.tab_item_top_tags>span"));
		return tagsList;

	}

	// return true if each webelement in the list equals or contains the given string.
	private boolean hasTheString(List<WebElement> _list, String _str) {
		for (WebElement element : _list) {
			if (element.getText().contains(_str) || element.getText().equalsIgnoreCase(_str)
					|| element.getAttribute("innerHTML").contains(_str)
					|| element.getAttribute("innerHTML").equalsIgnoreCase(_str)) {
				return true;
			}
		}

		return false;
	}

	// for each row in the table, we hover it so the hidden tags element of it is rendering in the HTML code. then we check if the given genre is among these tags.
	public boolean isGameGenreTagExistsDeepSearch(WebElement _gameTable, String _gameGenre) {
		List<WebElement> _gamesFrontRows = _gameTable.findElements(By.xpath("a"));
		hoverEachTableRow(_gamesFrontRows);
		int i = 1;
		while (i <= _gamesFrontRows.size()) {
			List<WebElement> actualTags = driver.findElements(
					By.cssSelector("#global_hover_content>div:nth-child(" + i + ")>div:nth-child(6)>div>.app_tag"));
			if (hasTheString(actualTags, _gameGenre) == false) {
				return false;
			}
			++i;
		}

		return true;
	}

	// hovering each row for the given table rows.
	private void hoverEachTableRow(List<WebElement> _rows) {
		for (WebElement row : _rows) {
			new Actions(driver).moveToElement(row).build().perform();
			waitTillDisplayed(driver.findElement(By.cssSelector("#global_hover")));
		}
	}

	// checking if the given price (or free to play string) exists in every page.
	public boolean isInAllPages(WebElement _gamesTable, String _price) {
		WebElement actualTable = _gamesTable.findElement(By.cssSelector("div"));
		WebElement nextButton = driver.findElement(By.cssSelector("#" + actualTable.getAttribute("id").substring(0,
				actualTable.getAttribute("id").length() - "Table".length()) + "_btn_next"));
		String pageButtonEnabled = nextButton.getAttribute("class");

		while (!pageButtonEnabled.contains("disabled")) {
			if (!IsExistsInCurrentPage(_gamesTable, _price)) {
				return false;
			}
			nextButton.click();
			waitTillOpacity(actualTable);
			pageButtonEnabled = nextButton.getAttribute("class");
		}
		return true;

	}

	// checking if given string exists in every row in for a certain page
	public boolean IsExistsInCurrentPage(WebElement _gameTable, String _toCheck) {
		for (String price : getDiscountedFinalPrices(_gameTable))
			if (!price.contains(_toCheck)) {
				return false;
			}

		return true;
	}

	// this is not the best way to implement I'm aware of that. I just couldn't
	// solve a case where there are also subPrices nodes which are not relevant.
	// In order to get rid of them, I had to end up doing split... but I'm aware
	// this is not the ideal solution.
	public List<String> getDiscountedFinalPrices(WebElement _gamesTable) {
		List<String> gamesDiscountedFinalPrices = new ArrayList<String>();
		List<WebElement> pricesElements = _gamesTable.findElements(By.xpath(
				"div[1]//a//div[2]//div[contains(@class,'price')]/div[contains(@class,'price') and not (contains(@class,'original'))]"));
		String actualPrice;
		for (WebElement pricesElement : pricesElements) {
			try {
				actualPrice = pricesElement.getAttribute("textContent").replaceAll("\\s+", "");
				if (actualPrice.length() >= 7) {
					actualPrice = actualPrice.split("¤")[2];
				} else {
					actualPrice = actualPrice.split("¤")[1];
				}
				gamesDiscountedFinalPrices.add(actualPrice);
			} catch (Exception e) {
				gamesDiscountedFinalPrices.add(pricesElement.getAttribute("innerHTML"));
			}
		}

		return gamesDiscountedFinalPrices;
	}

	private void waitTillDisplayed(WebElement _element) {
		webDriverWait.until(ExpectedConditions.attributeContains(_element, "style", "display: block"));
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void waitTillOpacity(WebElement _gameTable) {
		webDriverWait.until(ExpectedConditions.attributeToBe(_gameTable.findElement(By.cssSelector("div:nth-child(1)")),
				"style", "opacity: 1;"));
	}
	
	public int getMacSupportedGamesSize(String elementToCountPathString)
	{
		return driver.findElements(By.cssSelector(elementToCountPathString)).size();
	}
}
