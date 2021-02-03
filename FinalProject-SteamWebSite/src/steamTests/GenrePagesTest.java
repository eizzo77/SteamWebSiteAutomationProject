
package steamTests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import steamPages.GenrePage;
import utills.ExcelUtils;

public class GenrePagesTest extends BaseTest  {

	@Test(description = "selecting different genre pages and validates we are on the exact Page.")
	public void tc01_DifferentGenresPageValidation() {

		GenrePage genrePage = new GenrePage(driver, "Action");
		assertTrue(genrePage.isInPage());
		genrePage.selectAGenrePage("RPG");
		assertTrue(genrePage.isInPage());
		genrePage.selectAGenrePage("Indie");
		assertTrue(genrePage.isInPage());
		genrePage.selectAGenrePage("Casual");
		assertTrue(genrePage.isInPage());
	}

	@Test(dataProvider = "fetchTableGenrePossibleValueFromExcelTC2",
			description = "shallow search for game tags shown on front, validates that some games may have not the main tag on front, could be FALSE sometimes")
	public void tc02_validatesCurrectTagForEachGameShallowSearch(String genre, String table, String toLookFor) {
		GenrePage genrePage = new GenrePage(driver, genre);
		assertFalse(genrePage.isGameGenreTagExistsOnEveryRow(table));
	}

	@Test(dataProvider = "fetchTableGenrePossibleValueFromExcelTC1",
			description = "unlike shallow-search, searches for hidden tags as well - supposed to be TRUE all the time for game genres")
	public void tc03_validatesCurrectTagForEachGameDeepSearch(String genre, String table) {
		GenrePage genrePage = new GenrePage(driver, genre);
		assertTrue(genrePage.isGameGenreTagExistsDeepSearch(table));
	}

	@Test(dataProvider = "fetchTableGenrePossibleValueFromExcelTC2",
			description = "validates a certain input exists on each row for a front page in a certain genre and Table")
	public void tc04_validatesFreeToPlayOrDemoExistsOnEveryGameRow(String genre, String table, String toLookFor) {
		GenrePage genrePage = new GenrePage(driver, genre);
		assertTrue(genrePage.IsExistsInCurrentPage(table, toLookFor));
	}

	@Test(dataProvider = "fetchTableGenrePossibleValueFromExcelTC2",
			description = "validates whether the price/input('Demo','Free to play') given is exists in all pages for a certain genre and table could be False or True")
	public void tc05_validatesFreeToPlayOrDemoAppearanceForAllPages(String genre, String table, String toLookFor) {
		GenrePage genrePage = new GenrePage(driver, genre);
		assertTrue(genrePage.isInAllPages(table, toLookFor));
	}
	
	@DataProvider
	public Object[][] fetchTableGenrePossibleValueFromExcelTC2()
	{
		return ExcelUtils.fetchExcelData("./src/resources/ExcelResources.xlsx", "GenreTC2");
	}
	
	@DataProvider
	public Object[][] fetchTableGenrePossibleValueFromExcelTC1()
	{
		return ExcelUtils.fetchExcelData("./src/resources/ExcelResources.xlsx", "GenreTC1");
	}

}
