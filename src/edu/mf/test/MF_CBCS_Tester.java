package edu.mf.test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.mf.entity.MFInputEntity;
import edu.mf.entity.SITE_SOURCE;
import edu.mf.entity.Student;
import edu.mf.exception.MFIoException;
import edu.mf.util.FileIOUtil;
import edu.mf.util.ProcessingUtil;

public class MF_CBCS_Tester {

	/**
	 * @param args
	 * @throws MFIoException 
	 */
	public static void main(String[] args) throws Exception {
		
		//Student student = null;
		MFInputEntity inputFileData = null;
		try {
			//student = new Student(); // ("2BU1*CV01*", SITE_SOURCE.VTU_CBCS, null);
			inputFileData = FileIOUtil.getInputFileData();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		new ProcessingUtil().writeDataToOutput_CBCS(inputFileData);
		 
	}

}
