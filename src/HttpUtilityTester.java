import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtilityTester {

	/**
	 * This program uses the HttpUtility class to send a GET request to
	 * Google home page; and send a POST request to Gmail login page.
	 */
	public static void main(String[] args) {
		// test sending GET request
		String requestURL = "https://www.fastvturesults.com/result/2KE11CV001/900060561";
		try {
			HttpUtility.sendGetRequest(requestURL);
			String[] response = HttpUtility.readMultipleLinesRespone();
			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		HttpUtility.disconnect();
		
		
		/*System.out.println("=====================================");
		
		// test sending POST request
		Map<String, String> params = new HashMap<String, String>();
		String requestURL = "http://www.fastvturesults.com/result/2KE11CV001/900060561";
		//String requestURL = "http://results.vtu.ac.in/vitavi.php?rid=2KE13CV034&submit=SUBMIT";
		//String requestURL = "https://accounts.google.com/ServiceLoginAuth";
		//params.put("Email", "patil.ishwargouda09@gmail.com");
		//params.put("Passwd", "patil.ishwar");
		//params.put("rid", "2KE13CV034");
		//params.put("submit", "SUBMIT");
		
		try {
			HttpUtility.sendPostRequest(requestURL, params);
			System.out.println("Sent request..");
			String[] response = HttpUtility.readMultipleLinesRespone();
			System.out.println("Got response..");
			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		HttpUtility.disconnect();*/
	}
}