package steamTests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import steamPages.StorePage;
import utills.ExcelUtils;

public class StoreTest extends BaseTest {

	@Test(dataProvider = "fetchSearchInputs", description = "search for the given input, validates it is displayed on each result among results being shown")
	public void tc01_validatesGamesSearchedEqualsToSuggestionsShown(String toSearch) {
		StorePage storePage = new StorePage(driver);
		assertTrue(storePage.areSearchResultsEqual(toSearch));
	}

	 @Test
	public void tc02_checkIsInPage() {
		StorePage storePage = new StorePage(driver);
		assertTrue(storePage.isInPage());
	}

	@DataProvider
	public Object[][] fetchSearchInputs() {
		return ExcelUtils.fetchExcelData("./src/resources/ExcelResources.xlsx", "Search");
	}
}
