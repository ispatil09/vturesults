package edu.mf.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

import edu.mf.entity.MFInputEntity;
import edu.mf.entity.SEMESTER_URLID;
import edu.mf.exception.MFIoException;

public class FileIOUtil {
	private static final String DATA_FOLDER_NAME = "\\MarksFetcher";

	public static MFInputEntity getInputFileData() throws MFIoException {
		MFInputEntity inputEntity = new MFInputEntity();
		Scanner scanner = null;
		try {
			try {
				String homeDir = System.getProperty("user.dir");
				//System.out.println("User HomeDir... : "+homeDir);
				scanner = new Scanner(new BufferedReader(new FileReader(homeDir
						+ "/input.txt")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new MFIoException("Error reading input file", e);
			}
			List<String> USNList = inputEntity.getInputUSNList();
			String semNumberLine = scanner.next();
			String[] splitSemNumLine = semNumberLine.split(":");
			inputEntity.setSEMESTER(Byte.parseByte(splitSemNumLine[1]));
			String attemptYearLine = scanner.next();
			String[] splitAttemptYear = attemptYearLine.split(":");
			int attemptYear = Integer.parseInt(splitAttemptYear[1]);
			//setSemResultRequired(inputEntity,semester);
			inputEntity.setYear(attemptYear);

			while (scanner.hasNext()) {
				String usn = scanner.next();
				//System.out.println(usn);
				USNList.add(usn);
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return inputEntity;
	}

	private static void setSemResultRequired(MFInputEntity inputEntity,
			int semester) {
		switch (semester) {
		case 1:
			inputEntity.setSemesterURLId(SEMESTER_URLID.FVR_URL_ID_SEM_1);
			break;
		case 2:
			inputEntity.setSemesterURLId(SEMESTER_URLID.FVR_URL_ID_SEM_2);
			break;
		case 3:
			inputEntity.setSemesterURLId(SEMESTER_URLID.FVR_URL_ID_SEM_3);
			break;
		case 4:
			inputEntity.setSemesterURLId(SEMESTER_URLID.FVR_URL_ID_SEM_4);
			break;
		case 5:
			inputEntity.setSemesterURLId(SEMESTER_URLID.FVR_URL_ID_SEM_5);
			break;
		case 6:
			inputEntity.setSemesterURLId(SEMESTER_URLID.FVR_URL_ID_SEM_6);
			break;
		case 7:
			inputEntity.setSemesterURLId(SEMESTER_URLID.FVR_URL_ID_SEM_7);
			break;
		case 8:
			inputEntity.setSemesterURLId(SEMESTER_URLID.FVR_URL_ID_SEM_8);
			break;

		default:
			System.out.println("Semester : "+semester+" , NOT VALID SEM NUMBER");
			break;
		}
	}
}
