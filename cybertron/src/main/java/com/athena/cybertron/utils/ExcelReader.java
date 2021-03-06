package com.athena.cybertron.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;

	public static void getWorkBook(String dataSheetName) {

		try {
			FileInputStream fis = new FileInputStream(new File("./input/" + dataSheetName + ".xlsx"));
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static String[][] getExcelSheetData(String fileName, String sheetName) {

		getWorkBook(fileName);
		sheet = workbook.getSheet(sheetName);
		return getData();
	}

	public static String[][] getData() {
		int lastRowNum = sheet.getLastRowNum();
		String[][] data = new String[lastRowNum + 1][];

		for (int row = 1; row <= lastRowNum; row++) {
			data[row - 1] = getRowData(row);
		}

		return data;
	}

	public static String[] getRowData(int rowIndex) {
		String excelData = "";
		int lastCellNum = sheet.getRow(rowIndex).getLastCellNum();
		for (int col = 0; col < lastCellNum; col++) {
			if (sheet.getRow(rowIndex).getCell(col).getCellType() == XSSFCell.CELL_TYPE_BLANK) {
				excelData = excelData + sheet.getRow(rowIndex).getCell(col).getStringCellValue() + " ";
			} else if (sheet.getRow(rowIndex).getCell(col).getCellType() == XSSFCell.CELL_TYPE_STRING) {
				excelData = excelData + sheet.getRow(rowIndex).getCell(col).getStringCellValue() + "  ";
			} else if (sheet.getRow(rowIndex).getCell(col).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(sheet.getRow(rowIndex).getCell(col))) {
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					excelData = excelData + sdf.format(sheet.getRow(rowIndex).getCell(col).getDateCellValue()) + "  ";
				} else {
					excelData = excelData + new DataFormatter().formatCellValue(sheet.getRow(rowIndex).getCell(col))
							+ "  ";
				}
			} else if (sheet.getRow(rowIndex).getCell(col).getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
				excelData = excelData + sheet.getRow(rowIndex).getCell(col).getBooleanCellValue() + "  ";
			}
		}
		String[] excelDataArray = excelData.split("  ");
		return excelDataArray;
	}

}
