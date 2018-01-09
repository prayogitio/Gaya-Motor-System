import java.io.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample {
	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet();
		System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPost();
	}

	private void sendGet() throws Exception {
		String url = "http://localhost:8000/tarikpembukuan";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		} 
		in.close();

		System.out.println(response.toString());
	}

	private void sendPost() throws Exception {
		String url = "http://localhost:8000/insertdatapembukuan";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		//add reuqest header
		con.setRequestMethod("POST");
		String urlParameters = "tanggal=2017-12-06&keterangan=belitahek&debit=1&kredit=2&saldo=3";
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Length", Integer.toString(urlParameters.length()));
		// Send post request
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(urlParameters.getBytes());
		wr.flush();
		wr.close();

        System.out.println("CIPAI");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		//print result
		System.out.println(response.toString());
	}
}