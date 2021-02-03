package steamPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GenrePage extends StoreSubMenuBar implements IsInPageable {

	private String genreName;
	private List<WebElement> tableTabs;
	private List<WebElement> tables;
	private Table table;
	@FindBy(css = ".pageheader")
	private WebElement genrePageHeader;
	@FindBy(css = ".contenthub_subtitle")
	private WebElement genrePageSubTitle;

	public GenrePage(WebDriver driver, String _GenreName) {
		super(driver);
		PageFactory.initElements(driver, this);
		super.clickBrowseDropDownItem(_GenreName);
		this.genreName = _GenreName;
		table = new Table(driver);
		tableTabs = driver.findElements(By.cssSelector(".tab_content"));
		tables = driver.findElements(By.xpath("//div[contains(@id,'Table')]"));
	}

	String getGenreName() {
		return this.genreName;
	}

	void setGenreName(String _genreName) {
		this.genreName = _genreName;
	}

	@Override
	public boolean isInPage() {
		System.out.println(getText(genrePageHeader));
		System.out.println(getText(genrePageSubTitle));
		return getText(genrePageHeader).toLowerCase().contains(genreName.toLowerCase())
				&& getText(genrePageSubTitle).toLowerCase().contains(genreName.toLowerCase());
	}
	
	// navigate to other genre page with given genre string
	public void selectAGenrePage(String _genre) {
		super.selectGenrePage(_genre);
		this.setGenreName(_genre);
	}
	
	// get the current table name and pass to the generic method in Table class.
	public boolean isGameGenreTagExistsOnEveryRow(String _gameTableName) {
		click(super.popElement(tableTabs, _gameTableName));
		WebElement currentTable = super.popElement(tables, _gameTableName);
		return this.table.isGameGenreTagExistsOnEveryRow(currentTable, this.genreName);
	}

	// get the current table name and pass to generic method in Table class.
	public boolean isGameGenreTagExistsDeepSearch(String _gameTableName) {
		click(super.popElement(tableTabs, _gameTableName));
		WebElement tableDisplayed = table
				.getCurrentlyDisplayedTable(driver.findElements(By.xpath("//div[contains(@id,'tab_content')]")));
		System.out.println(tableDisplayed.getAttribute("id") + ":");
		WebElement currentTable = tableDisplayed.findElement(By.xpath("div/div[1]"));
		return this.table.isGameGenreTagExistsDeepSearch(currentTable, this.genreName);
	}
	// get the current table name and pass to generic method in Table class.
	public boolean IsExistsInCurrentPage(String _gameTableName, String _toCheck) {
		click(tableTabs.get(2));
		click(super.popElement(tableTabs, _gameTableName));
		WebElement currentTable = table
				.getCurrentlyDisplayedTable(driver.findElements(By.xpath("//div[contains(@id,'tab_content')]")));
		return table.IsExistsInCurrentPage(currentTable, _toCheck);
	}
	// get the current table name and pass to generic method in Table class.
	public boolean isInAllPages(String _gameTableName, String _toCheck) {
		click(tableTabs.get(2));
		click(super.popElement(tableTabs, _gameTableName));
		WebElement currentTable = table
				.getCurrentlyDisplayedTable(driver.findElements(By.xpath("//div[contains(@id,'tab_content')]")));
		return table.isInAllPages(currentTable, _toCheck);
	}
}
