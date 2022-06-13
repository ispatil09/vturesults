package edu.mf.test;

import edu.mf.entity.MFInputEntity;
import edu.mf.exception.MFException;
import edu.mf.exception.MFIoException;
import edu.mf.util.FileIOUtil;
import edu.mf.util.ProcessingUtil;

public class MFTester {
	public static void main(String args[]) throws MFException {
		//int numOfSubjectsFound = 0;
		//String str = "http://results.vtu.ac.in/vitavi.php?rid=2KE13CV001&submit=SUBMIT";
		MFInputEntity inputFileData = null;
		try {
			inputFileData = FileIOUtil.getInputFileData();
			System.out.println("REad input file....");
		} catch (MFIoException e) {
			e.printStackTrace();
			throw new MFException(e.getMessage(), e);
		}
		//new ProcessingUtil().writeDataToOutput(inputFileData);
		new ProcessingUtil().writeDataToOutput_August_2017_Format(inputFileData);
	}

}
