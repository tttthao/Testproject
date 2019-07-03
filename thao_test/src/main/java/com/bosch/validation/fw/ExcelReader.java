package com.bosch.validation.fw;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelReader {
	//private static XSSFSheet ExcelWSheet;
	//private static XSSFWorkbook ExcelWBook;
	//private static XSSFCell Cell;
	private static Sheet ExcelWSheet;
	private static Workbook ExcelWBook;
	private static Cell Cell;

	// This method is to set the File path and to open the Excel file
	// Pass Excel Path and SheetName as Arguments to this method
	public static void setExcelFile(String Path, String SheetName) {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// This method is to set the File path and to open the Excel file
	public static void setExcelFile(String Path) {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			// check file extension
			String fileExt = getFileExtension(Path);
			if (fileExt.equals("xlsx")) {
				ExcelWBook = new XSSFWorkbook(ExcelFile);
			} else if (fileExt.equals("xls")) {
				ExcelWBook = new HSSFWorkbook(ExcelFile);
			} else {
				ExcelFile.close();
				throw new Exception("Incorrect format ("+ fileExt +"). The extension of excel file should be xlsx or xls!");
			}
			ExcelWSheet = ExcelWBook.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// This method is to read the test data from the Excel cell
	// In this we are passing parameters/arguments as Row Num and Col Num
	public static String getCellData(int RowNum, int ColNum){
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}

	//This method is to get the row count used of the excel sheet
	public static int getRowCount(String SheetName) {
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number = ExcelWSheet.getLastRowNum() + 1;
			return number;
		} catch (Exception e) {
			return 0;
		}
	}
	
	//This method is to get extension of the file
	public static String getFileExtension(String Path) {
		return Path.substring(Path.lastIndexOf(".") + 1, Path.length());
	}
	
	//This method is to return data of excel file as array
	public static String[][] getData() {
		String[][] data = null;
		try {
			int rowCount = ExcelWSheet.getLastRowNum() - ExcelWSheet.getFirstRowNum() + 1;
			int colCount = ExcelWSheet.getRow(0).getLastCellNum();
			data = new String[rowCount][colCount];

			// loop to get data
			for (int i = 0; i < rowCount; i++) {
				String cellValue = null;
				Row row = ExcelWSheet.getRow(i);
				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					if (cell.getCellTypeEnum() == CellType.STRING) {
						cellValue = row.getCell(j).getStringCellValue();
					} else if (cell.getCellTypeEnum() == CellType.NUMERIC
							|| cell.getCellTypeEnum() == CellType.FORMULA) {
						cellValue = String.valueOf(cell.getNumericCellValue());
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							DateFormat df = new SimpleDateFormat("dd/MM/yy");
							Date date = cell.getDateCellValue();
							cellValue = df.format(date);
						}

					} else if (cell.getCellTypeEnum() == CellType.BLANK) {
						cellValue = "";
					} else {
						cellValue = String.valueOf(cell.getBooleanCellValue());
					}
					data[i][j] = cellValue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
}
