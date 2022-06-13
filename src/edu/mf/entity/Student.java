package edu.mf.entity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class Student {
	private String usn = "";
	private String studentName = "----------";
	private int numOfSubjectsFound = 0;
	private SITE_SOURCE siteSource = null;
	private SEMESTER_URLID semester_URLID = null;
	private byte sem=0;
	private int attemptYear = 0;
	/**
	 * Two dimensional array containing resukt information.
	 * 1st column contains - External Marks 
	 * 2nd column contains - Internal Marks 
	 * 3rd column contains - Total
	 * 4th column contains - Result
	 * 
	 * BUT FOR CBCS scheme
	 * 1st column contains - Subject Name 
	 * 2nd column contains - Subject Code 
	 * 3rd column contains - Grade Letter
	 */
	
	private String[][] resultData = null;

	public Student(String usn, SITE_SOURCE site_SOURCE, SEMESTER_URLID semester_URLID) throws IOException {
		this.usn=usn;
		this.siteSource=site_SOURCE;
		this.semester_URLID=semester_URLID;
		extractFromVTU_August_2017_Format(); //Same format for CBCS and NON-CBCS
		//extractFromVTU();
		//extractFromVTU_CBCS();
		//extractFromFVR();
	}

	public Student(String studentUSN, MFInputEntity inputFileData) throws IOException {
		this.usn = studentUSN;
		this.sem = inputFileData.getSEMESTER();
		this.attemptYear = inputFileData.getYear();
		extractFromVTU_CBCS();
	}

	private void extractFromFVR() throws IOException {
		String SEMESTER_URL_ID = getSemNumber();
		resultData = new String[10][5];
		 /*String FVR_URL_ID_SEM_1 = "/180012113";
		 String FVR_URL_ID_SEM_2 = "/360024225";
		 String FVR_URL_ID_SEM_3 = "/540036337";
		 String FVR_URL_ID_SEM_4 = "/720048449";
		 String FVR_URL_ID_SEM_5 = "/900060561";
		 String FVR_URL_ID_SEM_6 = "/1080072673";
		 String FVR_URL_ID_SEM_7 = "/1260084785";
		 String FVR_URL_ID_SEM_8 = "/1440096897";*/
		String url = "http://www.fastvturesults.com/result/"+usn+SEMESTER_URL_ID;
		 Document doc = Jsoup.connect(url).userAgent("Chrome").get();

		 /*To get data from a local file
		  * String pathStr=("F:\\My Work\\Eclipse workspace\\WebReader\\Sample Web files\\ABD.htm");
		 File file= new File(pathStr);
		 boolean exists = file.exists();
		 Document doc = Jsoup.parse(new File(pathStr), null);*/
		 //Elements tableElements = doc.select("tr");
		 Elements elementTitle = doc.getElementsByTag("title");
		 Element name = elementTitle.get(0);
		 String studNameStr = name.toString();
		 int firstInd = studNameStr.indexOf('>')+1;
		 int secondInd = studNameStr.indexOf('(')-1;
		 try {
		 this.studentName= studNameStr.substring(firstInd, secondInd);
		 } catch (Exception e) {
			 this.studentName="No Results Found!!!";
		}
		 
		 Element resultTable = doc.getElementById("scell");
		 if(resultTable==null)
			 return;
		 Elements tableElements = resultTable.select("tr");
		 for (int i = 1; i < tableElements.size(); i++) {
		    Element row = tableElements.get(i);
		    Elements rowItems = row.select("td");
		    int actualColumnSize = rowItems.size();
		    int requiredColumnsSize = actualColumnSize-2;
			//if (isMarksRow(rowItems)) {
				resultData[numOfSubjectsFound][0] = rowItems.get(0).text();
				for (int j = 0; j < requiredColumnsSize; j++) {
					System.out.print(rowItems.get(j).text()+"  ");
				}
				System.out.println(":,");
				//for (int j = 2; j < requiredColumnsSize-1; j++) {
				if(numOfSubjectsFound!=4) {  // Done Temparorily to block Advertisement
				String externalMarks = rowItems.get(3).text();
				resultData[numOfSubjectsFound][1] = externalMarks;
					String internalMarks = rowItems.get(2).text();
					resultData[numOfSubjectsFound][2] = internalMarks;
					String totalMarks = rowItems.get(4).text();
					resultData[numOfSubjectsFound][3] = totalMarks;
					String result = rowItems.get(5).text();
					resultData[numOfSubjectsFound][4] = result;
				
				//}
				resultData[numOfSubjectsFound][requiredColumnsSize-1] = rowItems.get(requiredColumnsSize-1).text();					
				numOfSubjectsFound++;
				System.out.println("");
				}
			//}
		 }
	}

	private String getSemNumber() {
		switch (this.semester_URLID) {
		case FVR_URL_ID_SEM_1:
			return "/180012113";
		case FVR_URL_ID_SEM_2:
			return "/360024225";
		case FVR_URL_ID_SEM_3:
			return "/540036337";
		case FVR_URL_ID_SEM_4:
			return "/720048449";
		case FVR_URL_ID_SEM_5:
			return "/900060561";
		case FVR_URL_ID_SEM_6:
			return "/1080072673";
		case FVR_URL_ID_SEM_7:
			return "/1260084785";
		case FVR_URL_ID_SEM_8:
			return "/1440096897";

		default:
			System.out.println("No Sem ID..");
			break;
		}
		return null;
	}

	private void extractFromVTU() throws IOException {
			resultData = new String[10][5];
			 //String url = "http://results.vtu.ac.in/vitavi.php?rid="+usn+"&submit=SUBMIT"; 
			 String url = "http://results.vtu.ac.in/cbcs_17/result_page.php?usn="+usn;
	         Document doc = Jsoup.connect(url).get();
	         Elements tableElements = doc.select("tr");

	         for (int i = 0; i < tableElements.size(); i++) {
	            Element row = tableElements.get(i);
	            Elements rowItems = row.select("td");
	            int columnsSize = rowItems.size();
				if (isMarksRow_OldFormat(rowItems)) {
					resultData[numOfSubjectsFound][0] = rowItems.get(0).text();
					for (int j = 0; j < columnsSize; j++) {
						System.out.print(rowItems.get(j).text()+"  ");
					}
					for (int j = 1; j < columnsSize-1; j++) {
						String marks = rowItems.get(j).text();
						resultData[numOfSubjectsFound][j] = marks;
					}
					resultData[numOfSubjectsFound][columnsSize-1] = rowItems.get(columnsSize-1).text();					
					numOfSubjectsFound++;
				}
	         }

	         // For Name
	         Elements tableElementsForName = doc.select("B");
	         Element element = tableElementsForName.get(2);
	         String studName = element.text();
			System.out.println(studName);
			this.studentName=studName;
	         	
	}
	/**
	 * This format is same for both CBCS and NON-CBCS scheme
	 * @throws IOException
	 */
	private void extractFromVTU_August_2017_Format() throws IOException {
		resultData = new String[20][6];
		//OLD URL:  String url = "http://results.vtu.ac.in/vitavi.php?rid="+usn+"&submit=SUBMIT"; 
		 String url = "http://results.vtu.ac.in/cbcs_17/result_page.php?usn="+usn; //CBCS
		// String url = "http://results.vtu.ac.in/results17/result_page.php?usn="+usn; // NON-CBCS
		Document doc = Jsoup.connect(url).get();
		Elements tableElements = doc.select("tr");
		System.out.print(usn+":-  ");
		
		for (int i = 0; i < tableElements.size(); i++) {
			Element row = tableElements.get(i);
			Elements rowItems = row.select("td");
			int columnsSize = rowItems.size();
			if (isMarksRow_NewFormat(rowItems)) {
				resultData[numOfSubjectsFound][0] = rowItems.get(0).text();
				for (int j = 0; j < columnsSize; j++) {
					System.out.print(rowItems.get(j).text()+"  ");
				}
				for (int j = 1; j < columnsSize-1; j++) {
					String marks = rowItems.get(j).text();
					resultData[numOfSubjectsFound][j] = marks;
				}
				resultData[numOfSubjectsFound][columnsSize-1] = rowItems.get(columnsSize-1).text();					
				numOfSubjectsFound++;
			}
		}
		
		// For Name
		//Elements tableElementsForName = doc.select("B");
		Elements tableElementsForName = doc.select("b:contains(Student Name)").parents();
		if (tableElementsForName.size()!=0) {
			Element element = tableElementsForName.get(1).child(1);
			String studNameTemp = element.text(); // here getting - : Aditya Hullolli
			String studName = studNameTemp.substring(2, studNameTemp.length());
			System.out.println(studName);
			this.studentName = studName;
		}
		
	}

	private void extractFromVTU_CBCS() throws IOException{
		//String url = "http://results.vtu.ac.in/vitavi.php?rid="+usn+"&submit=SUBMIT";
		
		String url = "http://result.vtu.ac.in/cbcs_results"+this.attemptYear+".aspx?usn="+usn+"&sem="+this.sem;
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("Possibly NO/UNSTABLE internet connection. Please check");
		}
		//SampleFile Run
		/*File file = new File("C:\\Users\\SONY\\Desktop\\Result Extractor Modification\\SampleSite.html");
		String url = file.toString();
		Document doc = Jsoup.parse(file, "UTF-8");*/
		Element elementTitle = doc.getElementById("Table1");
		// Element name = elementTitle.get(0);
		//System.out.println("DELLLLL.....");
		Elements tableElements = elementTitle.select("tr");
		//Elements tableElements = doc.select("tr");

		numOfSubjectsFound = tableElements.size()-1;
		resultData = new String[numOfSubjectsFound][3];
		for (int i = 1; i < numOfSubjectsFound; i++) {

				Element row = tableElements.get(i);
				Elements rowElements = row.select("td");
				
				// Extracting All the information from columns in row
				Element subNameTD = rowElements.get(0);
				Elements elements = subNameTD.select("input");
			if (elements.size() != 0) {
				String subName = elements.get(0).attr("value");

				Element subCodeTD = rowElements.get(1);
				Elements subCodeElement = subCodeTD.select("input");
				if (subCodeElement != null) {
					String subCode = subCodeElement.get(0).attr("value");

					Element subGradeTD = rowElements.get(4);
					String subGrade = subGradeTD.select("input").get(0)
							.attr("value");
					subGrade = subGrade.replaceAll("\\s", "");

					resultData[i - 1][0] = subName;
					resultData[i - 1][1] = subCode;
					resultData[i - 1][2] = subGrade;
				}
			}
		}
		// Last add SGPA
		Elements sgpaElement = doc.select("span[id=lblSGPA]");
		String sgpa = sgpaElement.text();
		resultData[numOfSubjectsFound-1][0]= sgpa;
		// For Name
		Elements stdntNameElements = doc.select("input[id=txtName]");
		if(stdntNameElements.size()!=0) {
			Element stdntNameElement = stdntNameElements.get(0);
			this.studentName = stdntNameElement.attr("value");
		}
		/*Elements stdntUSNElements = doc.select("input[id=txtUSN]");
		this.usn = stdntUSNElements.get(0).attr("value");*/
		
		this.siteSource = SITE_SOURCE.VTU_CBCS;
		
	}
	
	private static boolean isMarksRow_OldFormat(Elements rowItems) {
		int columnsSize = rowItems.size();
		if (columnsSize == 5) {
			try {
				for (int j = 1; j < 3; j++) {
					String text = rowItems.get(j).text();
					Integer.parseInt(text);
				}
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		} else
			return false;
	}
	
	private static boolean isMarksRow_NewFormat(Elements rowItems) {
		int columnsSize = rowItems.size();
		if (columnsSize == 6) {
			try {
				for (int j = 2; j < 4; j++) {
					String text = rowItems.get(j).text();
					Integer.parseInt(text);
				}
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		} else
			return false;
	}
	
	
	public String getUsn() {
		return usn;
	}
	public void setUsn(String usn) {
		this.usn = usn;
	}
	public int getNumOfSubjectsFound() {
		return numOfSubjectsFound;
	}
	public void setNumOfSubjectsFound(int numOfSubjectsFound) {
		this.numOfSubjectsFound = numOfSubjectsFound;
	}
	public SITE_SOURCE getSiteSource() {
		return siteSource;
	}
	public void setSiteSource(SITE_SOURCE siteSource) {
		this.siteSource = siteSource;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String[][] getResultData() {
		return resultData;
	}

	public void setResultData(String[][] resultData) {
		this.resultData = resultData;
	}
}
