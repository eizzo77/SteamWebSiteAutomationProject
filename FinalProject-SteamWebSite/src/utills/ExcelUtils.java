package utills;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelUtils {

	public static Object[][] fetchExcelData(String filePath, String sheetName) {
		Object[][] tabArray = null;

		try {
			FileInputStream ExcelFile = new FileInputStream(new File(filePath));
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			XSSFSheet ExcelWSheet = ExcelWBook.getSheet(sheetName);
			Row row;
			Cell cell;
			int totalRows = ExcelWSheet.getPhysicalNumberOfRows();
			int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
			tabArray = new Object[totalRows - 1][totalCols];
			for (int i = 1; i < totalRows; i++) {
				row = ExcelWSheet.getRow(i);
				for (int j = 0; j < totalCols; j++) {
					cell = row.getCell(j);
					tabArray[i-1][j] = cell.getStringCellValue();
				}
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Exception! Excel file could not be found!");
		} catch (IOException ex) {
			System.out.println("Exception! IO error occured while trying to load excel file!");
		} catch (Exception e) {
			System.out.println("Exception! Error occured while trying to load excel file!");
		}
		return tabArray;
	}

}
