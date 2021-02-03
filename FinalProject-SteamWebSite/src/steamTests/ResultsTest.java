package steamTests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import steamPages.ResultsPage;
import steamPages.StorePage;

public class ResultsTest extends BaseTest {

	 @Test(description = "validates 3 tags are remarked after checked (could be with any other number of tags)")
	public void tc_01_ValidatesNumberOfTagsChecked() {
		StorePage storePage = new StorePage(driver);
		storePage.clickSearchButton();
		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.pickGameTag("2D");
		resultsPage.pickGameTag("Action");
		resultsPage.pickGameTag("Retro");
		assertEquals(resultsPage.getNumOfCheckedTags(), 3);
	}

	 @Test(description = "DropdownByNameValidation")
	public void tc_02_SortByNameStringValidation() {
		StorePage storePage = new StorePage(driver);
		storePage.clickSearchButton();
		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.clickSortByDropDownOption("Name");
		assertEquals(resultsPage.getDropDownPicked(), "Name");
	}

	 @Test(description = "validates the sorting Price ASC option works")
	public void tc_03_ValidateSortByPrice() {
		StorePage storePage = new StorePage(driver);
		storePage.clickSearchButton();
		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.pickGameTag("Fantasy");
		resultsPage.pickGameTag("Pixel Graphics");
		resultsPage.pickGameTag("Story Rich");
		resultsPage.clickSortByDropDownOption("Lowest Price");
		List<Double> pricesListProvided = resultsPage.getDiscountedPrices();
		List<Double> sortedPricesListProvided = new ArrayList<Double>(pricesListProvided);
		Collections.sort(sortedPricesListProvided);
		assertEquals(pricesListProvided, sortedPricesListProvided);
	}

	 @Test(description = "validates there are 50 rows displaying when result page is spawn for the first time")
	public void tc_04_ChecksThereAre50RowsDisplaying() {
		StorePage storePage = new StorePage(driver);
		storePage.clickSearchButton();
		ResultsPage resultsPage = new ResultsPage(driver);
		assertEquals(resultsPage.getNumOfRowsShownOnTable(), 50);

	}

	 @Test(description = "validates that all games are Mac supported, checking the number of rows displayed are equal to number of rows with Mac supported Icons")
	public void tc_05_VerifyAllGamesAreForMac() {
		StorePage storePage = new StorePage(driver);
		storePage.clickSearchButton();
		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.clickMacSupportedCheckBox();
		assertEquals(resultsPage.getNumOfSupportedMacGames(), resultsPage.getNumOfRowsShownOnTable());

	}

	 @Test(description = "validates all games displayed on the table are tagged as 'SinglePlayer'")
	public void tc_06_VerifyAllGamesAreTaggedSinglePlayer() {
		StorePage storePage = new StorePage(driver);
		storePage.clickSearchButton();
		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.pickGameTag("Singleplayer");
		assertTrue(resultsPage.isGameGenreTagExistsDeepSearch("Singleplayer"));
	}

	@Test(description = "A Failed test - After removing one Tag there are still 4 tags checked - allegedly a Bug Spotted") 
	public void tc_07_checkSizeOftaggedGenresAfterExcluding_Failed() {
		StorePage storePage = new StorePage(driver);
		storePage.clickSearchButton();
		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.pickGameTag("Singleplayer");
		resultsPage.pickGameTag("Indie");
		resultsPage.pickGameTag("Simulation");
		resultsPage.pickGameTag("Platformer");
		resultsPage.excludeFromTagsList("Indie"); // 1 removed - 3 left.
		assertFalse((resultsPage.getNumOfCheckedTags() == 3)); // found 4 checked anyway
	}

	@Test(description = "A Successfull test - using the alternate way to remove the tags works fine.")
	public void tc_08_checkSizeOfTaggedGenresAfterexculding_Succeed() {
		StorePage storePage = new StorePage(driver);
		storePage.clickSearchButton();
		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.pickGameTag("Singleplayer");
		resultsPage.pickGameTag("Indie");
		resultsPage.pickGameTag("Simulation");
		resultsPage.pickGameTag("Platformer");
		resultsPage.excludeAnUpperTag("Indie");
		assertEquals(resultsPage.getNumOfCheckedTags(), 3);
		resultsPage.excludeAnUpperTag("Simulation");
		resultsPage.excludeAnUpperTag("Singleplayer");
		assertEquals(resultsPage.getNumOfCheckedTags(), 1);
	}

}
