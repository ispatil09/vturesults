package edu.mf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.mf.entity.MFInputEntity;
import edu.mf.entity.Student;
import edu.mf.exception.MFIoException;

public class ProcessingUtil {
	private static final String DATA_FOLDER_NAME = "\\MarksFetcher";
	String resultFilePath = "";
	int HEADING_ROW = 5;
	int SUB_COL_BEGIN = 3;

	public void writeDataToOutput(MFInputEntity inputFileData) throws MFIoException {

		XSSFWorkbook workBook = getWorkBook();
		XSSFSheet workSheet = workBook.getSheetAt(0);

		int studentEntryRow = 5;
		int firstDataEntryCol = 5;
		List<String> inputUSNList = inputFileData.getInputUSNList();
		int size = inputUSNList.size();
		for (int usn=0;usn<size;usn++) {
			String studentUSN = inputUSNList.get(usn);
			Student student=null;
			try {
				student = new Student(studentUSN,inputFileData.getSiteSource(),inputFileData.getSemesterURLId());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("\nChecking again AFTER FAIL : "+inputUSNList.get(usn));
				usn--;
				continue;
			}
			int numOfSubjects = student.getNumOfSubjectsFound();
			String[][] resultData = student.getResultData();
			POIUtil.writeToCell(studentEntryRow, firstDataEntryCol-2, student.getUsn(), workSheet);
			POIUtil.writeToCell(studentEntryRow, firstDataEntryCol-1, student.getStudentName(), workSheet);
			if(numOfSubjects==0) {
				POIUtil.changeCellColorToRed(studentEntryRow,firstDataEntryCol-2, workSheet);
				POIUtil.changeCellColorToRed(studentEntryRow,firstDataEntryCol-1, workSheet);
			} else if (inputFileData.getNumOfRegularSubjects()!=numOfSubjects) {
				POIUtil.changeCellColorToBlue(studentEntryRow, firstDataEntryCol-2, workSheet);
				POIUtil.changeCellColorToBlue(studentEntryRow, firstDataEntryCol-1, workSheet);
			}
			
			int tempDataEntryCol = firstDataEntryCol;
			for (int i = 0; i < numOfSubjects; i++) {
				// Write Marks
				int marksExt = Integer.parseInt(resultData[i][1]);
				POIUtil.writeToCell(studentEntryRow, tempDataEntryCol++, marksExt, workSheet);
				int marksInt = Integer.parseInt(resultData[i][2]);
				POIUtil.writeToCell(studentEntryRow, tempDataEntryCol++, marksInt, workSheet);
				int marksTotal = Integer.parseInt(resultData[i][3]);
				POIUtil.writeToCell(studentEntryRow, tempDataEntryCol++, marksTotal, workSheet);
			}
			studentEntryRow++;
		}
		
		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(
					resultFilePath));
			workBook.write(out);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void writeDataToOutput_August_2017_Format(MFInputEntity inputFileData) throws MFIoException {
		
		XSSFWorkbook workBook = getWorkBook();
		XSSFSheet workSheet = workBook.getSheetAt(0);
		
		int studentEntryRow = 5;
		int firstDataEntryCol = 5;
		List<String> inputUSNList = inputFileData.getInputUSNList();
		int size = inputUSNList.size();
		for (int usn=0;usn<size;usn++) {
			String studentUSN = inputUSNList.get(usn);
			Student student=null;
			try {
				student = new Student(studentUSN,inputFileData.getSiteSource(),inputFileData.getSemesterURLId());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("FAILED USN: "+usn);
				System.out.println("\nChecking again AFTER FAIL : "+inputUSNList.get(usn));
				usn--;
				continue;
			}
			int numOfSubjects = student.getNumOfSubjectsFound();
			String[][] resultData = student.getResultData();
			POIUtil.writeToCell(studentEntryRow, firstDataEntryCol-2, student.getUsn(), workSheet);
			POIUtil.writeToCell(studentEntryRow, firstDataEntryCol-1, student.getStudentName(), workSheet);
			if(numOfSubjects==0) {
				POIUtil.changeCellColorToRed(studentEntryRow,firstDataEntryCol-2, workSheet);
				POIUtil.changeCellColorToRed(studentEntryRow,firstDataEntryCol-1, workSheet);
			} else if (inputFileData.getNumOfRegularSubjects()!=numOfSubjects) {
				POIUtil.changeCellColorToBlue(studentEntryRow, firstDataEntryCol-2, workSheet);
				POIUtil.changeCellColorToBlue(studentEntryRow, firstDataEntryCol-1, workSheet);
			}
			
			int tempDataEntryCol = firstDataEntryCol;
			for (int i = 0; i < numOfSubjects; i++) {
				// Write Marks
				int marksExt = Integer.parseInt(resultData[i][2]);
				POIUtil.writeToCell(studentEntryRow, tempDataEntryCol++, marksExt, workSheet);
				int marksInt = Integer.parseInt(resultData[i][3]);
				POIUtil.writeToCell(studentEntryRow, tempDataEntryCol++, marksInt, workSheet);
				int marksTotal = Integer.parseInt(resultData[i][4]);
				POIUtil.writeToCell(studentEntryRow, tempDataEntryCol++, marksTotal, workSheet);
			}
			studentEntryRow++;
		}
		
		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(
					resultFilePath));
			workBook.write(out);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private XSSFWorkbook getWorkBook() throws MFIoException {
		XSSFWorkbook workbook = null;
		String homeDir = System.getProperty("user.dir");
		//resultFilePath = homeDir+DATA_FOLDER_NAME + "/Results.xlsx";
		resultFilePath = homeDir+ "/Results.xlsx";
		Path resultExcelFilePath = Paths.get(resultFilePath);
		File resultExcelFile = resultExcelFilePath.toFile();

		try {
			workbook = new XSSFWorkbook(new FileInputStream(resultExcelFile));
		} catch (Exception e) {
			e.printStackTrace();
			throw new MFIoException(e.getMessage());
		}
		return workbook;
	}
	
	public void writeDataToOutput_CBCS(MFInputEntity inputFileData) throws MFIoException {
		
		XSSFWorkbook workBook = getWorkBook();
		XSSFSheet workSheet = workBook.getSheetAt(0);

		int studentEntryRow = 8;
		int firstDataEntryCol = 3;
		List<String> inputUSNList = inputFileData.getInputUSNList();
		int size = inputUSNList.size();
		for (int usnIndex=0;usnIndex<size;usnIndex++) {
			String studentUSN = inputUSNList.get(usnIndex);
			Student student=null;
			try {
				//student = new Student(studentUSN,inputFileData.getSiteSource(),inputFileData.getSemesterURLId());
				student = new Student(studentUSN,inputFileData);
				System.out.println(studentUSN+": Reading Successfull!!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(studentUSN+": Reading failed........");
				usnIndex--;
				continue;
			}
			int numOfSubjects = student.getNumOfSubjectsFound();
			String[][] resultData = student.getResultData();
			POIUtil.writeToCell(studentEntryRow, firstDataEntryCol-1, student.getUsn(), workSheet);
			POIUtil.writeToCell(studentEntryRow, firstDataEntryCol-2, student.getStudentName(), workSheet);
			if(numOfSubjects==0) {
				POIUtil.changeCellColorToRed(studentEntryRow,firstDataEntryCol-2, workSheet);
				POIUtil.changeCellColorToRed(studentEntryRow,firstDataEntryCol-1, workSheet);
			} /*else if (inputFileData.getNumOfRegularSubjects()!=numOfSubjects) {
				POIUtil.changeCellColorToBlue(studentEntryRow, firstDataEntryCol-2, workSheet);
				POIUtil.changeCellColorToBlue(studentEntryRow, firstDataEntryCol-1, workSheet);
			}*/
			
			for (int i = 0; i < numOfSubjects-1; i++) {
				writeToProperColumnNumber(resultData,i,workSheet,studentEntryRow);
			}
			String sgpa = resultData[resultData.length-1][0];
			POIUtil.writeToCell(studentEntryRow, 19, sgpa, workSheet);
			studentEntryRow++;
		}
		
		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(
					resultFilePath));
			workBook.write(out);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private void writeToProperColumnNumber(String[][] resultData, int subIndex, XSSFSheet workSheet, int studentEntryRow) {
		String subName = resultData[subIndex][0];
		String subCode = resultData[subIndex][1];
		String subGrade = resultData[subIndex][2];
		int colIndCounter;
		for (colIndCounter = SUB_COL_BEGIN; colIndCounter < 19; colIndCounter++) {
			String codeAtCell = POIUtil.getStringAtCell(HEADING_ROW+2, colIndCounter, workSheet);
			if(codeAtCell.equals(subCode)) {
				POIUtil.writeToCell(HEADING_ROW, colIndCounter, subName, workSheet);
				POIUtil.writeToCell(studentEntryRow, colIndCounter, subGrade, workSheet);
				return;
			}
			else if(codeAtCell.equals("")) {
				POIUtil.writeToCell(HEADING_ROW+2, colIndCounter, subCode, workSheet);
				POIUtil.writeToCell(HEADING_ROW, colIndCounter, subName, workSheet);
				POIUtil.writeToCell(studentEntryRow, colIndCounter, subGrade, workSheet);
				return;
			}
		}
		//return 0;
	}

}
