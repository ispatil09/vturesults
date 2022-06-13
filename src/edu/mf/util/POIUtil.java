package edu.mf.util;

import java.util.Iterator;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIUtil {

	public static String getStringAtCell(int rowIndex, int colIndex,
			XSSFSheet workSheet) {
		Cell cell = getCellByIndexes(rowIndex, colIndex, workSheet);

		String stringCellValue = cell.getStringCellValue();
		return stringCellValue;
	}

	public static double getDoubleAtCell(int rowIndex, int colIndex,
			XSSFSheet workSheet) {
		Cell cell = getCellByIndexes(rowIndex, colIndex, workSheet);
		double doubleCellValue = cell.getNumericCellValue();
		return doubleCellValue;
	}

	public static int getIntAtCell(int rowIndex, int colIndex,
			XSSFSheet workSheet) {

		Cell cell = getCellByIndexes(rowIndex, colIndex, workSheet);

		double doubleCellValue = cell.getNumericCellValue();
		String doubleString = String.valueOf(doubleCellValue);
		String intString = doubleString.substring(0, doubleString.length() - 2);
		int intVal = Integer.parseInt(intString);
		return intVal;
	}

	public static void changeCellColorToRed(int rowInd,int colInd,
			XSSFSheet testWorkSheet) {
		XSSFWorkbook testWorkbook = testWorkSheet.getWorkbook();
		XSSFCellStyle cellStyle = testWorkbook.createCellStyle();
		XSSFFont font = testWorkbook.createFont();
		font.setColor(HSSFColor.RED.index);
		cellStyle.setFont(font);
		Cell cell = getCellByIndexes(rowInd, colInd, testWorkSheet);
		cell.setCellStyle(cellStyle);

	}
	public static void changeCellColorToBlue(int rowInd, int colInd,
			XSSFSheet testWorkSheet) {
		XSSFWorkbook testWorkbook = testWorkSheet.getWorkbook();
		XSSFCellStyle cellStyle = testWorkbook.createCellStyle();
		XSSFFont font = testWorkbook.createFont();
		font.setColor(HSSFColor.BLUE.index);
		cellStyle.setFont(font);
		Cell cell = getCellByIndexes(rowInd, colInd, testWorkSheet);
		cell.setCellStyle(cellStyle);
		
	}

	public static void writeToCell(int rowIndex, int colIndex, Object object,
			XSSFSheet workSheet) {
		Cell cell = getCellByIndexes(rowIndex, colIndex, workSheet);
		if (object instanceof String)
			cell.setCellValue((String) object);
		else if (object instanceof Double)
			cell.setCellValue((double) object);
		else if (object instanceof Integer)
			cell.setCellValue((int) object);

	}

	private static Cell getCellByIndexes(int rowIndex, int colIndex,
			XSSFSheet workSheet) {
		Row row = CellUtil.getRow(rowIndex, workSheet);
		Cell cell = CellUtil.getCell(row, colIndex);

		return cell;
	}

	public static void refreshCellColor(int rowIndex, int colIndex,
			XSSFSheet workSheet) {
		Cell cell = getCellByIndexes(rowIndex, colIndex, workSheet);
		XSSFWorkbook workbook = workSheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setColor(HSSFColor.AUTOMATIC.index);
		style.setFont(font);
		cell.setCellStyle(style);
	}

}
